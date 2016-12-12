package com.jd.vf.hibernate.dystatement.extractor;

import com.jd.vf.hibernate.dystatement.constants.MapperEnum;
import com.jd.vf.hibernate.dystatement.constants.MapperMethodAttrEnum;
import com.jd.vf.hibernate.dystatement.constants.MapperMethodTypeEnum;
import com.jd.vf.hibernate.dystatement.entity.Mapper;
import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.entity.impl.DMLMapperMethod;
import com.jd.vf.hibernate.dystatement.entity.impl.SelectMapperMethod;
import com.jd.vf.hibernate.dystatement.render.method.PrecomplieMethod;
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
				if (MapperMethodTypeEnum.Select.getName().equals(currElement.getName())) {
					handleSelect(mapper, currElement);
				} else if (MapperMethodTypeEnum.Insert.getName().equals(currElement.getName())
						|| MapperMethodTypeEnum.Update.getName().equals(currElement.getName())
						|| MapperMethodTypeEnum.Delete.getName().equals(currElement.getName())) {
					MapperMethodTypeEnum typeEnum = null;
					switch (currElement.getName().toLowerCase()) {
						case "insert":
							typeEnum = MapperMethodTypeEnum.Insert;
							break;
						case "update":
							typeEnum = MapperMethodTypeEnum.Update;
							break;
						case "delete":
							typeEnum = MapperMethodTypeEnum.Delete;
							break;
						default:
							throw new Exception(currElement.getName().toLowerCase() + " can't be resolve");
					}
					handleDML(mapper, currElement, typeEnum);
				}
			}
			return mapper;
		} else {
			throw new RuntimeException("root isn't <mapper>");
		}
	}

	/**
	 * 处理是insert、update、delete之类的定义
	 *
	 * @param mapper
	 * @param currElement
	 * @throws ClassNotFoundException
	 */

	private void handleDML(Mapper mapper, Element currElement, MapperMethodTypeEnum typeEnum) throws ClassNotFoundException {
		DMLMapperMethod method = new DMLMapperMethod();
		method.setId(currElement.attributeValue(
				MapperMethodAttrEnum.Id.getName()));
		method.setExecuteType(currElement.attributeValue(
				MapperMethodAttrEnum.Type.getName().toLowerCase()));
//        method.setParameterClazz(
//                Class.forName(
//                        currElement.attributeValue(
//                                MapperMethodAttrEnum.Parameter.getName())));
		method.setType(typeEnum);
		method.setDynamicTemplate(currElement.getTextTrim());
		handlePrecompile(method);
		mapper.addMapperMethod(method);
	}

	/**
	 * 处理查询语句的定义
	 *
	 * @param mapper
	 * @param currElement
	 * @throws ClassNotFoundException
	 */
	private void handleSelect(Mapper mapper, Element currElement) throws ClassNotFoundException {
		SelectMapperMethod method = new SelectMapperMethod();
		method.setId(currElement.attributeValue(
				MapperMethodAttrEnum.Id.getName()));
		method.setExecuteType(currElement.attributeValue(
				MapperMethodAttrEnum.Type.getName().toLowerCase()));
//        method.setParameterClazz(
//                Class.forName(
//                        currElement.attributeValue(
//                                MapperMethodAttrEnum.Parameter.getName())));
		method.setReturnClazz(
				Class.forName(
						currElement.attributeValue(
								MapperMethodAttrEnum.Return.getName())));
		method.setType(MapperMethodTypeEnum.Select);
		method.setDynamicTemplate(currElement.getTextTrim());
		handlePrecompile(method);
		mapper.addMapperMethod(method);
	}

	/**
	 * 处理sql类型预编译问题
	 *
	 * @param method
	 */
	private void handlePrecompile(MapperMethod method) {
//		 使用jdbc进行预编译，加入动态预编译函数声明
//            "yyyy-MM-dd HH:mm:ss","#.00"
		String assign = "<#assign precomplie=\"" + PrecomplieMethod.class.getCanonicalName() + "\"?new()/>";
		String template = method.getDynamicTemplate();
		String precompileTemplate = assign + template.replace("${", "${precomplie(").replace("}", ")}");
		log.debug(precompileTemplate);
		method.setDynamicTemplate(precompileTemplate);
	}
}
