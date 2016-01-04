package com.mre.service;

import java.util.List;

import com.mre.base.DaoSupport;
import com.mre.domain.Message;

public interface MessageService extends DaoSupport<Message> {
	
	/**
	 * 收件
	 * @param id
	 * @param isDeleteByReceiver : 是否被删除
	 * @return
	 */
	List<Message> getAllByToUserId(Long id, boolean isDeleteByReceiver);
	
	/**
	 * 发件
	 * @param id
	 * @param isDeleteBySender ： 是否被删除
	 * @return
	 */
	List<Message> getAllByFromUserId(Long id, boolean isDeleteBySender);
	
	/**
	 * 未读邮件的数量
	 * @param id
	 * @param isDeleteByReceiver
	 * @param isReadByReceiver
	 * @return
	 */
	int getMessageCountByToUserId(Long id, boolean isDeleteByReceiver, boolean isReadByReceiver);
	
	/**
	 * 根据收件人的openid获取未读邮件列表
	 * @param openid
	 * @param isDeleteByReceiver
	 * @param isReadByReceiver
	 * @return
	 */
	List<Message> getByToUserOpenid(String openid, boolean isDeleteByReceiver, boolean isReadByReceiver);
}
