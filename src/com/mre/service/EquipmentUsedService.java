package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.EquipmentUsed;

public interface EquipmentUsedService extends DaoSupport<EquipmentUsed> {
	
	/**
	 * 根据医院的id来查询所有的equipmentUsed
	 * @param id
	 * @return
	 */
	List<EquipmentUsed> findAllByHospitalId(Long id);
	
	/**
	 * 根据治疗师是的id来获取设备列表
	 * @return
	 */
	List<EquipmentUsed> findAllByTherapistId(Long id);
	
	/**
	 * 根据治疗师的openid来获取设备列表
	 * @param openid
	 * @return
	 */
	List<EquipmentUsed> getByTherapistOpenid(String openid);
}
