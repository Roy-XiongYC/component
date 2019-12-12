package com.xiongyc.sequence.utils;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2019年1月14日 - 下午6:14:35
 * @Info 初始版本
 * @Version 1.0
 */
public class SequenceUtil {
	
	/**
	 * 对于数字前补0
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            长度
	 * @return 前补0后的字符串
	 */
	public static String addLeftZero(String str, int num) {
		// stringbuffer添加0
		StringBuffer buffer = new StringBuffer();
		// 如果长度小于指定长度，则前补0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append("0");
			}
		}
		// 返回的字符串
		str = buffer.toString() + str;
		return str;
	}
}
