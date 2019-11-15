package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Menu;

@Repository("menuRepository")
public interface MenuRepository extends JpaRepository<Menu, Integer> {
	Menu findByMenuId(int menuId);
	Menu findByEatingName(String menuName);
	List<Menu> findByPrice(int price);
	boolean existsByEatingName(String eatingName);
}
