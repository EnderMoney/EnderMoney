package com.github.soniex2.endermoney.core.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class LiquidCoin extends BlockFluidClassic {

	public LiquidCoin(int id, Fluid fluid) {
		super(id, fluid, Material.water);
		fluid.setBlockID(this);
		this.setQuantaPerBlock(16);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return Block.waterMoving.getIcon(side, meta);
	}

	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int x, int y, int z) {
		return 0xFF0000;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x_coord, int y_coord, int z_coord,
			Entity entity) {
		if (world.isRemote) return;
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			living.setAir(300);
		}
	}

}
