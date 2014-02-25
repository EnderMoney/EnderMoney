package com.github.soniex2.endermoney.trading.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.trading.EnderMoneyTrading;
import com.github.soniex2.endermoney.trading.base.AbstractTraderBlock;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

public class BlockCreativeItemTrader extends AbstractTraderBlock {

	public BlockCreativeItemTrader() {
		super(Material.iron);
		this.setCreativeTab(EnderMoney.tab);
		this.setHardness(40F);
		this.setResistance(100F);
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockName("endermoneytrading.creativeIT");
		this.setBlockTextureName("endermoneytrading:itemtrader");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		if (entity instanceof EntityPlayer) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityCreativeItemTrader) {
				// We use getCommandSenderName to get the player's IGN/username
				((TileEntityCreativeItemTrader) te)
						.getOrSetOwner(((EntityPlayer) entity)
								.getCommandSenderName());
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityCreativeItemTrader();
	}

	@Override
	public boolean onBlockActivated(World world, int worldx, int worldy,
			int worldz, EntityPlayer player, int side, float blockx,
			float blocky, float blockz) {
		if (player.isSneaking()) {
			return false;
		}
		if (!world.isRemote)
			player.openGui(EnderMoneyTrading.instance, 0, world, worldx,
					worldy, worldz);
		return true;
	}
}
