package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class HugeGlowshroomFeature extends HugeMycorMushroomFeature {
    public HugeGlowshroomFeature(Codec<HugeMycorMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected void generateCap(WorldAccess world, Random random, BlockPos start, int offsetY, BlockPos.Mutable mutable, HugeMycorMushroomFeatureConfig config) {
        for(int y = offsetY - (config.foliageRadius * 2 - 1); y <= offsetY; ++y) {
            int edgePos = y < offsetY ? config.foliageRadius : config.foliageRadius - 1;
            int exposureBound = config.foliageRadius - 2;

            for(int x = -edgePos; x <= edgePos; ++x) {
                for(int z = -edgePos; z <= edgePos; ++z) {
                    boolean onWestEdge = x == -edgePos;
                    boolean onEastEdge = x == edgePos;
                    boolean onNorthEdge = z == -edgePos;
                    boolean onSouthEdge = z == edgePos;
                    boolean onXEdge = onWestEdge || onEastEdge;
                    boolean onYEdge = onNorthEdge || onSouthEdge;
                    boolean nonCornerEdge = onXEdge != onYEdge;
                    if (y >= offsetY || nonCornerEdge) {
                        // Sets mutable pos to start + (x, y, z)
                        mutable.set(start, x, y, z);

                        BlockState blockState = config.capProvider.get(random, start);
                        if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                            boolean westExposed = x < -exposureBound;
                            boolean eastExposed = x > exposureBound;
                            boolean northExposed = z < -exposureBound;
                            boolean southExposed = z > exposureBound;
                            blockState = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(MushroomBlock.UP, y >= offsetY - 1)).with(MushroomBlock.WEST, westExposed)).with(MushroomBlock.EAST, eastExposed)).with(MushroomBlock.NORTH, northExposed)).with(MushroomBlock.SOUTH, southExposed);
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