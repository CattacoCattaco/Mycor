package io.github.cattacocattaco.mycor.world.gen.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.feature.HugeRedMushroomFeature;

public class ModFeaturesRegisterer {
    public static final Feature<HugeMycorMushroomFeatureConfig> HUGE_GLOWSHROOM;

    public static void initialize() {}

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F)(Registry.register(Registries.FEATURE, "mycor:" + name, feature));
    }

    static {
        HUGE_GLOWSHROOM = register("huge_glowshroom", new HugeGlowshroomFeature(HugeMycorMushroomFeatureConfig.CODEC));
    }
}
