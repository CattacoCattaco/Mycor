package io.github.cattacocattaco.mycor;

import io.github.cattacocattaco.mycor.block.ModBlocks;
import io.github.cattacocattaco.mycor.item.ModItems;

import io.github.cattacocattaco.mycor.world.gen.feature.ModFeatures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.cattacocattaco.mycor.item.ModItems.CUSTOM_ITEM_GROUP;
import static io.github.cattacocattaco.mycor.item.ModItems.CUSTOM_ITEM_GROUP_KEY;

public class Mycor implements ModInitializer {
	public static final String MOD_ID = "mycor";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ModFeatures.initialize();

		ModItems.initialize();
		ModBlocks.initialize();

		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.MUSHROOM_STEM)
				.lightWithItem(ModItems.MYCOR_KEY)
				.destDimID(Identifier.of("mycor", "mycor"))
				.tintColor(149,85,201)
				.registerPortal();

		// Register the group.
		Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

		// Register items to the custom item group.
		ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
			itemGroup.add(ModItems.GLOWSHROOM_SPORES);
			itemGroup.add(ModBlocks.GLOWSHROOM_BLOCK.asItem());
			itemGroup.add(ModBlocks.GLOWSHROOM.asItem());
			itemGroup.add(ModBlocks.JUMPSHROOM_BLOCK.asItem());
			itemGroup.add(ModBlocks.JUMPSHROOM.asItem());
			itemGroup.add(ModItems.MYCOR_KEY);
		});

		// Add the glowshroom spores to the composting registry with a 30% chance of increasing the composter's level.
		CompostingChanceRegistry.INSTANCE.add(ModItems.GLOWSHROOM_SPORES, 0.3f);
	}
}