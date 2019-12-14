package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.model.ColumnPojo;
import com.xiongyc.generator.model.TablePojo;

public class MyBatisGenerator extends AbstractGenerator
{

	public MyBatisGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);

		System.out.println("init mybatis config generator success.");
	}

	@Override
	public boolean doGenerator(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/dao/mapper/");
		String fileName = table.getTableNameTransfer() + "Mapper.xml";

		String context = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
		context += "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n";
		context += "<mapper namespace=\"" + packageName + ".dao.I" + table.getTableNameTransfer() + "Dao\">\r\n";

		context += getResultMapStr(table);
		context += getCriteriaWhereClause(table);
		context += getBaseColumnList(table);
		context += getQueryPage(table);
		context += getQueryList(table);
		context += getQueryPageCount(table);
		context += getQueryEntity(table);
		context += getInsert(table);
		context += getInsertBatch(table);
		context += getInsertOrUpdateBatch(table);
		context += getUpdateByCriteria(table);
		context += getUpdateBatchByCriteria(table);
		context += getDeleteByCriteria(table);
		context += getDeleteById(table);
		context += getdeleteBatchByIds(table);
		context += "</mapper>\r\n";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate bean class file : " + filePath + fileName);

		return true;
	}

	private String getResultMapStr(TablePojo table)
	{
		String context = "<resultMap id=\"BaseResultMap\"  type=\"" + packageName + ".bean." + table.getTableNameTransfer() + "\" >\r\n";

		String template = "\t<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" />\r\n";

		for (ColumnPojo column : table.getColumns())
		{

			context += String.format(template, column.getColumnName(), column.getColumnNameTransfer(), column.getMyBatisDataType());
		}

		context += "</resultMap> \r\n";

		return context;
	}

	private String getCriteriaWhereClause(TablePojo table)
	{
		String context = "<sql id=\"Criteria_Where_Clause\"> \r\n";
		context += "\t<where> \r\n";

		for (ColumnPojo column : table.getColumns())
		{

			context += "\t\t<if test=\"params." + column.getColumnNameTransfer() + "!=null\">\r\n";
			context += "\t\t\t and " + column.getColumnName() + "=#{params." + column.getColumnNameTransfer() + "}\r\n";
			context += "\t\t</if>\r\n";
		}

		context += "\t</where> \r\n";
		context += "</sql> \r\n";

		return context;
	}

	private String getBaseColumnList(TablePojo table)
	{
		String context = "<sql id=\"Base_Column_List\">\r\n";
		context += "  ";
		for (ColumnPojo column : table.getColumns())
		{
			context += column.getColumnName() + ",";
		}
		context = context.substring(0, context.length() - 1) + "\r\n";

		context += "</sql> \r\n";

		return context;
	}

	private String getQueryPage(TablePojo table)
	{
		String context = "<select id=\"queryPage\" resultMap=\"BaseResultMap\" parameterType=\"com.xiongyc.utils.mybatis.Criteria\"> \r\n";

		context += "\tselect \r\n";
		context += "\t<include refid=\"Base_Column_List\" />\r\n";
		context += "\tfrom " + table.getTableName() + "\r\n";
		context += "\t<if test=\"params != null\"> \r\n";
		context += "\t\t<include refid=\"Criteria_Where_Clause\" /> \r\n";
		context += "\t</if> \r\n";
		context += "\t<if test=\"orderBy!=null\"> \r\n";
		context += "\t\torder by ${orderBy} \r\n";
		context += "\t</if> \r\n";
		context += "</select> \r\n";

		return context;
	}

	private String getQueryList(TablePojo table)
	{
		String context = "<select id=\"queryList\" resultMap=\"BaseResultMap\" parameterType=\"com.xiongyc.utils.mybatis.Criteria\"> \r\n";

		context += "\tselect \r\n";
		context += "\t<include refid=\"Base_Column_List\" />\r\n";
		context += "\tfrom " + table.getTableName() + "\r\n";
		context += "\t<if test=\"params != null\"> \r\n";
		context += "\t\t<include refid=\"Criteria_Where_Clause\" /> \r\n";
		context += "\t</if> \r\n";
		context += "\t<if test=\"orderBy!=null\"> \r\n";
		context += "\t\torder by ${orderBy} \r\n";
		context += "\t</if> \r\n";
		context += "</select> \r\n";

		return context;
	}
	
	private String getQueryPageCount(TablePojo table)
	{
		String context = "<select id=\"queryPageCount\" resultType=\"java.lang.Integer\" parameterType=\"com.xiongyc.utils.mybatis.Criteria\"> \r\n";
		context += "\tselect count(1) from " + table.getTableName() + " \r\n";
		context += "\t<if test=\"params != null\"> \r\n";
		context += "\t\t<include refid=\"Criteria_Where_Clause\" /> \r\n";
		context += "\t</if> \r\n";
		context += "</select> \r\n";

		return context;

	}

	private String getQueryEntity(TablePojo table)
	{
		String context = "<select id=\"queryEntityById\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.String\">\r\n";

		context += "\tselect \r\n";
		context += "\t<include refid=\"Base_Column_List\" />\r\n";
		context += "\tfrom " + table.getTableName();
		context += "\twhere " + table.getColumns().get(0).getColumnName() + " = #{" + table.getColumns().get(0).getColumnNameTransfer() + "}\r\n";
		context += "</select>\r\n";

		return context;
	}

	private String getInsert(TablePojo table)
	{
		String context = "<insert id=\"insert\" parameterType=\"" + packageName + ".bean." + table.getTableNameTransfer() + "\"> \r\n";
		context += "\tinsert into " + table.getTableName() + "\r\n";
		context += "\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (ColumnPojo column : table.getColumns())
		{
			context += "\t\t<if test=\"" + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t\t" + column.getColumnName() + ",\r\n";
			context += "\t\t</if>\r\n";
		}
		context += "\t</trim>\r\n";
		context += "\tvalues\r\n";
		context += "\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (ColumnPojo column : table.getColumns())
		{
			context += "\t<if test=\"" + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t#{" + column.getColumnNameTransfer() + "},\r\n";
			context += "\t</if>\r\n";
		}

		context += "\t</trim>\r\n";
		
		context += "</insert>\r\n";

		return context;
	}

	private String getInsertBatch(TablePojo table)
	{
		String context = "<insert id=\"insertBatch\" parameterType=\"java.util.List\"> \r\n";
		context += "\tinsert into " + table.getTableName() + "\r\n";
		context += "\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		context += "\t\t\t";
		for (ColumnPojo column : table.getColumns())
		{
			context += column.getColumnName() + ",";
		}
		context = context.substring(0, context.length()-1) + "\r\n";
		context += "\t</trim>\r\n";
		context += "\tvalues\r\n";
		context += "\t<foreach collection=\"list\" item=\"item\" separator=\",\">\r\n";
		context += "\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		context += "\t\t\t";
		for (ColumnPojo column : table.getColumns())
		{
			context += "#{item." + column.getColumnNameTransfer() + "},";
		}
		context = context.substring(0, context.length()-1) + "\r\n";
		context += "\t\t</trim>\r\n";
		context += "\t</foreach>\r\n";
		context += "</insert>\r\n";

		return context;
	}	
	
	private String getInsertOrUpdateBatch(TablePojo table)
	{
		
		String context ="<!--  批量保存 存在更新不存在插入(如果指定一个on duplicate key update子句，并且要插入的行将导致唯一索引或主键中的值重复，\r\n" + 
				"则会更新旧行, 对于innodb表，如果a是自动递增列，则效果不相同。对于自动递增列，insert语句会增加自动递增值，但update不会。 )\r\n" + 
				"详细文档 https://dev.mysql.com/doc/refman/5.5/en/insert-on-duplicate.html -->\r\n";
		context +="<insert id=\"insertOrUpdateBatch\" parameterType=\"java.util.List\"> \r\n";
		context += "\t<foreach collection=\"list\" item=\"item\" separator=\";\">\r\n";
		
		context += "\tinsert into " + table.getTableName() + "\r\n";
		context += "\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (ColumnPojo column : table.getColumns())
		{
			context += "\t\t<if test=\"item." + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t\t" + column.getColumnName() + ",\r\n";
			context += "\t\t</if>\r\n";
		}
		context += "\t</trim>\r\n";
		context += "\tvalues\r\n";
		context += "\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (ColumnPojo column : table.getColumns())
		{
			context += "\t<if test=\"item." + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t#{item." + column.getColumnNameTransfer() + "},\r\n";
			context += "\t</if>\r\n";
		}

		context += "\t</trim>\r\n";
		context += "\tON DUPLICATE KEY UPDATE\r\n";
		context += "\t";
		for (int i = 1; i < table.getColumns().size(); i++) {
			context += table.getColumns().get(i).getColumnName() + " = " +"#{item." + table.getColumns().get(i).getColumnNameTransfer() + "},";
		}
//		for (ColumnPojo column : table.getColumns())
//		{
//			context += column.getColumnName() + " = " +"#{item." + column.getColumnNameTransfer() + "},";
//		}
		context = context.substring(0, context.length()-1) + "\r\n";
		context += "\t</foreach>\r\n";
		context += "</insert>\r\n";

		return context;
	}		
	
	private String getUpdateByCriteria(TablePojo table)
	{
		String context = "<update id=\"updateByCriteria\" parameterType=\"com.xiongyc.utils.mybatis.Criteria\">\r\n";
		context += "\tupdate " + table.getTableName() + "\r\n";
		context += "\t<set>\r\n";

		for (ColumnPojo column : table.getColumns())
		{
			context += "\t<if test=\"record." + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t" + column.getColumnName() + " = #{record." + column.getColumnNameTransfer() + "},\r\n";
			context += "\t</if>\r\n";
		}

		context += "\t</set>\r\n";

		context += "\t<if test=\"params != null\">\r\n";
		context += "\t\t<include refid=\"Criteria_Where_Clause\" />\r\n";
		context += "\t</if>\r\n";
		context += "</update>\r\n";

		return context;
	}

	private String getUpdateBatchByCriteria(TablePojo table)
	{
		String context = "<update id=\"updateBatchByCriteria\" parameterType=\"java.util.List\">\r\n";
		
		context += "\t<foreach collection=\"list\" item=\"params\" separator=\";\">\r\n";
		
		context += "\t\tupdate " + table.getTableName() + "\r\n";
		context += "\t\t<set>\r\n";

		
		for (ColumnPojo column : table.getColumns())
		{
			if(column.getColumnNameTransfer().equals(table.getColumns().get(0).getColumnNameTransfer())){
				continue;
			}
			context += "\t\t<if test=\"params." + column.getColumnNameTransfer() + " != null\"> \r\n";
			context += "\t\t\t" + column.getColumnName() + " = #{params." + column.getColumnNameTransfer() + "},\r\n";
			context += "\t\t</if>\r\n";
		}

		context += "\t\t</set>\r\n";

		context += "\t\t<if test=\"params != null\">\r\n";
//		context += "\t\t\t<include refid=\"Criteria_Where_Clause\" />\r\n";
		context += "\t\t\t<where>\r\n";
		context += "\t\t\t\t<if test=\"params." + table.getColumns().get(0).getColumnNameTransfer() + " !=null\">\r\n";
		context += "\t\t\t\t\tand "+ table.getColumns().get(0).getColumnName() +"=#{params." + table.getColumns().get(0).getColumnNameTransfer() + " }\r\n";
		context += "\t\t\t\t</if>\r\n";
		context += "\t\t\t</where>\r\n";

		
		context += "\t\t</if>\r\n";
		context += "\t</foreach>\r\n";
		context += "</update>\r\n";
		
		return context;
	}
	
	private String getDeleteByCriteria(TablePojo table)
	{
		String context = "<delete id=\"deleteByCriteria\" parameterType=\"com.xiongyc.utils.mybatis.Criteria\">\r\n";
		context += "\tdelete from " + table.getTableName() + "\r\n";
		context += "\t<if test=\"params != null\">\r\n";
		context += "\t\t<include refid=\"Criteria_Where_Clause\" />\r\n";
		context += "\t</if>\r\n";
		context += "</delete>\r\n";
		return context;
	}

	private String getDeleteById(TablePojo table)
	{
		String context = "<delete id=\"deleteById\" parameterType=\"java.lang.String\">\r\n";
		context += "\tdelete from " + table.getTableName() + "\r\n";
		context += "\twhere " + table.getColumns().get(0).getColumnName() + " = #{" + table.getColumns().get(0).getColumnNameTransfer() + "}\r\n";
		context += "</delete>\r\n";
		return context;
	}
	
	private String getdeleteBatchByIds(TablePojo table)
	{
		String context = "<delete id=\"deleteBatchByIds\">\r\n";
		context += "\tdelete from " + table.getTableName() + "\r\n";
		context += "\twhere apply_id in\r\n";
		context += "\t<foreach collection=\"array\" item=\"id\" open=\"(\" close=\")\" separator=\",\">\r\n";
		context += 	"\t\t#{id}\r\n";
		context += "\t</foreach>\r\n";
		context += "</delete>\r\n";
		return context;
	}
}
