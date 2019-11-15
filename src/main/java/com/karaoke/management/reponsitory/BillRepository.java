package com.karaoke.management.reponsitory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.UserAccount;

@Repository("billRepository")
public interface BillRepository extends JpaRepository<Bill, Integer> {
	Bill findByBillId(int billId);
	List<Bill> findByCheckin(Date checkin);
	List<Bill> findByCheckout(Date checkout);
	List<Bill> findByTotal(double total);
	List<Bill> findByUserAccount(UserAccount userAccount);
	Bill findByRoom(Room room);
	boolean existsByRoom(Room room);
	Bill findFirstByRoomOrderByBillIdDesc(Room room);	
}
