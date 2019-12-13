package com.xiongyc.generator.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TablePojo
{

	public String toClassStr()
	{

		if (tableName == null || "".equals(tableName.trim()))
		{
			return "";
		}

		String classStr = "";

		classStr += getPackageNameTransfer(".bean");

		classStr += "\r\n";
		classStr += "import org.apache.commons.lang3.builder.ReflectionToStringBuilder;\r\n";
		classStr += "import com.xiongyc.utils.mybatis.IModel;\r\n";
		classStr += "import com.fasterxml.jackson.annotation.JsonFormat;\r\n";
		classStr += "import java.math.BigDecimal;\r\n";
		classStr += "import io.swagger.annotations.ApiModel;\r\n";
		classStr += "import io.swagger.annotations.ApiModelProperty;\r\n";
		classStr += "import java.util.Date;\r\n\r\n\r\n";
		classStr += "@ApiModel(\""+ getTableComment() +"\")\r\n";
		classStr += "public class " + getTableNameTransfer() + " implements IModel { ";

		classStr += "\r\n\r\n";

		classStr += "\tprivate static final long serialVersionUID = 1L; ";

		classStr += "\r\n\r\n";
		
		classStr += "\tpublic String toString() {return ReflectionToStringBuilder.toString(this);}";

		classStr += "\r\n\r\n";

		if (columns != null && !columns.isEmpty())
		{

			for (ColumnPojo column : columns)
			{
				classStr += " " + column.toFieldDesc();
			}
			classStr += "\r\n";
			
			for (ColumnPojo column : columns)
			{
				classStr += " " + column.toGetAndSetMethod();
			}

		}

		classStr += "\r\n";
		classStr += "\r\n";

		classStr += "} ";

		return classStr;

	}

	public String getTableNameTransferInitialLowcase()
	{
		String tabelNameTransfer = getTableNameTransfer();

		tabelNameTransfer = tabelNameTransfer.substring(0, 1).toLowerCase() + tabelNameTransfer.substring(1);
		
		return tabelNameTransfer;
	}

	public String getTableNameTransfer()
	{
		if (tableName == null)
		{
			return "";
		}

		String[] split = tableName.split("_");
		String ret = "";

		int i = 0;

		for (String str : split)
		{
			if (str == null || "".equals(str.trim()))
			{
				continue;
			}

			if (i == 0 && "t".equals(str.toLowerCase()))
			{
				i++;
				continue;
			}

			ret += str.substring(0, 1).toUpperCase() + str.substring(1);
			i++;
		}

		return ret;
	}

	public String getPackageNameTransfer(String sufix)
	{
		if (packageName != null && packageName.trim().length() > 0)
		{
			return "package " + packageName.trim().replaceAll(";", "") + (sufix != null ? sufix : "") + ";\r\n";
		}

		return "\r\n";
	}

	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	public List<ColumnPojo> getColumns()
	{
		return columns;
	}

	public void setColumns(List<ColumnPojo> columns)
	{
		this.columns = columns;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}


	public String getTableNameInitials()
	{
		String s = ""; 
		for (String str : tableName.split("_")) {
			s += str.substring(0, 1);
		}
		return s.toUpperCase();
	}
	
	
	private List<ColumnPojo> columns;

	private String tableName;

	private String tableComment;
	
	private String packageName;
}
