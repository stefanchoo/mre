package com.mre.serviceImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.CentrobotRehabPlan;
import com.mre.service.CentrobotRehabPlanService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CentrobotRehabPlanServiceImpl extends
		DaoSupportImpl<CentrobotRehabPlan> implements CentrobotRehabPlanService {

	@Override
	public List<CentrobotRehabPlan> getLast7PlansByPatientId(Long patientId) {
		List<CentrobotRehabPlan> list = getSession()
				.createQuery(//
						"FROM CentrobotRehabPlan c WHERE c.rehabPlan.completed = false AND c.rehabPlan.patient.id="
								+ patientId + " ORDER BY c.rehabPlan.time DESC")//
				.setFirstResult(0).setMaxResults(7).list();

		// 再将数据反向排序
		Collections.reverse(list);
		return list;
	}

	@Override
	public List<CentrobotRehabPlan> getAllByDoctorId(Long id) {
		return getSession().createQuery( //
				"FROM CentrobotRehabPlan c WHERE c.rehabPlan.doctor.id=" + id)//
				.list();
	}

	@Override
	public List<CentrobotRehabPlan> getAllByTherapistId(Long id) {
		return getSession().createQuery( //
				"FROM CentrobotRehabPlan c WHERE c.rehabPlan.equipment.therapist.id="
						+ id)//
				.list();
	}

	@Override
	public List<CentrobotRehabPlan> getCompletedPlansByPatientId(Long patientId) {
		return getSession()
				.createQuery( //
						"FROM CentrobotRehabPlan c WHERE c.rehabPlan.completed = true AND c.rehabPlan.patient.id="
								+ patientId + " ORDER BY c.rehabPlan.time DESC")//
				.list();
	}
}
