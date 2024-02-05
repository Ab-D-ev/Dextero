package com.example.springboot.Holidays;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface HolidayService {
	
	List<AddHolidays> getAllHolidays();
	void saveHolidays(AddHolidays holiday);
	
	AddHolidays getHolidaysById(long id);
	void deleteHolidaysById(long id);
}
