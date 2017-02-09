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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.screens.service.base.ScreensCommentServiceBaseImpl;

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

		//FIXME permissions
//		DiscussionPermission discussionPermission =
//			commentManager.getDiscussionPermission(getPermissionChecker());

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

//		discussionPermission.checkAddPermission(
//			group.getCompanyId(), assetEntry.getGroupId(), className, classPK);

		//FIXME permissions

		ServiceContext serviceContext = new ServiceContext();
		//FIXME serviceContext.setWorkflowAction("");

		MBMessage mbMessage = mbMessageLocalService.addMessage(
			getUserId(), getUser().getFullName(), 0L,
			StringPool.BLANK, body, serviceContext);

		//FIXME toJSONObject(comment, discussionPermission);
		return toJSONObject(mbMessage);
	}

	@Override
	public JSONObject getComment(long commentId)
		throws PortalException, SystemException {
//		DiscussionPermission discussionPermission =
//			commentManager.getDiscussionPermission(getPermissionChecker());

		MBMessage mbMessage = mbMessageLocalService.getMBMessage(
			commentId);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			mbMessage.getClassName(), mbMessage.getClassPK());

		Group group = groupLocalService.getGroup(assetEntry.getGroupId());

//		discussionPermission.checkViewPermission(
//			group.getCompanyId(), assetEntry.getGroupId(),
//			comment.getClassName(), comment.getClassPK());

		//FIXME toJSONObject(comment, discussionPermission);
		return toJSONObject(mbMessage);
	}

	//	@Override
//	public JSONArray getComments(
//		String className, long classPK, int start, int end)
//		throws PortalException {
//
//		DiscussionPermission discussionPermission =
//			commentManager.getDiscussionPermission(getPermissionChecker());
//
//		AssetEntry assetEntry = assetEntryLocalService.getEntry(
//			className, classPK);
//
//		Group group = groupLocalService.getGroup(assetEntry.getGroupId());
//
//		discussionPermission.checkViewPermission(
//			group.getCompanyId(), assetEntry.getGroupId(), className, classPK);
//
//		Discussion discussion = commentManager.getDiscussion(
//			getUserId(), assetEntry.getGroupId(), className, classPK,
//			createServiceContextFunction());
//
//		DiscussionComment rootDiscussionComment =
//			discussion.getRootDiscussionComment();
//
//		if (start == QueryUtil.ALL_POS) {
//			start = 0;
//		}
//
//		DiscussionCommentIterator threadDiscussionCommentIterator =
//			rootDiscussionComment.getThreadDiscussionCommentIterator(start);
//
//		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
//
//		if (end == QueryUtil.ALL_POS) {
//			while (threadDiscussionCommentIterator.hasNext()) {
//				JSONObject jsonObject = toJSONObject(
//					threadDiscussionCommentIterator.next(),
//					discussionPermission);
//
//				jsonArray.put(jsonObject);
//			}
//		}
//		else {
//			int commentsCount = end - start;
//
//			while (threadDiscussionCommentIterator.hasNext() &&
//				   (commentsCount > 0)) {
//
//				JSONObject jsonObject = toJSONObject(
//					threadDiscussionCommentIterator.next(),
//					discussionPermission);
//
//				jsonArray.put(jsonObject);
//
//				commentsCount--;
//			}
//		}
//
//		return jsonArray;
//	}
//
//	@Override
//	public int getCommentsCount(String className, long classPK)
//		throws PortalException, SystemException {
//
////		DiscussionPermission discussionPermission =
////			commentManager.getDiscussionPermission(getPermissionChecker());
//
//		AssetEntry assetEntry = assetEntryLocalService.getEntry(
//			className, classPK);
//
//		Group group = groupLocalService.getGroup(assetEntry.getGroupId());
//
////		discussionPermission.checkViewPermission(
////			group.getCompanyId(), assetEntry.getGroupId(), className, classPK);
//
//		return mbMessageLocalService.getMe.getCommentsCount(className, classPK);
//	}
//
//	@Override
//	public JSONObject updateComment(long commentId, String body)
//		throws PortalException {
//
//		DiscussionPermission discussionPermission =
//			commentManager.getDiscussionPermission(getPermissionChecker());
//
//		discussionPermission.checkUpdatePermission(commentId);
//
//		Comment comment = commentManager.fetchComment(commentId);
//
//		commentManager.updateComment(
//			getUserId(), comment.getClassName(), comment.getClassPK(),
//			commentId, StringPool.BLANK, body,
//			createServiceContextFunction(WorkflowConstants.ACTION_PUBLISH));
//
//		comment = commentManager.fetchComment(commentId);
//
//		return toJSONObject(comment, discussionPermission);
//	}
//
//
	protected JSONObject toJSONObject(
		MBMessage comment
//		, DiscussionPermission discussionPermission
	)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("body", comment.getBody());
		jsonObject.put("commentId", comment.getMessageId());
		jsonObject.put(
			"createDate", comment.getCreateDate().getTime());
//		jsonObject.put(
//			"deletePermission",
//			MBMessagePermission.hasDeletePermission(comment.getMessageId()));
		jsonObject.put(
			"modifiedDate", comment.getModifiedDate().getTime());
//		jsonObject.put(
//			"updatePermission",
//			discussionPermission.hasUpdatePermission(comment.getMessageId()));
		jsonObject.put("userId", comment.getUserId());
		jsonObject.put("userName", comment.getUserName());

		return jsonObject;
	}
//
//	@ServiceReference(type = CommentManager.class)
//	protected CommentManager commentManager;
}