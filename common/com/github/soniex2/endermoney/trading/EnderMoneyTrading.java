package com.github.soniex2.endermoney.trading;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import com.github.soniex2.endermoney.trading.block.BlockCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "EnderMoneyTrading", name = "EnderMoney Trading", version = Version.MOD_VERSION,
		dependencies = "required-after:EnderMoneyCore;required-after:Forge")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
		channels = { "EMTrading" }, packetHandler = PacketHandler.class)
public class EnderMoneyTrading {

	@Instance("EnderMoneyTrading")
	public static EnderMoneyTrading instance;

	@SidedProxy(clientSide = "com.github.soniex2.endermoney.trading.ClientProxy",
			serverSide = "com.github.soniex2.endermoney.trading.CommonProxy")
	public static CommonProxy proxy;

	public static final Block blockCreativeItemTrader = new BlockCreativeItemTrader(1999);

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Init
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		GameRegistry.registerBlock(blockCreativeItemTrader, ItemBlock.class,
				"blockCreativeItemTrader");
		GameRegistry.registerTileEntity(TileEntityCreativeItemTrader.class, "containerItemTrader");
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}

}
