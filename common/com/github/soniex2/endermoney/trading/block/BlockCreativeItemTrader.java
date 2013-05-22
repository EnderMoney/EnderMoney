package com.github.soniex2.endermoney.trading.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.trading.EnderMoneyTrading;
import com.github.soniex2.endermoney.trading.base.AbstractTraderBlock;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

public class BlockCreativeItemTrader extends AbstractTraderBlock {

	public BlockCreativeItemTrader(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(EnderMoney.tab);
		this.setHardness(40F);
		this.setResistance(100F);
		this.setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity,
			ItemStack stack) {
		if (entity instanceof EntityPlayer) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te instanceof TileEntityCreativeItemTrader) {
				((TileEntityCreativeItemTrader) te).getOrSetOwner(((EntityPlayer) entity).username);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCreativeItemTrader();
	}

	@Override
	public boolean onBlockActivated(World world, int worldx, int worldy, int worldz,
			EntityPlayer player, int side, float blockx, float blocky, float blockz) {
		if (player.isSneaking()) { return false; }
		if (!world.isRemote)
			player.openGui(EnderMoneyTrading.instance, 0, world, worldx, worldy, worldz);
		return true;
	}

	@Override
	public void registerIcons(IconRegister ireg) {
		this.blockIcon = ireg.registerIcon("endermoneytrading:itemtrader");
	}
}
