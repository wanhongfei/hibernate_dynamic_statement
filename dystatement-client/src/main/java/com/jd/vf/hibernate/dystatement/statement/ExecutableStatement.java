package com.jd.vf.hibernate.dystatement.statement;

import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import com.jd.vf.hibernate.dystatement.model.Pair;
import com.jd.vf.hibernate.dystatement.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

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
	 * 方法请求类型，是select还是DML
	 */
	private MapperMethod.ExecuteTypeEnum executeType;

	/**
	 * 方法是sql还是hql
	 */
	private MapperMethod.StatementTypeEnum statementType;

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

	/**
	 * 是不是增加更新和删除的sql
	 *
	 * @return
	 */
	public boolean isDML() {
		return executeType.equals(MapperMethod.ExecuteTypeEnum.Insert) ||
				executeType.equals(MapperMethod.ExecuteTypeEnum.Update) ||
				executeType.equals(MapperMethod.ExecuteTypeEnum.Delete);
	}

	/**
	 * 是不是查询
	 *
	 * @return
	 */
	public boolean isSelect() {
		return executeType.equals(MapperMethod.ExecuteTypeEnum.Select);
	}

	/**
	 * 返回线程绑定的list(不包括class)
	 *
	 * @return
	 */
	@SneakyThrows
	public List<Object> getParameters() {
		return BeanUtil.beans2Fields(parameters, "value2", Object.class);
	}
}
