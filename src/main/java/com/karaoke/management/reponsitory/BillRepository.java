package com.karaoke.management.reponsitory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Query(value = "SELECT * FROM bill WHERE bill.checkout >= ?1 and bill.checkout <= ?2", nativeQuery = true)
	List<Bill> findBillFromTo(LocalDateTime fromDate, LocalDateTime toDate);

	List<Bill> findBillByCheckout(LocalDateTime checkoutDate);
	
	@Query(value = "SELECT * FROM bill WHERE bill.room_id = ?1 and bill.checkout IS NULL ORDER BY bill.id DESC LIMIT 1", nativeQuery = true)
	Optional<Bill> checkBillSuccess(int id);

	@Query(value = "SELECT * FROM bill WHERE bill.checkout IS NULL", nativeQuery = true)
	List<Bill> findBillSuccess();
}
