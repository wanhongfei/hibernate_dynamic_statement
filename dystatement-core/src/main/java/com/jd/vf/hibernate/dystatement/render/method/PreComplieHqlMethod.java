package com.jd.vf.hibernate.dystatement.render.method;

/**
 * Created by hongfei.whf on 2016/12/11.
 * 针对hql自定义扩展
 */
public class PreComplieHqlMethod extends PreComplieMethod {

	// 计数器
	private static final ThreadLocal<Integer> counter = new ThreadLocal<>();

	@Override
	protected String getPlaceholder() {
		// hql 占位符 :p0
		Integer currPos = counter.get();
		if (currPos == null) {
			counter.set(currPos = 0);
		}
		counter.set(currPos + 1);
		// 返回替换符？
		return ":p" + currPos;
	}

}
