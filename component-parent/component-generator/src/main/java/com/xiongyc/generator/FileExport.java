package com.xiongyc.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileExport {

	public static boolean export(String filePath, String fileName,
			String context) {
		OutputStreamWriter out = null;
		try {

			File dir = new File(filePath);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(filePath + fileName);

			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

			out.write(context);

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
}
