/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.mixin.screen;

// Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;

// Sponge Powered Mixin
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Java Standard
import java.util.List;

// Universe Journey
import roeyqian.universejourney.utility.mixin.screen.ScreenHelperForRecipe;

@Mixin(value = RecipeBookComponent.class, priority = 240000)
public class RecipeBookComponentMixin {

    @Shadow
    private boolean visible;
    @Shadow
    private int xOffset;
    @Shadow
    private EditBox searchBox;
    @Shadow
    protected CycleButton<Boolean> filterButton;
    @Shadow @Final
    private RecipeBookPage recipeBookPage;
    @Shadow @Final
    private List<RecipeBookTabButton> tabButtons;
    @Shadow
    private int width;
    @Shadow
    private int height;

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void general$beforeExtractRenderState(
            GuiGraphicsExtractor context,
            int mouseX,
            int mouseY,
            float delta,
            CallbackInfo ci
    ) {
        ScreenHelperForRecipe.handleExtractRenderState(
                (RecipeBookComponent<?>) (Object) this,
                this.visible,
                this.xOffset,
                this.searchBox,
                this.filterButton,
                this.recipeBookPage,
                this.tabButtons,
                this.width, this.height,
                context,
                mouseX, mouseY,
                delta, ci
        );
    }

}