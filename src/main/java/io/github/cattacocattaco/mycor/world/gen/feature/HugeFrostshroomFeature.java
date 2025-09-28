package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class HugeFrostshroomFeature extends HugeMycorMushroomFeature {
    public HugeFrostshroomFeature(Codec<HugeMycorMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void generateCap(WorldAccess world, Random random, BlockPos start, int offsetY, BlockPos.Mutable mutable, HugeMycorMushroomFeatureConfig config) {
        for(int layer = 0; layer <= config.foliageRadius; ++layer) {
            int layerStartY = offsetY + 3 * layer;
            for (int y = layerStartY; y < layerStartY + 3; ++y) {
                int edgePos = config.foliageRadius - layer;
                int exposureBound = edgePos - 1;

                for (int x = -edgePos; x <= edgePos + 1; ++x) {
                    for (int z = -edgePos; z <= edgePos + 1; ++z) {
                        boolean onWestEdge = x == -edgePos;
                        boolean onEastEdge = x == edgePos + 1;
                        boolean onNorthEdge = z == -edgePos;
                        boolean onSouthEdge = z == edgePos + 1;
                        boolean onWestEastEdge = onWestEdge || onEastEdge;
                        boolean onNorthSouthEdge = onNorthEdge || onSouthEdge;
                        boolean corner = onWestEastEdge && onNorthSouthEdge;
                        if (layer == config.foliageRadius || !corner) {
                            // Sets mutable pos to start + (x, y, z)
                            mutable.set(start, x, y, z);

                            BlockState blockState = config.capProvider.get(random, start);
                            if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                                boolean onWestExposureLine = x == -exposureBound;
                                boolean onEastExposureLine = x == exposureBound + 1;
                                boolean onNorthExposureLine = z == -exposureBound;
                                boolean onSouthExposureLine = z == exposureBound + 1;
                                boolean onWestEastExposureLine = onWestExposureLine || onEastExposureLine;
                                boolean onNorthSouthExposureLine = onNorthExposureLine || onSouthExposureLine;
                                boolean topExposed = y == layerStartY + 2 && (onNorthSouthEdge || onWestEastEdge || (onWestEastExposureLine && onNorthSouthExposureLine));
                                boolean westExposed = onWestEdge || (onNorthSouthEdge && onWestExposureLine);
                                boolean eastExposed = onEastEdge || (onNorthSouthEdge && onEastExposureLine);
                                boolean northExposed = onNorthEdge || (onWestEastEdge && onNorthExposureLine);
                                boolean southExposed = onSouthEdge || (onWestEastEdge && onSouthExposureLine);
                                blockState = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(MushroomBlock.UP, topExposed)).with(MushroomBlock.WEST, westExposed)).with(MushroomBlock.EAST, eastExposed)).with(MushroomBlock.NORTH, northExposed)).with(MushroomBlock.SOUTH, southExposed);
                            }

                            this.placeBlock(world, mutable, blockState);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected int getCapSize(int i, int j, int capSize, int y) {
        int k = 0;
        if (y < j && y >= j - 5) {
            k = capSize;
        } else if (y == j) {
            k = capSize;
        }

        return k;
    }
}