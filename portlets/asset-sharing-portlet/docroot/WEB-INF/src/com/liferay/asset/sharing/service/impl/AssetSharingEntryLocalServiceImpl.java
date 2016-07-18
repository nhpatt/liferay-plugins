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

package com.liferay.asset.sharing.service.impl;

import com.liferay.asset.sharing.model.AssetSharingEntry;
import com.liferay.asset.sharing.service.base.AssetSharingEntryLocalServiceBaseImpl;
import com.liferay.asset.sharing.service.persistence.AssetSharingEntryPK;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sherry Yang
 */
public class AssetSharingEntryLocalServiceImpl
	extends AssetSharingEntryLocalServiceBaseImpl {

	@Override
	public void addAssetSharingEntries(
			long classNameId, long classPK,
			Map<Long, Set<Long>> sharedToClassPKsMap)
		throws SystemException {

		for (Long sharedToClassNameId : sharedToClassPKsMap.keySet()) {
			Set<Long> sharedToClassPKs = sharedToClassPKsMap.get(
				sharedToClassNameId);

			if (sharedToClassPKs.isEmpty()) {
				continue;
			}

			for (long sharedToClassPK : sharedToClassPKs) {
				addAssetSharingEntry(
					classNameId, classPK, sharedToClassNameId, sharedToClassPK);
			}
		}
	}

	@Override
	public void addAssetSharingEntry(
			long classNameId, long classPK, long sharedToClassNameId,
			long sharedToClassPK)
		throws SystemException {

		AssetSharingEntryPK assetSharingEntryPK = new AssetSharingEntryPK(
			classNameId, classPK, sharedToClassNameId, sharedToClassPK);

		AssetSharingEntry assetSharingEntry =
			assetSharingEntryPersistence.fetchByPrimaryKey(assetSharingEntryPK);

		if (assetSharingEntry == null) {
			assetSharingEntry = assetSharingEntryPersistence.create(
				assetSharingEntryPK);

			assetSharingEntryPersistence.update(assetSharingEntry);
		}
	}

	@Override
	public void deleteAssetSharingEntries(long classNameId, long classPK)
		throws SystemException {

		assetSharingEntryPersistence.removeByC_C(classNameId, classPK);
	}

	public void deleteSharedToAssetSharingEntries(
			long sharedToClassNameId, long sharedToClassPK)
		throws SystemException {

		assetSharingEntryPersistence.removeByS_S(
			sharedToClassNameId, sharedToClassPK);
	}

	@Override
	public List<AssetSharingEntry> getAssetSharingEntries(
			long classNameId, long classPK)
		throws SystemException {

		return assetSharingEntryPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<AssetSharingEntry> getAssetSharingEntries(
			long classNameId, long classPK, long sharedToClassNameId)
		throws SystemException {

		return assetSharingEntryPersistence.findByC_C_S(
			classNameId, classPK, sharedToClassNameId);
	}

	@Override
	public List<AssetSharingEntry> getSharedToAssetSharingEntries(
			long sharedToClassNameId, long sharedToClassPK, int start, int end)
		throws SystemException {

		return assetSharingEntryPersistence.findByS_S(
			sharedToClassNameId, sharedToClassPK, start, end);
	}

	@Override
	public List<AssetSharingEntry> getSharedToAssetSharingEntries(
			long classNameId, long sharedToClassNameId, long sharedToClassPK,
			int start, int end)
		throws SystemException {

		return assetSharingEntryPersistence.findByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK, start, end);
	}

	@Override
	public int getSharedToAssetSharingEntriesCount(
			long sharedToClassNameId, long sharedToClassPK)
		throws SystemException {

		return assetSharingEntryPersistence.countByS_S(
			sharedToClassNameId, sharedToClassPK);
	}

	@Override
	public int getSharedToAssetSharingEntriesCount(
			long classNameId, long sharedToClassNameId, long sharedToClassPK)
		throws SystemException {

		return assetSharingEntryPersistence.countByC_S_S(
			classNameId, sharedToClassNameId, sharedToClassPK);
	}

}