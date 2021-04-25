package com.trade.store.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.trade.store.exception.TradeSystemException;
import com.trade.store.model.Trade;

/**
 * @author akhalesh
 * Class represent service layer of application and provides interaction methods to web layer.
 */
public interface TradeService {
	
	public List<Trade> findTrades();
	
	public Trade addTrade(Trade trade) throws TradeSystemException;
	
	public ResponseEntity<Trade> updateTrade(int tradeVersoin, Trade trade) throws TradeSystemException;

}
