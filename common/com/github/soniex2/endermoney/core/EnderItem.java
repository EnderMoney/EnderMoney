package com.github.soniex2.endermoney.core;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public class EnderItem extends Item {

	public class EnderSubItem {
		public final int itemID;
		public final int superID = EnderItem.this.itemID;
		protected Icon iconIndex;
		private String unlocalizedName;

		public EnderSubItem(int id) {
			itemID = id;
			if (items[id] != null) {
				System.out.println("CONFLICT @ " + id + " item slot already occupied by "
						+ items[id] + " while adding " + this);
			}
			items[id] = this;
		}

		public final ItemStack getItemStack() {
			return getItemStack(1);
		}

		public final ItemStack getItemStack(int amount) {
			return new ItemStack(EnderItem.this.itemID, amount, itemID);
		}

		public Icon getIcon() {
			return iconIndex;
		}

		public void updateIcons(IconRegister ireg) {
			iconIndex = ireg.registerIcon(unlocalizedName);
		}

		public EnderSubItem setUnlocalizedName(String name) {
			unlocalizedName = name;
			return this;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void getSubItems(int id, int dmg, CreativeTabs tab, List list) {
			list.add(new ItemStack(id, 1, dmg));
		}

		public String getUnlocalizedName() {
			return "item." + unlocalizedName;
		}

		public String getItemDisplayName(ItemStack is) {
			return ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(is)))
					.trim();
		}

		public String getLocalizedName(ItemStack is) {
			String s = this.getUnlocalizedName(is);
			return s == null ? "" : StatCollector.translateToLocal(s);
		}

		public String getUnlocalizedName(ItemStack is) {
			return "item." + unlocalizedName;
		}

		public int getColorFromItemStack(ItemStack is, int pass) {
			return 0xFFFFFF;
		}

		public boolean hasEffect(ItemStack is) {
			return false;
		}
	}

	private EnderSubItem[] items = new EnderSubItem[256];
	public static final EnderItem instance = new EnderItem(27001);

	private EnderItem(int id) {
		super(id);
		setCreativeTab(EnderMoney.tab);
		setUnlocalizedName("EnderItem");
		setHasSubtypes(true);
	}

	@Override
	public Icon getIconFromDamage(int dmg) {
		dmg &= 0xFF;
		if (items[dmg] != null)
			return items[dmg].getIcon();
		else
			return itemIcon;
	}

	@Override
	public void registerIcons(IconRegister ireg) {
		for (int x = 0; x < items.length; x++) {
			if (items[x] != null) {
				items[x].updateIcons(ireg);
			}
		}
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		int x = is.getItemDamage();
		if (items[x] != null)
			return items[x].getItemDisplayName(is);
		else
			return "";
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		int x = is.getItemDamage();
		if (items[x] != null)
			return items[x].getUnlocalizedName(is);
		else
			return "";
	}

	@Override
	public String getUnlocalizedName() {
		return null;
	}

	@Override
	public String getLocalizedName(ItemStack is) {
		int x = is.getItemDamage();
		if (items[x] != null)
			return items[x].getLocalizedName(is);
		else
			return "";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int x = 0; x < items.length; x++) {
			if (items[x] != null) {
				items[x].getSubItems(id, x, tab, list);
			}
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack is, int pass) {
		int dmg = is.getItemDamage();
		if (items[dmg] != null) return items[dmg].getColorFromItemStack(is, pass);
		return 0xFFFFFF;
	}

	@Override
	public boolean hasEffect(ItemStack is) {
		int dmg = is.getItemDamage();
		if (items[dmg] != null) return items[dmg].hasEffect(is);
		return false;
	}
}
