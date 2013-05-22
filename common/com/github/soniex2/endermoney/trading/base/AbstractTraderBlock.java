package com.github.soniex2.endermoney.trading.base;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class AbstractTraderBlock extends BlockContainer {

	protected AbstractTraderBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public abstract boolean onBlockActivated(World world, int worldx, int worldy, int worldz,
			EntityPlayer player, int side, float blockx, float blocky, float blockz);

	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();

		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) { return; }
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz,
						new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound(
							(NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		for (int l = 0; l < 3; ++l) {
			@SuppressWarnings("unused")
			double d0 = (double) ((float) par2 + par5Random.nextFloat());
			double d1 = (double) ((float) par3 + par5Random.nextFloat());
			d0 = (double) ((float) par4 + par5Random.nextFloat());
			double d2 = 0.0D;
			double d3 = 0.0D;
			double d4 = 0.0D;
			int i1 = par5Random.nextInt(2) * 2 - 1;
			int j1 = par5Random.nextInt(2) * 2 - 1;
			d2 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
			d3 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
			d4 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
			double d5 = (double) par4 + 0.5D + 0.25D * (double) j1;
			d4 = (double) (par5Random.nextFloat() * 1.0F * (float) j1);
			double d6 = (double) par2 + 0.5D + 0.25D * (double) i1;
			d2 = (double) (par5Random.nextFloat() * 1.0F * (float) i1);
			par1World.spawnParticle("portal", d6, d1, d5, d2, d3, d4);
		}
	}

}
