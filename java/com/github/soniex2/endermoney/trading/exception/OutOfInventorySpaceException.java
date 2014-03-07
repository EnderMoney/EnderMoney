package com.github.soniex2.endermoney.trading.exception;

public class OutOfInventorySpaceException extends TradeException {

	private static final long serialVersionUID = -5526134479365035340L;
	
	public OutOfInventorySpaceException() {
		super();
	}

	public OutOfInventorySpaceException(String s) {
		super(s);
	}

	public OutOfInventorySpaceException(String s, Throwable cause) {
		super(s, cause);
	}
	
	public OutOfInventorySpaceException(Throwable cause) {
		super(cause);
	}

}
