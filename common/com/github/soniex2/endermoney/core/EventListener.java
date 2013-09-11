package com.github.soniex2.endermoney.core;

import com.github.soniex2.endermoney.core.item.EnderCoin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class EventListener {

	@ForgeSubscribe
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
			@SuppressWarnings("unused")
			NBTTagCompound tag = ((EntityPlayer) event.entity).getEntityData();

		}
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onEnderTeleport(EnderTeleportEvent event) {
		if (!(event.entity instanceof EntityEnderman)) return;
		EntityEnderman ender = (EntityEnderman) event.entity;
		int x = (int) Math.floor(ender.posX);
		int y = (int) Math.floor(ender.posY);
		int z = (int) Math.floor(ender.posZ);
		World world = ender.worldObj;
		if (world.getBlockId(x, y, z) == EnderMoney.blockLiqEC.blockID) {
			event.setCanceled(true);
		}
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase living = event.entityLiving;
		int x = (int) Math.floor(living.posX);
		int y = (int) Math.floor(living.posY);
		int z = (int) Math.floor(living.posZ);
		World world = living.worldObj;
		if (world.getBlockId(x, y, z) == EnderMoney.blockLiqEC.blockID) {
			if ((living instanceof EntityBlaze || living instanceof EntityEnderman)) {
				if (event.source == DamageSource.drown && event.ammount == 1.0F) {
					event.setCanceled(true);
				}
			}
		}
	}

	@ForgeSubscribe
	public void FillBucket(FillBucketEvent event) {
		if (event.current.getItem() == EnderMoney.coin) {
			if (EnderCoin.getValueFromItemStack(event.current) == Long.MAX_VALUE) {
				event.setResult(Result.DENY);
				return;
			}
			ItemStack result = attemptFill(event.world, event.target, event.current);
			if (result != null) {
				event.result = result;
				event.setResult(Result.ALLOW);
			}
		}
	}

	private ItemStack attemptFill(World world, MovingObjectPosition p, ItemStack item) {
		int id = world.getBlockId(p.blockX, p.blockY, p.blockZ);
		if (id == EnderMoney.blockLiqEC.blockID) {
			if (world.getBlockMetadata(p.blockX, p.blockY, p.blockZ) == 0) {
				world.setBlock(p.blockX, p.blockY, p.blockZ, 0);
				EnderCoin c = ((EnderCoin) EnderMoney.coin);
				return c.getItemStack(EnderCoin.getValueFromItemStack(item) + 1);
			}
		}
		return null;
	}
}
