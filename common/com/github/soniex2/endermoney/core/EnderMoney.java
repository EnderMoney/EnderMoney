package com.github.soniex2.endermoney.core;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.github.soniex2.endermoney.core.EnderItem.EnderSubItem;
import com.github.soniex2.endermoney.core.item.GenericItem;

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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

// @SuppressWarnings("unused")
@Mod(modid = "EnderMoneyCore", name = "EnderMoney Core", version = Version.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EnderMoney {

	public static final CreativeTabs tab = new CreativeTabs("EnderMoney") {
		@Override
		public ItemStack getIconItemStack() {
			return ((EnderCoin) coin).getItemStack(10000000L);
		}
	};
	public static final EnderItem enderItem = EnderItem.instance;
	public static final Item coin = new EnderCoin(27000);
	public static final Block ore = new Ore(500);
	public static final EnderSubItem ender = new GenericItem(0, "dustEnder", "endermoneycore:dust",
			0x228866, true);
	public static final EnderSubItem ironDust = new GenericItem(1, "dustIron",
			"endermoneycore:dust", 0xDDDDDD);

	@Instance("EnderMoneyCore")
	public static EnderMoney instance;

	@SidedProxy(clientSide = "com.github.soniex2.endermoney.core.ClientProxy",
			serverSide = "com.github.soniex2.endermoney.core.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Init
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers();
		GameRegistry.registerBlock(ore, Ore.Item.class, "endermoneycore.ore");
		OreDictionary.registerOre("dustEnder", ender.getItemStack());
		OreDictionary.registerOre("dustIron", ironDust.getItemStack());
		OreDictionary.registerOre("oreEnderDust", new ItemStack(ore, 1, 1));
		LanguageRegistry.instance().addStringLocalization("item.endercoin.name", "EnderCoin");
		LanguageRegistry.addName(ender.getItemStack(), "Ender Dust");
		LanguageRegistry.addName(ironDust.getItemStack(), "Iron Dust");
		LanguageRegistry.addName(new ItemStack(ore, 1, 0), "Dusty Iron Ore");
		LanguageRegistry.addName(new ItemStack(ore, 1, 1), "Ender Ore");
		LanguageRegistry.instance().addStringLocalization("itemGroup.EnderMoney", "EnderMoney");
		GameRegistry.addRecipe(new CoinCrafter());
		GameRegistry.addShapedRecipe(((EnderCoin) coin).getItemStack(1, 64), "xyx", "y#y", "xyx",
				'x', ender.getItemStack(), 'y', ironDust.getItemStack(), '#', new ItemStack(
						Item.enderPearl));
		FurnaceRecipes.smelting().addSmelting(ironDust.superID, ironDust.itemID,
				new ItemStack(Item.ingotIron, 1), 0F);
		MinecraftForge.EVENT_BUS.register(new EventListener());
		MinecraftForge.ORE_GEN_BUS.register(new OreGenListener());
		GameRegistry.registerWorldGenerator(new WorldGenerator());
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}

}
