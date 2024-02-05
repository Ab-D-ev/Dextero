package com.example.springboot.Expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AllExpensesRepository extends JpaRepository<AllExpenses,Integer>{


	@Query("delete from AllExpenses as f where f.id=?1") 
	void deleteFileByImage(Integer id, String receipt);
	
}
