package com.github.soniex2.endermoney.core.item;

import java.util.List;

import com.github.soniex2.endermoney.core.EnderMoney;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class EnderItem extends Item {

	public class EnderSubItem {
		public final int idx;
		protected IIcon iconIndex;
		private String unlocalizedName;
		protected IIcon altPassIcon;

		public EnderSubItem(int id) {
			idx = id;
			if (items[id] != null) {
				System.out.println("CONFLICT @ " + id
						+ " item slot already occupied by " + items[id]
						+ " while adding " + this);
			}
			items[id] = this;
		}

		public final ItemStack getItemStack() {
			return getItemStack(1);
		}

		public final ItemStack getItemStack(int amount) {
			return new ItemStack(EnderItem.this, amount, idx);
		}

		public IIcon getIcon() {
			return iconIndex;
		}

		public void updateIcons(IIconRegister ireg) {
			iconIndex = ireg.registerIcon("endermoneycore:" + unlocalizedName);
		}

		public EnderSubItem setUnlocalizedName(String name) {
			unlocalizedName = name;
			return this;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void getSubItems(Item item, int dmg, CreativeTabs tab, List list) {
			list.add(new ItemStack(item, 1, dmg));
		}

		public String getUnlocalizedName() {
			return "item.endermoneycore." + unlocalizedName;
		}

		public String getItemStackDisplayName(ItemStack is) {
			return ("" + StatCollector.translateToLocal(this
					.getUnlocalizedNameInefficiently(is) + ".name")).trim();
		}

		public String getUnlocalizedNameInefficiently(ItemStack is) {
			String s = this.getUnlocalizedName(is);
			return s == null ? "" : StatCollector.translateToLocal(s);
		}

		public String getUnlocalizedName(ItemStack is) {
			return getUnlocalizedName();
		}

		public int getColorFromItemStack(ItemStack is, int pass) {
			return 0xFFFFFF;
		}

		@Deprecated
		public boolean hasEffect(ItemStack is) {
			return false;
		}

		public boolean hasEffect(ItemStack is, int pass) {
			return hasEffect(is) && (pass == 0);
		}

		public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
			return iconIndex;
		}

		public int getRenderPasses(int metadata) {
			return 1;
		}

		public int getItemStackLimit(ItemStack is) {
			return 64;
		}
	}

	private EnderSubItem[] items = new EnderSubItem[256];
	public static EnderItem instance;

	public EnderItem() {
		super();
		setCreativeTab(EnderMoney.tab);
		setUnlocalizedName("endermoneycore.EnderItem");
		setHasSubtypes(true);
		setTextureName("EnderItem");
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (items[dmg & 255] != null)
			return items[dmg & 255].getIcon();
		else
			return itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister ireg) {
		for (int x = 0; x < items.length; x++) {
			if (items[x] != null) {
				items[x].updateIcons(ireg);
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		int x = is.getItemDamage();
		if (items[x] != null)
			return items[x].getItemStackDisplayName(is);
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
	public String getUnlocalizedNameInefficiently(ItemStack is) {
		int x = is.getItemDamage();
		if (items[x] != null)
			return items[x].getUnlocalizedNameInefficiently(is);
		else
			return "";
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int x = 0; x < items.length; x++) {
			if (items[x] != null) {
				items[x].getSubItems(item, x, tab, list);
			}
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack is, int pass) {
		int dmg = is.getItemDamage();
		if (items[dmg] != null)
			return items[dmg].getColorFromItemStack(is, pass);
		return 0xFFFFFF;
	}

	@Override
	public boolean hasEffect(ItemStack is, int pass) {
		int dmg = is.getItemDamage();
		if (items[dmg] != null)
			return items[dmg].hasEffect(is, pass);
		return false;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getItemStackLimit(ItemStack is) {
		int dmg = is.getItemDamage();
		if (items[dmg] != null)
			return items[dmg].getItemStackLimit(is);
		return 64;
	}

	@Override
	public int getRenderPasses(int metadata) {
		if (items[metadata] != null)
			return items[metadata].getRenderPasses(metadata);
		return 1;
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
		if (items[damage] != null)
			return items[damage].getIconFromDamageForRenderPass(damage, pass);
		return this.itemIcon;
	}
}
