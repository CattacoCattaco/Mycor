package io.github.cattacocattaco.mycor.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import io.github.cattacocattaco.mycor.Mycor;
import io.github.cattacocattaco.mycor.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static void initialize() {
        // Add the glowshroom spores to the composting registry with a 30% chance of increasing the composter's level.
        CompostingChanceRegistry.INSTANCE.add(ModItems.GLOWSHROOM_SPORES, 0.3f);
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        Object FabricDocsReference;
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Mycor.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Mycor.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.GLOWSHROOM_SPORES))
            .displayName(Text.translatable("itemGroup.mycor"))
            .build();

    public static final Item GLOWSHROOM_SPORES = register(
            "glowshroom_spores",
            Item::new,
            new Item.Settings()
    );

    public static final Item FUNGAL_SIGN = register(
            "fungal_sign",
            (settings) -> new SignItem(ModBlocks.FUNGAL_SIGN, ModBlocks.FUNGAL_WALL_SIGN, settings),
            new Item.Settings().maxCount(16)
    );

    public static final Item FUNGAL_HANGING_SIGN = register(
            "fungal_hanging_sign",
            (settings) -> new HangingSignItem(ModBlocks.FUNGAL_HANGING_SIGN, ModBlocks.FUNGAL_WALL_HANGING_SIGN, settings),
            new Item.Settings().maxCount(16)
    );

    public static final Item FUNGAL_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Identifier.of("mycor", "fungal"),
            false
    );

    public static final Item FUNGAL_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(
            Identifier.of("mycor", "fungal"),
            true
    );

    public static final Item MYCOR_KEY = register(
            "mycor_key",
            Item::new,
            new Item.Settings()
    );
}
