package com.jd.vf.hibernate.dystatemenet.node.impl;

import com.jd.vf.hibernate.dystatemenet.node.DyStatementNode;
import lombok.Data;

/**
 * Created by hongfei.whf on 2016/12/16.
 */
@Data
public class ListDyStatemenetNode extends DyStatementNode {

	/**
	 * 分隔符
	 */
	private String separator = null;

	/**
	 * list变量名
	 */
	private String listVarName = null;

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param separator
	 * @param listVarName
	 */
	public ListDyStatemenetNode(StringBuilder context, String separator, String listVarName) {
		super(context);
		this.separator = separator;
		this.listVarName = listVarName;
	}

	/**
	 * toString
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return " <#assign isFirst = true>\n" +
				" <#list " + listVarName + " as item>\n" +
				"       <#if isFirst == false>,</#if>\n" +
				"       " + context + "\n" +
				"       <#assign isFirst = false>\n" +
				" </#list>";
	}
}
