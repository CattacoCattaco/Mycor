package io.github.cattacocattaco.mycor.block;

import io.github.cattacocattaco.mycor.Mycor;
import io.github.cattacocattaco.mycor.world.gen.feature.ModFeatures;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static void initialize() {
        BlockEntityType.SIGN.addSupportedBlock(FUNGAL_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(FUNGAL_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(FUNGAL_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(FUNGAL_WALL_HANGING_SIGN);
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Block registerStairsBlock(String id, Block base) {
        return register(id, (settings) -> new StairsBlock(base.getDefaultState(), settings), AbstractBlock.Settings.copy(base), true);
    }

    private static AbstractBlock.Settings copyLootTable(Block block, boolean copyTranslationKey) {
        AbstractBlock.Settings settings = block.getSettings();
        AbstractBlock.Settings settings2 = AbstractBlock.Settings.create().lootTable(block.getLootTableKey());
        if (copyTranslationKey) {
            settings2 = settings2.overrideTranslationKey(block.getTranslationKey());
        }

        return settings2;
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mycor.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mycor.MOD_ID, name));
    }

    public static final Block GLOWSHROOM_BLOCK = register(
            "glowshroom_block",
            GlowshroomBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).sounds(BlockSoundGroup.WOOD).luminance(GlowshroomBlock::getLuminance).burnable(),
            true
    );

    public static final Block GLOWSHROOM = register(
            "glowshroom",
            (settings) -> new MushroomPlantBlock(ModFeatures.HUGE_GLOWSHROOM_KEY, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(Blocks::always).luminance((state) -> 6).pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static final Block JUMPSHROOM_BLOCK = register(
            "jumpshroom_block",
            JumpshroomBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.BASS).strength(0.2F).sounds(BlockSoundGroup.WOOD).jumpVelocityMultiplier(1.2f).burnable(),
            true
    );

    public static final Block JUMPSHROOM = register(
            "jumpshroom",
            (settings) -> new MushroomPlantBlock(ModFeatures.HUGE_JUMPSHROOM_KEY, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static final Block FUNGAL_PLANKS = register(
            "fungal_planks",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable(),
            true
    );

    public static final Block FUNGAL_SIGN = register(
            "fungal_sign",
            (settings) -> new SignBlock(ModWoodType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable(),
            false
    );

    public static final Block FUNGAL_WALL_SIGN = register(
            "fungal_wall_sign",
            (settings) -> new WallSignBlock(ModWoodType.FUNGAL, settings),
            copyLootTable(FUNGAL_SIGN, true).mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable(),
            false
    );

    public static final Block FUNGAL_HANGING_SIGN = register(
            "fungal_hanging_sign",
            (settings) -> new HangingSignBlock(ModWoodType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable(),
            false
    );

    public static final Block FUNGAL_WALL_HANGING_SIGN = register(
            "fungal_wall_hanging_sign",
            (settings) -> new WallHangingSignBlock(ModWoodType.FUNGAL, settings),
            copyLootTable(FUNGAL_HANGING_SIGN, true).mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable(),
            false
    );

    public static final Block FUNGAL_PRESSURE_PLATE = register(
            "fungal_pressure_plate",
            (settings) -> new PressurePlateBlock(ModBlockSetType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).solid().instrument(NoteBlockInstrument.BASS).strength(0.5F).sounds(BlockSoundGroup.WOOD).noCollision().pistonBehavior(PistonBehavior.DESTROY).burnable(),
            true
    );

    public static final Block FUNGAL_TRAPDOOR = register(
            "fungal_trapdoor",
            (settings) -> new TrapdoorBlock(ModBlockSetType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).nonOpaque().instrument(NoteBlockInstrument.BASS).strength(3.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning(Blocks::never).burnable(),
            true
    );

    public static final Block FUNGAL_BUTTON = register(
            "fungal_button",
            (settings) -> new ButtonBlock(ModBlockSetType.FUNGAL, 30, settings),
            AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static final Block FUNGAL_STAIRS = registerStairsBlock(
            "fungal_stairs",
            FUNGAL_PLANKS
    );

    public static final Block FUNGAL_SLAB = register(
            "fungal_slab",
            SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable(),
            true
    );

    public static final Block FUNGAL_FENCE_GATE = register(
            "fungal_fence_gate",
            (settings) -> new FenceGateBlock(ModWoodType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable(),
            true
    );

    public static final Block FUNGAL_FENCE = register(
            "fungal_fence",
            FenceBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable(),
            true
    );

    public static final Block FUNGAL_DOOR = register(
            "fungal_door",
            (settings) -> new DoorBlock(ModBlockSetType.FUNGAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).instrument(NoteBlockInstrument.BASS).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().burnable(),
            true
    );
}
