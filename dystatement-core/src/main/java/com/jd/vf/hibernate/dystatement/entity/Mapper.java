package com.jd.vf.hibernate.dystatement.entity;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
@Data
public class Mapper {

	/**
	 * 命名空间
	 */
	private String namespace;

	/**
	 * 该命名空间下的查询方法
	 */
	private Map<String, MapperMethod> mapperMethods = new HashMap<>();

	public void addMapperMethod(MapperMethod method) {
		mapperMethods.put(method.getId(), method);
	}

	public void addMapperMethods(Map<String, MapperMethod> methods) {
		mapperMethods.putAll(methods);
	}

	public MapperMethod getMapperMethod(@NonNull String methodId) {
		return this.getMapperMethods().get(methodId);
	}
}
