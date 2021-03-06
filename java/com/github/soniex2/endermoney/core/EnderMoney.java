package com.github.soniex2.endermoney.core;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.github.soniex2.endermoney.core.block.BlockEnderOre;
import com.github.soniex2.endermoney.core.block.ItemBlockEnderOre;
import com.github.soniex2.endermoney.core.item.EnderCoin;
import com.github.soniex2.endermoney.core.item.EnderItem;
import com.github.soniex2.endermoney.core.item.EnderItem.EnderSubItem;
import com.github.soniex2.endermoney.core.item.GenericItem;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "EnderMoneyCore", name = "EnderMoney Core", version = Version.MOD_VERSION, dependencies = "required-after:Forge")
public class EnderMoney {

	public static final CreativeTabs tab = new CreativeTabs(
			"endermoneycore.EnderMoney") {
		@Override
		public Item getTabIconItem() {
			return coin;
		}

		@Override
		public int func_151243_f() {
			return 0;
		}
	};
	private static final int[] enderMoneyValues = new int[] { 1, 2, 5, 10, 25,
			50, 100 };
	public static EnderItem enderItem;
	public static EnderCoin coin;
	public static BlockEnderOre ore;
	public static EnderSubItem ender;
	public static EnderSubItem ironDust;
	public static EnderSubItem enderIngot;
	public static EnderSubItem chestception;

	@Instance("EnderMoneyCore")
	public static EnderMoney instance;

	@SidedProxy(clientSide = "com.github.soniex2.endermoney.core.ClientProxy", serverSide = "com.github.soniex2.endermoney.core.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		Configuration config = new Configuration(new File(
				event.getModConfigurationDirectory(), "endermoney/core.cfg"));
		config.load();
		Property craftableCoins = config.get(Configuration.CATEGORY_GENERAL,
				"Enable EnderCoin crafting", true);
		config.save();

		enderItem = EnderItem.instance = new EnderItem();
		coin = new EnderCoin(enderMoneyValues);
		ore = new BlockEnderOre();
		ender = new GenericItem(0, "dustEnder", "endermoneycore:dust",
				0x228866, true);
		ironDust = new GenericItem(1, "dustIron", "endermoneycore:dust",
				0xDDDDDD);
		enderIngot = new GenericItem(2, "ingotEnder", "iron_ingot", 0x228866,
				true);
		chestception = new GenericItem(3, "chestception", "chestception");

		GameRegistry.registerBlock(ore, ItemBlockEnderOre.class, "ender_ore",
				"endermoney");
		GameRegistry.registerItem(coin, "endercoin", "endermoney");
		GameRegistry.registerItem(enderItem, "ender_item", "endermoney");

		OreDictionary.registerOre("dustEnder", ender.getItemStack());
		OreDictionary.registerOre("dustIron", ironDust.getItemStack());
		OreDictionary.registerOre("ingotEnder", enderIngot.getItemStack());
		OreDictionary.registerOre("oreEnderDust", new ItemStack(ore, 1, 1));

		RecipeSorter.register("endermoney:endercoin", CoinRecipe.class,
				Category.SHAPELESS, "after:minecraft:shapeless");
		coin.registerRecipes();

		if (craftableCoins.getBoolean(true)) {
			GameRegistry
					.addRecipe(new ShapedOreRecipe(new ItemStack(coin, 1, 0),
							false, "xyx", "y#y", "xyx", 'x', "ingotEnder", 'y',
							Items.iron_ingot, '#', Items.ender_pearl));
		}

		GameRegistry.addRecipe(new ShapelessOreRecipe(ender.getItemStack(2),
				"dustIron", "dustIron", Items.ender_pearl));

		FurnaceRecipes.smelting().func_151394_a(
				new ItemStack(enderItem, ironDust.idx),
				new ItemStack(Items.iron_ingot, 1), 0F);
		FurnaceRecipes.smelting().func_151394_a(
				new ItemStack(enderItem, ender.idx),
				new ItemStack(enderItem, 1, enderIngot.idx), 0.5F);

		MinecraftForge.EVENT_BUS.register(new EventListener());
		MinecraftForge.ORE_GEN_BUS.register(new OreGenListener());

		GameRegistry.registerWorldGenerator(new WorldGenerator(), 1);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.setCustomRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
