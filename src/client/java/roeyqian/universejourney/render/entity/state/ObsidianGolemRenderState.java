/*
 * SPDX-License-Identifier: GPL-3.0-or-later
 * Copyright (C) 2026 Roey Qian
 *
 * This file is part of Universe Mod.
 * Full license text available in the LICENSE file in the project root.
 */
package roeyqian.universejourney.render.entity.state;

// Fabric
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// Minecraft
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Crackiness;

@Environment(EnvType.CLIENT)
public class ObsidianGolemRenderState extends LivingEntityRenderState {

    public float attackTicksRemaining;
    public int offerFlowerTick;
    public final BlockModelRenderState flowerBlock = new BlockModelRenderState();
    public Crackiness.Level crackiness;

    public ObsidianGolemRenderState() {
        this.crackiness = Crackiness.Level.NONE;
    }

}

