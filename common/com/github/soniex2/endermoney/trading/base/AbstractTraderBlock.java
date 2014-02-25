package com.github.soniex2.endermoney.trading.base;

import java.util.Random;

import net.minecraft.block.Block;
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

	protected AbstractTraderBlock(Material par2Material) {
		super(par2Material);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block,
			int metadata) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public abstract boolean onBlockActivated(World world, int worldx,
			int worldy, int worldz, EntityPlayer player, int side,
			float blockx, float blocky, float blockz);

	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z
						+ rz, new ItemStack(item.getItem(), item.stackSize,
						item.getItemDamage()));

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

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		for (int l = 0; l < 3; ++l) {
			double py = par3 + par5Random.nextFloat();
			int i1 = par5Random.nextInt(2) * 2 - 1;
			int j1 = par5Random.nextInt(2) * 2 - 1;
			double pvx = par5Random.nextFloat() * 1.0F * i1;
			double pvy = (par5Random.nextFloat() - 0.5D) * 0.125D;
			double pvz = par5Random.nextFloat() * 1.0F * j1;
			double pz = par4 + 0.5D + 0.25D * j1;
			double px = par2 + 0.5D + 0.25D * i1;
			par1World.spawnParticle("portal", px, py, pz, pvx, pvy, pvz);
		}
	}

}
