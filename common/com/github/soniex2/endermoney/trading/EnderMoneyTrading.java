package com.github.soniex2.endermoney.trading;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.github.soniex2.endermoney.trading.block.BlockCreativeItemTrader;
import com.github.soniex2.endermoney.trading.block.BlockItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityItemTrader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "EnderMoneyTrading", name = "EnderMoney Trading", version = Version.MOD_VERSION,
		dependencies = "required-after:EnderMoneyCore;required-after:Forge")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "EMTrading" },
		packetHandler = PacketHandler.class)
public class EnderMoneyTrading {

	@Instance("EnderMoneyTrading")
	public static EnderMoneyTrading instance;

	@SidedProxy(clientSide = "com.github.soniex2.endermoney.trading.ClientProxy",
			serverSide = "com.github.soniex2.endermoney.trading.CommonProxy")
	public static CommonProxy proxy;

	public static Block blockCreativeItemTrader, blockItemTrader;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File configDir = new File(event.getSuggestedConfigurationFile().getParentFile(),
				"EnderMoney/Trading.cfg");
		Configuration config = new Configuration(configDir);
		config.load();
		Property creativeItemTrader = config.getBlock("trader.creative.item", 501,
				"Creative Item Trader Block ID");
		Property itemTrader = config.getBlock("trader.item", 503, "Creative Item Trader Block ID");
		config.save();

		blockCreativeItemTrader = new BlockCreativeItemTrader(creativeItemTrader.getInt(501));
		blockItemTrader = new BlockItemTrader(itemTrader.getInt(503));

		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		GameRegistry.registerBlock(blockCreativeItemTrader, ItemBlock.class,
				"blockCreativeItemTrader");
		GameRegistry.registerBlock(blockItemTrader, ItemBlock.class, "blockItemTrader");
		GameRegistry.registerTileEntity(TileEntityCreativeItemTrader.class,
				"containerCreativeItemTrader");
		GameRegistry.registerTileEntity(TileEntityItemTrader.class, "containerItemTrader");

		LanguageRegistry.addName(blockCreativeItemTrader, "Creative Item Trader");
		LanguageRegistry.addName(blockItemTrader, "Item Trader");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
