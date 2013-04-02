package com.github.soniex2.endermoney.core;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CoinCrafter implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting ctInv, World world) {
		int invSize = ctInv.getSizeInventory();
		int a = 0;
		for (int x = 0; x < invSize; x++) {
			if (ctInv.getStackInSlot(x) != null) {
				a++;
			}
		}
		if (a == 0 || a == 1) return false;
		long newValue = 0;
		for (int x = 0; x < invSize; x++) {
			ItemStack is = ctInv.getStackInSlot(x);
			if (is != null) {
				if (is.getItem() instanceof EnderCoin) {
					newValue += EnderCoin.getValueFromItemStack(is);
					if (newValue > Long.MAX_VALUE || newValue <= 0) return false;
				} else
					return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ctInv) {
		int invSize = ctInv.getSizeInventory();
		int a = 0;
		for (int x = 0; x < invSize; x++) {
			if (ctInv.getStackInSlot(x) != null) {
				a++;
			}
		}
		if (a == 0) return null;
		long newValue = 0;
		for (int x = 0; x < invSize; x++) {
			ItemStack is = ctInv.getStackInSlot(x);
			if (is != null) {
				if (is.getItem() instanceof EnderCoin) {
					newValue += EnderCoin.getValueFromItemStack(is);
					if (newValue > Long.MAX_VALUE || newValue <= 0) return null;
				} else
					return null;
			}
		}
		if (newValue > Long.MAX_VALUE || newValue <= 0) return null;
		return ((EnderCoin) EnderMoney.coin).getItemStack(newValue);
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ((EnderCoin) EnderMoney.coin).getItemStack(0L);
	}

}
