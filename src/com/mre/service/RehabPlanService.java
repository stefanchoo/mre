package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.RehabPlan;
import com.mre.domain.Therapist;

public interface RehabPlanService extends DaoSupport<RehabPlan> {
	
	/**
	 * 根据医生的ID 找出所有的康复计划，并以时间顺序显示
	 * @param id
	 * @return
	 */
	List<RehabPlan> findAllByDoctorId(Long id);
	
	/**
	 * 根据HQL 语句查询
	 * @param hqlQuery
	 * @return
	 */
	List<RehabPlan> findByHQL(String hqlQuery, List<Object> parameters);
	
	/**
	 * 医生查询今日计划
	 * 使用SQL 语句
	 * @return
	 */
	List<RehabPlan> getTodayRehabPlansByDoctor(Long id);
	
	/**
	 * 患者查询今日计划
	 * @param id
	 * @return
	 */
	List<RehabPlan> getTodayRehabPlansByPatient(Long id);
	
	/**
	 * 根据设备查询今日计划
	 * @param id
	 * @return
	 */
	List<RehabPlan> getTodayRehabPlansByEquipment(Long id);
	
	/**
	 * 获取今日计划的个数
	 * @param discriminator
	 * @param user
	 * @return
	 */
	int getTodayRehabPlanCount(String discriminator, Object user);
	
	/**
	 * 获取已完成计划列表
	 * @param id
	 * @return
	 */
	List<RehabPlan> getCompletedRehabPlansByPatient(Long id);
	
	/**
	 * 获取未完成的计划列表
	 * @param id
	 * @return
	 */
	List<RehabPlan> getNotCompletedRehabPlansByPatient(Long id);
	
	/**
	 * 根据治疗师来查询今日计划
	 * @param user
	 * @return
	 */
	List<RehabPlan> getTodayRehabPlansByTherapist(Therapist user);
	
	/**
	 * 根据user的openid来查询今日计划
	 * @param openid
	 * @param identity
	 * @return
	 */
	List<RehabPlan> getTodayRehabPlanListByOpenid(String openid, String identity);
	
	/**
	 * 获取最近的7条记录
	 * @param id
	 * @return
	 */
	List<RehabPlan> get7ByPatientId(Long id);

}
