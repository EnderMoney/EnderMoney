package com.github.soniex2.endermoney.core.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidEnderCoin extends Fluid {

	public FluidEnderCoin() {
		super("endermoneycore.liquidEnderMoney");
		this.setDensity(10);
		this.setViscosity(1000);
		FluidRegistry.registerFluid(this);
	}

}
