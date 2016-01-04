package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Therapist;
import com.mre.service.TherapistService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class TherapistServiceImpl extends DaoSupportImpl<Therapist> implements
		TherapistService {

	@Override
	public Therapist findByLoginNameAndPassword(String loginName,
			String password) {
		return (Therapist) getSession().createQuery(
				"FROM Therapist t WHERE t.loginName='" + loginName
						+ "' AND t.password='" + password + "'").uniqueResult();
	}

	@Override
	public List<Therapist> findAllByHospitalId(Long id) {
		return getSession().createQuery(//
				"FROM Therapist t WHERE t.hospital.id=" + id)//
				.list();
	}

	@Override
	public Therapist findByOpenid(String openid) {
		return (Therapist)getSession().createQuery( //
				"FROM Therapist t WHERE t.openId='" + openid + "'")//
				.uniqueResult();
	}

}
