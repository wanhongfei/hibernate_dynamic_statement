package com.jd.vf.hibernate.dystatement.scanner;

import com.jd.vf.hibernate.dystatement.util.BeanUtil;
import com.jd.vf.hibernate.dystatement.util.StringUtil;
import lombok.Data;
import lombok.SneakyThrows;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
@Data
@Deprecated
public class ClassesScanner {

	private Map<Class<?>, Set<Field>> classPropertiesMapper = new HashMap<>();

	@SneakyThrows
	public ClassesScanner(List<String> packageName) {
		for (String path : packageName) {
			if (StringUtil.isEmpty(path)) throw new Exception("packageName has null!");
			scan(path);
		}
	}

	public void scan(String packageName) {
		List<Class<?>> classes = BeanUtil.scan(packageName);
		for (Class clazz : classes) {
			if (clazz.getAnnotation(Table.class) != null
					&& clazz.getAnnotation(Entity.class) != null) {
				Set<Field> fields = new HashSet<>();
				Field[] clazzFields = clazz.getDeclaredFields();
				fields.addAll(Arrays.asList(clazzFields));
				classPropertiesMapper.put(clazz, fields);
			}
		}
	}

	public String getFieldNames(Class clazz) {
		Set<Field> fields = classPropertiesMapper.get(clazz);
		return StringUtil.join(fields, ",");
	}

}
