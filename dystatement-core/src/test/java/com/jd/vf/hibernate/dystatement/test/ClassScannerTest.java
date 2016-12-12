package com.jd.vf.hibernate.dystatement.test;

import com.jd.vf.hibernate.dystatement.scanner.ClassesScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongfei.whf on 2016/11/24.
 */
public class ClassScannerTest {

    private ClassesScanner scanner = null;

    @Before
    public void before() {
        List<String> paths = new ArrayList<>();
        paths.add("com.jd.vf.hibernate.dystatement.test.entity");
        scanner = new ClassesScanner(paths);
    }

    @After
    public void after() {
    }

    @Test
    public void test() {
        System.out.println(scanner.getClassPropertiesMapper());
    }
}
