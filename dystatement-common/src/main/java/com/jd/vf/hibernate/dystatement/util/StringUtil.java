package com.jd.vf.hibernate.dystatement.util;

import lombok.NonNull;

import java.util.Collection;

/**
 * Created by hongfei.whf on 2016/12/2.
 */
public class StringUtil {

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

}
