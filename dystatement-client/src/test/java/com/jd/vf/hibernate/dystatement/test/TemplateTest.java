package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.template.impl.DynamicStatementTemplate;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class TemplateTest {

	private DynamicStatementTemplate template = null;

	@Before
	public void before() throws DocumentException, IOException, ClassNotFoundException {
		template = new DynamicStatementTemplate();

		List<String> paths = new ArrayList<>();
		paths.add("C:\\workspace\\dystatement\\dystatement-client\\src\\test\\java\\com\\jd\\vf\\hibernate\\dystatement\\test\\mapper");
		template.setMapperScanDirectory(paths);

		List<String> packages = new ArrayList<>();
		packages.add("com.jd.vf.hibernate.dystatement.test.entity");
		template.setClassScanDirectory(packages);

		template.init();
	}

	@After
	public void after() {

	}

	@Test
	public void test() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("age", 11.1);
		map.put("start", 0);
		map.put("offset", 20);
		map.put("name", new Date());
		System.out.println(template.assembled("test.namespace", "test_method2", map));
	}
}
