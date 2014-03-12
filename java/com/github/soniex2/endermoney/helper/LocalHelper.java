package com.github.soniex2.endermoney.helper;

import java.util.IllegalFormatException;
import java.util.regex.Pattern;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LocalHelper {

	private static final Pattern bullshit = Pattern
			.compile("%=((\\d+\\$)?[\\d\\.]*[df])");

	public static String formatted(String s, Object... o) {
		String s1 = StatCollector.translateToLocal(s);
		try {
			return ("" + String.format(fixBullshit(s1), o)).trim();
		} catch (IllegalFormatException illegalformatexception) {
			return ("" + "Format error: " + s1).trim();
		}
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

	/**
	 * Because of bullshit
	 * 
	 * @param inputstream
	 * @return
	 */
	private static String fixBullshit(String original) {
		String output = bullshit.matcher(original).replaceAll("%$1");
		return output;
	}

}
