package com.jd.vf.hibernate.dystatement.render;

/**
 * Created by wanhongfei on 2016/11/24.
 */
public interface Render {

	/**
	 * 整合模板和参数
	 *
	 * @param id
	 * @param template
	 * @param params
	 * @return
	 */
	public String proccessTemplate(String id, String template, Object params);

}
