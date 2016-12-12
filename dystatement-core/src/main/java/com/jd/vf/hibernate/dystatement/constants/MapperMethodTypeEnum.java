package com.jd.vf.hibernate.dystatement.constants;

import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public enum MapperMethodTypeEnum {

	Select("select"),
	Insert("insert"),
	Update("update"),
	Delete("delete");

	@Getter
	private String name;

	MapperMethodTypeEnum(String name) {
		this.name = name;
	}
}
