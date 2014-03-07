package com.github.soniex2.endermoney.trading;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.trading.block.BlockCreativeItemTrader;
import com.github.soniex2.endermoney.trading.block.BlockItemTrader;
import com.github.soniex2.endermoney.trading.packet.PacketPipeline;
import com.github.soniex2.endermoney.trading.packet.TradePacket;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityItemTrader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "EnderMoneyTrading", name = "EnderMoney Trading", version = Version.MOD_VERSION, dependencies = "required-after:EnderMoneyCore;required-after:Forge")
public class EnderMoneyTrading {

	@Instance("EnderMoneyTrading")
	public static EnderMoneyTrading instance;

	@SidedProxy(clientSide = "com.github.soniex2.endermoney.trading.ClientProxy", serverSide = "com.github.soniex2.endermoney.trading.CommonProxy")
	public static CommonProxy proxy;

	public static Block blockCreativeItemTrader, blockItemTrader;

	public static final PacketPipeline packetPipe = new PacketPipeline(
			"EnderMoney|Trading");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		blockCreativeItemTrader = new BlockCreativeItemTrader();
		blockItemTrader = new BlockItemTrader();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		GameRegistry.registerBlock(blockCreativeItemTrader, ItemBlock.class,
				"creative_item_trader", "endermoney");
		GameRegistry.registerBlock(blockItemTrader, ItemBlock.class,
				"item_trader", "endermoney");
		GameRegistry.registerTileEntity(TileEntityCreativeItemTrader.class,
				"endermoneytrading.containerCreativeItemTrader");
		GameRegistry.registerTileEntity(TileEntityItemTrader.class,
				"endermoneytrading.containerItemTrader");

		packetPipe.registerPacket(TradePacket.class);

		// TODO: JSON
		LanguageRegistry.addName(blockCreativeItemTrader,
				"Creative Item Trader");
		LanguageRegistry.addName(blockItemTrader, "Item Trader");

		GameRegistry.addRecipe(new ShapedOreRecipe(blockItemTrader, "xxx",
				"xyx", "xzx", 'x', "ingotEnder", 'y', new ItemStack(
						EnderMoney.coin, 1, OreDictionary.WILDCARD_VALUE), 'z',
				Blocks.chest));
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers();
		packetPipe.initialize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
