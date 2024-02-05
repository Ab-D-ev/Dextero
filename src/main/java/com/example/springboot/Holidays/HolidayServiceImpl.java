package com.example.springboot.Holidays;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService{
	
	@Autowired
	private HolidayRepositroy holidayRepositroy;

	@Override
	public List<AddHolidays> getAllHolidays() {
		return holidayRepositroy.findAll();
		
	}

	@Override
	public void saveHolidays(AddHolidays holiday) {
		this.holidayRepositroy.save(holiday);
		
	}

	@Override
	public AddHolidays getHolidaysById(long id) {
		java.util.Optional<AddHolidays> optional = holidayRepositroy.findById(id);
		AddHolidays  holiday=null;
		if(optional.isPresent()) {
			 holiday=optional.get();
		}else {
			throw new RuntimeException(" id not found"+id);
		}
		return  holiday;
	}

	@Override
	public void deleteHolidaysById(long id) {
		this.holidayRepositroy.deleteById(id);
		
	}

	public List<AddHolidays> getHolidayList(){
		return holidayRepositroy.getHolidayList();
	}
	
	
}
