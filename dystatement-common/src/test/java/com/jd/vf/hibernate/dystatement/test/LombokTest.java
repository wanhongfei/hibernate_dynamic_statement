package com.jd.vf.hibernate.dystatement.test;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * Created by hongfei.whf on 2016/12/2.
 */
public class LombokTest {

	@Test
	public void testAnSetterAndGetter() {
		People people = new People("hello", 1, true);
		People people1 = new People();
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode
	private class People {
		private String name;
		private Integer age;
		private boolean sex;
	}

}
