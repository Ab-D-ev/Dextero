package com.example.springboot.API;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.springboot.TimeSheet.TimeSheetReportService;
import com.example.springboot.TimeSheet.TimeSheetSchedular;

@Component
public class API {

	@Autowired
	private TimeSheetSchedular timeSheetSchedular;
	
	@Autowired
    private TimeSheetReportService timeSheetReportService;
		
	@Value("${TimeSheet-MailReport-Status}")
    private String TimeSheet_MailReport_Status;
	
//	Fires at 6 AM every day
//	@Scheduled(fixedDelay = 2*60*1000)
//	@Scheduled(cron = "0 0 6 * * ?")
//	@Scheduled(cron = "0 26 16 * * ?") // 3.10pm
	@Scheduled(cron = "0 50 7 ? * 1-6", zone = "UTC")
	public void run() throws IOException, NullPointerException, Exception {
//	Timsheet
		if(TimeSheet_MailReport_Status.equals("YES")) {
			timeSheetSchedular.daily_update();
		}
		if(TimeSheet_MailReport_Status.equals("YES")) {
			timeSheetSchedular.new_data();
		}
		
		if(TimeSheet_MailReport_Status.equals("YES")) {
			timeSheetSchedular.SendMail();
		}
		
		if(TimeSheet_MailReport_Status.equals("YES")) {
			 timeSheetSchedular.dailyShiftData();
		 }
	}
	
}
