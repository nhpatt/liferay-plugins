<%--
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
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.compat.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.compat.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.marketplace.FileExtensionException" %><%@
page import="com.liferay.marketplace.model.App" %><%@
page import="com.liferay.marketplace.service.AppLocalServiceUtil" %><%@
page import="com.liferay.marketplace.util.comparator.PluginComparator" %><%@
page import="com.liferay.portal.kernel.deploy.DeployManagerUtil" %><%@
page import="com.liferay.portal.kernel.plugin.RequiredPluginPackageException" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayPortletURL" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.servlet.ServletContextPool" %><%@
page import="com.liferay.portal.kernel.upload.UploadException" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.LayoutTemplate" %><%@
page import="com.liferay.portal.model.Plugin" %><%@
page import="com.liferay.portal.model.PluginSetting" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.Theme" %><%@
page import="com.liferay.portal.service.CompanyLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PluginSettingLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortletCategoryKeys" %><%@
page import="com.liferay.portlet.PortletURLFactoryUtil" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Iterator" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletRequest" %>

<%@ page import="javax.servlet.ServletContext" %>