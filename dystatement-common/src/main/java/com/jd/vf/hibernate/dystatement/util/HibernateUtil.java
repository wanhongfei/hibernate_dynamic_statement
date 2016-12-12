package com.jd.vf.hibernate.dystatement.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class HibernateUtil {

	private static Configuration configuration = null;
	private static SessionFactory sessionFactory = null;

	static {
		configuration = new Configuration();
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	/**
	 * 开启session
	 *
	 * @return
	 */
	public static Session openSession() {
		return sessionFactory.openSession();
	}

	/**
	 * 关闭session
	 *
	 * @param session
	 */
	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

	/**
	 * 开启事务
	 *
	 * @param session
	 * @return
	 */
	public static Transaction beginTransaction(Session session) {
		return session.beginTransaction();
	}

	/**
	 * 提交事务
	 *
	 * @param transaction
	 * @return
	 */
	public static void commitTransaction(Transaction transaction) {
		transaction.commit();
	}

	/**
	 * 事务回滚
	 *
	 * @param transaction
	 * @return
	 */
	public static void rollbackTransaction(Transaction transaction) {
		transaction.rollback();
	}

	/**
	 * 获取connection
	 *
	 * @return
	 */
	public static Connection createConnection(Session session) throws SQLException {
		return session.connection();
	}

}
