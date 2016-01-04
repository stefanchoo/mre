package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.EquipmentUsed;
import com.mre.service.EquipmentUsedService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class EquipmentUsedImpl extends DaoSupportImpl<EquipmentUsed> implements
		EquipmentUsedService {

	
	@Override
	public List<EquipmentUsed> findAllByHospitalId(Long id) {
		return getSession().createQuery(//
				"FROM EquipmentUsed e WHERE e.hospital.id="+id)//
				.list();
	}

	@Override
	public List<EquipmentUsed> findAllByTherapistId(Long id) {
		return getSession().createQuery( //
				"FROM EquipmentUsed e WHERE e.therapist.id="+id) //
				.list(); 
	}

	@Override
	public List<EquipmentUsed> getByTherapistOpenid(String openid) {
		 return getSession().createQuery( //
				"FROM EquipmentUsed e WHERE e.therapist.openId='"+openid+"'") //
				.list(); 
	}
}
