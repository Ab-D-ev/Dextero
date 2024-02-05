package com.example.springboot.Leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeavePolicyRepo extends JpaRepository<LeavePolicy, Integer>{
	
	@Query("SELECT lp FROM LeavePolicy lp WHERE lp.year = :year AND lp.company_id = :companyId")
    public LeavePolicy findLeavePolicyByYearAndCompanyId(@Param("year") Integer year, @Param("companyId") Integer companyId);

	@Query(nativeQuery = true, value ="select leaves_carried_forward FROM leave_policy WHERE company_id =?1 AND  year =?2")
    public Integer findLeaveCarriedForward(Integer company_id, Integer year);
	
	@Query(nativeQuery = true, value ="select paid_leave FROM leave_policy WHERE company_id =?1 AND year =?2")
    public Integer findMonthlyPaidLeaveCount(Integer company_id, Integer year);

}
