package com.example.springboot.Holidays;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepositroy extends JpaRepository<AddHolidays,Long>{  
	
	@Query(nativeQuery = true, value =" SELECT *  from  addholidays "
			+ "WHERE  DATE_ADD(date_in_years, "
			+ "                INTERVAL YEAR(curdate())-YEAR(date_in_years)"
			+ "                         + IF(DAYOFYEAR(curdate()) > DAYOFYEAR(date_in_years),1,0)YEAR) "
			+ "            BETWEEN curdate() AND DATE_ADD(curdate(), INTERVAL 90 DAY) ORDER BY dates ASC ")
	   public List<AddHolidays> getHolidayList(); 
	
}
