package com.trade.store.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.trade.store.constant.TradeConstants;
import com.trade.store.exception.TradeSystemException;
import com.trade.store.model.Trade;
import com.trade.store.repository.TradeRepository;

/**
 * @author akhalesh
 *
 * Class provides implementation to all TradeService methodes.
 */
@Service
public class TradeServiceImpl implements TradeService {

	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Override
	public List<Trade> findTrades() {
		return tradeRepository.findAll();
	}

	/* Service implementation of adding Trade into Store.
	 * @see com.trade.store.service.TradeService#addTrade(com.trade.store.model.Trade)
	 */
	@Override
	public Trade addTrade(Trade trade) {
		//Maturity date compare with current date 
		dateCompare(trade);
		return tradeRepository.save(trade);
	}

	/**
	 * @param trade
	 * Method provides compares dates and raise exception if found maturity date is lesser then today's date. 
	 */
	private void dateCompare(Trade trade) throws TradeSystemException{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();
		calendar.set(Calendar.YEAR, trade.getMaturityDate().getYear());
		calendar.set(Calendar.YEAR, trade.getMaturityDate().getMonth());
		calendar.set(Calendar.YEAR, trade.getMaturityDate().getDay());
		Date maturityDate = calendar.getTime();
		if(maturityDate.before(today)) {
			throw new TradeSystemException("Trade maturity date is lesser then today's date.");
		}
	}

	/* Service implementation to update existing Trade.
	 * @see com.trade.store.service.TradeService#updateTrade(com.trade.store.model.Trade)
	 */
	@Override
	public ResponseEntity<Trade> updateTrade(int version, Trade trade) throws TradeSystemException {
		Trade existingTradeDetails = tradeRepository.findById(version).orElseThrow(() -> new TradeSystemException("No trade associated to provided version."));
		if(existingTradeDetails.getVersion() < tradeRepository.maxVersoin() && existingTradeDetails.getExpired().equals(TradeConstants.TRADE_EXPIRED)) {
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
