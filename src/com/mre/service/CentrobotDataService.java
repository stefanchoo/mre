package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.CentrobotData;

public interface CentrobotDataService extends DaoSupport<CentrobotData>{
	
	/**
	 * 根据rehabPlanId获取最新的15条数据
	 * @param rehabPlanId
	 * @return
	 */
	List<CentrobotData> get15DataByRehabPlanId(Long rehabPlanId);
	
	/**
	 * 从采样时间点开始每次采集15个
	 * @param samplingTime
	 * @return
	 */

	List<CentrobotData> get15DataBySamplingTimeAndRehabPlanId(
			float samplingTime, Long rehabPlanId);
	
	/**
	 * 根据centrobotRehabPlanId 删除对应的centrobotData
	 * @param centrobotRehabPlanId
	 */
	void deleteByRehabPlanId(Long centrobotRehabPlanId);
	
	/**
	 * 删除 100s以后的数据
	 * @param centrobotRehabPlanId
	 */
	void deleteAfter100(Long centrobotRehabPlanId);
	
	/**
	 * 获取该训练计划剩下的100s的数据
	 * @param rehabPlanId
	 * @return
	 */
	List<CentrobotData> getAllDataByCentrobotDataId(Long rehabPlanId);
}
