package com.jd.vf.hibernate.dystatement.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hongfei.whf on 2016/12/3.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, Q> {

	protected T value1;

	protected Q value2;

}
