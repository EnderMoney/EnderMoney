package com.github.soniex2.endermoney.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.github.soniex2.endermoney.core.ClientProxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockEnderOreRenderer implements ISimpleBlockRenderingHandler {

	private void renderFace(int s, double x, double y, double z, Block b,
			int m, RenderBlocks r) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		IIcon i = r.getBlockIconFromSideAndMetadata(b, 5, m);
		switch (s) {
		case 0: // -Y
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			r.renderFaceYNeg(b, x, y, z, i);
			break;
		case 1: // +Y
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			r.renderFaceYPos(b, x, y, z, i);
			break;
		case 2: // +X
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			r.renderFaceXPos(b, x, y, z, i);
			break;
		case 3: // -X
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			r.renderFaceXNeg(b, x, y, z, i);
			break;
		case 4: // +Z
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			r.renderFaceZPos(b, x, y, z, i);
			break;
		case 5: // -Z
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			r.renderFaceZNeg(b, x, y, z, i);
			break;
		}
		tessellator.draw();
	}

	@Override
	public void renderInventoryBlock(Block par1Block, int par2, int modelID,
			RenderBlocks renderer) {
		par1Block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(par1Block);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		if (par2 == 0) {
			for (int a = 0; a < 6; a++) {
				renderFace(a, 0.0, 0.0, 0.0, par1Block, par2, renderer);
			}
		} else {
			Block b = Blocks.end_stone;
			for (int a = 0; a < 6; a++) {
				renderFace(a, 0.0, 0.0, 0.0, b, 0, renderer);
			}

			float red, green, blue;
			red = 0x22 / 255.0F;
			green = 0x88 / 255.0F;
			blue = 0x66 / 255.0F;
			GL11.glColor4f(red, green, blue, 1.0F);

			for (int a = 0; a < 6; a++) {
				renderFace(a, 0.0, 0.0, 0.0, par1Block, par2, renderer);
			}
		}
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if (world.getBlockMetadata(x, y, z) == 0) {
			renderer.renderStandardBlock(Blocks.iron_ore, x, y, z);
		} else {
			renderer.renderStandardBlock(Blocks.end_stone, x, y, z);
			renderer.setOverrideBlockTexture(block.getIcon(0,
					world.getBlockMetadata(x, y, z)));
			float r, g, b;
			r = 0x22 / 255.0F;
			g = 0x88 / 255.0F;
			b = 0x66 / 255.0F;
			renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, r,
					g, b);
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxy.oreRenderType;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
}
