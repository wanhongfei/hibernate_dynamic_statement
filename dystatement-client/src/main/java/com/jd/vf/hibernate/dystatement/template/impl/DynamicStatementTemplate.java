package com.jd.vf.hibernate.dystatement.template.impl;

import com.jd.vf.hibernate.dystatement.entity.Mapper;
import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.model.PairExt;
import com.jd.vf.hibernate.dystatement.reader.MapperReader;
import com.jd.vf.hibernate.dystatement.render.Render;
import com.jd.vf.hibernate.dystatement.render.impl.FreemarkerRender;
import com.jd.vf.hibernate.dystatement.render.method.PrecomplieMethod;
import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;
import com.jd.vf.hibernate.dystatement.statement.impl.ExecutableHqlStatement;
import com.jd.vf.hibernate.dystatement.statement.impl.ExecutableSqlStatement;
import com.jd.vf.hibernate.dystatement.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Created by wanhongfei on 2016/11/24.
 */
@Data
@Slf4j
public class DynamicStatementTemplate implements Template {

	private final static String classpath = DynamicStatementTemplate.class.getResource("").getPath();
	private List<String> MapperScanDirectory = null;
	private List<String> ClassScanDirectory = null;

	private MapperReader reader = null;
	private Render resolver = null;
	private PrecomplieMethod precomplieMethod = null;

	@SneakyThrows
	@Override
	public void init() {
		log.debug("init start..");
		reader = new MapperReader(MapperScanDirectory);
		precomplieMethod = new PrecomplieMethod();
		resolver = new FreemarkerRender();
		log.debug("init end..");
	}

	@Override
	public ExecutableStatement assembled(String namespace, String methodId, Object params) {
		PairExt<Mapper, MapperMethod, String> res = proccess(namespace, methodId, params);
		MapperMethod method = res.getValue2();
		if ("sql".equals(method.getExecuteType())) {
			return new ExecutableSqlStatement(res.getValue3(), precomplieMethod.getParameters());
		} else {
			return new ExecutableHqlStatement(res.getValue3(), precomplieMethod.getParameters());
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
