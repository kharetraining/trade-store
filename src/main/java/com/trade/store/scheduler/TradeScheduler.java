package com.trade.store.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trade.store.constant.TradeConstants;
import com.trade.store.model.Trade;
import com.trade.store.repository.TradeRepository;

/**
 * @author akhalesh
 * This is a scheduler class provides trades scheduling on specific time to update flag. 
 */
@Component
public class TradeScheduler {
	@Autowired
	private TradeRepository tradeRepository;
	
	/**
	 * Scheduler configured on each day at midnight and update expire flag to Y if found trade crosses the maturity date.  
	 */
	@Scheduled(cron ="0 0 * * * *")
	public void run() {
		//fetch list of trigger
		List<Trade> trades = tradeRepository.findAll();
		for(Trade trade : trades) {
			Date maturityDate = trade.getMaturityDate();
			if(maturityDate.after(new Date())) {
				trade.setExpired(TradeConstants.TRADE_EXPIRED);
			}
			// Update trade as expired.
			tradeRepository.save(trade);
		}
	}

}
