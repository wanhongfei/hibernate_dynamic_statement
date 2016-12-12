package com.jd.vf.hibernate.dystatement.constants;

import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/18.
 */
public enum CharsetEnum {

	UTF_8("UTF-8"),
	GB2312("GB2312"),
	ISO_8859_1("ISO-8859-1"),
	GBK("GBK");

	@Getter
	private String value;

	CharsetEnum(String value) {
		this.value = value;
	}
}
