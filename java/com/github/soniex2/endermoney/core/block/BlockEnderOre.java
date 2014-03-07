package com.github.soniex2.endermoney.core.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.github.soniex2.endermoney.core.ClientProxy;
import com.github.soniex2.endermoney.core.EnderMoney;

// TODO: Make this modular
public class BlockEnderOre extends BlockOre {

	private static IIcon oreTexture;

	public BlockEnderOre() {
		super();
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeStone);
		setBlockName("endermoney.ore");
		setCreativeTab(EnderMoney.tab);
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		if (metadata == 0 && rand.nextInt(4) == 0) {
			return EnderMoney.enderItem;
		}
		return Item.getItemFromBlock(this);
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata == 0 ? EnderMoney.ironDust.idx : 0;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if (metadata == 0) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			boolean v = item == Item.getItemFromBlock(Blocks.iron_ore);
			int count = v ? 1 : 2;
			for (int i = 0; i < count; i++) {
				if (item != null) {
					ret.add(new ItemStack(item, 1, v ? 0
							: EnderMoney.ironDust.idx));
				}
			}
		} else {
			int a = 2;
			for (int i = 0; i < a; i++) {
				ret.add(new ItemStack(EnderMoney.enderItem, 1,
						EnderMoney.ender.idx));
			}
		}
		return ret;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x,
			int y, int z, int meta) {
		if (meta == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ClientProxy.oreRenderType;
	}

	@Override
	public boolean canRenderInPass(int pass) {
		ClientProxy.renderPass = pass;
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		if (metadata == 0)
			return Blocks.iron_ore.getIcon(side, metadata);
		else
			return oreTexture;
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int x, int y, int z,
			int metadata, float par6, int fortune) {
		super.dropBlockAsItemWithChance(par1World, x, y, z, metadata, par6,
				fortune);

		if (metadata != 0
				&& getItemDropped(metadata, par1World.rand, fortune) != Item
						.getItemFromBlock(this)) {
			int j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 2,
					5 + fortune);
			dropXpOnBlockBreak(par1World, x, y, z, j1);
		}
	}
}
