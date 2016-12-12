package com.jd.vf.hibernate.dystatement.template;

import com.jd.vf.hibernate.dystatement.statement.ExecutableStatement;

/**
 * Created by wanhongfei on 2016/11/24.
 */
public interface Template {

	public void init();

	public ExecutableStatement assembled(String namespace, String methodId, Object params);

}
