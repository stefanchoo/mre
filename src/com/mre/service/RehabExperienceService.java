package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.RehabExperience;

public interface RehabExperienceService extends DaoSupport<RehabExperience> {
	/**
	 * 根据作者获取康复经验
	 * @param id
	 * @return
	 */
	List<RehabExperience> findAllByAuthorId(Long id);
	
}
