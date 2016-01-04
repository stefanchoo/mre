package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.RehabExperience;
import com.mre.service.RehabExperienceService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RehabExperienceServiceImpl extends DaoSupportImpl<RehabExperience>
		implements RehabExperienceService {
	
	/**
	 * 按照时间降序排列
	 */
	@Override
	public List<RehabExperience> findAllByAuthorId(Long id) {
		return getSession().createQuery( //
				"FROM RehabExperience r WHERE r.author.id = " + id +" ORDER BY r.postTime DESC")//
				.list();
	}

}
