package com.jd.vf.hibernate.dystatement.node.impl;

import com.jd.vf.hibernate.dystatement.node.DyStatementNode;

/**
 * Created by wanhongfei on 2016/12/19.
 */
public class WhereDyStatement extends DyStatementNode {

	@Override
	public DyStatementNode append(String context) {
		this.context.append("and " + context);
		return this;
	}

	@Override
	public DyStatementNode addSubNode(DyStatementNode subNode) {
		this.context.append("and " + subNode.toString());
		return this;
	}

	@Override
	public String toString() {
		return "where 1=1";
	}

}
