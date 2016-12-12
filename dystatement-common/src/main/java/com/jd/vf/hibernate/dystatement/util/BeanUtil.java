package com.jd.vf.hibernate.dystatement.util;

import lombok.NonNull;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author wwhhf
 * @comment 类相关工具
 * @since 2016年6月13日
 */
public class BeanUtil {

	/**
	 * @param clazzName
	 * @param methodName
	 * @param args
	 * @author wwhhf
	 * @comment 拼装字符串
	 * @since 2016年6月1日
	 */
	public static String methodInfo(@NonNull String clazzName,
	                                @NonNull String methodName,
	                                @NonNull Object[] args) {
		StringBuffer sb = new StringBuffer(clazzName).append(".")
				.append(methodName).append("(");
		boolean isFirst = true;
		for (Object arg : args) {
			if (isFirst == true) {
				sb.append(arg);
				isFirst = false;
			} else {
				sb.append(", ").append(arg);
			}
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @param clazzName
	 * @param methodName
	 * @param args
	 * @author wwhhf
	 * @comment 拼装字符串
	 * @since 2016年6月1日
	 */
	public static String methodInfo(@NonNull String clazzName,
	                                @NonNull String methodName,
	                                @NonNull Object[] args,
	                                @NonNull Long version) {
		StringBuffer sb = new StringBuffer(methodInfo(clazzName, methodName, args)).append(".").append(version);
		return sb.toString();
	}

	/**
	 * 检查对象的属性是否相同
	 * propertyName 小写字母开头
	 *
	 * @param object1
	 * @param object2
	 * @param clazz
	 * @param propertyName
	 * @param <T>
	 * @return
	 */
	public static <T> boolean isPropertyEqual(@NonNull T object1, @NonNull T object2, @NonNull Class<T> clazz, @NonNull String propertyName) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
			Method method = pd.getReadMethod();
			Object res1 = method.invoke(object1);
			Object res2 = method.invoke(object2);
			if (res1 == null && res2 == null) {
				return true;
			} else if (res1 != null && res2 != null && res1.equals(res2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 利用反射设置属性值
	 * （1）getDeclaredFields()返回Class中所有的字段，包括私有字段；
	 * （2）getFields  只返回公共字段，即有public修饰的字段
	 *
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(@NonNull Object obj, @NonNull String fieldName,
	                                 @NonNull Object value) throws NoSuchFieldException, IllegalAccessException {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
			Method method = pd.getWriteMethod();
			if (!Modifier.isPublic(method.getModifiers())) {   //设置非共有类属性权限
				method.setAccessible(true);
			}
			method.invoke(obj, value);
		} catch (Exception e) {
			Field targetField = obj.getClass().getDeclaredField(fieldName);
			if (!Modifier.isPublic(targetField.getModifiers())) {   //设置非共有类属性权限
				targetField.setAccessible(true);
			}
			targetField.set(obj, value); //设置类属性值
		}
	}

	/**
	 * 利用反射获取属性值
	 *
	 * @param obj
	 * @param fieldName
	 */
	public static <T> T getFieldValue(@NonNull Object obj, @NonNull String fieldName,
	                                  @NonNull Class<T> clazz)
			throws IllegalAccessException, NoSuchFieldException {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
			Method method = pd.getReadMethod();
			if (!Modifier.isPublic(method.getModifiers())) {   //设置非共有类属性权限
				method.setAccessible(true);
			}
			return (T) method.invoke(obj);
		} catch (Exception e) {
			Field targetField = obj.getClass().getDeclaredField(fieldName);
			if (!Modifier.isPublic(targetField.getModifiers())) {   //设置非共有类属性权限
				targetField.setAccessible(true);
			}
			return (T) targetField.get(obj); //设置类属性值
		}
	}

	/**
	 * 从包package中获取所有的Class
	 *
	 * @param packageName
	 * @return
	 */
	public static List<Class<?>> scan(@NonNull String packageName) {
		//第一个class类的集合
		List<Class<?>> classes = new ArrayList<Class<?>>();
		//是否循环迭代
		boolean recursive = true;
		//获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		//定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			//循环迭代下去
			while (dirs.hasMoreElements()) {
				//获取下一个元素
				URL url = dirs.nextElement();
				//得到协议的名称
				String protocol = url.getProtocol();
				//如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					//获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					//以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					//如果是jar包文件
					//定义一个JarFile
					JarFile jar;
					try {
						//获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						//从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						//同样的进行循环迭代
						while (entries.hasMoreElements()) {
							//获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							//如果是以/开头的
							if (name.charAt(0) == '/') {
								//获取后面的字符串
								name = name.substring(1);
							}
							//如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								//如果以"/"结尾 是一个包
								if (idx != -1) {
									//获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								//如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									//如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										//去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											//添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 *
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
	                                                     final boolean recursive, List<Class<?>> classes) {
		//获取此包的目录 建立一个File
		File dir = new File(packagePath);
		//如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		//如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			//自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		//循环所有文件
		for (File file : dirfiles) {
			//如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
						file.getAbsolutePath(),
						recursive,
						classes);
			} else {
				//如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					//添加到集合中去
					classes.add(Class.forName(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将bean转化为map
	 *
	 * @param obj
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> bean2Map(@NonNull Object obj) throws NoSuchFieldException, IllegalAccessException {
		Map<String, Object> res = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			res.put(field.getName(),
					getFieldValue(obj, field.getName(), field.getType()));
		}
		return res;
	}

	/**
	 * 将map转化为bean
	 *
	 * @param map
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> T map2Bean(@NonNull Map<String, Object> map, Class<T> clazz)
			throws NoSuchFieldException, IllegalAccessException, InstantiationException {
		Object object = clazz.newInstance();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			setFieldValue(object, entry.getKey(), entry.getValue());
		}
		return (T) object;
	}

	/**
	 * bean数组转为fields数组
	 *
	 * @param objs
	 * @param fieldsName
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> beans2Fields(@NonNull List objs, @NonNull String fieldsName,
	                                       @NonNull Class<T> clazz)
			throws NoSuchFieldException, IllegalAccessException {
		List<T> fields = new ArrayList<>();
		for (Object object : objs) {
			fields.add(getFieldValue(object, fieldsName, clazz));
		}
		return fields;
	}

}