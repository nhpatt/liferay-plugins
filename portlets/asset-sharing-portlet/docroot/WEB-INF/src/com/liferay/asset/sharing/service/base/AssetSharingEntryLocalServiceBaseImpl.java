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

package com.liferay.asset.sharing.service.base;

import com.liferay.asset.sharing.model.AssetSharingEntry;
import com.liferay.asset.sharing.service.AssetSharingEntryLocalService;
import com.liferay.asset.sharing.service.persistence.AssetSharingEntryPK;
import com.liferay.asset.sharing.service.persistence.AssetSharingEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset sharing entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.asset.sharing.service.impl.AssetSharingEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.sharing.service.impl.AssetSharingEntryLocalServiceImpl
 * @see com.liferay.asset.sharing.service.AssetSharingEntryLocalServiceUtil
 * @generated
 */
public abstract class AssetSharingEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AssetSharingEntryLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.asset.sharing.service.AssetSharingEntryLocalServiceUtil} to access the asset sharing entry local service.
	 */

	/**
	 * Adds the asset sharing entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetSharingEntry the asset sharing entry
	 * @return the asset sharing entry that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetSharingEntry addAssetSharingEntry(
		AssetSharingEntry assetSharingEntry) throws SystemException {
		assetSharingEntry.setNew(true);

		return assetSharingEntryPersistence.update(assetSharingEntry);
	}

	/**
	 * Creates a new asset sharing entry with the primary key. Does not add the asset sharing entry to the database.
	 *
	 * @param assetSharingEntryPK the primary key for the new asset sharing entry
	 * @return the new asset sharing entry
	 */
	@Override
	public AssetSharingEntry createAssetSharingEntry(
		AssetSharingEntryPK assetSharingEntryPK) {
		return assetSharingEntryPersistence.create(assetSharingEntryPK);
	}

	/**
	 * Deletes the asset sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetSharingEntryPK the primary key of the asset sharing entry
	 * @return the asset sharing entry that was removed
	 * @throws PortalException if a asset sharing entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetSharingEntry deleteAssetSharingEntry(
		AssetSharingEntryPK assetSharingEntryPK)
		throws PortalException, SystemException {
		return assetSharingEntryPersistence.remove(assetSharingEntryPK);
	}

	/**
	 * Deletes the asset sharing entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetSharingEntry the asset sharing entry
	 * @return the asset sharing entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetSharingEntry deleteAssetSharingEntry(
		AssetSharingEntry assetSharingEntry) throws SystemException {
		return assetSharingEntryPersistence.remove(assetSharingEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AssetSharingEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetSharingEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.sharing.model.impl.AssetSharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return assetSharingEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.sharing.model.impl.AssetSharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return assetSharingEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetSharingEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return assetSharingEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AssetSharingEntry fetchAssetSharingEntry(
		AssetSharingEntryPK assetSharingEntryPK) throws SystemException {
		return assetSharingEntryPersistence.fetchByPrimaryKey(assetSharingEntryPK);
	}

	/**
	 * Returns the asset sharing entry with the primary key.
	 *
	 * @param assetSharingEntryPK the primary key of the asset sharing entry
	 * @return the asset sharing entry
	 * @throws PortalException if a asset sharing entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetSharingEntry getAssetSharingEntry(
		AssetSharingEntryPK assetSharingEntryPK)
		throws PortalException, SystemException {
		return assetSharingEntryPersistence.findByPrimaryKey(assetSharingEntryPK);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return assetSharingEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the asset sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.sharing.model.impl.AssetSharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset sharing entries
	 * @param end the upper bound of the range of asset sharing entries (not inclusive)
	 * @return the range of asset sharing entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetSharingEntry> getAssetSharingEntries(int start, int end)
		throws SystemException {
		return assetSharingEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of asset sharing entries.
	 *
	 * @return the number of asset sharing entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetSharingEntriesCount() throws SystemException {
		return assetSharingEntryPersistence.countAll();
	}

	/**
	 * Updates the asset sharing entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetSharingEntry the asset sharing entry
	 * @return the asset sharing entry that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetSharingEntry updateAssetSharingEntry(
		AssetSharingEntry assetSharingEntry) throws SystemException {
		return assetSharingEntryPersistence.update(assetSharingEntry);
	}

	/**
	 * Returns the asset sharing entry local service.
	 *
	 * @return the asset sharing entry local service
	 */
	public com.liferay.asset.sharing.service.AssetSharingEntryLocalService getAssetSharingEntryLocalService() {
		return assetSharingEntryLocalService;
	}

	/**
	 * Sets the asset sharing entry local service.
	 *
	 * @param assetSharingEntryLocalService the asset sharing entry local service
	 */
	public void setAssetSharingEntryLocalService(
		com.liferay.asset.sharing.service.AssetSharingEntryLocalService assetSharingEntryLocalService) {
		this.assetSharingEntryLocalService = assetSharingEntryLocalService;
	}

	/**
	 * Returns the asset sharing entry persistence.
	 *
	 * @return the asset sharing entry persistence
	 */
	public AssetSharingEntryPersistence getAssetSharingEntryPersistence() {
		return assetSharingEntryPersistence;
	}

	/**
	 * Sets the asset sharing entry persistence.
	 *
	 * @param assetSharingEntryPersistence the asset sharing entry persistence
	 */
	public void setAssetSharingEntryPersistence(
		AssetSharingEntryPersistence assetSharingEntryPersistence) {
		this.assetSharingEntryPersistence = assetSharingEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.asset.sharing.model.AssetSharingEntry",
			assetSharingEntryLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.asset.sharing.model.AssetSharingEntry");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return AssetSharingEntry.class;
	}

	protected String getModelClassName() {
		return AssetSharingEntry.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = assetSharingEntryPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.asset.sharing.service.AssetSharingEntryLocalService.class)
	protected com.liferay.asset.sharing.service.AssetSharingEntryLocalService assetSharingEntryLocalService;
	@BeanReference(type = AssetSharingEntryPersistence.class)
	protected AssetSharingEntryPersistence assetSharingEntryPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private AssetSharingEntryLocalServiceClpInvoker _clpInvoker = new AssetSharingEntryLocalServiceClpInvoker();
}