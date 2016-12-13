package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class HibernateTest {

	@Before
	public void before() {
	}

	@After
	public void after() {
	}

	@Test
	public void test() throws SQLException {
		Session session = HibernateUtil.openSession();
		Query query = session.createQuery("from com.jd.vf.hibernate.dystatement.test.entity.Student stu where 1=1 and stu.name = ? and stu.age = ?");
		query.setString("p0", "whf");
		query.setInteger("p1", 1);
		System.out.println(query.list());
		HibernateUtil.closeSession(session);
	}
}
