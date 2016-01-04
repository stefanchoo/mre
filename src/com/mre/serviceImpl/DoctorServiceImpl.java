package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Doctor;
import com.mre.service.DoctorService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DoctorServiceImpl extends DaoSupportImpl<Doctor> implements
		DoctorService {

	@Override
	public Doctor findByLoginNameAndPassword(String loginName, String password) {
		return (Doctor) getSession().createQuery(
				"FROM Doctor d WHERE d.loginName='" + loginName
						+ "' AND d.password='" + password + "'").uniqueResult();
	}

	@Override
	public List<Doctor> findAllByHospitalId(Long id) {
		return getSession().createQuery(//
				"FROM Doctor d WHERE d.hospital.id=" + id)//
				.list();
	}

	@Override
	public Doctor findByOpenid(String openid) {
		return (Doctor) getSession().createQuery( //
				"FROM Doctor d WHERE d.openId = '" + openid +"'")//
				.uniqueResult();
	}
}
