package com.github.soniex2.endermoney.trading.exception;

public class TradeException extends Exception {

	private static final long serialVersionUID = -6611918055597503311L;
	
	public TradeException() {
		super();
	}

	public TradeException(String s) {
		super(s);
	}

	public TradeException(String s, Throwable cause) {
		super(s, cause);
	}
	
	public TradeException(Throwable cause) {
		super(cause);
	}

}
