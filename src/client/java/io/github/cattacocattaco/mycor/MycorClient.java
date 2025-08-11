package io.github.cattacocattaco.mycor;

import io.github.cattacocattaco.mycor.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class MycorClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		//Partially transparent blocks need special rendering stuff
		BlockRenderLayerMap.putBlock(ModBlocks.GLOWSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.JUMPSHROOM, BlockRenderLayer.CUTOUT);
	}
}