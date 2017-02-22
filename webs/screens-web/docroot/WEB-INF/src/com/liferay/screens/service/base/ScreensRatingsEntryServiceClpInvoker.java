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

package com.liferay.screens.service.base;

import com.liferay.screens.service.ScreensRatingsEntryServiceUtil;

import java.util.Arrays;

/**
 * @author José Manuel Navarro
 * @generated
 */
public class ScreensRatingsEntryServiceClpInvoker {
	public ScreensRatingsEntryServiceClpInvoker() {
		_methodName36 = "getBeanIdentifier";

		_methodParameterTypes36 = new String[] {  };

		_methodName37 = "setBeanIdentifier";

		_methodParameterTypes37 = new String[] { "java.lang.String" };

		_methodName40 = "deleteRatingsEntry";

		_methodParameterTypes40 = new String[] { "long", "java.lang.String", "int" };

		_methodName41 = "getRatingsEntries";

		_methodParameterTypes41 = new String[] { "long", "int" };

		_methodName42 = "getRatingsEntries";

		_methodParameterTypes42 = new String[] { "long", "java.lang.String", "int" };

		_methodName43 = "updateRatingsEntry";

		_methodParameterTypes43 = new String[] {
				"long", "java.lang.String", "double", "int"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return ScreensRatingsEntryServiceUtil.getBeanIdentifier();
		}

		if (_methodName37.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes37, parameterTypes)) {
			ScreensRatingsEntryServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return ScreensRatingsEntryServiceUtil.deleteRatingsEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return ScreensRatingsEntryServiceUtil.getRatingsEntries(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return ScreensRatingsEntryServiceUtil.getRatingsEntries(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Integer)arguments[2]).intValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return ScreensRatingsEntryServiceUtil.updateRatingsEntry(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Double)arguments[2]).doubleValue(),
				((Integer)arguments[3]).intValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName37;
	private String[] _methodParameterTypes37;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
}