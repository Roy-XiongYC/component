package com.xiongyc.generator;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiongyc.generator.database.mysql.ColumnRowMapper;
import com.xiongyc.generator.generator.AbstractGenerator;
import com.xiongyc.generator.generator.ControllerGenerator;
import com.xiongyc.generator.generator.DaoGenerator;
import com.xiongyc.generator.generator.ModelGenerator;
import com.xiongyc.generator.generator.MyBatisGenerator;
import com.xiongyc.generator.generator.ServiceGenerator;
import com.xiongyc.generator.generator.SpringConfigGenerator;
import com.xiongyc.generator.generator.WebPageGenerator;
import com.xiongyc.generator.model.ColumnPojo;
import com.xiongyc.generator.model.TablePojo;

public class Enter {

	public static void main(String[] args) {
//		System.out.println("**************EXCEPTION INFO IS NULL*******************");
		Enter enter = new Enter();
		Properties prop = new Properties();
		
		try {
			InputStream in;
			
			Resource resource = new ClassPathResource("generator.properties");
			in = new BufferedInputStream(resource.getInputStream());
			prop.load(in); /// 加载属性列表
//			Iterator<String> it = prop.stringPropertyNames().iterator();
//				while (it.hasNext()) {
//					String key = it.next();
//					System.out.println(key + ":" + prop.getProperty(key));
//				}
			System.out.println("init database connection.");
			enter.ds = new DruidDataSource();
			enter.ds.setUrl(prop.getProperty("spring.datasource.url", null));
			enter.ds.setUsername("uc_test");
			enter.ds.setPassword("uc_test");
			enter.ds.setUsername(prop.getProperty("spring.datasource.username", null));
			enter.ds.setPassword(prop.getProperty("spring.datasource.password", null));
			packageName = prop.getProperty("packageName", null);
			MYSQL_NAME = prop.getProperty("MYSQL_NAME", null);
			String[] tableNames = prop.getProperty("tableNames", null).split(",");
			enter.jdbcTemplate = new JdbcTemplate(enter.ds);
			enter.generate(tableNames);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			enter.ds.close();
		}

	}

//	private static final String JDBC_URL = "jdbc:mysql://172.1.10.24:3306/afterSale?characterEncoding=utf8";
//	private static final String JDBC_USERNAME = "root";
//	private static final String JDBC_PASSWORD = "123456";
	private static String MYSQL_NAME;// 主干数据
	private static String packageName;

	public void generate(String[] tableNames) {

		String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + "/demo/";

		exportPath = desktopPath;

		model = new ModelGenerator(packageName, exportPath, jdbcTemplate);

		mybatis = new MyBatisGenerator(packageName, exportPath, jdbcTemplate);

		repository = new DaoGenerator(packageName, exportPath, jdbcTemplate);

		service = new ServiceGenerator(packageName, exportPath, jdbcTemplate);

		controller = new ControllerGenerator(packageName, exportPath, jdbcTemplate);

		webpage = new WebPageGenerator(packageName, exportPath, jdbcTemplate);

		springCofig = new SpringConfigGenerator(packageName, exportPath, jdbcTemplate);

		for (String tableName : tableNames) {
			generate(tableName);
		}

		System.out.println("\r\n\r\n");
		System.out.println(((SpringConfigGenerator) springCofig).contextRespository);
	}

	public void generate(String tableName) {
		TablePojo table = queryTable(tableName);

		model.doGenerator(table);
		mybatis.doGenerator(table);
		repository.doGenerator(table);
		service.doGenerator(table);
		controller.doGenerator(table);
		webpage.doGenerator(table);
		springCofig.doGenerator(table);

	}

	public TablePojo queryTable(String tableName) {
		TablePojo table = new TablePojo();
		table.setTableName(tableName);
		table.setPackageName(packageName);

		/* test 去掉 sys_code 列 */
		String sql = "SELECT COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE,COLUMN_TYPE  FROM information_schema.COLUMNS   WHERE TABLE_NAME = '"
				+ tableName + "' and table_schema = '" + MYSQL_NAME
				+ "' and column_name not in ('sys_code') ORDER BY ORDINAL_POSITION";

		List<ColumnPojo> columns = jdbcTemplate.query(sql, new ColumnRowMapper());

		sql = "SELECT TABLE_COMMENT  FROM INFORMATION_SCHEMA.TABLES   WHERE TABLE_NAME = '" + tableName + "' and table_schema = '" + MYSQL_NAME +"'";
		String tableComment  = jdbcTemplate.queryForObject(sql, String.class);
		
		table.setColumns(columns);
		table.setTableComment(tableComment);
		return table;

	}

	private AbstractGenerator model;

	private AbstractGenerator mybatis;

	private AbstractGenerator repository;

	private AbstractGenerator service;

	private AbstractGenerator springCofig;

	private AbstractGenerator controller;

	private AbstractGenerator webpage;

	private String exportPath;

	private JdbcTemplate jdbcTemplate;

	private DruidDataSource ds;

}
