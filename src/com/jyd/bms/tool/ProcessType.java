package com.jyd.bms.tool;

public class ProcessType {
	/**
	 * @category 根据缩略图取原图地址
	 * @param str
	 * @return
	 */
	public static String getImgPath(String str) {
		String[] arr = str.split("/");
		StringBuilder url = new StringBuilder();
		url.append("/");
		url.append(arr[1]);
		url.append("/");
		url.append(arr[arr.length - 1]);
		return url.toString();
	}
}
