/**
 * 
 */
package com.trade.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trade.store.model.Trade;
import com.trade.store.service.TradeService;

/**
 * @author akhalesh
 *
 * TradeController represent web layer of store activity and provides way of transmission methods into store. 
 */
@RestController
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	/**
	 * Method fetch all trades form stores.
	 * @return list of Trades
	 */
	@GetMapping("/findTrades")
	public List<Trade> findAllTrade(){
		return tradeService.findTrades();
	}
	/**
	 * Controller method to save coming trades into store, which accepts Trade in request while adding.
	 * @param trade
	 * @return
	 */
	@PostMapping("/addTrade")
	public Trade saveTrade(@RequestBody Trade trade) {
		return tradeService.addTrade(trade);
	}
	/**
	 * This method update existing trade associated to version, and method accepts trade updated values. 
	 * @param trade
	 * @return
	 */
	@PutMapping("/trade/{version}")
	public ResponseEntity<Trade> updateTrade(@PathVariable int version, @RequestBody Trade trade) {
		return tradeService.updateTrade(version, trade);
	}
}
