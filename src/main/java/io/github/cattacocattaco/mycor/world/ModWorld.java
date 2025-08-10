package io.github.cattacocattaco.mycor.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModWorld {
    public static final RegistryKey<World> MYCOR = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("mycor", "mycor"));
}
