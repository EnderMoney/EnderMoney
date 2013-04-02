package com.github.soniex2.endermoney.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EventListener {

	@ForgeSubscribe
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
			@SuppressWarnings("unused")
			NBTTagCompound tag = ((EntityPlayer) event.entity).getEntityData();

		}
	}
}
