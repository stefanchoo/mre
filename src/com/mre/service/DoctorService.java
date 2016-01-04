package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.Doctor;

public interface DoctorService extends DaoSupport<Doctor> {
	/**
	 * 验证登录的医生
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Doctor findByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 查询同一医院的医生
	 * @param id
	 * @return
	 */
	public List<Doctor> findAllByHospitalId(Long id);
	
	/**
	 * 根据openid获取医生信息
	 * @param openid
	 * @return
	 */
	public Doctor findByOpenid(String openid);
}
