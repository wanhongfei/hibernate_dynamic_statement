package com.jd.vf.hibernate.dystatement.extractor;

import com.jd.vf.hibernate.dystatement.constants.MapperEnum;
import com.jd.vf.hibernate.dystatement.constants.MapperMethodAttrEnum;
import com.jd.vf.hibernate.dystatement.constants.MapperMethodTypeEnum;
import com.jd.vf.hibernate.dystatement.entity.Mapper;
import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.render.method.PreComplieMethod;
import com.jd.vf.hibernate.dystatement.util.StringUtil;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by wanhongfei on 2016/11/24.
 */
@Slf4j
public class Dom4jXmlExtractor {

	private final static SAXReader reader = new SAXReader();

	/**
	 * 默认处理hql预编译的类
	 */
	@Setter
	private Class PreCompileHqlMethodClass = PreComplieMethod.class;

	/**
	 * 解析xml文件
	 *
	 * @param file
	 * @return
	 * @throws ClassNotFoundException
	 * @throws DocumentException
	 */
	@SneakyThrows
	public Mapper parserFile2Mapper(File file) throws ClassNotFoundException, DocumentException {
		Mapper mapper = new Mapper();
		Document document = reader.read(file);
		Element root = document.getRootElement();
		if (MapperEnum.Mapper.getName().equals(root.getName())) {
			mapper.setNamespace(root.attributeValue(MapperMethodAttrEnum.Namespace.getName()));
			Iterator iter = root.elementIterator();
			while (iter.hasNext()) {
				Element currElement = (Element) iter.next();
				String elementName = currElement.getName().toLowerCase();
				if (MapperMethodTypeEnum.Select.getName().equals(elementName)
						|| MapperMethodTypeEnum.Insert.getName().equals(currElement.getName())
						|| MapperMethodTypeEnum.Update.getName().equals(currElement.getName())
						|| MapperMethodTypeEnum.Delete.getName().equals(currElement.getName())) {
					handleMapperMethod(mapper, currElement);
				}
			}
			return mapper;
		} else {
			throw new RuntimeException("root isn't <mapper>");
		}
	}

	/**
	 * 生成MapperMethod
	 *
	 * @param mapper
	 * @param element
	 */
	@SneakyThrows
	private void handleMapperMethod(Mapper mapper, Element element) {
		MapperMethod method = handleExecuteType(element);
		handleStatementType(method, element);
		method.setId(element.attributeValue(MapperMethodAttrEnum.Id.getName()));
		method.setDynamicTemplate(element.getTextTrim());
		handlePrecompile(method);
		mapper.addMapperMethod(method);
	}

	/**
	 * 执行类型
	 *
	 * @param element
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SneakyThrows
	private MapperMethod handleExecuteType(Element element) {
		String elementName = element.getName().toLowerCase();
		if ("insert".equals(elementName) || "update".equals(elementName)
				|| "delete".equals(elementName)) {
			MapperMethod method = new MapperMethod();
			switch (elementName) {
				case "insert":
					method.setExecuteType(MapperMethod.ExecuteTypeEnum.Insert);
					break;
				case "update":
					method.setExecuteType(MapperMethod.ExecuteTypeEnum.Update);
					break;
				case "delete":
					method.setExecuteType(MapperMethod.ExecuteTypeEnum.Delete);
					break;
			}
			return method;
		} else if ("select".equals(elementName)) {
			MapperMethod method = new MapperMethod();
			method.setExecuteType(MapperMethod.ExecuteTypeEnum.Select);
			return method;
		} else {
			throw new Exception("mapper has error!!! select|insert|update|delete");
		}
	}

	/**
	 * 处理语句类型：是sql还是hql
	 *
	 * @param method
	 * @param element
	 */
	@SneakyThrows
	private void handleStatementType(MapperMethod method, Element element) {
		String statementName = element.attributeValue(MapperMethodAttrEnum.Type.getName()).toLowerCase();
		if (StringUtil.isEmpty(statementName) || "sql".equals(statementName)) {
			method.setStatementType(MapperMethod.StatementTypeEnum.SQL);
		} else if ("hql".equals(statementName)) {
			method.setStatementType(MapperMethod.StatementTypeEnum.HQL);
		} else {
			throw new Exception("type only support sql and hql");
		}
	}

	/**
	 * 处理sql和hql类型预编译问题
	 *
	 * @param method
	 */
	private void handlePrecompile(MapperMethod method) {
		method.precompile(PreCompileHqlMethodClass);
	}
}
