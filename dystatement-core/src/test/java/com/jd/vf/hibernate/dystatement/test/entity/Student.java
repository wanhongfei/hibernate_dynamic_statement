package com.jd.vf.hibernate.dystatement.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
@Table(name = "student")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private int age;
}
