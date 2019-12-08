package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Food;

@Repository("menuRepository")
public interface FoodRepository extends JpaRepository<Food, Integer> {
	
	Food findByEatingName(String menuName);
	List<Food> findByPrice(int price);
	boolean existsByEatingName(String eatingName);
	
	@Query(value = "SELECT * FROM food WHERE food.id = ?1 and food.is_delete != true", nativeQuery = true)
	Food findByFoodId(int foodId);
	
	@Query(value = "SELECT * FROM food WHERE food.is_delete != true", nativeQuery = true)
	List<Food> findAllFood();
	
	@Query(value = "SELECT * FROM food WHERE food.id != ?2 and food.food_name = ?1", nativeQuery = true)
	List<Food> existsByEatingName(String eatingName, int id);
}
