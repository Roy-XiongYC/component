package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.model.TablePojo;

public abstract class AbstractGenerator {

	public AbstractGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate) {
		super();
		this.packageName = packageName;
		this.exportPath = exportPath;
		this.jdbcTemplate = jdbcTemplate;
	}

	public abstract boolean doGenerator(TablePojo table);

	public String getExportPathWithPackage(String sufix) {
		String packagePath = "";
		if (null != packageName && !"".equals(packageName)) {
			packagePath = packageName.replaceAll("\\.", "\\/");
		}
		return exportPath + packagePath + (sufix != null ? sufix : "");
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	protected String packageName;

	protected String exportPath;

	protected JdbcTemplate jdbcTemplate;

}
