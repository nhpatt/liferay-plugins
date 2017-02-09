/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.screens.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.screens.service.base.ScreensCommentServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the screens comment remote service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.screens.service.ScreensCommentService} interface.
 * <p/>
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author José Manuel Navarro
 * @see com.liferay.screens.service.base.ScreensCommentServiceBaseImpl
 * @see com.liferay.screens.service.ScreensCommentServiceUtil
 */
public class ScreensCommentServiceImpl extends ScreensCommentServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.screens.service.ScreensCommentServiceUtil} to access the screens comment remote service.
	 */

	@Override
	public JSONObject addComment(String className, long classPK, String body)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);
		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

		try {
			PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBDiscussion,
				getPermissionChecker(),
				group.getCompanyId(), group.getGroupId(), className, classPK,
				getUserId(), ActionKeys.ADD_DISCUSSION);
		}
		catch (Exception e) {
//			MBDiscussionPermission.check(
//				getPermissionChecker(), group.getCompanyId(), group.getGroupId(),
//				className, classPK, getUserId(), ActionKeys.ADD_DISCUSSION);
			e.printStackTrace();
		}

		MBMessage mbMessage = addComment(
			getUserId(), getUser().getFullName(), group.getGroupId(), className,
			classPK, body, new ServiceContext());

		return toJSONObject(mbMessage);
	}

	private MBMessage addComment(
		long userId, String fullName, long groupId, String className,
		long classPK, String body, ServiceContext serviceContext)
		throws SystemException, PortalException {

		MBMessageDisplay messageDisplay =
			mbMessageLocalService.getDiscussionMessageDisplay(
				userId, groupId, className, classPK,
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		List<MBMessage> messages = mbMessageLocalService.getThreadMessages(
			thread.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		for (MBMessage message : messages) {
			String messageBody = message.getBody();

			if (messageBody.equals(body)) {
				throw new SystemException(body);
			}
		}

		return mbMessageLocalService.addDiscussionMessage(
			userId, fullName, groupId, className, classPK,
			thread.getThreadId(), thread.getRootMessageId(), StringPool.BLANK,
			body, serviceContext);
	}

	@Override
	public JSONObject getComment(long commentId)
		throws PortalException, SystemException {

		MBMessage mbMessage = mbMessageLocalService.getMBMessage(
			commentId);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			mbMessage.getClassName(), mbMessage.getClassPK());

		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

		try {
			PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBDiscussionWithMessage,
				getPermissionChecker(),
				group.getCompanyId(), group.getGroupId(),
				assetEntry.getClassName(), assetEntry.getClassPK(), commentId,
				getUserId(), ActionKeys.ADD_DISCUSSION);
		}
		catch (Exception e) {
//			MBDiscussionPermission.check(getPermissionChecker(),
//				group.getCompanyId(), group.getGroupId(), assetEntry.getClassName(),
//				assetEntry.getClassPK(),
//				commentId, getUserId(),
//				ActionKeys.VIEW);
			e.printStackTrace();
		}

		return toJSONObject(mbMessage);
	}

	@Override
	public JSONArray getComments(
		String className, long classPK, int start, int end)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

		try {
			PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBDiscussion,
				getPermissionChecker(),
				group.getCompanyId(), group.getGroupId(), className, classPK,
				getUserId(), ActionKeys.VIEW);
		}
		catch (Exception e) {
//			MBDiscussionPermission.check(
//			getPermissionChecker(), group.getCompanyId(), group.getGroupId(),
//				className, classPK, getUserId(), ActionKeys.VIEW);
			e.printStackTrace();
		}


		List<MBMessage> mbMessages =
			mbMessageLocalService.getUserDiscussionMessages(
				getUserId(), classNameLocalService.getClassNameId(className),
				classPK, WorkflowConstants.STATUS_APPROVED,
				start, end,
				null);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBMessage mbMessage : mbMessages) {
			jsonArray.put(toJSONObject(mbMessage));
		}

		return jsonArray;

