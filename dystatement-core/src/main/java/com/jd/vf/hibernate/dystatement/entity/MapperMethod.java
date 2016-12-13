package com.jd.vf.hibernate.dystatement.entity;

import lombok.Data;
import lombok.Getter;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
@Data
public class MapperMethod {

	/**
	 * 查询方法唯一标识
	 */
	protected String id;

	/**
	 * selet还是DML
	 */
	protected ExecuteTypeEnum executeType;

	/**
	 * 动态sql模板
	 */
	protected String dynamicTemplate;

	/**
	 * 执行类型：sql或者hql
	 * 默认sql
	 */
	protected StatementTypeEnum statementType = StatementTypeEnum.SQL;

	/**
	 * 语句类型，sql或者hql
	 */
	public static enum ExecuteTypeEnum {
		Select("select"), Insert("insert"), Update("update"), Delete("delete");
		@Getter
		private String name;

		ExecuteTypeEnum(String name) {
			this.name = name;
		}
	}

	/**
	 * 语句类型，sql或者hql
	 */
	public static enum StatementTypeEnum {
		SQL("sql"), HQL("hql");
		@Getter
		private String name;

		StatementTypeEnum(String name) {
			this.name = name;
		}
	}
}
