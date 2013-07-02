package com.github.soniex2.endermoney.trading;

public class TradeException extends Exception {
	private static final long serialVersionUID = 1L;
	public final int id;

	public TradeException(int id, String s) {
		super(s);
		this.id = id;
	}

	public TradeException(int id, String s, Throwable cause) {
		super(s, cause);
		this.id = id;
	}

	@Override
	public void printStackTrace() {

	}
}
