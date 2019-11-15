package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.UserAccount;

@Repository("userAccountRepository")
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	UserAccount findById(int id);
	UserAccount findByUserName(String username);
	List<UserAccount> findByName(String name);
	List<UserAccount> findByRoleLevel(int roleLevel);
	boolean existsByUserName(String username);
	
}
