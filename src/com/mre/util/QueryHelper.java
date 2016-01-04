package com.mre.util;

import java.util.ArrayList;
import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.util.domain.PageBean;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用来拼接HQL语句
 * 
 * @author Administrator
 * 
 */
public class QueryHelper {
	private String fromClause; // FROM 子句 ------必须有
	private String whereClause = ""; // WHERE 子句 ------可选
										// 注意这里不要设置为NULL，因为在叠加的时候，HQL语句中会出现NULL
	private String orderByClause = ""; // ORDER BY 子句----可选

	// 用来 ? 存储参数
	private List<Object> parameters = new ArrayList<Object>();

	/**
	 * 用来拼接 FROM 子句
	 * 
	 * @param clazz
	 *            类名
	 * @param sc
	 *            类的缩写
	 */
	@SuppressWarnings("rawtypes")
	public QueryHelper(Class clazz, String sc) {
		// ----> FROM Topic t <----
		fromClause = "FROM " + clazz.getSimpleName() + " " + sc;
	}

	/**
	 * 用来拼接 WHERE 子句
	 * 
	 * @param propertyName
	 *            条件名
	 * @param params
	 *            条件中? 对应的参数值
	 */
	public QueryHelper addCondition(String condition, Object... params) {
		if (whereClause.length() == 0) { // 如何是第一个条件语句，则使用" WHERE"
			// ----> WHERE t.forum=? <-----
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		// 存储 ? 对应的参
		if (params != null) {
			for (Object p : params) {
				parameters.add(p);
			}
		}
		return this;
	}

	/**
	 * 用来拼接 WHERE 子句
	 * 
	 * @param append
	 *            判断条件是否成立
	 * @param propertyName
	 *            条件名
	 * @param params
	 *            条件中? 对应的参数值
	 * @return QueryHelper 类似于StringBuffer, 可以实现链式编程
	 */
	public QueryHelper addCondition(boolean append, String condition,
			Object... params) {
		if (append) {
			if (whereClause.length() == 0) { // 如何是第一个条件语句，则使用" WHERE"
				// ----> WHERE t.forum=? <-----
				whereClause = " WHERE " + condition;
			} else {
				whereClause += " AND " + condition;
			}

			// 存储 ? 对应的参数
			if (params != null) {
				for (Object p : params) {
					parameters.add(p);
				}
			}
		}
		return this;
	}

	/**
	 * 用来拼接 ORDER BY 子句
	 * 
	 * @param propertyName
	 *            条件名
	 * @param asc
	 *            true 升序， false 降序
	 */
	public QueryHelper addOrderProperty(String propertyName, boolean asc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName
					+ (asc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
		}
		return this;
	}

	/**
	 * 用来拼接 ORDER BY 子句
	 * @param append
	 * @param propertyName
	 * @param asc
	 * @return QueryHelper
	 */
	public QueryHelper addOrderProperty(boolean append, String propertyName,
			boolean asc) {
		if (append) {
			if (orderByClause.length() == 0) {
				orderByClause = " ORDER BY " + propertyName
						+ (asc ? " ASC" : " DESC");
			} else {
				orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
			}
		}
		return this;
	}

	/**
	 * 提供一个可以获取参数的函数
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * 用来获取完整的hql语句的函数
	 * 
	 * @return
	 */
	public String getHqlQuery() {
		return fromClause + whereClause + orderByClause;
	}

	/**
	 * 用来获取countQuery的语句函数
	 * 
	 * @return
	 */
	public String getCountQuery() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	/**
	 * 准备pageBean
	 * @param service
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public QueryHelper preparePageBean(DaoSupport<?> service, int pageNow, int pageSize) {
		PageBean pageBean = service.getPageBean(pageNow, pageSize, this);
		ActionContext.getContext().getValueStack().push(pageBean);
		return this;
	}
}
