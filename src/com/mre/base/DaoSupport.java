package com.mre.base;

import java.util.List;

import com.mre.util.QueryHelper;
import com.mre.util.domain.PageBean;

public interface DaoSupport<T> {
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 根据Id得到实体
	 * @param id
	 * @return
	 */
	public T getById(Long id);
	
	/**
	 * 查询多个实体 用于处理类似checkbox的情况
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有实体
	 * @return
	 */
	public List<T> findAll();	
	
	/**
	 *  准备分页信息 <最终版>
	 * @param pageNow
	 * @param pageSize
	 * @param queryHelper
	 * @return
	 */
	public PageBean getPageBean(int pageNow, int pageSize, QueryHelper queryHelper);
}
