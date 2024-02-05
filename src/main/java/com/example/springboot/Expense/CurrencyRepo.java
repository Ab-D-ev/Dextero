package com.example.springboot.Expense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Integer>{

	
}
