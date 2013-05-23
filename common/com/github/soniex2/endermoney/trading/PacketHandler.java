package com.github.soniex2.endermoney.trading;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.github.soniex2.endermoney.trading.container.ContainerCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("EMTrading")) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			int x, y, z;
			String doTrade;
			try {
				x = dis.readInt();
				y = dis.readShort();
				z = dis.readInt();
				doTrade = dis.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			if (doTrade.equals("CreativeItem")) {
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP p = (EntityPlayerMP) player;
					if (p.openContainer instanceof ContainerCreativeItemTrader) {
						if (!(p.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityCreativeItemTrader)) {
							FMLLog.warning("Invalid trade packet for location: %d, %d, %d", x, y, z);
							return;
						}
						((ContainerCreativeItemTrader) p.openContainer)
								.doTrade((EntityPlayer) player);
					}
				}
			} else {
				return;
			}
		}
	}

}
