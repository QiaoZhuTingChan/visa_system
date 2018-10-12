package com.jyd.bms.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.jyd.bms.common.Environment;

/**
 * 按公式生成Java代码
 * 
 * @author ganpeng
 *
 */
public class GenerateCode {
	/**
	 * 分析特殊字符，返回特殊字符列表
	 * 
	 * @param formula
	 * @return
	 */
	public static List<String> analyzeSpecialCode(String formula) {
		List<String> list = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();

		int position = 0;
		while ((position = formula.indexOf("@")) != -1) {
			String value = formula.substring(position, position + 4);
			formula = formula.substring(position + 4, formula.length());
			if (!map.containsKey(value)) {
				map.put(value, value);
			}
		}
		for (String s : map.keySet()) {
			list.add(s);
		}
		return list;
	}

	/**
	 * 自动生成代码
	 * 
	 * @param formula
	 * @param formulaKey
	 * @return
	 * @throws IOException
	 */
	public static boolean generateCode(String formula, String formulaKey) throws IOException {
		List<String> list = analyzeSpecialCode(formula);
		if (list.size() == 0)
			return false;

		String formulaString = "";
		for (String key : list) {
			formulaString += "double " + key.replace("@", "") + ",";
		}

		formulaString = formulaString.substring(0, formulaString.length() - 1);

		String formulaCode = "import java.math.BigDecimal;\n" + "import java.text.DecimalFormat;\n"
				+ "import java.text.NumberFormat;\n" + "public class " + formulaKey.replace("@", "") + "{\n"
				+ "	public double calculate(" + formulaString + ") {\n" + "		return new BigDecimal("
				+ formula.replace("@", "") + ").setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();\n" + "	}\n" + "}";

		File writename = new File(Environment.getGenerateCodePath() + formulaKey.replace("@", "") + ".java"); // 相对路径，如果没有则要建立一个新的output。txt文件
		writename.createNewFile(); // 创建新文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));

		out.write(formulaCode);
		out.flush(); // 把缓存区内容压入文件
		out.close(); // 最后记得关闭文件

		boolean compilerFlag = compiler(Environment.getGenerateCodePath() + formulaKey.replace("@", "") + ".java");
		if (compilerFlag == false)
			return false;

		return true;
	}

	/**
	 * 检查类是否存在
	 * 
	 * @param formulaKey
	 * @return
	 */
	public static boolean checkClassExists(String formulaKey) {
		File file = new File(Environment.getGenerateCodePath() + formulaKey.replace("@", "") + ".class");
		return file.exists();
	}

	/**
	 * 编译Java代码
	 * 
	 * @param name
	 * @return
	 */
	public static boolean compiler(String name) {
		try {
			JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = jc.getStandardFileManager(null, null, null);
			Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(name);
			CompilationTask cTask = jc.getTask(null, fileManager, null, null, null, fileObjects);
			cTask.call();

			fileManager.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 计算结果
	 * 
	 * @param formulaKey
	 *            Key
	 * @param formula
	 *            公式
	 * @param listParam
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public static double calculate(String formulaKey, String formula, List<Double> listParam) throws Exception {
		// 使用URLClassLoader加载class到内存
		double value = 0;

		List<String> listSpecialKey = analyzeSpecialCode(formula);
		MyClassLoader classLoader = new MyClassLoader(
				Environment.getGenerateCodePath() + formulaKey.replace("@", "") + ".class");

		Class<?> c = classLoader.loadClass(formulaKey.replace("@", ""));
		// 利用class创建实例，反射执行方法
		Object obj = c.newInstance();
		Class[] arg = new Class[listSpecialKey.size()];

		for (int index = 0; index < listSpecialKey.size(); index++) {
			arg[index] = double.class;
		}
		Method method = c.getMethod("calculate", arg);
		value = (double) method.invoke(obj, listParam.toArray());
		classLoader.clearAssertionStatus();

		return value;
	}

	public static void main(String[] arr) {
		String formula = "(@abc+@bcd/2+@aec)>100?100:500";
		String formulaKey = "@eee";
		List<String> list = GenerateCode.analyzeSpecialCode(formula);

		try {
			boolean flag = generateCode(formula, formulaKey);
			List<Double> listValue = new ArrayList<Double>();
			listValue.add(10d);
			listValue.add(20d);
			listValue.add(30d);

			for (int i = 1; i < 10; i++) {
				listValue.set(0, i * 1d);
				listValue.set(1, i * 10d);
				listValue.set(2, i * 20d);
				double value = calculate(formulaKey, formula, listValue);

				System.out.println("Value:" + value);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
