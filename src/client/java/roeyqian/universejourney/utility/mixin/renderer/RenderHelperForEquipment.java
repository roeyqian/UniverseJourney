/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.utility.mixin.renderer;

// Minecraft
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;

// JOML
import org.joml.Vector4f;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Java Standard
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

// Universe Journey
import roeyqian.universejourney.UniverseJourney;
import roeyqian.universejourney.mixin.render.ItemStackRenderStateAccessor;
import roeyqian.universejourney.mixin.render.LayerRenderStateAccessor;
import roeyqian.universejourney.render.layer.UniverseEquipmentRenderLayers;
import roeyqian.universejourney.utility.registry.item.RegConsumableItems;
import roeyqian.universejourney.utility.registry.item.RegDurableItems;

public final class RenderHelperForEquipment {
    private static final Map<Object, Object> oldBindings = Collections.synchronizedMap(new WeakHashMap<>());
    private static Field texturesField;
    private static Field samplerField;
    private static Constructor<?> bindingCtor;

    private RenderHelperForEquipment() {}

    public static void handleUniverseHelmetVision(
            Camera camera,
            CallbackInfoReturnable<FogData> cir
    ) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.getItemBySlot(EquipmentSlot.HEAD).is(RegDurableItems.UNIVERSE_HELMET)) return;

        FogType type = camera.getFluidInCamera();
        FogData fogData = cir.getReturnValue();
        if (fogData == null) {
            return;
        }

        Vector4f color = fogData.color;
        if (type == FogType.LAVA) {
            color.set(color.x, color.y, color.z, 0.75f);
            fogData.environmentalStart = Math.max(fogData.environmentalStart, 0.0f);
            fogData.environmentalEnd = Math.max(fogData.environmentalEnd, 8.0f);
        } else if (type == FogType.WATER) {
            color.set(color.x, color.y, color.z, 0.5f);
            fogData.environmentalStart = Math.max(fogData.environmentalStart, 0.0f);
            fogData.environmentalEnd = Math.max(fogData.environmentalEnd, 96.0f);
        }
    }

    public static void handleRenderLayers(
            ItemStack itemStack
    ) {
        UniverseEquipmentRenderLayers.useRainbowGlint = itemStack.is(RegDurableItems.UNIVERSE_HELMET)
                || itemStack.is(RegDurableItems.UNIVERSE_CHESTPLATE)
                || itemStack.is(RegDurableItems.UNIVERSE_LEGGINGS)
                || itemStack.is(RegDurableItems.UNIVERSE_BOOTS);
    }

    public static void handleRenderItemHead(
            SubmitNodeStorage.ItemSubmit submit
    ) {
        if (submit.foilType() == ItemStackRenderState.FoilType.SPECIAL) {
            UniverseEquipmentRenderLayers.useRainbowGlint = true;
        }
    }

    @SuppressWarnings("unchecked")
    public static void handleBeforeDraw(
            Object owner,
            String name,
            RenderSetup state
    ) {
        if (!UniverseEquipmentRenderLayers.useRainbowGlint || !name.contains("glint")) {
            return;
        }

        try {
            if (texturesField == null) {
                texturesField = RenderSetup.class.getDeclaredField("textures");
                texturesField.setAccessible(true);
            }

            Map<String, Object> textures = (Map<String, Object>) texturesField.get(state);
            Object oldBinding = textures.get("Sampler0");
            if (oldBinding == null) {
                return;
            }

            if (samplerField == null) {
                samplerField = oldBinding.getClass().getDeclaredField("sampler");
                samplerField.setAccessible(true);
            }

            if (bindingCtor == null) {
                bindingCtor = oldBinding.getClass().getDeclaredConstructors()[0];
                bindingCtor.setAccessible(true);
            }

            Object sampler = samplerField.get(oldBinding);
            Object newBinding = bindingCtor.newInstance(UniverseEquipmentRenderLayers.RAINBOW_TEXTURE_ID, sampler);

            textures.put("Sampler0", newBinding);
            oldBindings.put(owner, oldBinding);
        } catch (Exception e) {
            UniverseJourney.LOGGER.error("RenderTypeDrawUtility.handleBeforeDraw", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void handleAfterDraw(
            Object owner,
            RenderSetup state
    ) {
        Object oldBinding = oldBindings.remove(owner);
        if (oldBinding == null) return;

        try {
            if (texturesField == null) {
                texturesField = RenderSetup.class.getDeclaredField("textures");
                texturesField.setAccessible(true);
            }
            Map<String, Object> textures = (Map<String, Object>) texturesField.get(state);
            textures.put("Sampler0", oldBinding);
        } catch (Exception e) {
            UniverseJourney.LOGGER.error("RenderTypeDrawUtility.handleAfterDraw", e);
        }
    }

    public static void handleDrawToSlotHead(ItemStackRenderState item) {
        ItemStackRenderStateAccessor acc = (ItemStackRenderStateAccessor) item;
        int count = acc.getActiveLayerCount();
        ItemStackRenderState.LayerRenderState[] layers = acc.getLayers();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (((LayerRenderStateAccessor) layers[i]).getFoilType() == ItemStackRenderState.FoilType.SPECIAL) {
                found = true;
                break;
            }
        }
        UniverseEquipmentRenderLayers.useRainbowGlint = found;
    }

    public static void handleDrawToSlotTail() {
        UniverseEquipmentRenderLayers.useRainbowGlint = false;
    }

    public static void handleAppendItemLayers(ItemStackRenderState output, ItemStack item) {
        boolean isUniverseItem = item.is(RegConsumableItems.UNIVERSE_STAR)
                || item.is(RegConsumableItems.UNIVERSE_STICK)
                || item.is(RegDurableItems.UNIVERSE_ULTIMA_SWORD)
                || item.is(RegDurableItems.UNIVERSE_OMNI_BLADE)
                || item.is(RegDurableItems.UNIVERSE_CONSOLE)
                || item.is(RegDurableItems.UNIVERSE_HELMET)
                || item.is(RegDurableItems.UNIVERSE_CHESTPLATE)
                || item.is(RegDurableItems.UNIVERSE_LEGGINGS)
                || item.is(RegDurableItems.UNIVERSE_BOOTS)
                || item.is(RegConsumableItems.UNIVERSE_GUARDIAN_SPAWN_EGG);

        if (!isUniverseItem) {
            return;
        }

        ItemStackRenderStateAccessor stateAcc = (ItemStackRenderStateAccessor) output;
        int count = stateAcc.getActiveLayerCount();
        ItemStackRenderState.LayerRenderState[] layers = stateAcc.getLayers();

        for (int i = 0; i < count; i++) {
            layers[i].setFoilType(ItemStackRenderState.FoilType.SPECIAL);
        }
    }

}
