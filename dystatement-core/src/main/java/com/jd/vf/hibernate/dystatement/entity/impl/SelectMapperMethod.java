package com.jd.vf.hibernate.dystatement.entity.impl;

import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import lombok.Data;

/**
 * Created by hongfei.whf on 2016/12/4.
 */
@Data
public class SelectMapperMethod extends MapperMethod {

	/**
	 * 返回类型
	 */
	private Class returnClazz;

}
