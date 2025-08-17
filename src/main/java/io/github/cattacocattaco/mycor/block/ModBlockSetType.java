package io.github.cattacocattaco.mycor.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.util.Identifier;

public class ModBlockSetType {
    public static final BlockSetType FUNGAL;
    static {
        FUNGAL = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(Identifier.of("mycor", "fungal"));
    }
}
