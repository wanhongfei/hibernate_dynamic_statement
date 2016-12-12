package com.jd.vf.hibernate.dystatement.statement;

import com.jd.vf.hibernate.dystatement.model.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by hongfei.whf on 2016/12/11.
 */
@Data
@AllArgsConstructor
public abstract class ExecutableStatement<T, Q> {

	/**
	 * 预编译sql或者hql
	 */
	protected String statement;

	/**
	 * 预编译sql和hql替换符对应的？
	 */
	protected List<Pair<Class, Object>> parameters;

	/**
	 * 预编译sql或hql
	 * sql返回ps
	 * hql返回query
	 *
	 * @param q
	 * @return
	 */
	public abstract T precomplie(Q q) throws Exception;

	/**
	 * 预编译设置参数
	 *
	 * @param t
	 * @param pos
	 * @param clazz
	 * @param parameter
	 */
	protected abstract void setParameter(T t, int pos, Class clazz, Object parameter) throws Exception;
}
