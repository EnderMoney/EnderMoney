package com.github.soniex2.endermoney.trading.helper.money;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.item.ItemStack;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.core.item.EnderCoin;
import com.github.soniex2.endermoney.trading.helper.item.ItemStackMapKey;

public class MoneyHelper {

	private MoneyHelper() {
	}

	public static BigInteger extractFromHashMap(HashMap<ItemStackMapKey, Integer> map) {
		BigInteger value = BigInteger.ZERO;
		Set<ItemStackMapKey> keySet = map.keySet();
		Iterator<ItemStackMapKey> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			ItemStackMapKey key = keyIterator.next();
			if(key.itemID == EnderMoney.coin.itemID) {
				ItemStack is = new ItemStack(key.itemID, 1, key.damage);
				is.stackTagCompound = key.getTag();
				long v = EnderCoin.getValueFromItemStack(is);
				value = value.add(BigInteger.valueOf(v).multiply(BigInteger.valueOf(map.get(key))));
				keyIterator.remove();
			}
		}
		return value;
	}

}
