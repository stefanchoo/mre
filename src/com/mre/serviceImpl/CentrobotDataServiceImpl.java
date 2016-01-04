package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.CentrobotData;
import com.mre.service.CentrobotDataService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CentrobotDataServiceImpl extends DaoSupportImpl<CentrobotData>
		implements CentrobotDataService {

	@Override
	public List<CentrobotData> get15DataByRehabPlanId(Long rehabPlanId) {		
		return (List<CentrobotData>)getSession().createQuery( //
				"FROM CentrobotData c WHERE c.centrobotRehabPlan.id="+rehabPlanId+" ORDER BY c.samplingTime DESC")//
				.setFirstResult(0)
				.setMaxResults(15)
				.list();
	}

	@Override
	public List<CentrobotData> get15DataBySamplingTimeAndRehabPlanId(
			float samplingTime, Long rehabPlanId) {
		return (List<CentrobotData>)getSession().createQuery( //
				"FROM CentrobotData c WHERE c.centrobotRehabPlan.id="+rehabPlanId+" AND c.samplingTime > "+samplingTime+" ORDER BY c.samplingTime ASC")//
				.setFirstResult(0)
				.setMaxResults(15)
				.list();
	}

	@Override
	public void deleteByRehabPlanId(Long centrobotRehabPlanId) {
		getSession().createSQLQuery( //
				"DELETE FROM mre._centrobotdata WHERE centrobotRehabPlanId=" + centrobotRehabPlanId //           
				); 
	}

	@Override
	public void deleteAfter100(Long centrobotRehabPlanId) {
		getSession().createSQLQuery( //
				"DELETE FROM mre._centrobotdata WHERE centrobotRehabPlanId=" + centrobotRehabPlanId + "AND samplingTime > 100"//           
				); 
	}

	@Override
	public List<CentrobotData> getAllDataByCentrobotDataId(Long rehabPlanId) {
		return (List<CentrobotData>)getSession().createQuery( //
				"FROM CentrobotData c WHERE c.centrobotRehabPlan.id=" + rehabPlanId)//
				.list();
	}
}
