package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.Therapist;

public interface TherapistService extends DaoSupport<Therapist> {
	/**
	 * 验证登录的治疗师
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Deprecated
	public Therapist findByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 查询同一家医院的治疗师
	 * @param id
	 * @return
	 */
	public List<Therapist> findAllByHospitalId(Long id);
	
	/**
	 * 根据openid获取治疗师
	 * @param openid
	 * @return
	 */
	public Therapist findByOpenid(String openid);
}
