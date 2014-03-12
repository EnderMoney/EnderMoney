package com.github.soniex2.endermoney.core.item;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.github.soniex2.endermoney.core.CoinRecipe;
import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.helper.LocalHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class EnderCoin extends Item {

	private int[] metadata;

	private Random rand = new Random();

	public EnderCoin(int[] values) {
		super();
		setMaxStackSize(64);
		setCreativeTab(EnderMoney.tab);
		setUnlocalizedName("endermoney.endercoin");
		this.setHasSubtypes(true);
		this.setTextureName("endermoneycore:coin");
		metadata = Arrays.copyOf(values, values.length);
	}

	public void registerRecipes() {
		for (int x = 0; x < metadata.length; x++) {
			CoinRecipe handler = new CoinRecipe(this, x);
			registerRecipes(metadata[x], 0, new int[9], handler);
			handler.register();
		}
	}

	private void registerRecipes(int left, int index, int[] recipe,
			CoinRecipe handler) {
		if (left > 0) {
			if (index >= 9)
				return;
			for (int x = 0; x < metadata.length; x++) {
				int v = metadata[x];
				if ((index == 0 || v >= metadata[recipe[index - 1]])
						&& v <= left) {
					recipe[index] = x;
					registerRecipes(left - v, index + 1, recipe, handler);
				}
			}
		} else if (left == 0) {
			handler.addRecipe(Arrays.copyOf(recipe, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass) {
		int v = is.getItemDamage();
		rand.setSeed(v);
		int r = 255 - rand.nextInt(224) & 255;
		int g = 255 - rand.nextInt(224) & 255;
		int b = 255 - rand.nextInt(224) & 255;
		return r << 16 | g << 8 | b;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		int v = is.getItemDamage();
		if (v >= 0 && v < metadata.length)
			return LocalHelper.formatted(is, metadata[v]);
		else
			return "INVALID";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ireg) {
		itemIcon = ireg.registerIcon("endermoneycore:coin");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < metadata.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer player, List list,
			boolean debugMode) {
		super.addInformation(is, player, list, debugMode);
		if (Keyboard
				.isKeyDown(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak
						.getKeyCode())) {
			list.add(LocalHelper.plain("endermoney:endercoin.tooltip"));
			if (debugMode) { // F3+H
				/*
				 * list.add(EnumChatFormatting.GRAY +
				 * String.format("Color: #%06X", getColorFromItemStack(is, 0)) +
				 * EnumChatFormatting.RESET);
				 */
				list.add(LocalHelper.formatted(
						"endermoney:endercoin.tooltip.extension",
						getColorFromItemStack(is, 0)));
			}
		} else {
			list.add(LocalHelper.plain("endermoney:endercoin.tooltip.sneak"));
		}
	}
}
