package com.jd.vf.hibernate.dystatemenet.node.impl;

import com.jd.vf.hibernate.dystatemenet.node.DyStatementNode;
import lombok.Data;

/**
 * Created by hongfei.whf on 2016/12/16.
 */
@Data
public class IfDyStatementNode extends DyStatementNode {

	/**
	 * 判断条件
	 */
	private String condition = null;

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param condition
	 */
	public IfDyStatementNode(StringBuilder context, String condition) {
		super(context);
		this.condition = condition;
	}

	/**
	 * toString
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "<#if " + condition + ">" + context + "</#if>";
	}
}
