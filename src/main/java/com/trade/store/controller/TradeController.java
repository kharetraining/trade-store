/**
 * 
 */
package com.trade.store.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trade.store.constant.TradeConstants;
import com.trade.store.exception.TradeSystemException;
import com.trade.store.model.Trade;
import com.trade.store.repository.TradeRepository;

/**
 * @author akhalesh
 *
 * TradeController represent web layer of store activity and provides way of transmission methods into store. 
 */
@RestController
public class TradeController {
	
	@Autowired
	private TradeRepository tradeRepository;
	/**
	 * Method fetch all trades form stores.
	 * @return list of Trades
	 */
	@GetMapping("/findTrades")
	public List<Trade> findAllTrade(){
		return tradeRepository.findAll();
	}
	/**
	 * Controller method to save coming trades into store, which accepts Trade in request while adding.
	 * @param trade
	 * @return
	 */
	@PostMapping("/addTrade")
	public Trade saveTrade(@RequestBody Trade trade) {
		if(trade.getMaturityDate().before(new Date())) {
			throw new TradeSystemException("Trade maturity date is lesser then today's date.");
		}
		return tradeRepository.save(trade);
	}
	/**
	 * This method update existing trade associated to version, and method accepts trade updated values. 
	 * @param trade
	 * @return
	 */
	@PutMapping("/trade/{version}")
	public ResponseEntity<Trade> updateTrade(@RequestBody Trade trade) {
		Trade existingTradeDetails = tradeRepository.findById(trade.getVersion()).orElseThrow(() -> new TradeSystemException("No trade associated to provided version."));
		if(trade.getVersion() < tradeRepository.maxVersoin() && trade.getExpired().equals(TradeConstants.TRADE_EXPIRED)) {
			throw new TradeSystemException("Received lower version of trade which is already expired.");
		}
		existingTradeDetails.setBookId(trade.getBookId());
		existingTradeDetails.setCounterPartyId(trade.getCounterPartyId());
		existingTradeDetails.setCreatedDate(trade.getCreatedDate());
		existingTradeDetails.setExpired(trade.getExpired());
		existingTradeDetails.setMaturityDate(trade.getMaturityDate());
		existingTradeDetails.setTradeId(trade.getTradeId());
		return ResponseEntity.ok(tradeRepository.save(existingTradeDetails));
	}
}
