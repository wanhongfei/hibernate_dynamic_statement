package com.jd.vf.hibernate.dystatement.render.method;

import com.jd.vf.hibernate.dystatement.model.Pair;
import freemarker.template.SimpleDate;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongfei.whf on 2016/12/11.
 */
public class PrecomplieMethod implements TemplateMethodModelEx {

	private static final ThreadLocal<List<Pair<Class, Object>>> threadLocal = new ThreadLocal<>();

	@Override
	public Object exec(List list) throws TemplateModelException {
		if (list == null || list.size() != 1) {
			throw new TemplateModelException("parameter is null");
		}
		// 获取和线程绑定的参数列表
		List<Pair<Class, Object>> parameters = threadLocal.get();
		if (parameters == null) {
			parameters = new ArrayList<>();
			threadLocal.set(parameters);
		}
		// 格式化并且加入参数，实现预编译sql（？替换）和sql的参数
		Object parameter = list.get(0);
		Class clazz = parameter.getClass();
		if (clazz.equals(SimpleNumber.class)) {
			SimpleNumber number = (SimpleNumber) parameter;
			Class baseValueType = number.getAsNumber().getClass();
			// Number
			parameters.add(new Pair<Class, Object>(
					number.getAsNumber().getClass(),
					number.getAsNumber()));
		} else if (clazz.equals(SimpleDate.class)) {
			// dateTime
			SimpleDate date = (SimpleDate) parameter;
			parameters.add(new Pair<Class, Object>(
					date.getAsDate().getClass(),
					date.getAsDate()));
		} else if (clazz.equals(SimpleScalar.class)) {
			SimpleScalar scalar = (SimpleScalar) parameter;
			parameters.add(new Pair<Class, Object>(
					scalar.getAsString().getClass(),
					scalar.getAsString()));
		} else {
			throw new TemplateModelException("parameter require String|Integer|Long|Float|Double|Date|java.sql.Date");
		}
		// 返回替换符？
		return "?";
	}

	/**
	 * 返回线程绑定的list
	 *
	 * @return
	 */
	public List<Pair<Class, Object>> getParameters() {
		return threadLocal.get();
	}
}