//		MBDiscussion discussion = mbDiscussionLocalService.getDiscussion(
//			className, classPK);
//
////		DiscussionComment rootDiscussionComment =
////			discussion.getRootDiscussionComment();
//
//		if (start == QueryUtil.ALL_POS) {
//			start = 0;
//		}
//
////		DiscussionCommentIterator threadDiscussionCommentIterator =
////			rootDiscussionComment.getThreadDiscussionCommentIterator(start);
////
//
//
//		if (end == QueryUtil.ALL_POS) {
////			while (threadDiscussionCommentIterator.hasNext()) {
////				JSONObject jsonObject = toJSONObject(
////					threadDiscussionCommentIterator.next(),
////					discussionPermission);
////
////				jsonArray.put(jsonObject);
////			}
//		}
//		else {
////			int commentsCount = end - start;
////
////			while (threadDiscussionCommentIterator.hasNext() &&
////				   (commentsCount > 0)) {
////
////				JSONObject jsonObject = toJSONObject(
////					threadDiscussionCommentIterator.next(),
////					discussionPermission);
////
////				jsonArray.put(jsonObject);
////
////				commentsCount--;
////			}
//		}

//		return jsonArray;
	}

	@Override
	public int getCommentsCount(String className, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

		try {
			PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBDiscussion,
				getPermissionChecker(),
				group.getCompanyId(), group.getGroupId(), className, classPK,
				getUserId(), ActionKeys.VIEW);
		}
		catch (Exception e) {
//			MBDiscussionPermission.check(
//			getPermissionChecker(), group.getCompanyId(), group.getGroupId(),
//				className, classPK, getUserId(), ActionKeys.VIEW);
			e.printStackTrace();
		}

		return mbMessageLocalService.getDiscussionMessagesCount(
			className, classPK, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public JSONObject updateComment(long commentId, String body)
		throws PortalException, SystemException {

		try {
			PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBMessage,
				commentId, ActionKeys.UPDATE_DISCUSSION);
		}
		catch (Exception e) {
//			MBMessagePermission.check(
//				getPermissionChecker(), commentId, ActionKeys.UPDATE_DISCUSSION);
			e.printStackTrace();
		}

		MBMessage mbMessage = mbMessageLocalService.getMBMessage(commentId);

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		mbMessage = mbMessageLocalService.updateDiscussionMessage(
			mbMessage.getMessageId(),
			getUserId(), mbMessage.getClassName(), mbMessage.getClassPK(),
			StringPool.BLANK, body,
			serviceContext);

		return toJSONObject(mbMessage);
	}

	protected JSONObject toJSONObject(MBMessage comment)
		throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("body", comment.getBody());
		jsonObject.put("commentId", comment.getMessageId());
		jsonObject.put(
			"createDate", comment.getCreateDate().getTime());
		jsonObject.put("modifiedDate", comment.getModifiedDate().getTime());
		jsonObject.put("userId", comment.getUserId());
		jsonObject.put("userName", comment.getUserName());

		try {
			boolean deletePermission = (Boolean) PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBMessageContains,
				comment.getMessageId(), ActionKeys.DELETE_DISCUSSION);
			boolean updatePermission = (Boolean) PortalClassInvoker.invoke(
				false, _checkPermissionMethodKeyMBMessageContains,
				comment.getMessageId(), ActionKeys.UPDATE_DISCUSSION);
			jsonObject.put("updatePermission", updatePermission);
			jsonObject.put("deletePermission", deletePermission);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}


	private static final MethodKey _checkPermissionMethodKeyMBMessage =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(
				"com.liferay.portlet.messageboards.service.permission." +
				"MBMessagePermission"),
			"check", PermissionChecker.class, Long.class, String.class);

	private static final MethodKey _checkPermissionMethodKeyMBMessageContains =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(
				"com.liferay.portlet.messageboards.service.permission." +
				"MBMessagePermission"),
			"contains", PermissionChecker.class, Long.class,
			String.class);

	private static final MethodKey _checkPermissionMethodKeyMBDiscussion =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(
				"com.liferay.portlet.messageboards.service.permission." +
				"MBDiscussionPermission"),
			"check", PermissionChecker.class, Long.class, Long.class,
			String.class, Long.class, Long.class, String.class);

	private static final MethodKey
		_checkPermissionMethodKeyMBDiscussionWithMessage =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(
				"com.liferay.portlet.messageboards.service.permission." +
				"MBDiscussionPermission"),
			"check", PermissionChecker.class, Long.class, Long.class,
			String.class, Long.class, Long.class, Long.class, String.class);

}