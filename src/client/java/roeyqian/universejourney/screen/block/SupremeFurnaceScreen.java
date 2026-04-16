/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.screen.block;

// Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

// JetBrains Specify
import org.jspecify.annotations.NonNull;

// Universe Journey
import roeyqian.universejourney.menu.block.SupremeFurnaceMenu;
import roeyqian.universejourney.screen.recipe.SupremeCookingBookComponent;

public class SupremeFurnaceScreen extends AbstractRecipeBookScreen<SupremeFurnaceMenu> {

    private static final Identifier TEXTURE = Identifier.withDefaultNamespace(
            "textures/gui/container/furnace.png"
    );
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.withDefaultNamespace(
            "container/furnace/lit_progress"
    );
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.withDefaultNamespace(
            "container/furnace/burn_progress"
    );

    public SupremeFurnaceScreen(
            SupremeFurnaceMenu handler,
            Inventory inventory,
            Component title
    ) {
        super(handler, new SupremeCookingBookComponent(handler), inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override @NonNull
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 20, this.height / 2 - 49);
    }

    @Override
    public void extractBackground(
            @NonNull GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        int x = this.leftPos;
        int y = this.topPos;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, TEXTURE,
                x, y,
                0.0F, 0.0F,
                this.imageWidth, this.imageHeight,
                256, 256
        );

        if (this.menu.isBurning()) {
            int burnProgress = (int) (this.menu.getFuelProgress() * 13.0F) + 1;
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    LIT_PROGRESS_TEXTURE,
                    14, 14,
                    0, 14 - burnProgress,
                    x + 56, y + 36 + 14 - burnProgress,
                    14, burnProgress
            );
        }

        int cookProgress = (int) (this.menu.getCookProgress() * 24.0F);
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                BURN_PROGRESS_TEXTURE,
                24, 16,
                0, 0,
                x + 79, y + 34,
                cookProgress + 1, 16
        );
    }

}

