package com.huawei.inoc.most.platform.secure.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyUtils {
	private static final Logger LOGGER = Logger.getLogger(KeyUtils.class
			.getName());

	public static List<String> loadProperties(String filename)
			throws IOException {
		File clFile = getConfigFile(new File(new File("").getCanonicalPath()),
				filename);

		List<String> clList = new ArrayList<String>();
		if ((clFile != null) && (clFile.canRead())) {
			BufferedReader br = new BufferedReader(new FileReader(clFile));
			for (String line = null; (line = br.readLine()) != null;) {
				if (!line.isEmpty())
					clList.add(line.trim());
			}
			if (br != null)
				br.close();
		}
		return clList;
	}

	public static List<String> loadPMProperties(String filePath)
			throws IOException {
		File clFile = getConfigFile(new File(filePath),
				"component_list.properties");

		List<String> clList = new ArrayList<String>();
		if ((clFile != null) && (clFile.canRead())) {
			BufferedReader br = new BufferedReader(new FileReader(clFile));
			for (String line = null; (line = br.readLine()) != null;) {
				if (!line.isEmpty())
					clList.add(line.trim());
			}

			if (br != null)
				br.close();
		}
		return clList;
	}

	public static File getConfigFile(File file, String filename) {
		if ((file == null) || (file.isFile())) {
			LOGGER.log(Level.SEVERE, "Can not find " + filename + "!");
			return null;
		}

		try {
			File target = new File(file.getCanonicalPath() + "/encrypt" + "/"
					+ filename);
			if (target.exists()) {
				return target;
			}

			target = new File(file.getCanonicalPath() + "/common/encrypt" + "/"
					+ filename);
			if (target.exists()) {
				return target;
			}

			if (file.getParent() == null) {
				return null;
			}
			return getConfigFile(new File(file.getParent()), filename);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Can not find " + filename + "!");
		}

		return null;
	}
	
	/**
	 * 此方法是将一个偶数个长度的字符数组转换为长度为入参数组长度的1/2的叔祖并返回
	 * */
	public static byte[] decodeHex(char[] data) {
		int len = data.length;

		if ((len & 0x1) != 0) {
			return null;
		}

		byte[] out = new byte[len >> 1];

		int i = 0;
		for (int j = 0; j < len; i++) {
			//Character.digit(data[j], 16)是将16进制转换为十进制
			int f = Character.digit(data[j], 16) << 4;
			j++;
			f |= Character.digit(data[j], 16);
			j++;
			out[i] = ((byte) (f & 0xFF));
		}

		return out;
	}

	/**
	 * 此方法是将入参数组转换为一个二倍长度的数组并返回;
	 * 入参数组中的每个元素被映射为2个元素，放入返回数组中;
	 * 映射为的元素被限制为0-f直接的字符
	 * */
	public static char[] encodeHex(byte[] data) {
		char[] toDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		int l = data.length;
		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			/**
			 * "0xF0 & data[i]"的最大值为240，">>> 4"相当于除以16，所以"(0xF0 & data[i]) >>> 4"的最大值为15
			 * 所以data数组中的每个元素的值都被计算转换为0-15内的数字
			 * "0xF & data[i]"的最大值为15
			 * */
			out[(j++)] = toDigits[(0xF0 & data[i]) >>> 4];
			out[(j++)] = toDigits[0xF & data[i]];
		}
		return out;
	}
}
