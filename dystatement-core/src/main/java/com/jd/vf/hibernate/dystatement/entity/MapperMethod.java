package com.jd.vf.hibernate.dystatement.entity;

import com.jd.vf.hibernate.dystatement.constants.MapperMethodTypeEnum;
import lombok.Data;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
@Data
public class MapperMethod {

	/**
	 * 查询方法唯一标识
	 */
	protected String id;

	/**
	 * selet还是DML
	 */
	protected MapperMethodTypeEnum type;

	/**
	 * 参数类型
	 */
	protected Class parameterClazz;

	/**
	 * 动态sql模板
	 */
	protected String dynamicTemplate;

	/**
	 * 执行类型：sql或者hql
	 */
	protected String executeType;
}
