package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.CentrobotRehabPlan;

public interface CentrobotRehabPlanService extends DaoSupport<CentrobotRehabPlan> {
	
	/**
	 * 获取上7个未完成的计划
	 * @param patientId
	 * @return
	 */
	List<CentrobotRehabPlan> getLast7PlansByPatientId(Long patientId);
	
	/**
	 * 根据医生ID获取所有的设备康复计划
	 * @param id
	 * @return
	 */
	List<CentrobotRehabPlan> getAllByDoctorId(Long id);
	
	/**
	 * 根据治疗师ID获取所有的设备康复计划
	 * @param id
	 * @return
	 */
	List<CentrobotRehabPlan> getAllByTherapistId(Long id);
	
	/**
	 * 根据患者ID获取完成的康复计划
	 * @param patientId
	 * @return
	 */
	List<CentrobotRehabPlan> getCompletedPlansByPatientId(Long patientId);
	
}
