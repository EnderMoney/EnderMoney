package com.github.soniex2.endermoney.trading;

public class TradeError extends Exception {
	private static final long serialVersionUID = 1L;
	public final int id;

	public TradeError(int id, String s) {
		super(s);
		this.id = id;
	}

	public TradeError(int id, String s, Throwable cause) {
		super(s, cause);
		this.id = id;
	}

	@Override
	public void printStackTrace() {

	}
}