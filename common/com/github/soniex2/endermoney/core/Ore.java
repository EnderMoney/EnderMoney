package com.github.soniex2.endermoney.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

// TODO: Make this modular
public class Ore extends BlockOre {

	public static class Item extends ItemBlock {

		public Item(int id) {
			super(id);
			setHasSubtypes(true);
		}

		@Override
		public int getMetadata(int damage) {
			return damage;
		}
	}

	public static class Renderer implements ISimpleBlockRenderingHandler {

		@Override
		public void renderInventoryBlock(Block par1Block, int par2, int modelID,
				RenderBlocks renderer) {
			Tessellator tessellator = Tessellator.instance;
			par1Block.setBlockBoundsForItemRender();
			renderer.setRenderBoundsFromBlock(par1Block);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			if (par2 == 0) {
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
				tessellator.draw();
			} else {
				Block b = Block.whiteStone;
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer.renderFaceYNeg(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 0, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer.renderFaceYPos(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 1, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer.renderFaceXPos(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 2, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer.renderFaceXNeg(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 3, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer.renderFaceZNeg(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 4, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer.renderFaceZPos(b, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(b, 5, par2));
				tessellator.draw();

				float red, green, blue;
				red = 0x22 / 255.0F;
				green = 0x88 / 255.0F;
				blue = 0x66 / 255.0F;
				GL11.glColor4f(red, green, blue, 1.0F);

				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D,
						renderer.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
				tessellator.draw();
			}
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}

		@Override
		public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block,
				int modelId, RenderBlocks renderer) {
			if (world.getBlockMetadata(x, y, z) == 0) {
				renderer.renderStandardBlock(block, x, y, z);
			} else {
				renderer.renderStandardBlock(Block.whiteStone, x, y, z);
				renderer.setOverrideBlockTexture(enderTexture);
				float r, g, b;
				r = 0x22 / 255.0F;
				g = 0x88 / 255.0F;
				b = 0x66 / 255.0F;
				renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
				renderer.clearOverrideBlockTexture();
			}
			return true;
		}

		@Override
		public boolean shouldRender3DInInventory() {
			return true;
		}

		@Override
		public int getRenderId() {
			return ClientProxy.oreRenderType;
		}
	}

	private static Icon enderTexture;

	public Ore(int id) {
		super(id);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("ore");
		setCreativeTab(EnderMoney.tab);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		if (par1 == 0) {
			if (par2Random.nextInt(4) == 0) return EnderMoney.ironDust.superID;
			return Block.oreIron.blockID;
		} else
			return EnderMoney.ender.superID;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata,
			int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		if (metadata == 0) {
			int id = idDropped(metadata, world.rand, fortune);
			int count = id == Block.oreIron.blockID ? 1 : 2;
			for (int i = 0; i < count; i++) {
				if (id > 0) {
					ret.add(new ItemStack(id, 1, id == Block.oreIron.blockID ? 0
							: EnderMoney.ironDust.itemID));
				}
			}
		} else {
			int a = 2 + world.rand.nextInt(3);
			for (int i = 0; i < a; i++) {
				ret.add(new ItemStack(EnderMoney.ender.superID, 1, EnderMoney.ender.itemID));
			}
		}
		return ret;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("oreIron");
		enderTexture = par1IconRegister.registerIcon("endermoneycore:orebase");
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta) {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
	}

	@Override
	public Icon getIcon(int s, int m) {
		if (m == 0)
			return blockIcon;
		else
			return enderTexture;
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5,
			float par6, int par7) {
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

		if (par5 == 1 && idDropped(par5, par1World.rand, par7) != blockID) {
			int j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
			dropXpOnBlockBreak(par1World, par2, par3, par4, j1);
		}
	}
}
