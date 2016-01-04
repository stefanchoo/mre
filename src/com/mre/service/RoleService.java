package com.mre.service;

import com.mre.base.DaoSupport;
import com.mre.domain.Role;

public interface RoleService extends DaoSupport<Role>{
	/**
	 * 根据名称获取角色
	 * @param name
	 * @return
	 */
	public Role getByName(String name);
}
