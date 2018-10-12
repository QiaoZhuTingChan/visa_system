package com.jyd.bms.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {
	private byte[] results;

	public MyClassLoader(String pathName) {
		// 拿到class转成的字节码文件
		results = loadClassFile(pathName);
	}

	// 把我们的class文件转成字节码，用于classloader动态加载
	private byte[] loadClassFile(String classPath) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			FileInputStream fi = new FileInputStream(classPath);
			BufferedInputStream bis = new BufferedInputStream(fi);
			byte[] data = new byte[1024 * 256];
			int ch = 0;
			while ((ch = bis.read(data, 0, data.length)) != -1) {
				bos.write(data, 0, ch);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bos.toByteArray();

	}

	@Override
	protected Class<?> loadClass(String arg0, boolean arg1) throws ClassNotFoundException {
		Class<?> clazz = findLoadedClass(arg0);
		if (clazz == null) {
			if (getParent() != null) {
				try {
					clazz = getParent().loadClass(arg0);
				} catch (Exception e) {
				}
			}

			if (clazz == null) {
				clazz = defineClass(arg0, results, 0, results.length);
			}
		}

		return clazz;
	}
}
