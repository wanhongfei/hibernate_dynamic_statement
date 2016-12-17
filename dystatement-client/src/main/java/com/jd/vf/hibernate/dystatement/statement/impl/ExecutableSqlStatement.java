package com.jd.vf.hibernate.dystatement.statement.impl;


import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.model.Pair;
import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;
import com.jd.vf.hibernate.dystatement.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

/**
 * Created by wanhongfei on 2016/12/12.
 */
public class ExecutableSqlStatement extends ExecutableStatement<PreparedStatement, Connection> {

	public ExecutableSqlStatement(String statement, List<Pair<Class, Object>> parameters, MapperMethod.ExecuteTypeEnum executeType, MapperMethod.StatementTypeEnum statementType) {
		super(statement, parameters, executeType, statementType);
	}

	/**
	 * ps 缓存：1.mysql数据库缓存，2.Java连接池
	 *
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	@Override
	public PreparedStatement precomplie(Connection connection) throws Exception {
		PreparedStatement ps = connection.prepareStatement(this.statement);
		for (int i = 0, len = this.parameters.size(); i < len; i++) {
			Pair<Class, Object> parameter = this.parameters.get(i);
			setParameter(ps, i + 1, parameter.getValue1(), parameter.getValue2());
		}
		return ps;
	}

	@Override
	protected void setParameter(PreparedStatement ps, int pos, Class clazz, Object parameter) throws Exception {
		if (clazz.equals(Integer.class)) {
			ps.setInt(pos, (Integer) parameter);
		} else if (clazz.equals(Long.class)) {
			ps.setLong(pos, (Long) parameter);
		} else if (clazz.equals(Float.class)) {
			ps.setFloat(pos, (Float) parameter);
		} else if (clazz.equals(Double.class)) {
			ps.setDouble(pos, (Double) parameter);
		} else if (clazz.equals(Date.class)) {
			ps.setDate(pos, DateUtil.date2sqlDate((Date) parameter));
		} else if (clazz.equals(java.sql.Date.class)) {
			ps.setDate(pos, (java.sql.Date) parameter);
		} else if (clazz.equals(String.class)) {
			ps.setString(pos, (String) parameter);
		} else if (clazz.equals(Boolean.class)) {
			ps.setBoolean(pos, (Boolean) parameter);
		} else {
			throw new Exception("only support int|long|float|double|date|string|boolean");
		}
	}

}
