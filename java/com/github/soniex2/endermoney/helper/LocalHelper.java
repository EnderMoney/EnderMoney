package com.github.soniex2.endermoney.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LocalHelper {

	public static String formatted(String s, Object... o) {
		return ("" + StatCollector.translateToLocalFormatted(s, o)).trim();
	}

	public static String plain(String s) {
		return ("" + StatCollector.translateToLocal(s)).trim();
	}

	public static String plain(ItemStack is, Object... o) {
		return plain(is.getItem().getUnlocalizedNameInefficiently(is) + ".name");
	}

	public static String formatted(ItemStack is, Object... o) {
		return formatted(is.getItem().getUnlocalizedNameInefficiently(is)
				+ ".name", o);
	}

}
