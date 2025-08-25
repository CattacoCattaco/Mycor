package io.github.cattacocattaco.mycor.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class FrostshroomBlock extends MushroomBlock {
    public static final int FREEZE_RADIUS = 3;
    public static final float FREEZE_CHANCE = 0.2F;

    public FrostshroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        tryFreezeRange(world, pos, random);
        super.randomTick(state, world, pos, random);
    }

    protected void tryFreezeRange(ServerWorld world, BlockPos pos, Random random) {
        for(int x = pos.getX() - FREEZE_RADIUS; x <= pos.getX() + FREEZE_RADIUS; ++x) {
            for(int y = pos.getY() - FREEZE_RADIUS; y <= pos.getY() + FREEZE_RADIUS; ++y) {
                for(int z = pos.getZ() - FREEZE_RADIUS; z <= pos.getZ() + FREEZE_RADIUS; ++z) {
                    BlockPos currentPos = new BlockPos(x, y, z);

                    if(x != pos.getX() || y != pos.getY() || z != pos.getZ()) {
                        if(tryFreezeBlock(world, currentPos, random)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    protected boolean tryFreezeBlock(ServerWorld world, BlockPos currentPos, Random random) {
        FluidState fluidState = world.getFluidState(currentPos);

        if(!fluidState.isIn(FluidTags.WATER)) {
            return false;
        }

        BlockState blockState = world.getBlockState(currentPos);

        if(blockState.getBlock() instanceof FluidBlock && random.nextFloat() < FREEZE_CHANCE) {
            world.setBlockState(currentPos, Blocks.ICE.getDefaultState());
            return true;
        }

        return false;
    }
}
