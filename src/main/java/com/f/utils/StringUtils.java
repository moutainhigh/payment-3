package com.f.utils;

import java.util.regex.Pattern;

import com.google.common.base.Joiner;

public class StringUtils {

	/**
	 * 判断字符长度
	 * 
	 * @param v
	 * @param charset
	 * @return
	 */
	public static int length(String strValue, String charset) {
		if (isEmpty(strValue))
			return 0;
		int len = -1;
		try {
			byte[] strbytes = strValue.getBytes(charset);
			len = strbytes.length;
		} catch (Exception e) {
		}
		return len;
	}

	/**
	 * 判断字符是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (value == null)
			return true;
		if ("".equals(value) || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否不为空字符串
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNotEmpty(String text) {
		if (text == null || "".equals(text.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化成XML识别的字符
	 * 
	 * @param value
	 *            字符串
	 * @return string 过滤后的字符串
	 */
	public static String formatStringToXML(final String value) {
		if (value == null)
			return "";
		char[] content = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {

			case '&':
				result.append("＆");
				break;
			case '<':
				result.append("＜");
				break;
			case '>':
				result.append("＞");
				break;
			case '"':
				result.append("＄");
				break;
			case '\'':
				result.append("＇");
				break;
			case '$':
				result.append("");
				break;
			/*
			 * case '&': result.append("&amp;"); break; case '<': result.append("&lt;"); break; case '>': result.append("&gt;"); break; case '"': result.append("&quot;"); break; case '\'': result.append("&#39;");
			 * break;
			 */
			default:
				result.append(content[i]);
			}
		}
		return result.toString();
	}

	public static String formatStringToXML(final String value, int length) {
		String s = formatStringToXML(value);
		if (s.length() > length)
			s = s.substring(0, length);

		return s;
	}

	/**
	 * 是否全数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 连接字符串
	 * 
	 * @return
	 */
	public static String append(String prefix, Object... params) {
		return append(prefix, "|", params);
	}

	/**
	 * 连接字符串
	 * 
	 * @return
	 */
	public static String append(String prefix, String separator, Object... params) {
		Joiner joiner = Joiner.on(separator);
		StringBuilder prefixSb = new StringBuilder(prefix);
		if (!isEmpty(prefix))
			prefixSb.append(separator);
		return joiner.appendTo(prefixSb, params).toString();
	}

	public static void append(final StringBuilder sbString, final String... strings) {
		sbString.ensureCapacity(sbString.length() + getLength(strings));

		for (final String string : strings) {
			sbString.append(string);
		}
	}

	private static int getLength(final String[] strings) {
		int length = 0;

		for (final String string : strings) {
			if (string == null) {
				length += 4;
			} else {
				length += string.length();
			}
		}

		return length;
	}

	/**
	 * 过滤表情
	 * 
	 * @param source
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source) {
		if (!StringUtils.isEmpty(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		} else {
			return source;
		}
	}

	/**
	 * 字符串转换为整数
	 * 
	 * @param strValue
	 * @return
	 */
	public static int str2int(String strValue) {
		if (isEmpty(strValue))
			return 0;
		return Integer.valueOf(strValue);
	}
}
