/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.screens.service.base.ScreensRatingsEntryServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the screens ratings entry remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.screens.service.ScreensRatingsEntryService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author José Manuel Navarro
 * @see com.liferay.screens.service.base.ScreensRatingsEntryServiceBaseImpl
 * @see com.liferay.screens.service.ScreensRatingsEntryServiceUtil
 */
public class ScreensRatingsEntryServiceImpl
	extends ScreensRatingsEntryServiceBaseImpl {

	public static final int RATING_MAXIMUM = 5;

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.screens.service.ScreensRatingsEntryServiceUtil} to access the screens ratings entry remote service.
	 */

	@Override
	public JSONObject deleteRatingsEntry(
		long classPK, String className, int ratingsLength)
			throws PortalException, SystemException {

		checkPermission(
			_checkPermissionMethodKeyClassNameClassPK, getPermissionChecker(),
			className, classPK, ActionKeys.DELETE);

		ratingsEntryLocalService.deleteEntry(getUserId(), className, classPK);

		return getRatingsEntries(classPK, className, ratingsLength);
	}

	@Override
	public JSONObject getRatingsEntries(long assetEntryId, int ratingsLength)
		throws PortalException, SystemException {

		checkPermission(
			_checkPermissionMethodKeyEntryId, getPermissionChecker(),
			assetEntryId, ActionKeys.VIEW);

		AssetEntry entry = assetEntryLocalService.fetchEntry(assetEntryId);

		return getRatingsEntries(
			entry.getClassPK(), entry.getClassName(), ratingsLength);
	}

	@Override
	public JSONObject getRatingsEntries(
		long classPK, String className, int ratingsLength)
			throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<RatingsEntry> ratingsEntries = ratingsEntryLocalService.getEntries(
			className, classPK);

		int[] ratings = new int[ratingsLength];
		double totalScore = 0;
		double userScore = -1;

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			double score = ratingsEntry.getScore() / RATING_MAXIMUM;
			int index = (int)(score * ratingsLength);

			if (index == ratingsLength) {
				index--;
			}

			ratings[index]++;
			totalScore += score;

			if (ratingsEntry.getUserId() == getUserId()) {
				userScore = score;
			}
		}

		if (!ratingsEntries.isEmpty()) {
			jsonObject.put("average", totalScore / ratingsEntries.size());
		}
		else {
			jsonObject.put("average", 0);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int rating : ratings) {
			jsonArray.put(rating);
		}

		jsonObject.put("className", className);
		jsonObject.put("classPK", classPK);
		jsonObject.put("ratings", jsonArray);
		jsonObject.put("totalCount", ratingsEntries.size());
		jsonObject.put("totalScore", totalScore);
		jsonObject.put("userScore", userScore);

		return jsonObject;
	}

	@Override
	public JSONObject updateRatingsEntry(
		long classPK, String className, double score, int ratingsLength)
			throws PortalException, SystemException {

		checkPermission(
				_checkPermissionMethodKeyClassNameClassPK,
				getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		ratingsEntryLocalService.updateEntry(
				getUserId(), className, classPK, score, new ServiceContext());

		return getRatingsEntries(classPK, className, ratingsLength);
	}

	protected Object checkPermission(
			MethodKey methodKey, PermissionChecker permissionChecker,
			long entryId, String actionId) throws PortalException {

		try {
			return PortalClassInvoker.invoke(
					false, methodKey, permissionChecker, entryId, actionId);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static final MethodKey _checkPermissionMethodKeyClassNameClassPK =
			new MethodKey(
					ClassResolverUtil.resolveByPortalClassLoader(
							"com.liferay.portlet.asset.service.permission." +
									"AssetEntryPermission"),
					"check", PermissionChecker.class, String.class, long.class,
					String.class);
	private static final MethodKey _checkPermissionMethodKeyEntryId =
		new MethodKey(
			ClassResolverUtil.resolveByPortalClassLoader(
					"com.liferay.portlet.asset.service.permission." +
							"AssetEntryPermission"),
			"check", PermissionChecker.class, long.class, String.class);

	private static Log _log = LogFactoryUtil.getLog(
		ScreensRatingsEntryServiceImpl.class);

}