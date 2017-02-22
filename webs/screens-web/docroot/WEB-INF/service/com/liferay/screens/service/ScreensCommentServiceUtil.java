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

package com.liferay.screens.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * Provides the remote service utility for ScreensComment. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensCommentServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensCommentService
 * @see com.liferay.screens.service.base.ScreensCommentServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensCommentServiceImpl
 * @generated
 */
public class ScreensCommentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensCommentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this interface directly. Always use {@link com.liferay.screens.service.ScreensCommentServiceUtil} to access the screens comment remote service.
	*/
	public static com.liferay.portal.kernel.json.JSONObject addComment(
		java.lang.String className, long classPK, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addComment(className, classPK, body);
	}

	public static com.liferay.portal.kernel.json.JSONObject getComment(
		long commentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getComment(commentId);
	}

	public static com.liferay.portal.kernel.json.JSONArray getComments(
		java.lang.String className, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getComments(className, classPK, start, end);
	}

	public static int getCommentsCount(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCommentsCount(className, classPK);
	}

	public static com.liferay.portal.kernel.json.JSONObject updateComment(
		long commentId, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateComment(commentId, body);
	}

	public static void clearService() {
		_service = null;
	}

	public static ScreensCommentService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ScreensCommentService.class.getName());

			if (invokableService instanceof ScreensCommentService) {
				_service = (ScreensCommentService)invokableService;
			}
			else {
				_service = new ScreensCommentServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(ScreensCommentServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ScreensCommentService service) {
	}

	private static ScreensCommentService _service;
}