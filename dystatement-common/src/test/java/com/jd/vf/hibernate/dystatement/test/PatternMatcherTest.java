package com.jd.vf.hibernate.dystatement.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class PatternMatcherTest {

	private Pattern pattern = Pattern.compile("select\\s+([\\w\\W]+)\\s+from");

	@Test
	public void Test() {
		String sql = "select * from x";
		Matcher matcher = pattern.matcher(sql);
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
