package com.github.soniex2.endermoney.core;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;

public class OreGenListener {

	@ForgeSubscribe
	public void onOreGen(OreGenEvent.GenerateMinable event) {
		if (event.type.equals(EventType.IRON)) {
			event.setResult(Result.DENY);
		}
	}
}
