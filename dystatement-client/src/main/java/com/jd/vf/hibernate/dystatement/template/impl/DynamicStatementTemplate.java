package com.jd.vf.hibernate.dystatement.template.impl;

import com.jd.vf.hibernate.dystatement.entity.Mapper;
import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.extractor.Dom4jXmlExtractor;
import com.jd.vf.hibernate.dystatement.model.PairExt;
import com.jd.vf.hibernate.dystatement.reader.MapperReader;
import com.jd.vf.hibernate.dystatement.render.Render;
import com.jd.vf.hibernate.dystatement.render.impl.FreemarkerRender;
import com.jd.vf.hibernate.dystatement.render.method.PreComplieMethod;
import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;
import com.jd.vf.hibernate.dystatement.statement.impl.ExecutableHqlStatement;
import com.jd.vf.hibernate.dystatement.statement.impl.ExecutableSqlStatement;
import com.jd.vf.hibernate.dystatement.template.Template;
import com.jd.vf.hibernate.dystatement.util.StringUtil;
import freemarker.template.TemplateException;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

/**
 * Created by wanhongfei on 2016/11/24.
 */
@Data
public class DynamicStatementTemplate implements Template {

	private final static String classpath = DynamicStatementTemplate.class.getResource("").getPath();
	private List<String> MapperScanDirectory = null;
	private String PreCompileHqlMethodClass = null;

	private MapperReader reader = null;
	private Render resolver = null;
	private Dom4jXmlExtractor extractor = null;
	private PreComplieMethod preComplieMethod = null;

	@SneakyThrows
	@Override
	public void init() {
		extractor = new Dom4jXmlExtractor();
		if (!StringUtil.isEmpty(PreCompileHqlMethodClass)) {
			Class clazz = Class.forName(PreCompileHqlMethodClass);
			if (clazz == null) {
				throw new Exception(PreCompileHqlMethodClass + " is not exist");
			}
			extractor.setPreCompileHqlMethodClass(clazz);
		}
		if (MapperScanDirectory == null || MapperScanDirectory.size() == 0) {
			throw new Exception("MapperScanDirectory is empty");
		}
		reader = new MapperReader(MapperScanDirectory, extractor);
		preComplieMethod = new PreComplieMethod();
		resolver = new FreemarkerRender();
	}

	@Override
	public ExecutableStatement assembled(String namespace, String methodId, Object params) {
		preComplieMethod.initParameters();
		PairExt<Mapper, MapperMethod, String> res = proccess(namespace, methodId, params);
		MapperMethod method = res.getValue2();
		if ("sql".equals(method.getExecuteType())) {
			return new ExecutableSqlStatement(res.getValue3(),
					preComplieMethod.getClassesAndParameters(),
					method.getExecuteType(),
					method.getStatementType());
		} else {
			return new ExecutableHqlStatement(res.getValue3(),
					preComplieMethod.getClassesAndParameters(),
					method.getExecuteType(),
					method.getStatementType());
		}
	}

	/**
	 * 解析自定义模板
	 *
	 * @param namespace
	 * @param methodId
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SneakyThrows
	public PairExt<Mapper, MapperMethod, String> proccess(String namespace, String methodId, Object params) {
		Mapper mapper = reader.getMapper(namespace);
		if (mapper == null) {
			throw new Exception("指定namespace的Mapper不存在");
		}
		MapperMethod method = mapper.getMapperMethod(methodId);
		if (method == null) {
			throw new Exception("MapperMethod");
		}
		String sql = resolver.proccessTemplate(
				mapper.getNamespace() + "." + method.getId(),
				method.getDynamicTemplate(),
				params)
				// 去除多余换行
				.replaceAll("\\s+", " ");
		return new PairExt<>(mapper, method, sql);
	}
}
