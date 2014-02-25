package com.github.soniex2.endermoney.trading.packet;

import com.github.soniex2.endermoney.trading.base.AbstractTraderContainer;

import cpw.mods.fml.common.FMLLog;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class TradePacket extends AbstractPacket {

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(0xDEADBEEF);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		if (buffer.readableBytes() < 4) {
			FMLLog.severe("Someone's hacking? Couldn't read packet \"signature\"");
		}
		int x = buffer.readInt();
		if (x != 0xDEADBEEF) {
			FMLLog.severe("Someone's hacking? Expected 0xDEADBEEF, got 0x%X", x);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (player.openContainer instanceof AbstractTraderContainer) {
			((AbstractTraderContainer) player.openContainer).doTrade(player);
		}
	}

}
