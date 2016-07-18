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

package com.liferay.asset.entry.set.util;

import com.liferay.asset.entry.set.model.AssetEntrySet;
import com.liferay.asset.entry.set.service.AssetEntrySetLocalServiceUtil;
import com.liferay.asset.entry.set.service.persistence.AssetEntrySetActionableDynamicQuery;
import com.liferay.asset.sharing.model.AssetSharingEntry;
import com.liferay.asset.sharing.service.AssetSharingEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Sherry Yang
 */
public class AssetEntrySetIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetEntrySet.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_ENTRY_SET;

	public AssetEntrySetIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		contextQuery.addRequiredTerm("parentAssetEntrySetId", 0);

		BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		String[] membershipSearchTerms =
			AssetEntrySetParticipantInfoUtil.getMembershipSearchTerms(
				searchContext.getUserId());

		for (String membershipSearchTerm : membershipSearchTerms) {
			booleanQuery.add(
				getBooleanQuery(
					searchContext, "sharedTo", membershipSearchTerm),
				BooleanClauseOccur.SHOULD);
		}

		booleanQuery.add(
			getBooleanQuery(
				searchContext, "privateAssetEntrySet", StringPool.FALSE),
			BooleanClauseOccur.SHOULD);

		contextQuery.add(booleanQuery, BooleanClauseOccur.MUST);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "creatorName", true);
		addSearchTerm(searchQuery, searchContext, "message", true);
		addSearchTerm(searchQuery, searchContext, "title", true);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		AssetEntrySet assetEntrySet = (AssetEntrySet)obj;

		deleteDocument(
			assetEntrySet.getCompanyId(), assetEntrySet.getAssetEntrySetId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetEntrySet assetEntrySet = (AssetEntrySet)obj;

		JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject(
			assetEntrySet.getPayload());

		Document document = getBaseModelDocument(PORTLET_ID, assetEntrySet);

		document.addKeyword(Field.TYPE, payloadJSONObject.getString("type"));

		document.addKeyword("createTime", assetEntrySet.getCreateTime());
		document.addText(
			"creatorName",
			AssetEntrySetParticipantInfoUtil.getParticipantName(
				assetEntrySet.getCreatorClassNameId(),
				assetEntrySet.getCreatorClassPK()));
		document.addText("message", payloadJSONObject.getString("message"));
		document.addKeyword("modifiedTime", assetEntrySet.getModifiedTime());
		document.addKeyword(
			"parentAssetEntrySetId", assetEntrySet.getParentAssetEntrySetId());
		document.addKeyword(
			"privateAssetEntrySet", assetEntrySet.getPrivateAssetEntrySet());
		document.addKeyword(
			"sharedTo", getSharedTo(assetEntrySet.getAssetEntrySetId()));
		document.addText("title", payloadJSONObject.getString("title"));

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

		return new Summary(document.get(Field.ENTRY_CLASS_PK), null, null);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		AssetEntrySet assetEntrySet = (AssetEntrySet)obj;

		Document document = getDocument(assetEntrySet);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), assetEntrySet.getCompanyId(), document);

		if (assetEntrySet.getParentAssetEntrySetId() > 0) {
			AssetEntrySet parentAssetEntrySet =
				AssetEntrySetLocalServiceUtil.getAssetEntrySet(
					assetEntrySet.getParentAssetEntrySetId());

			doReindex(parentAssetEntrySet);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetEntrySet assetEntrySet =
			AssetEntrySetLocalServiceUtil.getAssetEntrySet(classPK);

		doReindex(assetEntrySet);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected BooleanQuery getBooleanQuery(
			SearchContext searchContext, String field, String value)
		throws Exception {

		BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		TermQuery termQuery = TermQueryFactoryUtil.create(
			searchContext, field, value);

		booleanQuery.add(termQuery, BooleanClauseOccur.MUST);

		return booleanQuery;
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String[] getSharedTo(long assetEntrySetId) throws Exception {
		List<AssetSharingEntry> assetSharingEntries =
			AssetSharingEntryLocalServiceUtil.getAssetSharingEntries(
				AssetEntrySetConstants.ASSET_ENTRY_SET_CLASS_NAME_ID,
				assetEntrySetId);

		String[] sharedTo = new String[assetSharingEntries.size()];

		for (int i = 0; i < assetSharingEntries.size(); i++) {
			AssetSharingEntry assetSharingEntry = assetSharingEntries.get(i);

			sharedTo[i] = AssetEntrySetParticipantInfoUtil.getSearchTerm(
				assetSharingEntry.getSharedToClassNameId(),
				assetSharingEntry.getSharedToClassPK());
		}

		return sharedTo;
	}

	protected void reindexEntries(long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new AssetEntrySetActionableDynamicQuery() {

				@Override
				protected void performAction(Object object) {
					AssetEntrySet assetEntrySet = (AssetEntrySet)object;

					try {
						Document document = getDocument(assetEntrySet);

						addDocument(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index asset entry set " +
									assetEntrySet.getAssetEntrySetId(),
								pe);
						}
					}
				}
		};

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntrySetIndexer.class);

}