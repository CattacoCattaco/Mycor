package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class HugeSwiftshroomFeature extends HugeMycorMushroomFeature {
    public HugeSwiftshroomFeature(Codec<HugeMycorMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void generateCap(WorldAccess world, Random random, BlockPos start, int offsetY, BlockPos.Mutable mutable, HugeMycorMushroomFeatureConfig config) {
        for(int y = offsetY + 1; y >= offsetY - 1; --y) {
            int indentation = Math.abs(y - offsetY);
            int edgePos = config.foliageRadius - indentation;
            int exposureBound = edgePos - 1;

            for (int x = -edgePos; x <= edgePos; ++x) {
                for (int z = -edgePos; z <= edgePos; ++z) {
                    boolean onWestEdge = x == -edgePos;
                    boolean onEastEdge = x == edgePos;
                    boolean onNorthEdge = z == -edgePos;
                    boolean onSouthEdge = z == edgePos;
                    boolean onWestEastEdge = onWestEdge || onEastEdge;
                    boolean onNorthSouthEdge = onNorthEdge || onSouthEdge;
                    boolean corner = onWestEastEdge && onNorthSouthEdge;
                    if (!corner) {
                        // Sets mutable pos to start + (x, y, z)
                        mutable.set(start, x, y, z);

                        BlockState blockState = config.capProvider.get(random, start);
                        if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                            boolean onWestExposureLine = x == -exposureBound;
                            boolean onEastExposureLine = x == exposureBound;
                            boolean onNorthExposureLine = z == -exposureBound;
                            boolean onSouthExposureLine = z == exposureBound;
                            boolean onWestEastExposureLine = onWestExposureLine || onEastExposureLine;
                            boolean onNorthSouthExposureLine = onNorthExposureLine || onSouthExposureLine;
                            boolean topExposed = y == offsetY + 1 || (y >= offsetY  && (onNorthSouthEdge || onWestEastEdge || (onWestEastExposureLine && onNorthSouthExposureLine)));
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