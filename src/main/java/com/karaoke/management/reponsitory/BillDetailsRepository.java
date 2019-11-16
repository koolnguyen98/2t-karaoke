package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.BillDetails;
import com.karaoke.management.entity.BillDetailsKey;
import com.karaoke.management.entity.Food;

@Repository("billDetailsRepository")
public interface BillDetailsRepository extends JpaRepository<BillDetails, BillDetailsKey> {
	List<BillDetails> findByBill(Bill bill);
	List<BillDetails> findByFood(Food food);
	//BillDetails findByBillDetail(BillDetailsKey billDetailsId);
	boolean existsByFood(Food food);
	BillDetails findByBillAndFood(Bill bill, Food food);	
}
