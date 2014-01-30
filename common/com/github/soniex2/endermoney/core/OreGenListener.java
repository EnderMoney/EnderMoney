package com.github.soniex2.endermoney.core;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;

public class OreGenListener {

	@SubscribeEvent
	public void onOreGen(OreGenEvent.GenerateMinable event) {
		if (event.type.equals(EventType.IRON)) {
			event.setResult(Result.DENY);
		}
	}
}
