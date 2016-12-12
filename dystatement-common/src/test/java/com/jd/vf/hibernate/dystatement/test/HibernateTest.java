package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
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
		Connection connection = HibernateUtil.createConnection(session);
		System.out.println(connection);
		connection.close();
		HibernateUtil.closeSession(session);
	}
}
