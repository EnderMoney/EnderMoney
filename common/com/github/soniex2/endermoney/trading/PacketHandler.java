package com.github.soniex2.endermoney.trading;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.github.soniex2.endermoney.trading.base.AbstractTraderContainer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("EMTrading")) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			String doTrade;
			try {
				doTrade = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			if (doTrade.equals("CreativeItem")) {
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP p = (EntityPlayerMP) player;
					if (p.openContainer instanceof AbstractTraderContainer) {
						((AbstractTraderContainer) p.openContainer)
								.doTrade((EntityPlayer) player);
					}
				}
			} else {
				return;
			}
		}
	}

}
