package com.mre.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Role;
import com.mre.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements
		RoleService {

	@Override
	public Role getByName(String name) {
		return (Role) getSession().createQuery(
				"FROM Role r WHERE r.name='"+name+"'")
				.uniqueResult();
	}

}
