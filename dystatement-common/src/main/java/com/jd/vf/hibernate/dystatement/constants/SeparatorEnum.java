package com.jd.vf.hibernate.dystatement.constants;

import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/18.
 */
public enum SeparatorEnum {

	COMMA(",");

	@Getter
	private String value;

	SeparatorEnum(String value) {
		this.value = value;
	}
}
