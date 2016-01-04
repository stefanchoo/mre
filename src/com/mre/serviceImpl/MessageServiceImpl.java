package com.mre.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mre.base.DaoSupportImpl;
import com.mre.domain.Message;
import com.mre.service.MessageService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class MessageServiceImpl extends DaoSupportImpl<Message> implements
		MessageService {

	@Override
	public List<Message> getAllByToUserId(Long id, boolean isDeleteByReceiver) {
		return getSession().createQuery(//
				"FROM Message m WHERE m.toUser.id=" + id
						+ " AND m.deleteByReceiver= " + isDeleteByReceiver
						+ " ORDER BY m.postTime DESC")//
				.list();
	}

	@Override
	public List<Message> getAllByFromUserId(Long id, boolean isDeleteBySender) {
		return getSession().createQuery(//
				"FROM Message m WHERE m.fromUser.id=" + id
						+ " AND m.deleteBySender= " + isDeleteBySender
						+ " ORDER BY m.postTime DESC")//
				.list();
	}

	@Override
	public int getMessageCountByToUserId(Long id, boolean isDeleteByReceiver,
			boolean isReadByReceiver) {
		return ((Long) getSession().createQuery( //
				"SELECT COUNT(*) FROM Message m WHERE m.toUser.id=" + id
						+ " AND m.deleteByReceiver=" + isDeleteByReceiver
						+ " AND m.readByReceiver=" + isReadByReceiver) //
				.uniqueResult()).intValue();
	}

	@Override
	public List<Message> getByToUserOpenid(String openid,
			boolean isDeleteByReceiver, boolean isReadByReceiver) {
		return getSession().createQuery( //
				"FROM Message m WHERE m.toUser.openId='" + openid
						+ "' AND m.deleteByReceiver=" + isDeleteByReceiver
						+ " AND m.readByReceiver=" + isReadByReceiver).list();
	}

}
