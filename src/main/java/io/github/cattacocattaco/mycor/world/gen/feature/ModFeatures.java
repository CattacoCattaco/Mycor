package io.github.cattacocattaco.mycor.world.gen.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ModFeatures {
    public static final Feature<HugeMycorMushroomFeatureConfig> HUGE_GLOWSHROOM;
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_GLOWSHROOM_KEY;
    public static final Feature<HugeMycorMushroomFeatureConfig> HUGE_JUMPSHROOM;
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_JUMPSHROOM_KEY;

    public static void initialize() {}

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F)(Registry.register(Registries.FEATURE, "mycor:" + name, feature));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of("mycor", id));
    }

    static {
        HUGE_GLOWSHROOM = register("huge_glowshroom", new HugeGlowshroomFeature(HugeMycorMushroomFeatureConfig.CODEC));
        HUGE_GLOWSHROOM_KEY = keyOf("huge_glowshroom");
        HUGE_JUMPSHROOM = register("huge_jumpshroom", new HugeJumpshroomFeature(HugeMycorMushroomFeatureConfig.CODEC));
        HUGE_JUMPSHROOM_KEY = keyOf("huge_jumpshroom");
    }
}
