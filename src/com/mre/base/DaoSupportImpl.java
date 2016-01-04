package com.mre.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mre.util.QueryHelper;
import com.mre.util.domain.PageBean;


//@Transactional注解可以被继承
//@Transactional注解对父类中声明的方法无效
@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {
	@Resource
	private SessionFactory sessionFactory;

	private Class<T> clazz; // 这是一个问题！不知道是那个类？

	public DaoSupportImpl() {
		// 使用反射类得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();// 获取当前new对象父类泛型的类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
		/* System.out.println("clazz--->"+ clazz); */
	}

	/**
	 * 获取当前的Session, protected 类型表示子类可以访问，这样就可以省去sessionFactory的注入
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public void deleteById(Long id) {
		Object object = getById(id);
		if (object != null) {
			getSession().delete(object);
		}
	}

	@Override
	public T getById(Long id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			// 注意这里返回Collection empty的集合，避免报空指针异常
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(//
					"FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")//
					.setParameterList("ids", ids).list();
		}
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery( //
				"FROM " + clazz.getSimpleName()) //
				.list();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBean(int pageNow, int pageSize, QueryHelper queryHelper) {
		
		// 查询记录列表
		Query listQuery = getSession().createQuery(queryHelper.getHqlQuery());
		
		// 查询总数量
		Query countQuery = getSession().createQuery(queryHelper.getCountQuery());
		int index = 0;
		// 设置多个参数
		for(Object parameter : queryHelper.getParameters()){
			listQuery.setParameter(index, parameter);
			countQuery.setParameter(index++, parameter);
		}
		listQuery.setFirstResult((pageNow - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		// 查询列表
		List recordList = listQuery.list(); 

		// 查询记录
		Long recordCount = (Long) countQuery.uniqueResult();
		return new PageBean(pageNow, pageSize, recordList,
				recordCount.intValue());
	}
}
