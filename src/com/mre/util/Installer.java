package com.mre.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mre.domain.Doctor;
import com.mre.domain.EquipmentUsed;
import com.mre.domain.Hospital;
import com.mre.domain.Privilege;
import com.mre.domain.Role;
import com.mre.domain.Therapist;
import com.mre.domain.User;

@Component
public class Installer {
	
	@Resource
	private SessionFactory sessionFactory;
	/**
	 * 初始化程序的基本数据，包括超级管理员的账号和密码，以及一些权限数据
	 * 要实现跨数据库
	 */
	@Transactional
	public void install(){
		
		Session session = sessionFactory.getCurrentSession();
		//-----------------------------
			
		//---------------------------------------
		//2. 初始化权限数据
		
		//a. 管理员权限
		Privilege p1 = new Privilege();
		p1.setName("管理员中心");
		p1.setUrl("/admin_");
		
		//b. 医生权限
		
		Privilege p2 = new Privilege();
		p2.setName("医生中心");
		p2.setUrl("/doctor_");
		
		Privilege p3 = new Privilege();
		p3.setName("处方管理");
		p3.setUrl("/prescription_");
		
		
		//c. 治疗师权限
		
		Privilege p4 = new Privilege();
		p4.setName("治疗师中心");
		p4.setUrl("/therapist_");
				
		//d. 患者权限
	
		Privilege p5 = new Privilege();
		p5.setName("患者中心");
		p5.setUrl("/patient_");
		
		//e. 医生和治疗师的控制设备的权限
		Privilege p6 = new Privilege();
		p6.setName("centrobot管理");
		p6.setUrl("/centrobot_");
		
		Privilege p7 = new Privilege();
		p6.setName("设备数据管理");
		p6.setUrl("/dev/centrobotData_");
		
		session.save(p1);
		session.save(p2);		
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		session.save(p7);
		
		//---------------------------------------
		//3. 初始化角色数据
		// role_privilege中间表是由role来主导的，所以在保存role的同时会更新role_privilege表
		Set<Privilege> ps1 = new HashSet<Privilege>();
		ps1.add(p1);
		
		Set<Privilege> ps2 = new HashSet<Privilege>();
		ps2.add(p2);
		ps2.add(p3);
		ps2.add(p6);
		ps2.add(p7);
		
		Set<Privilege> ps3 = new HashSet<Privilege>();
		ps3.add(p4);
		ps3.add(p6);
		ps3.add(p7);
		
		Set<Privilege> ps4 = new HashSet<Privilege>();
		ps4.add(p5);
		
		Role role1 = new Role();
		role1.setName("管理员");
		role1.setPrivileges(ps1);
		
		Role role2 = new Role();
		role2.setName("医生");
		role2.setPrivileges(ps2);
		
		Role role3 = new Role();
		role3.setName("治疗师");
		role3.setPrivileges(ps3);
		
		Role role4 = new Role();
		role4.setName("患者");
		role4.setPrivileges(ps4);
		
		
		session.save(role1);
		session.save(role2);
		session.save(role3);
		session.save(role4);
		
		// 增加一条医院数据
		Hospital hospital = new Hospital();
		hospital.setName("新华医院");
		hospital.setLevel("三级甲等");
		session.save(hospital);
		
		
		// 增加一个测试用的医生和治疗师
		Set<Role> roles1 = new HashSet<Role>();
		roles1.add(role1);
		
		Set<Role> roles2 = new HashSet<Role>();
		roles2.add(role2);
		
		Set<Role> roles3 = new HashSet<Role>();
		roles3.add(role3);
		
		//1. 存储管理员的数据
		User admin = new User();
		admin.setLoginName("mreAdmin");
		admin.setPassword(DigestUtils.md5Hex("mreAdmin"));
		admin.setIcon("default.png");
		admin.setTrueName("超级管理员");
		admin.setRoles(roles1);
		session.save(admin);
		
		Doctor doctor = new Doctor();
		doctor.setLoginName("testDoc");
		doctor.setTrueName("医生");
		doctor.setIcon("doctor.png");
		doctor.setRegisterTime(new Date());
		doctor.setHospital(hospital);
		doctor.setPassword(DigestUtils.md5Hex("testDoc"));
		doctor.setRoles(roles2);
		session.save(doctor);
		
		Therapist therapist = new Therapist();
		therapist.setLoginName("testThe");
		therapist.setTrueName("治疗师");
		therapist.setIcon("doctor.png");
		therapist.setRegisterTime(new Date());
		therapist.setHospital(hospital);
		therapist.setPassword(DigestUtils.md5Hex("testThe")); 
		therapist.setRoles(roles3);
		session.save(therapist);
		
		// 增加两条康复设备
		EquipmentUsed u1 = new EquipmentUsed();
		u1.setName("centrobot上肢康复训练机器人");
		u1.setNumber("sz-centrobot-0001");
		u1.setType("上肢康复训练");
		u1.setTherapist(therapist);
		u1.setHospital(hospital);
		
		EquipmentUsed u2 = new EquipmentUsed();
		u2.setName("rehand手部功能训练器");
		u2.setNumber("sb-rehand-0001");
		u2.setType("手部康复训练");
		u2.setTherapist(therapist);
		u2.setHospital(hospital);
		
		session.save(u1);
		session.save(u2);
	}
		/**
		 * 此方法用于在程序安装之前启动，初始化基本数据
		 */
		@SuppressWarnings("resource")
		public static void main(String[] args) {
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			Installer installer = (Installer)ac.getBean("installer");
			installer.install();
		}
}
