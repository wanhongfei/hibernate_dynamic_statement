package com.jd.vf.hibernate.dystatement.constants;

import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public enum MapperEnum {

	Mapper("mapper");

	@Getter
	private String name;

	MapperEnum(String name) {
		this.name = name;
	}
}
