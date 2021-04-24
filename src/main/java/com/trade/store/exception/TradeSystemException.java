/**
 * 
 */
package com.trade.store.exception;

import javassist.bytecode.CodeAttribute.RuntimeCopyException;

/**
 * @author akhalesh
 *
 * Class provides custom exception handling to Trade store, contains constructors to display exception according to need.
 */
public class TradeSystemException extends RuntimeCopyException{

	private static final long serialVersionUID = 1L;

	public TradeSystemException(String s) {
		super(s);
	}

}
