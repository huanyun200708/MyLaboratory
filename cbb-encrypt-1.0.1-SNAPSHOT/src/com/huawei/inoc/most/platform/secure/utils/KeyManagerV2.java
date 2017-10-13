package com.huawei.inoc.most.platform.secure.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyManagerV2 {
	private static final Logger LOGGER = Logger.getLogger(KeyManagerV2.class
			.getName());
	private static List<String> rootKeyComponents;
	private static String workKey;

	static {
		loadRootKeyComponent();

		loadWorkKey();
	}

	private static void loadRootKeyComponent() {
		try {
			List<String> clList = KeyUtils
					.loadProperties("component_list.properties");

			if ((clList == null) || (clList.isEmpty())) {
				String rootConf = System.getProperty("PM4H_CONF");

				if ((rootConf == null) || (rootConf.isEmpty())) {
					rootConf = System.getenv("PM4H_CONF");
				}

				clList = KeyUtils.loadPMProperties(rootConf);
			}

			List<String> keyComponent = readRootKeyComponent(clList);

			if ((keyComponent == null) || (keyComponent.size() < 2)) {
				return;
			}

			keyComponent.add(getCodeRootKeyComponent());

			rootKeyComponents = keyComponent;
			keyComponent = null;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Load properties error!");
		}
	}

	private static String getCodeRootKeyComponent() {
		byte[] codeRootKeyComponent = { -93, -24, -74, 101, 62, -66, 112, 42,
				32, 98, -126, -82, -59, 116, 8, 99 };
		codeRootKeyComponent[0] = ((byte) (codeRootKeyComponent[10] - codeRootKeyComponent[9]));
		codeRootKeyComponent[1] = ((byte) (codeRootKeyComponent[12] & codeRootKeyComponent[15]));
		codeRootKeyComponent[2] = ((byte) (codeRootKeyComponent[7] - codeRootKeyComponent[11]));
		codeRootKeyComponent[3] = ((byte) (codeRootKeyComponent[6] << codeRootKeyComponent[10]));
		codeRootKeyComponent[4] = ((byte) (codeRootKeyComponent[7] * codeRootKeyComponent[10]));
		codeRootKeyComponent[5] = ((byte) (codeRootKeyComponent[11] - codeRootKeyComponent[12]));
		codeRootKeyComponent[6] = ((byte) (codeRootKeyComponent[15] + codeRootKeyComponent[7]));
		codeRootKeyComponent[7] = ((byte) (codeRootKeyComponent[3] & codeRootKeyComponent[4]));
		codeRootKeyComponent[8] = ((byte) (codeRootKeyComponent[15] << codeRootKeyComponent[9]));
		codeRootKeyComponent[9] = ((byte) (codeRootKeyComponent[0] ^ codeRootKeyComponent[0]));
		codeRootKeyComponent[10] = ((byte) (codeRootKeyComponent[12] | codeRootKeyComponent[1]));
		codeRootKeyComponent[11] = ((byte) (codeRootKeyComponent[8] * codeRootKeyComponent[14]));
		codeRootKeyComponent[12] = ((byte) (codeRootKeyComponent[11] | codeRootKeyComponent[4]));
		codeRootKeyComponent[13] = ((byte) (codeRootKeyComponent[8] & codeRootKeyComponent[11]));
		codeRootKeyComponent[14] = ((byte) (codeRootKeyComponent[4] ^ codeRootKeyComponent[15]));
		codeRootKeyComponent[15] = ((byte) (codeRootKeyComponent[3] | codeRootKeyComponent[1]));

		return new String(KeyUtils.encodeHex(codeRootKeyComponent));
	}

	private static void loadWorkKey() {
		try {
			File clFile = KeyUtils.getConfigFile(
					new File(new File("").getCanonicalPath()),
					"work_key.properties");

			if ((clFile == null) || (0L == clFile.length())) {
				String workConf = System.getProperty("PM4H_CONF");

				if ((workConf == null) || (workConf.isEmpty())) {
					workConf = System.getenv("PM4H_CONF");
				}
				clFile = KeyUtils.getConfigFile(new File(workConf),
						"work_key.properties");
			}

			String wkey = null;
			if ((clFile != null) && (clFile.canRead())) {
				InputStream is = new FileInputStream(clFile);
				Properties prop = new Properties();
				prop.load(is);
				wkey = prop.getProperty("work_key");
				if (is != null)
					is.close();
			}

			workKey = wkey;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Load Work Key error");
		}
	}

	private static List<String> readRootKeyComponent(List<String> files) {
		if ((files == null) || (files.size() < 2)) {
			LOGGER.log(Level.WARNING,
					"Root key component count must be greater than 2!");
			return null;
		}

		File file = null;
		for (String filename : files) {
			file = new File(filename);
			if ((!file.exists()) || (!file.canRead())) {
				LOGGER.log(Level.WARNING,
						"Root key component not exist or can not read!");
				return null;
			}

		}

		List<String> components = new ArrayList<String>();
		Properties prop = null;
		String item = null;
		for (String filename : files) {
			try {
				InputStream is = new FileInputStream(filename);
				prop = new Properties();
				prop.load(is);

				item = prop.getProperty("component");

				if ((item == null) || (item.length() != 32)) {
					LOGGER.log(Level.WARNING,
							"Root key component length error!");
					return null;
				}
				components.add(item);
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Can not read Root key component!");
				return null;
			}
		}

		return components;
	}

	public static byte[] genRootKeyFromComponent() {
		if ((rootKeyComponents == null) || (rootKeyComponents.size() < 2)) {
			LOGGER.log(Level.WARNING,
					"Root key component initial failure or count less than 2!");
			return null;
		}

		byte[] rootKey0 = KeyUtils.decodeHex(((String) rootKeyComponents.get(0))
				.toCharArray());
		byte[] comp = null;

		if (rootKey0 == null) {
			LOGGER.log(Level.WARNING, "Recovery root key failure!");
			return null;
		}

		for (int i = 1; i < rootKeyComponents.size(); i++) {
			comp = KeyUtils.decodeHex(((String) rootKeyComponents.get(i))
					.toCharArray());
			if (comp != null) {
				for (int j = 0; j < rootKey0.length; j++) {
					//将rootkey0数组中的每个元素和4个rootkey数组中的每个元素进行异或处理
					rootKey0[j] = ((byte) (rootKey0[j] ^ comp[j]));
				}
			}
		}
		if (rootKey0.length != 16) {
			LOGGER.log(Level.WARNING, "Recovery root key failure!");
			return null;
		}

		return rootKey0;
	}

	public static String getWorkKey() {
		return workKey;
	}
}

/*
 * Location: C:\Users\acer-pc\Desktop\cbb-encrypt-1.0.1-SNAPSHOT.jar Qualified
 * Name: com.huawei.inoc.most.platform.secure.utils.KeyManagerV2 JD-Core
 * Version: 0.6.2
 */