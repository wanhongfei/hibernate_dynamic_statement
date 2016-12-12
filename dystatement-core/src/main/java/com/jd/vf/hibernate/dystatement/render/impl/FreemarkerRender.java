package com.jd.vf.hibernate.dystatement.render.impl;

import com.jd.vf.hibernate.dystatement.render.Render;
import com.jd.vf.hibernate.dystatement.util.FreeMarkerUtil;
import lombok.SneakyThrows;

/**
 * Created by wanhongfei on 2016/11/24.
 */
public class FreemarkerRender implements Render {

	@Override
	@SneakyThrows
	public String proccessTemplate(String id, String stringTemplate, Object params) {
		return FreeMarkerUtil.proccessTemplate(id, stringTemplate, params);
	}
}
