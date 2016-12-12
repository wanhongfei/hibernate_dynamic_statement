package com.jd.vf.hibernate.dystatement.util;

import lombok.Getter;
import lombok.NonNull;

import java.text.DecimalFormat;

/**
 * Created by hongfei.whf on 2016/12/11.
 */
public class DecimalUtil {

	/**
	 * decimal(double|float)->String
	 *
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String decimal2String(@NonNull Object d, @NonNull String pattern) throws Exception {
		if (d.getClass().equals(Double.class)
				|| d.getClass().equals(Float.class)) {
			DecimalFormat df = new DecimalFormat(pattern);
			return df.format(d);
		} else {
			throw new Exception("d is not double or float");
		}
	}

	/**
	 * String->double
	 *
	 * @param d
	 * @return
	 */
	public static Double string2Double(@NonNull String d) {
		return Double.parseDouble(d);
	}

	/**
	 * String->double
	 *
	 * @param d
	 * @return
	 */
	public static Float string2Float(@NonNull String d) {
		return Float.parseFloat(d);
	}

	/**
	 * Created by hongfei.whf on 2016/12/11.
	 */
	public static enum DecimalPreciseEnum {

		ONE("#.0"),
		TWO("#.00"),
		THREE("#.000"),
		FOUR("#.0000"),
		FIVE("#.00000");

		@Getter
		public String precise;

		DecimalPreciseEnum(String precise) {
			this.precise = precise;
		}
	}

}
