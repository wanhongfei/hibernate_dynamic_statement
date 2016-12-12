package com.jd.vf.hibernate.dystatement.util;

import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author wwhhf
 * @comment 数组工具
 * @since 2016年6月13日
 */
public class CollectionUtil {

	/**
	 * @param ids
	 * @param symbol
	 * @return
	 * @author wwhhf
	 * @comment 字符串转为整形list
	 * @since 2016年6月13日
	 */
	public static List<Integer> string2IntList(String ids, @NonNull String symbol) {
		if (StringUtil.isEmpty(ids))
			return null;
		String s[] = ids.split(symbol);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0, length = s.length; i < length; i++) {
			Integer x = Integer.valueOf(s[i]);
			if (list.contains(x) == false)
				list.add(x);
		}
		return list;
	}

	/**
	 * @param ids
	 * @param symbol
	 * @return
	 * @author wwhhf
	 * @comment 字符串转为字符串数组
	 * @since 2016年6月13日
	 */
	public static List<String> string2StrList(String ids, @NonNull String symbol) {
		if (StringUtil.isEmpty(ids))
			return null;
		String s[] = ids.split(symbol);
		List<String> list = new ArrayList<String>();
		for (int i = 0, length = s.length; i < length; i++) {
			if (list.contains(s[i]) == false)
				list.add(s[i]);
		}
		return list;
	}

	/**
	 * @param ids
	 * @param symbol
	 * @return
	 * @author wwhhf
	 * @comment 拼接字符串
	 * @since 2016年6月13日
	 */
	public static String list2String(@NonNull List<Integer> ids,
	                                 @NonNull String symbol) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = ids.size(); i < len; i++) {
			if (i == 0)
				sb.append(ids.get(i));
			else {
				sb.append(symbol).append(ids.get(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 从list对象中取出指定属性当作key
	 *
	 * @param <T>
	 * @param <Q>
	 * @return
	 */
	public static <T, Q> Map<T, Q> list2Map(@NonNull List<Q> objs, @NonNull String fieldName,
	                                        @NonNull Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
		Map<T, Q> res = new HashMap<>();
		for (Q obj : objs) {
			res.put(BeanUtil.getFieldValue(obj, fieldName, clazz), obj);
		}
		return res;
	}

	/**
	 * 将list转为set
	 *
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> list2Set(@NonNull List<T> list) {
		Set<T> set = new HashSet<>();
		for (T t : list) {
			set.add(t);
		}
		return set;
	}

	/**
	 * list去重
	 *
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> listDuplicate(@NonNull List<T> list) {
		List<T> newList = new ArrayList<>();
		newList.addAll(list2Set(list));
		return newList;
	}

	/**
	 * list 转为数组
	 *
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> T[] list2Array(@NonNull List<T> list, Class<T> clazz) {
		T[] array = (T[]) Array.newInstance(clazz, list.size());
		return list.toArray(array);
	}

	/**
	 * 数组转为list
	 *
	 * @param array
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> array2List(@NonNull T[] array) {
		return Arrays.asList(array);
	}
}
