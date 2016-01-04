package com.mre.service;

import java.util.List;
import java.util.Map;

import com.mre.base.DaoSupport;
import com.mre.domain.Patient;

public interface PatientService extends DaoSupport<Patient> {
	/**
	 * 验证登录的患者
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Deprecated
	public Patient findByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 只更新基本信息
	 * @param model
	 * @param user : 登录的user
	 */
	public void updateBasicInfo(Patient model, Patient user);
	
	/**
	 * 更新密码
	 * @param newPassword ： 新密码
	 * @param user
	 */
	public void changePwd(String newPassword, Patient user);
	
	/**
	 * 通过用户名和真实姓名搜索用户
	 * @param loginName
	 * @param trueName
	 * @return
	 */
	public Patient findByLoginNameAndTrueName(String loginName, String trueName);
	
	/**
	 * 通过医生查询所有的患者
	 * @param id
	 * @return
	 */
	public List<Patient> findAllByDoctorId(Long id);
	
	/**
	 * 通过医生的openid查询患者列表
	 * @param openid
	 * @return
	 */
	public List<Patient> findAllByDoctorOpenid(String openid);
	
	/**
	 * 通过患者的openid来查询患者
	 * @param openid
	 * @return
	 */
	public Map<String, Object> getPatientByOpenid(String openid);
	
	/**
	 * 根据真实姓名和医生的openid来查询用户
	 * @param patientName
	 * @param openid
	 * @return Patient
	 */
	public Patient getByNameAndDoctorOpenid(String patientName,
			String openid);
	
	/**
	 * 根据openid来查询用户
	 * @param string
	 * @return
	 */
	public Patient getByOpenid(String openid);
		
}
