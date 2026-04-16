/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.item;

// Minecraft
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.Tool;

// Java Standard
import java.util.ArrayList;
import java.util.List;

public interface CustomItemSettings {

    static Item.Properties applySupremeDefaults(
            Item.Properties settings
    ) {
        return settings.rarity(Rarity.RARE);
    }

    static Item.Properties applyUniverseDefaults(
            Item.Properties settings
    ) {
        return settings
                .rarity(Rarity.EPIC)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }

    static ItemLore supremeLore(
            String itemId,
            int lineCount
    ) {
        List<Component> lines = new ArrayList<>();
        lines.add(
                Component.translatable("item.universejourney." + itemId + ".lore_main")
                        .withStyle(ChatFormatting.DARK_GREEN)
        );
        for (int i = 1; i <= lineCount; i++) {
            lines.add(
                    Component.translatable("item.universejourney." + itemId + ".lore" + i)
                            .withStyle(ChatFormatting.GREEN)
            );
        }
        return new ItemLore(lines);
    }

    static ItemLore universeLore(
            String itemId,
            int lineCount
    ) {
        List<Component> lines = new ArrayList<>();
        lines.add(
                Component.translatable("item.universejourney." + itemId + ".lore_main")
                        .withStyle(ChatFormatting.DARK_RED)
        );
        for (int i = 1; i <= lineCount; i++) {
            lines.add(
                    Component.translatable("item.universejourney." + itemId + ".lore" + i)
                            .withStyle(ChatFormatting.RED)
            );
        }
        return new ItemLore(lines);
    }

    static Tool createTool(
            float f,
            int i,
            boolean bl
    ) {
        return new Tool(List.of(), f, i, bl);
    }

}
