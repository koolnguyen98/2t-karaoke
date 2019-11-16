package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Food;

@Repository("menuRepository")
public interface FoodRepository extends JpaRepository<Food, Integer> {
	Food findByFoodId(int menuId);
	Food findByEatingName(String menuName);
	List<Food> findByPrice(int price);
	boolean existsByEatingName(String eatingName);
}
