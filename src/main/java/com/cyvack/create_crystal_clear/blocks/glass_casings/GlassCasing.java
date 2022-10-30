package com.cyvack.create_crystal_clear.blocks.glass_casings;

import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlassCasing extends GlassBlock implements IWrenchable {

	public GlassCasing(Properties p_53640_) {
		super(p_53640_);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction side) {
		return ((pState.getBlock() instanceof GlassCasing) && (pAdjacentBlockState.getBlock() instanceof GlassCasing));
	}

	@Override
	public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidState) {
		return true;
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context){
		return InteractionResult.FAIL;
	}

	@Override
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		if (context.getLevel() instanceof ServerLevel world) {
			var pos = context.getClickedPos();
			var player = context.getPlayer();
			if (player != null && !player.isCreative())
				player.getInventory().placeItemBackInInventory(new ItemStack(this.asItem()));
			state.spawnAfterBreak(world, pos, ItemStack.EMPTY);
			world.destroyBlock(pos, false);
			playRemoveSound(world, pos);
		}
		return InteractionResult.SUCCESS;
	}
}
