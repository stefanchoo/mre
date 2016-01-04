package com.mre.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Doctor;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.Patient;
import com.mre.domain.RehabPlan;
import com.mre.domain.Therapist;
import com.mre.service.RehabPlanService;
import com.mre.util.Formatter;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RehabPlanServiceImpl extends DaoSupportImpl<RehabPlan> implements
		RehabPlanService {

	@Override
	public List<RehabPlan> findAllByDoctorId(Long id) {
		return getSession().createQuery( //
				"FROM RehabPlan r WHERE r.doctor.id=" + id
						+ " ORDER BY r.time DESC")//
				.list();
	}

	@Override
	public List<RehabPlan> findByHQL(String hqlQuery, List<Object> parameters) {
		Query listQuery = getSession().createQuery(hqlQuery);
		int index = 0;
		// 设置多个参数
		for (Object parameter : parameters) {
			listQuery.setParameter(index++, parameter);
		}
		return listQuery.list();
	}

	@Override
	public List<RehabPlan> getTodayRehabPlansByDoctor(Long id) {
		// 注意使用SQL 需要转化为实体
		return getSession()
				.createSQLQuery(//
						"SELECT * FROM _rehabplan WHERE DATE(time) = CURDATE() AND doctorId="
								+ id)//
				.addEntity(RehabPlan.class).list();
	}

	@Override
	public List<RehabPlan> getTodayRehabPlansByPatient(Long id) {
		return getSession()
				.createSQLQuery(//
						"SELECT * FROM _rehabplan WHERE DATE(time) = CURDATE() AND patientId="
								+ id)//
				.addEntity(RehabPlan.class).list();
	}
	
	@Override
	public List<RehabPlan> getTodayRehabPlansByEquipment(Long id) {
		return getSession()
				.createSQLQuery(//
						"SELECT * FROM _rehabplan WHERE DATE(time) = CURDATE() AND equipmentId="
								+ id + " AND completed = 0 ORDER BY time ASC") // 按照时间升序排列 
				.addEntity(RehabPlan.class).list();
	}

	@Override
	public int getTodayRehabPlanCount(String discriminator, Object user) {
		// 注意使用原声SQL语句进行查询时，返回的是bigInteger类型, 使用HQL语句则返回的是Long
		// BigInteger todayRehabPlanCount = BigInteger.ZERO;

		Long todayRehabPlanCount = 0L;
		if (discriminator.equals("doctor")) {
			Doctor doctor = (Doctor) user;
			/** SQL查询 **/
			/*
			 * todayRehabPlanCount = (BigInteger) getSession().createSQLQuery(//
			 * "SELECT COUNT(*) FROM _RehabPlan WHERE DATE(time) = CURDATE() AND doctorId="
			 * +doctor.getId()) .uniqueResult();
			 */
			/** HQL查询 **/
			todayRehabPlanCount = (Long) getSession()
					.createQuery(//
						"SELECT COUNT(*) FROM RehabPlan  r WHERE DATE(r.time) = CURDATE() "
						+ "AND r.doctor.id="
									+ doctor.getId()).uniqueResult();
		} else if (discriminator.equals("patient")) {
			Patient patient = (Patient) user;
			/** SQL查询 **/
			/*
			 * todayRehabPlanCount = (BigInteger)getSession().createSQLQuery(//
			 * "SELECT COUNT(*) FROM _RehabPlan WHERE DATE(time) = CURDATE() AND patientId="
			 * +patient.getId()) .uniqueResult();
			 */
			todayRehabPlanCount = (Long) getSession()
					.createQuery(//
							"SELECT COUNT(*) FROM RehabPlan r WHERE DATE(time) = CURDATE() AND r.patient.id="
									+ patient.getId()).uniqueResult();
		} else if (discriminator.equals("therapist")) {
			Therapist therapist = (Therapist) user;
			todayRehabPlanCount = (Long) getSession()
					.createQuery(//
							"SELECT COUNT(*) FROM RehabPlan r WHERE DATE(r.time) = CURDATE() AND r.equipment.therapist.id="
									+ therapist.getId()).uniqueResult();
		}

		return todayRehabPlanCount.intValue();
	}

	@Override
	public List<RehabPlan> getCompletedRehabPlansByPatient(Long id) {
		return getSession().createQuery(//
				"FROM RehabPlan r WHERE r.completed=true AND r.patient.id="+id)//
				.list();
	}

	@Override
	public List<RehabPlan> getNotCompletedRehabPlansByPatient(Long id) {
		return getSession().createQuery(//
				"FROM RehabPlan r WHERE r.completed=false AND r.patient.id="+id)//
				.list();
	}

	@Override
	public List<RehabPlan> getTodayRehabPlansByTherapist(Therapist user) {
		// 1. 实现想法： 遍历治疗师手下的设备，再遍历每个设备对应的今日计划，然后整合成一个List
		List<RehabPlan> rehabPlans = new ArrayList<RehabPlan>();
		for(EquipmentUsed e : user.getEquipments()){
			for(RehabPlan r : this.getTodayRehabPlansByEquipment(e.getId())){
				rehabPlans.add(r);
			}
		}
		return rehabPlans;
	}

	@Override
	public List<RehabPlan> getTodayRehabPlanListByOpenid(String openid,
			String identity) {
		List<RehabPlan> rehabPlansList = new ArrayList<RehabPlan>();
		if(identity.equals("doctor")){
			// 使用HQL语句，使用mysql的date_format(date, pattern)来判断时间是否是今天
			rehabPlansList = getSession().createQuery( //
					"FROM RehabPlan r WHERE r.doctor.openId=:openid AND date_format(r.time, '%Y-%m-%d')=:today")//
					.setParameter("openid", openid)
					.setParameter("today", Formatter.formatWithPattern(new Date(), "yyyy-MM-dd"))
					.list();
		}else if(identity.equals("patient")){
			rehabPlansList = getSession().createQuery( //
					"FROM RehabPlan r WHERE r.patient.openId=:openid AND date_format(r.time, '%Y-%m-%d')=:today")//
					.setParameter("openid", openid)
					.setParameter("today", Formatter.formatWithPattern(new Date(), "yyyy-MM-dd"))
					.list();
		}else if(identity.equals("therapist")){
			rehabPlansList = getSession().createQuery( //
					"FROM RehabPlan r WHERE r.equipment.therapist.openId=:openid AND date_format(r.time, '%Y-%m-%d')=:today")//
					.setParameter("openid", openid)
					.setParameter("today", Formatter.formatWithPattern(new Date(), "yyyy-MM-dd"))
					.list();
		}
		return rehabPlansList;
	}

	@Override
	public List<RehabPlan> get7ByPatientId(Long id) {
		return getSession().createQuery( //
				"FROM RehabPlan r WHERE r.patient.id=" + id + " ORDER BY r.time DESC") //
				.setFirstResult(0)
				.setMaxResults(7)
				.list();
	}

}
