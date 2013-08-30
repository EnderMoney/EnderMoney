package com.github.soniex2.endermoney.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import net.minecraft.client.Minecraft;

import com.github.soniex2.endermoney.core.block.Ore;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public static int renderPass, oreRenderType;

	@Override
	public void setCustomRenderers() {
		oreRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new Ore.Renderer());
	}

	private static final Set<String> nameBlacklist = new HashSet<String>(
			Arrays.asList(new String[] { "mdiyo" }));

	@Override
	public void checkBlacklist() {
		if (nameBlacklist.contains(Minecraft.getMinecraft().func_110432_I().func_111285_a()
				.toLowerCase(Locale.ENGLISH))) {
			throw new RuntimeException("You're not allowed to use this mod!");
		}
	}

}
