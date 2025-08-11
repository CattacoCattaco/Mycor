package io.github.cattacocattaco.mycor.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.cattacocattaco.mycor.Mycor;
import io.github.cattacocattaco.mycor.misc.MutableInt;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Vector;

public abstract class HugeMycorMushroomFeature extends Feature<HugeMycorMushroomFeatureConfig> {
    public HugeMycorMushroomFeature(Codec<HugeMycorMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected void generateStem(WorldAccess world, Random random, BlockPos pos, HugeMycorMushroomFeatureConfig config, int height, BlockPos.Mutable mutablePos, Vector<BlockPos.Mutable> endPositions) {
        generateStem(world, random, pos, config, height, mutablePos, endPositions, 0, 0, new MutableInt(0), 0, Direction.UP, 100);
    }

    protected void generateStem(WorldAccess world, Random random, BlockPos pos, HugeMycorMushroomFeatureConfig config, int height, BlockPos.Mutable mutablePos, Vector<BlockPos.Mutable> endPositions, int depth, int currentHeight, MutableInt branchCount, int branchDepth, Direction from, int stepsSinceTurn) {
        int newCurrentHeight = currentHeight;
        if(from == Direction.UP) {
            newCurrentHeight++;
        }

        int newBranchDepth = branchDepth;

        Direction newDirection = from;

        int newStepsSinceTurn = stepsSinceTurn + 1;

        this.placeBlock(world, mutablePos, config.stemProvider.get(random, pos));

        if((depth < height || currentHeight < config.minStemHeight) && currentHeight < config.maxStemHeight) {
            if(depth >= height - config.endVerticalLength) {
                if(stepsSinceTurn < config.minHorizontalTurnDistance && from != Direction.UP) {
                    if(world.getBlockState((new BlockPos.Mutable()).set(mutablePos.toImmutable()).move(from)).isAir()) {
                        mutablePos.move(newDirection);
                    }
                    else {
                        newDirection = getRandomEmptyDirection(world, random, mutablePos, Direction.UP);
                        mutablePos.move(newDirection);
                    }

                    generateStem(world, random, pos, config, height, mutablePos, endPositions, depth, newCurrentHeight, branchCount, newBranchDepth, newDirection, newStepsSinceTurn);
                }

                newDirection = Direction.UP;
                mutablePos.move(newDirection);
                newCurrentHeight++;
            }
            else if(depth > config.startVerticalLength && config.branching && branchCount.getValue() < config.maxBranchCount && branchDepth < config.maxBranchDepth && (stepsSinceTurn > config.minHorizontalTurnDistance || (stepsSinceTurn > config.minVerticalTurnDistance && from == Direction.UP)) && random.nextFloat() < config.branchChance) {
                branchCount.increment();
                newBranchDepth++;

                BlockPos.Mutable branchPos = new BlockPos.Mutable();
                branchPos.set(new BlockPos(mutablePos.getX(), mutablePos.getY(), mutablePos.getZ()));
                Direction branchDirection = getRandomEmptyDirection(world, random, mutablePos, from);
                branchPos.move(branchDirection);

                newStepsSinceTurn = 0;

                generateStem(world, random, pos, config, height + random.nextInt(config.maxBranchGrowth), branchPos, endPositions, depth + 1, newCurrentHeight, branchCount, newBranchDepth, branchDirection, newStepsSinceTurn);

                newDirection = getRandomEmptyDirection(world, random, mutablePos, Direction.DOWN);
                mutablePos.move(newDirection);
            }
            else if(depth > config.startVerticalLength && config.turning && (stepsSinceTurn > config.minHorizontalTurnDistance || (stepsSinceTurn > config.minVerticalTurnDistance && from == Direction.UP)) && random.nextFloat() < config.turnChance) {
                newDirection = getRandomEmptyDirection(world, random, mutablePos, from);
                mutablePos.move(newDirection);

                newStepsSinceTurn = 0;
            }
            else {
                if(world.getBlockState((new BlockPos.Mutable()).set(mutablePos.toImmutable()).move(from)).isAir()) {
                    mutablePos.move(newDirection);
                }
                else {
                    newDirection = getRandomEmptyDirection(world, random, mutablePos, from);
                    mutablePos.move(newDirection);
                }
            }

            BlockState blockState = world.getBlockState(mutablePos);
            if(blockState.isAir() || blockState.isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                generateStem(world, random, pos, config, height, mutablePos, endPositions, depth + 1, newCurrentHeight, branchCount, newBranchDepth, newDirection, newStepsSinceTurn);
            }
        }
        else {
            endPositions.add(mutablePos);
        }
    }

    protected void placeBlock(WorldAccess world, BlockPos.Mutable pos, BlockState state) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isAir() || blockState.isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
            this.setBlockState(world, pos, state);
        }

    }

    protected Direction getRandomEmptyDirection(WorldAccess world, Random random, BlockPos.Mutable mutablePos, Direction avoid) {
        Vector<Direction> emptyDirections = new Vector<>();

        if(avoid != Direction.UP && world.getBlockState(mutablePos.up()).isAir()) {
            emptyDirections.add(Direction.UP);
        }
        if(avoid != Direction.EAST && world.getBlockState(mutablePos.east()).isAir()) {
            emptyDirections.add(Direction.EAST);
        }
        if(avoid != Direction.WEST && world.getBlockState(mutablePos.west()).isAir()) {
            emptyDirections.add(Direction.WEST);
        }
        if(avoid != Direction.NORTH && world.getBlockState(mutablePos.north()).isAir()) {
            emptyDirections.add(Direction.NORTH);
        }
        if(avoid != Direction.SOUTH && world.getBlockState(mutablePos.south()).isAir()) {
            emptyDirections.add(Direction.SOUTH);
        }

        if(!emptyDirections.isEmpty()) {
            int directionIndex = random.nextInt(emptyDirections.size());

            return emptyDirections.get(directionIndex);
        }
        else {
            Vector<Direction> replaceableDirections = new Vector<>();

            if(avoid != Direction.UP && world.getBlockState(mutablePos.up()).isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                replaceableDirections.add(Direction.UP);
            }
            if(avoid != Direction.EAST && world.getBlockState(mutablePos.east()).isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                replaceableDirections.add(Direction.EAST);
            }
            if(avoid != Direction.WEST && world.getBlockState(mutablePos.west()).isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                replaceableDirections.add(Direction.WEST);
            }
            if(avoid != Direction.NORTH && world.getBlockState(mutablePos.north()).isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                replaceableDirections.add(Direction.NORTH);
            }
            if(avoid != Direction.SOUTH && world.getBlockState(mutablePos.south()).isIn(BlockTags.REPLACEABLE_BY_MUSHROOMS)) {
                replaceableDirections.add(Direction.SOUTH);
            }

            if(!replaceableDirections.isEmpty()) {
                int directionIndex = random.nextInt(replaceableDirections.size());

                return replaceableDirections.get(directionIndex);
            }
            else {
                return avoid;
            }
        }
    }

    protected int getHeight(Random random, HugeMycorMushroomFeatureConfig config) {
        int i = random.nextInt(config.maxStemHeight - config.minStemHeight) + config.minStemHeight;
        if (random.nextInt(120) == 0) {
            i *= 2;
        }

        return i;
    }

    protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutablePos, HugeMycorMushroomFeatureConfig config) {
        int i = pos.getY();
        if (i >= world.getBottomY() + 1 && i + height + 1 <= world.getTopYInclusive()) {
            BlockState blockState = world.getBlockState(pos.down());
            if (!isSoil(blockState) && !blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
                return false;
            }
            else {
                for (int j = 0; j <= height; ++j) {
                    int k = this.getCapSize(-1, -1, config.foliageRadius, j);

                    for (int l = -k; l <= k; ++l) {
                        for (int m = -k; m <= k; ++m) {
                            BlockState blockState2 = world.getBlockState(mutablePos.set(pos, l, j, m));
                            if (!blockState2.isAir() && !blockState2.isIn(BlockTags.LEAVES)) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public boolean generate(FeatureContext<HugeMycorMushroomFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        HugeMycorMushroomFeatureConfig hugeMycorMushroomFeatureConfig = context.getConfig();
        int i = this.getHeight(random, hugeMycorMushroomFeatureConfig);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        if (!this.canGenerate(structureWorldAccess, blockPos, i, mutable, hugeMycorMushroomFeatureConfig)) {
            return false;
        }
        else {
            mutable.set(blockPos);
            Vector<BlockPos.Mutable> endPositions = new Vector<>();
            this.generateStem(structureWorldAccess, random, blockPos, hugeMycorMushroomFeatureConfig, i, mutable, endPositions);
            for (BlockPos.Mutable endPosition : endPositions) {
                this.generateCap(structureWorldAccess, random, endPosition.toImmutable(), 1, new BlockPos.Mutable(endPosition.getX(), endPosition.getY(), endPosition.getZ()), hugeMycorMushroomFeatureConfig);
            }
            return true;
        }
    }

    protected abstract int getCapSize(int i, int j, int capSize, int y);

    protected abstract void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMycorMushroomFeatureConfig config);
}
