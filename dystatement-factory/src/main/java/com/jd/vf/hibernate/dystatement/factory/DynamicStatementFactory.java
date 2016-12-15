package com.jd.vf.hibernate.dystatement.factory;

import com.jd.vf.hibernate.dystatemenet.node.DyStatementNode;
import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import lombok.NonNull;

/**
 * Created by hongfei.whf on 2016/12/16.
 */
public class DynamicStatementFactory {

	/**
	 * 创建动态模板节点
	 *
	 * @param statementType
	 * @param executeType
	 * @return
	 */
	public static DyStatementNode builder(
			@NonNull MapperMethod.StatementTypeEnum statementType,
			@NonNull MapperMethod.ExecuteTypeEnum executeType) {
		return new DyStatementNode()
				.statementType(statementType)
				.executeType(executeType);
	}

	/**
	 * 创建动态模板节点
	 *
	 * @param statementType
	 * @return
	 */
	public static DyStatementNode builder(
			@NonNull MapperMethod.StatementTypeEnum statementType) {
		return new DyStatementNode()
				.statementType(statementType);
	}

	/**
	 * 创建动态模板节点
	 *
	 * @param executeType
	 * @return
	 */
	public static DyStatementNode builder(
			@NonNull MapperMethod.ExecuteTypeEnum executeType) {
		return new DyStatementNode()
				.executeType(executeType);
	}

	/**
	 * 创建动态模板节点
	 *
	 * @return
	 */
	public static DyStatementNode builder() {
		return new DyStatementNode();
	}

}
