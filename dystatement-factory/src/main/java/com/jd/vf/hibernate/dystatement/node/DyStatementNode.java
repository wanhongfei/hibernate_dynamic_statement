package com.jd.vf.hibernate.dystatement.node;

import com.jd.vf.hibernate.dystatement.entity.MapperMethod;
import lombok.NoArgsConstructor;

/**
 * Created by hongfei.whf on 2016/12/16.
 */
@NoArgsConstructor
public class DyStatementNode {

	/**
	 * 存储mapperMethod的信息
	 */
	protected MapperMethod mapperMethod = new MapperMethod();

	/**
	 * 节点的信息
	 */
	protected StringBuilder context = null;

	/**
	 * 构造函数
	 *
	 * @param context
	 */
	public DyStatementNode(StringBuilder context) {
		this.context = context;
	}

	/**
	 * 设置是sql还是hql
	 *
	 * @param statementType
	 * @return
	 */
	public DyStatementNode statementType(MapperMethod.StatementTypeEnum statementType) {
		mapperMethod.setStatementType(statementType);
		return this;
	}

	/**
	 * 设置执行类型
	 *
	 * @param executeType
	 * @return
	 */
	public DyStatementNode executeType(MapperMethod.ExecuteTypeEnum executeType) {
		mapperMethod.setExecuteType(executeType);
		return this;
	}

	/**
	 * 添加子节点
	 *
	 * @return
	 */
	public DyStatementNode addSubNode(DyStatementNode subNode) {
		context.append(subNode.toString());
		return this;
	}

	/**
	 * 拼接语句
	 *
	 * @param context
	 * @return
	 */
	public DyStatementNode append(String context) {
		this.context.append(context);
		return this;
	}

	/**
	 * 重写toString函数
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return context.toString();
	}
}
