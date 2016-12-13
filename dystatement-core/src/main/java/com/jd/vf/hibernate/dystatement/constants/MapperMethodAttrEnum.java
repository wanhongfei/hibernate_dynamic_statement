package com.jd.vf.hibernate.dystatement.constants;

import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public enum MapperMethodAttrEnum {

	Namespace("namespace"),
	Id("id"),
	Type("type");

	@Getter
	private String name;

	MapperMethodAttrEnum(String name) {
		this.name = name;
	}
}
