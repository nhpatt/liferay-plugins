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

package com.liferay.shortlink.hook.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.shortlink.model.ShortLinkEntry;
import com.liferay.shortlink.model.ShortLinkEntryConstants;
import com.liferay.shortlink.service.ShortLinkEntryLocalServiceUtil;

/**
 * @author Miroslav Ligas
 */
public class RewriteShortLinkFilter extends BaseShortLinkFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected String getOriginalURL(String shortUrl)
		throws PortalException, SystemException {

		if (shortUrl.length() <
				ShortLinkEntryConstants.SHORT_URL_MINIMUM_SIZE) {

			return null;
		}

		ShortLinkEntry shortLinkEntry =
			ShortLinkEntryLocalServiceUtil.getShortLinkEntry(shortUrl, false);

		return shortLinkEntry.getOriginalURL();
	}

	private static Log _log = LogFactoryUtil.getLog(
		RewriteShortLinkFilter.class);

}