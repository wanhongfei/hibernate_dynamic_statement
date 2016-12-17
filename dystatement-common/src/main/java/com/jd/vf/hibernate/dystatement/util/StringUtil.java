package com.jd.vf.hibernate.dystatement.util;

import lombok.NonNull;

import java.util.Collection;

/**
 * Created by hongfei.whf on 2016/12/2.
 */
public class StringUtil {

	/**
	 * 特殊集合
	 *
	 * @return
	 */
	public static String emptyString = "";

	/**
	 * 字符串是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 字符串是否为非空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串相等
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else if (a != null && b != null) {
			return a.trim().equals(b.trim());
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串相等（忽略大小写）
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equalsIgnoreCase(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else if (a != null && b != null) {
			return a.trim().toLowerCase().equals(b.trim().toLowerCase());
		} else {
			return false;
		}
	}

	/**
	 * 将collection用指定分隔符组合成字符串
	 *
	 * @param collection
	 * @param op
	 * @return
	 */
	public static String join(@NonNull Collection collection, @NonNull String op) {
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		for (Object obj : collection) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append(op);
			}
			sb.append(obj);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否包含
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean contains(@NonNull String a, String b) {
		if (isEmpty(b)) {
			return false;
		} else {
			return a.contains(b);
		}
	}

	/**
	 * 字符串撰文其他基本类型
	 *
	 * @param value
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T string2BaseType(@NonNull String value, @NonNull Class<T> clazz) {
		if (clazz.equals(Integer.class)) {
			return (T) Integer.valueOf(value);
		} else if (clazz.equals(Long.class)) {
			return (T) Long.valueOf(value);
		} else if (clazz.equals(Float.class)) {
			return (T) Float.valueOf(value);
		} else if (clazz.equals(Double.class)) {
			return (T) Double.valueOf(value);
		} else if (clazz.equals(Boolean.class)) {
			return (T) Boolean.valueOf(value);
		} else {
			return (T) value;
		}
	}

}
