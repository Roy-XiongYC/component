package com.xiongyc.generator.generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.VelocityExport;
import com.xiongyc.generator.model.TablePojo;

public class WebPageGenerator extends AbstractGenerator
{

	public WebPageGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);

	}

	@Override
	public boolean doGenerator(TablePojo table)
	{
//		generateListPage(table);
//		generateDetailPage(table);
		return false;
	}

	@SuppressWarnings("unused")
	private String generateListPage(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/jsp/" + table.getPackageName() + "/");
		String fileName = table.getTableNameTransfer() + "List.jsp";

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("page-list-url", "/" + table.getPackageName() + "/" + table.getTableNameTransferInitialLowcase());

		context.put("query-conditions", "aaaa");

		context.put("query-list-columns", table.getColumns());

		context.put("query-page-url", "/" + table.getPackageName() + "/" + table.getTableNameTransferInitialLowcase() + "/queryPage");
		context.put("detail-page-url", "/" + table.getPackageName() + "/" + table.getTableNameTransferInitialLowcase() + "/detail");
		context.put("delete-page-url", "/" + table.getPackageName() + "/" + table.getTableNameTransferInitialLowcase() + "/queryPage");

		VelocityExport.export(filePath, fileName, context, "webpageList.vm");

		System.out.println("Generate repository class file : " + filePath + fileName);
		return null;

	}

	@SuppressWarnings("unused")
	private String generateDetailPage(TablePojo table)
	{

		String filePath = getExportPathWithPackage("/jsp/" + table.getPackageName() + "/");
		String fileName = table.getTableNameTransfer() + "Detail.jsp";

		String context = "";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate repository class file : " + filePath + fileName);
		return null;
	}
}
