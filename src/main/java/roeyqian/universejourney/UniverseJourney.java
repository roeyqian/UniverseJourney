/*
 * Universe Journey - Copyright (C) 2026 Roey Qian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package roeyqian.universejourney;

// Fabric
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;

// Minecraft
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Universe Journey
import roeyqian.universejourney.utility.registry.block.RegActiveBlockEntities;
import roeyqian.universejourney.utility.registry.block.RegActiveBlocks;
import roeyqian.universejourney.utility.registry.block.RegInsertBlocks;
import roeyqian.universejourney.utility.registry.entity.RegLiveEntities;
import roeyqian.universejourney.utility.registry.gen.RegBiomeSources;
import roeyqian.universejourney.utility.registry.gen.RegComponentTypes;
import roeyqian.universejourney.utility.registry.gen.RegDimensions;
import roeyqian.universejourney.utility.registry.gen.RegParticles;
import roeyqian.universejourney.utility.registry.gen.RegRecipes;
import roeyqian.universejourney.utility.registry.item.RegConsumableItems;
import roeyqian.universejourney.utility.registry.item.RegDurableItems;
import roeyqian.universejourney.utility.registry.item.RegItemNetworks;
import roeyqian.universejourney.utility.registry.menu.RegBlockMenus;
import roeyqian.universejourney.utility.registry.menu.RegItemMenus;

public class UniverseJourney implements ModInitializer {

	public static final String MOD_ID = "universejourney";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ResourceKey<CreativeModeTab> SUPREME_GROUP_KEY = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, "supreme_group")
	);
	private static final ResourceKey<CreativeModeTab> UNIVERSE_GROUP_KEY = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, "universe_group")
	);

	@Override
	public void onInitialize() {
		RegConsumableItems.init();
		RegDurableItems.init();
		RegItemNetworks.init();

		RegInsertBlocks.init();
		RegActiveBlocks.init();
		RegActiveBlockEntities.init();

		RegLiveEntities.init();

		RegItemMenus.init();
		RegBlockMenus.init();

		RegRecipes.init();
		RegParticles.init();
		RegDimensions.init();
		RegBiomeSources.init();
		RegComponentTypes.init();

		registerSupremeItemTab();
		registerUniverseItemTab();
	}

	private void registerSupremeItemTab() {
		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB, SUPREME_GROUP_KEY, FabricCreativeModeTab
						.builder()
						.title(Component.translatable("group.universejourney.supreme_group"))
						.icon(() -> new ItemStack(RegConsumableItems.SUPREME_CORE))
						.displayItems((_, entries) -> {
							entries.accept(RegDurableItems.SUPREME_MOBILE);
							entries.accept(RegDurableItems.SUPREME_HELMET);
							entries.accept(RegDurableItems.SUPREME_CHESTPLATE);
							entries.accept(RegDurableItems.SUPREME_LEGGINGS);
							entries.accept(RegDurableItems.SUPREME_BOOTS);
							entries.accept(RegConsumableItems.SUPREME_CRYSTAL);
							entries.accept(RegConsumableItems.SUPREME_METAL);
							entries.accept(RegConsumableItems.SEED_OF_ALL_THINGS);
							entries.accept(RegConsumableItems.FOOD_OF_ALL_THINGS);
							entries.accept(RegConsumableItems.SUPREME_BANQUET);
							entries.accept(RegConsumableItems.SUPREME_CORE);
							entries.accept(RegConsumableItems.WITHER_SPAWN_EGG);
							entries.accept(RegConsumableItems.ENDER_DRAGON_SPAWN_EGG);
							entries.accept(RegConsumableItems.SKULK_BEHEMOTH_SPAWN_EGG);
							entries.accept(RegConsumableItems.THE_UNNAMEABLE_EGG);
							entries.accept(RegConsumableItems.BELL_RINGER_SPAWN_EGG);
							entries.accept(RegConsumableItems.BELL_SOUL_SPAWN_EGG);
							entries.accept(RegConsumableItems.PALE_LORD_SPAWN_EGG);
							entries.accept(RegConsumableItems.OBSIDIAN_GOLEM_SPAWN_EGG);
							entries.accept(RegConsumableItems.STRANGE_MATTER);
							entries.accept(RegConsumableItems.STRANGE_POTION);
							entries.accept(RegConsumableItems.STRANGE_SPLASH_POTION);
							entries.accept(RegConsumableItems.STRANGE_LINGERING_POTION);

							entries.accept(RegInsertBlocks.GOLDEN_SAPLING);
							entries.accept(RegInsertBlocks.GOLDEN_LOG);
							entries.accept(RegInsertBlocks.STRIPPED_GOLDEN_LOG);
							entries.accept(RegInsertBlocks.GOLDEN_WOOD);
							entries.accept(RegInsertBlocks.STRIPPED_GOLDEN_WOOD);
							entries.accept(RegInsertBlocks.GOLDEN_PLANKS);
							entries.accept(RegInsertBlocks.GOLDEN_LEAVES);
							entries.accept(RegInsertBlocks.EVER_WATER_GRASS_BLOCK);
							entries.accept(RegInsertBlocks.EVER_WATER_SOIL);
							entries.accept(RegInsertBlocks.EVER_WATER_FARMLAND);
							entries.accept(RegInsertBlocks.SUPREME_GEM_BLOCK);
							entries.accept(RegInsertBlocks.SUPREME_FODDER_BLOCK);
							entries.accept(RegActiveBlocks.SUPREME_WORKTABLE);
							entries.accept(RegActiveBlocks.SUPREME_FURNACE);
							entries.accept(RegActiveBlocks.SUPREME_RESERVER);
						})
						.build()
		);
	}

	private void registerUniverseItemTab() {
		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB, UNIVERSE_GROUP_KEY, FabricCreativeModeTab
						.builder()
						.title(Component.translatable("group.universejourney.universe_group"))
						.icon(() -> new ItemStack(RegConsumableItems.UNIVERSE_STAR))
						.displayItems((_, entries) -> {
							entries.accept(RegDurableItems.UNIVERSE_ULTIMA_SWORD);
							entries.accept(RegDurableItems.UNIVERSE_OMNI_BLADE);
							entries.accept(RegDurableItems.UNIVERSE_CONSOLE);
							entries.accept(RegDurableItems.UNIVERSE_HELMET);
							entries.accept(RegDurableItems.UNIVERSE_CHESTPLATE);
							entries.accept(RegDurableItems.UNIVERSE_LEGGINGS);
							entries.accept(RegDurableItems.UNIVERSE_BOOTS);
							entries.accept(RegConsumableItems.UNIVERSE_STAR);
							entries.accept(RegConsumableItems.UNIVERSE_STICK);
							entries.accept(RegConsumableItems.UNIVERSE_GUARDIAN_SPAWN_EGG);
							entries.accept(RegConsumableItems.UNIVERSE_GEMRED);
							entries.accept(RegConsumableItems.UNIVERSE_GEMBLUE);
							entries.accept(RegConsumableItems.UNIVERSE_GEMYELLOW);
							entries.accept(RegConsumableItems.UNIVERSE_GEMGREEN);
							entries.accept(RegConsumableItems.UNIVERSE_GEMBLACK);
							entries.accept(RegConsumableItems.UNIVERSE_GEMWHITE);
							entries.accept(RegConsumableItems.UNIVERSE_PRIMARY_FRAGMENT);

							entries.accept(RegInsertBlocks.UNIVERSE_PRIMARY_BLOCK);
							entries.accept(RegInsertBlocks.UNIVERSE_BLOCK);
							entries.accept(RegActiveBlocks.UNIVERSE_WORKSTATION);
							entries.accept(RegActiveBlocks.UNIVERSE_REFINERY);
							entries.accept(RegActiveBlocks.UNIVERSE_VOID_POOL);
							entries.accept(RegActiveBlocks.UNIVERSE_LIBRARY);
							entries.accept(Blocks.COMMAND_BLOCK);
							entries.accept(Blocks.STRUCTURE_BLOCK);
							entries.accept(Blocks.JIGSAW);
							entries.accept(Blocks.BARRIER);
						})
						.build()
		);
	}

}
