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
			if (key.item == EnderMoney.coin) {
				ItemStack is = new ItemStack(key.item, 1, key.damage);
				is.stackTagCompound = key.getTag();
				long v = EnderCoin.getValueFromItemStack(is);
				value = value.add(BigInteger.valueOf(v).multiply(BigInteger.valueOf(map.get(key))));
				keyIterator.remove();
			}
		}
		return value;
	}

	public static void insertIntoHashMap(HashMap<ItemStackMapKey, Integer> map, BigInteger money) {
		if (money.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
			BigInteger[] coinCount = money.divideAndRemainder(BigInteger.valueOf(Long.MAX_VALUE));
			int a = coinCount[0].intValue();
			long b = coinCount[1].longValue();
			ItemStack is1 = ((EnderCoin) EnderMoney.coin).getItemStack(Long.MAX_VALUE);
			ItemStack is2 = ((EnderCoin) EnderMoney.coin).getItemStack(b);
			ItemStackMapKey index1 = new ItemStackMapKey(is1);
			ItemStackMapKey index2 = new ItemStackMapKey(is2);
			if (map.containsKey(index1))
				map.put(index1, map.get(index1) + a);
			else
				map.put(index1, a);
			if (map.containsKey(index2))
				map.put(index2, map.get(index2) + 1);
			else
				map.put(index2, 1);
		} else if (!money.equals(BigInteger.ZERO)) {
			ItemStack is = ((EnderCoin) EnderMoney.coin).getItemStack(money.longValue(), 1);
			ItemStackMapKey index = new ItemStackMapKey(is);
			if (map.containsKey(index))
				map.put(index, map.get(index) + 1);
			else
				map.put(index, 1);
		}
	}

}
