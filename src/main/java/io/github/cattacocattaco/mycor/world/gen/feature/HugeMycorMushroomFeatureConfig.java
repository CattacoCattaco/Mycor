package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class HugeMycorMushroomFeatureConfig implements FeatureConfig {
    public static final Codec<HugeMycorMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.capProvider), BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.stemProvider), Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.foliageRadius), Codec.INT.fieldOf("min_stem_height").orElse(3).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.minStemHeight), Codec.INT.fieldOf("max_stem_height").orElse(7).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.maxStemHeight)).apply(instance, HugeMycorMushroomFeatureConfig::new));
    public final BlockStateProvider capProvider;
    public final BlockStateProvider stemProvider;
    public final int foliageRadius;
    public final int minStemHeight;
    public final int maxStemHeight;

    public HugeMycorMushroomFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius, int minStemHeight, int maxStemHeight) {
        this.capProvider = capProvider;
        this.stemProvider = stemProvider;
        this.foliageRadius = foliageRadius;
        this.minStemHeight = minStemHeight;
        this.maxStemHeight = maxStemHeight;
    }
}
