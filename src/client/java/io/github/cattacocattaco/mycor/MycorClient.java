package io.github.cattacocattaco.mycor;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import io.github.cattacocattaco.mycor.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.util.Identifier;

public class MycorClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		//Partially transparent blocks need special rendering stuff
		BlockRenderLayerMap.putBlock(ModBlocks.GLOWSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.JUMPSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SWIFTSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.FROSTSHROOM, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.FUNGAL_DOOR, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.FUNGAL_TRAPDOOR, BlockRenderLayer.CUTOUT);

		TerraformBoatClientHelper.registerModelLayers(Identifier.of("mycor", "fungal"));
	}
}