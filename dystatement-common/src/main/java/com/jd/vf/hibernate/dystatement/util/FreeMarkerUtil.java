package com.jd.vf.hibernate.dystatement.util;

import com.jd.vf.hibernate.dystatement.constants.CharsetEnum;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by hongfei.whf on 2016/11/26.
 */
public class FreeMarkerUtil {

	private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

	static {
		cfg.setDefaultEncoding(CharsetEnum.UTF_8.getValue());
	}

	/**
	 * 对模板内容和参数进行整合
	 *
	 * @param id
	 * @param stringTemplate
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String proccessTemplate(String id, String stringTemplate, Object params) throws IOException, TemplateException {
		Template template = new Template(id, new StringReader(stringTemplate), cfg);
		StringWriter writer = new StringWriter();
		template.process(params, writer);
		return writer.toString();
	}
}
