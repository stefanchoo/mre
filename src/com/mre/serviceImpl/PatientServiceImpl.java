package com.mre.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Doctor;
import com.mre.domain.Patient;
import com.mre.service.PatientService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PatientServiceImpl extends DaoSupportImpl<Patient> implements
		PatientService {

	@Override
	public Patient findByLoginNameAndPassword(String loginName, String password) {
		return (Patient) getSession().createQuery(
				"FROM Patient p WHERE p.loginName='" + loginName
						+ "' AND p.password='" + password + "'").uniqueResult();
	}

	@Override
	public void updateBasicInfo(Patient model, Patient user) {

		// 设置更新的数据
		user.setTrueName(model.getTrueName());
		user.setGender(model.getGender());
		user.setBirthday(model.getBirthday());
		user.setCity(model.getCity());
		user.setEmail(model.getEmail());
		user.setTelephone(model.getTelephone());
		user.setWeixin(model.getWeixin());

		/* 因为在一个session里所以会自动更新 */
		// this.update(user);
	}

	@Override
	public void changePwd(String newPassword, Patient user) {
		// 更新密码
		user.setPassword(DigestUtils.md5Hex(newPassword));
	}

	@Override
	public Patient findByLoginNameAndTrueName(String loginName, String trueName) {
		return (Patient) getSession().createQuery( //
				"FROM Patient p WHERE p.loginName='" + loginName //
						+ "' AND p.trueName='" + trueName + "'")//
				.uniqueResult();
	}

	@Override
	public List<Patient> findAllByDoctorId(Long id) {
		return getSession().createQuery( //
				"FROM Patient p WHERE p.doctor.id=" + id)//
				.list();
	}

	@Override
	public List<Patient> findAllByDoctorOpenid(String openid) {
		return getSession().createQuery( //
				"FROM Patient p WHERE p.doctor.openId='" + openid + "'")//
				.list();
	}

	@Override
	public Map<String, Object> getPatientByOpenid(String openid) {
		Map<String, Object> result = new HashMap<String, Object>();
		Patient patient = (Patient)getSession().createQuery(
				"FROM Patient p WHERE p.openId='" + openid + "'")
				.uniqueResult();
		if(patient != null){
			Doctor doctor = patient.getDoctor();
			result.put("姓名", doctor.getTrueName());
			result.put("职称", doctor.getTitle());
			result.put("微信", doctor.getWeixin());
			result.put("擅长", doctor.getGoodAt());
		} else {
			result.put("errorMsg", "抱歉，您还没有关联的医生！");
		}
		return result;
	}

	@Override
	public Patient getByNameAndDoctorOpenid(String patientName, String openid) {
		return (Patient)getSession().createQuery(//
				"FROM Patient p WHERE p.trueName=:patientName AND p.doctor.openId=:openid")//
				.setParameter("patientName", patientName)
				.setParameter("openid", openid)
				.uniqueResult();
	}

	@Override
	public Patient getByOpenid(String openid) {
		return (Patient)getSession().createQuery(//
				"FROM Patient p WHERE p.openId='"+openid+"'")//
				.uniqueResult();
	}
}
