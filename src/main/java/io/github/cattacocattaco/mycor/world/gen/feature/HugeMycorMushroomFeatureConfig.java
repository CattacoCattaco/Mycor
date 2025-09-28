package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class HugeMycorMushroomFeatureConfig implements FeatureConfig {
    public static final Codec<HugeMycorMushroomFeatureConfig> CODEC = RecordCodecBuilder.create(
            (instance) ->
                    instance.group(
                            BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.capProvider),
                            BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.stemProvider),
                            Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.foliageRadius),
                            IntProvider.createValidatingCodec(1, 500).fieldOf("stem_height").orElse(UniformIntProvider.create(7, 15)).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.stemHeight),
                            Codec.INT.fieldOf("stem_width").orElse(1).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.stemWidth),
                            Codec.BOOL.fieldOf("turning").orElse(false).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.turning),
                            Codec.FLOAT.fieldOf("turn_chance").orElse(0.3f).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.turnChance),
                            Codec.INT.fieldOf("start_vertical_length").orElse(3).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.startVerticalLength),
                            Codec.INT.fieldOf("end_vertical_length").orElse(6).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.endVerticalLength),
                            Codec.INT.fieldOf("min_horizontal_turn_distance").orElse(6).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.minHorizontalTurnDistance),
                            Codec.INT.fieldOf("min_vertical_turn_distance").orElse(3).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.minVerticalTurnDistance),
                            Codec.BOOL.fieldOf("branching").orElse(false).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.branching),
                            Codec.FLOAT.fieldOf("branch_chance").orElse(0.3f).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.branchChance),
                            Codec.INT.fieldOf("max_branch_count").orElse(15).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.maxBranchCount),
                            Codec.INT.fieldOf("max_branch_depth").orElse(3).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.maxBranchDepth),
                            Codec.INT.fieldOf("max_branch_growth").orElse(5).forGetter((hugeMushroomFeatureConfig) -> hugeMushroomFeatureConfig.maxBranchGrowth)
                    ).apply(instance, HugeMycorMushroomFeatureConfig::new)
    );
    public final BlockStateProvider capProvider;
    public final BlockStateProvider stemProvider;
    public final int foliageRadius;
    public final IntProvider stemHeight;
    public final int stemWidth;
    public final boolean turning;
    public final float turnChance;
    public final int startVerticalLength;
    public final int endVerticalLength;
    public final int minHorizontalTurnDistance;
    public final int minVerticalTurnDistance;
    public final boolean branching;
    public final float branchChance;
    public final int maxBranchCount;
    public final int maxBranchDepth;
    public final int maxBranchGrowth;

    public HugeMycorMushroomFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius, IntProvider stemHeight, int stemWidth, boolean turning, float turnChance, int startVerticalLength, int endVerticalLength, int minHorizontalTurnDistance, int minVerticalTurnDistance, boolean branching, float branchChance, int maxBranchCount, int maxBranchDepth, int maxBranchGrowth) {
        this.capProvider = capProvider;
        this.stemProvider = stemProvider;
        this.foliageRadius = foliageRadius;
        this.stemHeight = stemHeight;
        this.stemWidth = stemWidth;
        this.turning = turning;
        this.turnChance = turnChance;
        this.startVerticalLength = startVerticalLength;
        this.endVerticalLength = endVerticalLength;
        this.minHorizontalTurnDistance = minHorizontalTurnDistance;
        this.minVerticalTurnDistance = minVerticalTurnDistance;
        this.branching = branching;
        this.branchChance = branchChance;
        this.maxBranchCount = maxBranchCount;
        this.maxBranchDepth = maxBranchDepth;
        this.maxBranchGrowth = maxBranchGrowth;
    }
}
