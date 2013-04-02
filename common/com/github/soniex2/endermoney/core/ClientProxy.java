package com.github.soniex2.endermoney.core;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public static int renderPass, oreRenderType;

	@Override
	public void registerRenderers() {

	}

	@Override
	public void setCustomRenderers() {
		oreRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new Ore.Renderer());
	}

}
