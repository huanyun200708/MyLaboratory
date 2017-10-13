package com.huawei.inoc.most.platform.secure.utils;

import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2;
import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2Algorithm;
import com.huawei.inoc.most.platform.secure.pbkdf2.PBKDF2Factory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class KeyGeneratorV2 {
	private static final Logger LOGGER = Logger.getLogger(KeyGeneratorV2.class
			.getName());

	private static byte[] initVector = null;

	static {
		initInitVector();
	}

	private static void initInitVector() {
		initVector = new byte[] { 10, 68, 2, -56, -8, -70, 82, 112, -9, 118,
				-55, 94, 104, 75, -76, -9 };
		initVector[0] = ((byte) (initVector[9] & initVector[1]));
		initVector[1] = ((byte) (initVector[0] * initVector[12]));
		initVector[2] = ((byte) (initVector[9] & initVector[4]));
		initVector[3] = ((byte) (initVector[1] | initVector[4]));
		initVector[4] = ((byte) (initVector[13] + initVector[14]));
		initVector[5] = ((byte) (initVector[15] & initVector[8]));
		initVector[6] = ((byte) (initVector[1] * initVector[12]));
		initVector[7] = ((byte) (initVector[0] & initVector[8]));
		initVector[8] = ((byte) (initVector[3] + initVector[6]));
		initVector[9] = ((byte) (initVector[2] + initVector[15]));
		initVector[10] = ((byte) (initVector[7] << initVector[1]));
		initVector[11] = ((byte) (initVector[3] << initVector[14]));
		initVector[12] = ((byte) (initVector[9] << initVector[12]));
		initVector[13] = ((byte) (initVector[15] * initVector[8]));
		initVector[14] = ((byte) (initVector[8] << initVector[8]));
		initVector[15] = ((byte) (initVector[7] | initVector[5]));
	}

	public static boolean genRootKeyComponent() {
		List<String> files = null;
		try {
			files = KeyUtils.loadProperties("component_list.properties");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
					"Load root key component list file failure!");
			return false;
		}

		if ((files == null) || (files.size() < 2)) {
			LOGGER.log(Level.SEVERE,
					"Root key component count must be greater than 2!");
			return false;
		}

		File file = null;
		try {
			for (String filename : files) {
				file = new File(filename);
				if (file.exists()) {
					if (!file.delete()) {
						LOGGER.log(Level.SEVERE, "Create file failure !");
						return false;
					}
				}

				if (!file.createNewFile()) {
					LOGGER.log(Level.SEVERE, "Create file failure !");
					return false;
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Create file failure !");
			return false;
		}

		List<String> keys = new ArrayList<String>();

		for (int i = 0; i < files.size(); i++) {
			keys.add(exportFactory());
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						(String) files.get(i)));
				bw.write((new StringBuilder("component=")).append(
						(String) keys.get(i)).toString());
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE,
						"Write root key compnent into file failure !");
				return false;
			}
		}

		return true;
	}

	public static String exportFactory() {
		byte[] keyFactor = new byte[''];

		SecureRandom sr = new SecureRandom();
		sr.nextBytes(keyFactor);

		String result = new String(KeyUtils.encodeHex(PBKDF2Factory
				.getInstance(PBKDF2Algorithm.PBKDF2WithHmacSHA256).getKey(
						keyFactor, initVector)));

		return result;
	}
}

/*
 * Location: C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar Qualified
 * Name: com.huawei.inoc.most.platform.secure.utils.KeyGeneratorV2 JD-Core
 * Version: 0.6.2
 */