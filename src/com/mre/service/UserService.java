package com.mre.service;

import java.util.Map;

import com.mre.base.DaoSupport;
import com.mre.domain.User;
import com.mre.util.domain.SaveBean;

/**
 * 这个用来实现查_User 的表
 * @author Administrator
 *
 */
public interface UserService extends DaoSupport<User> {
	/**
	 * 注册时，查询用户名是否重复，在整张表中查询
	 * @param loginName
	 * @return
	 */
	public boolean findByLoginName(String loginName);
	
	/**
	 * 登录时，验证用户,通过先取出discriminator 然后将查询结果设置保存起来
	 * @param saveBean    : 设置保存的量
	 * @param loginName
	 * @param password    : MD5算法加密之后
	 * @return
	 */
	@Deprecated
	public boolean findByLoginNameAndPassword(String loginName, String password, SaveBean saveBean);
	
	/**
	 * 登录时，验证用户，使用HQL多态查询
	 * @param loginName
	 * @param password
	 * @param saveBean
	 * @return 错误信息
	 */
	public String validateUser(String loginName, String password, SaveBean saveBean);
	
	/**
	 * 退出和保存用户相关信息，用户注销的时候调用
	 * @param saveBean
	 * @param object              : User Patient Doctor Therapist
	 */
	public void quitAndUpdateUser(SaveBean saveBean, Object object);
	
	/**
	 * 获取管理员
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(String loginName);
	
	/**
	 * 验证并更新用户
	 * @param username
	 * @param password
	 * @param openid
	 * @return
	 */
	public Map<String, Object> bindAccountAndGetIdentity(String username, String password, String openid);
	
	/**
	 * 根据Openid获取User, 并返回用户信息
	 * @param openid
	 * @return
	 */
	public Map<String, Object> getByOpenid(String openid);
}
