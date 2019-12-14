package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.model.TablePojo;

public class SpringConfigGenerator extends AbstractGenerator
{

	public SpringConfigGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);

		System.out.println("init spring config generator success.");
	}

	@Override
	public boolean doGenerator(TablePojo table)
	{

//		String repositoryBeanId = table.getTableNameTransferInitialLowcase() + "Repository";
//		String repositoryClzz = table.getPackageName() + ".repository.impl." + table.getTableNameTransfer() + "RepositoryImpl";
//		contextRespository += String.format(TEMPLATE_REPOSITORY, repositoryBeanId, repositoryClzz);
//
//		String serviceBeanId = table.getTableNameTransferInitialLowcase() + "Service";
//		String serviceClzz = table.getPackageName() + ".service.impl." + table.getTableNameTransfer() + "ServiceImpl";
//		contextRespository += String.format(TEMPLATE_SERVICE, serviceBeanId, serviceClzz, repositoryBeanId, repositoryBeanId);

		return false;
	}

	public String contextRespository = "";

//	private static String TEMPLATE_REPOSITORY = "<bean id=\"%s\" class=\"%s\" parent=\"abstractRepository\"></bean>\r\n";

//	private static String TEMPLATE_SERVICE = "<bean id=\"%s\" class=\"%s\">\r\n\t<property name=\"%s\" ref=\"%s\"></property>\r\n\t<property name=\"dictService\" ref=\"sysDictService\"></property>\r\n\t<property name=\"messageService\" ref=\"messageService\"></property>\r\n</bean>\r\n";

}
