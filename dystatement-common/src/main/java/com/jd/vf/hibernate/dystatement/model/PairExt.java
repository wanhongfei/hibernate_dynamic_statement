package com.jd.vf.hibernate.dystatement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by hongfei.whf on 2016/12/3.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PairExt<T, Q, K> extends Pair<T, Q> {

	protected K value3;

	public PairExt(T value1, Q value2, K value3) {
		super(value1, value2);
		this.value3 = value3;
	}

}
