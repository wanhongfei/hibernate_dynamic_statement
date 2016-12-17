package com.jd.vf.hibernate.dystatement.statement.impl;

import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.model.Pair;
import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;
import com.jd.vf.hibernate.dystatement.util.DateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by wanhongfei on 2016/12/12.
 */
public class ExecutableHqlStatement extends ExecutableStatement<Query, Session> {

	public ExecutableHqlStatement(String statement, List<Pair<Class, Object>> parameters, MapperMethod.ExecuteTypeEnum executeType, MapperMethod.StatementTypeEnum statementType) {
		super(statement, parameters, executeType, statementType);
	}

	/**
	 * query 缓存：开启hibernate缓存-><property name="hibernate.cache.use_query_cache">true</property>
	 * .setCacheable(true)
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@Override
	public Query precomplie(Session session) throws Exception {
		Query query = session.createQuery(this.statement);
		for (int i = 0, len = this.parameters.size(); i < len; i++) {
			Pair<Class, Object> parameter = this.parameters.get(i);
			setParameter(query, i, parameter.getValue1(), parameter.getValue2());
		}
		return query;
	}

	@Override
	protected void setParameter(Query query, int pos, Class clazz, Object parameter) throws Exception {
		if (clazz.equals(Integer.class)) {
			query.setInteger(pos, (Integer) parameter);
		} else if (clazz.equals(Long.class)) {
			query.setLong(pos, (Long) parameter);
		} else if (clazz.equals(Float.class)) {
			query.setFloat(pos, (Float) parameter);
		} else if (clazz.equals(Double.class)) {
			query.setDouble(pos, (Double) parameter);
		} else if (clazz.equals(Date.class)) {
			query.setDate(pos, (Date) parameter);
		} else if (clazz.equals(java.sql.Date.class)) {
			query.setDate(pos, DateUtil.sqlDate2Date((java.sql.Date) parameter));
		} else if (clazz.equals(String.class)) {
			query.setString(pos, (String) parameter);
		} else if (clazz.equals(Boolean.class)) {
			query.setBoolean(pos, (Boolean) parameter);
		} else {
			throw new Exception("only support int|long|float|double|date|string|boolean");
		}
	}
}
