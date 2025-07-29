package io.github.cattacocattaco.mycor.block;

import io.github.cattacocattaco.mycor.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GlowshroomBlock extends MushroomBlock {
    public static final BooleanProperty SHEARED;

    public GlowshroomBlock(Settings settings) {
        super(settings);

        // Set the default state of the block to be unsheared.
        setDefaultState(getDefaultState().with(SHEARED, false));
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            boolean is_sheared = state.get(SHEARED);
            if(stack.isOf(Items.SHEARS) && !is_sheared) {
                world.setBlockState(pos, state.with(SHEARED, true));


                int rand_i = 1 + world.random.nextInt(3);
                Direction direction = hit.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + (double)0.5F + (double)direction2.getOffsetX() * 0.65, (double)pos.getY() + 0.1, (double)pos.getZ() + (double)0.5F + (double)direction2.getOffsetZ() * 0.65, new ItemStack(ModItems.GLOWSHROOM_SPORES, rand_i));
                itemEntity.setVelocity(0.05 * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02);
                world.spawnEntity(itemEntity);
                stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                world.emitGameEvent(player, GameEvent.SHEAR, pos);
                player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));

                return ActionResult.SUCCESS;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(SHEARED);
    }

    public static int getLuminance(BlockState currentBlockState) {
        // Get the value of the "activated" property.
        boolean sheared = currentBlockState.get(GlowshroomBlock.SHEARED);

        // Return a light level if activated = true
        return sheared ? 0 : 15;
    }

    static {
        SHEARED = BooleanProperty.of("sheared");
    }
}
