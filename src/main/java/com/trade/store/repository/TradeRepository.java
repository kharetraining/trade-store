/**
 * 
 */
package com.trade.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trade.store.model.Trade;

/**
 * @author akhalesh
 * Class represents database layer, provides all curd operations to manage trades flow into store.
 *
 */
public interface TradeRepository extends JpaRepository<Trade, Integer>{
	@Query(value="select max(version) FROM TRADE")
	public int maxVersoin();

}
