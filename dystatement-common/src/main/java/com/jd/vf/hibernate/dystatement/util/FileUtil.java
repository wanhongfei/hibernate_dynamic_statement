package com.jd.vf.hibernate.dystatement.util;

import lombok.Cleanup;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wwhhf
 * @comment 文件工具类
 * @since 2016年6月13日
 */
public class FileUtil {

	/**
	 * 获取classpath路径下的文件
	 *
	 * @param fileName
	 * @return
	 */
	public static File classpathFile(String fileName) throws IOException {
		org.springframework.core.io.Resource fileRource = new ClassPathResource(fileName);
		return fileRource.getFile();
	}

	/**
	 * 是否存在
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean isExist(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * @param bytes
	 * @param filePath
	 * @throws IOException
	 * @author wwhhf
	 * @comment 比特数组转为文件
	 * @since 2016年6月13日
	 */
	public static void byteArray2File(@NonNull byte[] bytes, @NonNull String filePath) throws IOException {
		File file = new File(filePath);
		@Cleanup InputStream is = new ByteArrayInputStream(bytes);
		FileUtils.copyInputStreamToFile(is, file);
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 * @author wwhhf
	 * @comment 文件转为byte数组
	 * @since 2016年6月13日
	 */
	public static byte[] file2ByteArray(@NonNull File file) throws IOException {
		return FileUtils.readFileToByteArray(file);
	}

	/**
	 * @param content
	 * @param filePath
	 * @param isAppend
	 * @throws IOException
	 * @author wwhhf
	 * @comment 写入文本文件
	 * @since 2016年6月13日
	 */
	public static void writeFile(@NonNull String content,
	                             @NonNull String filePath,
	                             boolean isAppend) throws IOException {
		File file = new File(filePath);
		FileUtils.write(file, content, isAppend);
	}

	/**
	 * @param content
	 * @param filePath
	 * @throws IOException
	 * @author wwhhf
	 * @comment 写入文本文件
	 * @since 2016年6月13日
	 */
	public static void writeFile(@NonNull String content,
	                             @NonNull String filePath) throws IOException {
		writeFile(content, filePath, true);
	}

	/**
	 * @param filePath
	 * @author wwhhf
	 * @comment 删除文件
	 * @since 2016年6月13日
	 */
	public static void deleteFile(@NonNull String filePath) {
		File file = new File(filePath);
		FileUtils.deleteQuietly(file);
	}

	/**
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @author wwhhf
	 * @comment 读取文本文件内容
	 * @since 2016年6月13日
	 */
	public static String readFile(@NonNull String filePath)
			throws IOException {
		FileUtils.readFileToString(new File(filePath));
		return FileUtils.readFileToString(new File(filePath));
	}

}
