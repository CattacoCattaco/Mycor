package io.github.cattacocattaco.mycor.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class ModWoodType {
    public static final WoodType FUNGAL;

    static {
        FUNGAL = WoodTypeBuilder.copyOf(WoodType.OAK).register(Identifier.of("mycor", "fungal"), ModBlockSetType.FUNGAL);
    }
}
