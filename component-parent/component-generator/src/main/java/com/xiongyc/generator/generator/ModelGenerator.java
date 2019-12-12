package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.model.TablePojo;

public class ModelGenerator extends AbstractGenerator {

	public ModelGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate) {
		super(packageName, exportPath, jdbcTemplate);

		System.out.println("init model class generator success.");
	}

	@Override
	public boolean doGenerator(TablePojo table) {

		String exportPath = getExportPathWithPackage("/bean/");

		FileExport.export(exportPath, table.getTableNameTransfer() + ".java",
				table.toClassStr());

		System.out.println("Generate model class file : " + exportPath
				+ table.getTableNameTransfer() + ".java");

		return true;
	}

}
