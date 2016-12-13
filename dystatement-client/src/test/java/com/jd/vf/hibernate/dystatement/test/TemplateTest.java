package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;
import com.jd.vf.hibernate.dystatement.template.impl.DynamicStatementTemplate;
import com.jd.vf.hibernate.dystatement.test.entity.Student;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class TemplateTest {

	private DynamicStatementTemplate template = null;

	@Before
	public void before() throws DocumentException, IOException, ClassNotFoundException {
		template = new DynamicStatementTemplate();
		List<String> paths = new ArrayList<>();
		paths.add("classpath:com.jd.vf.hibernate.dystatement.test.mapper");
		template.setMapperScanDirectory(paths);
		template.setPreCompileHqlMethodClass("com.jd.vf.hibernate.dystatement.render.method.PreComplieHqlMethod");
//		List<String> packages = new ArrayList<>();
//		packages.add("com.jd.vf.hibernate.dystatement.test.entity");
//		template.setClassScanDirectory(packages);
		template.init();
	}

	@After
	public void after() {

	}

	@Test
	public void test() throws Exception {
		Map<String, Object> map = new HashMap<>();
		Student student1 = new Student(1, "2", 3);
		Student student2 = new Student(2, "3", 4);
		map.put("age", 11.1);
		map.put("start", 0);
		map.put("offset", 20);
		map.put("name", new Date());
		map.put("studentClassName", Student.class.getCanonicalName());
		map.put("list", Arrays.asList(new Student[]{student1, student2}));
		ExecutableStatement es = template.assembled("test.namespace", "test_method2", map);
		System.out.println(es);
		ExecutableStatement es1 = template.assembled("test.namespace", "test_method", map);
		System.out.println(es1);
		System.out.println(es1.getParameters());
		ExecutableStatement es2 = template.assembled("test.namespace", "test_method1", map);
		System.out.println(es2);
		System.out.println(es2.getParameters());
	}
}
