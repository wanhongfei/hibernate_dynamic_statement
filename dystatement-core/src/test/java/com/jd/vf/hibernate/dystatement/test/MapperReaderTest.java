package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.extractor.Dom4jXmlExtractor;
import com.jd.vf.hibernate.dystatement.reader.MapperReader;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class MapperReaderTest {

	private MapperReader reader = null;

	@Before
	public void before() throws DocumentException, IOException, ClassNotFoundException {
		List<String> paths = new ArrayList<>();
		paths.add("C:\\workspace\\dystatement\\dystatement-client\\src\\test\\java\\com\\jd\\vf\\hibernate\\dystatement\\test\\mapper");
		reader = new MapperReader(paths, new Dom4jXmlExtractor());
	}

	@After
	public void after() {
	}

	@Test
	public void test() {
		System.out.println(reader.getMapper("test.namespace"));
	}
}
