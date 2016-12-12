package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.render.Render;
import com.jd.vf.hibernate.dystatement.render.impl.FreemarkerRender;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class RenderTest {

	private Render render = null;

	@Before
	public void before() throws DocumentException, IOException, ClassNotFoundException {
		render = new FreemarkerRender();
	}

	@After
	public void after() {
	}

	@Test
	public void test() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("age", 1);
		params.put("name", 2);
		System.out.println(render.proccessTemplate("test.namespace", "${age},hello,${name}", params));
	}

}
