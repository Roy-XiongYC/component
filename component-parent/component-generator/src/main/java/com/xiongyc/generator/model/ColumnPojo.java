package com.xiongyc.generator.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ColumnPojo
{
	static final Map<String, String> javaTypeMap = new HashMap<String, String>();
	static final Map<String, String> myBatisTypeMap = new HashMap<String, String>();

	static
	{
		javaTypeMap.put("varchar", "String");
		javaTypeMap.put("text", "String");
		javaTypeMap.put("char", "String");
		javaTypeMap.put("bit", "Boolean");
		javaTypeMap.put("int", "Integer");
		javaTypeMap.put("tinyint", "Integer");
		javaTypeMap.put("bigint", "Long");
		javaTypeMap.put("date", "Date");
		javaTypeMap.put("datetime", "Date");
		javaTypeMap.put("timestamp", "Date");
		javaTypeMap.put("decimal", "BigDecimal");
		javaTypeMap.put("float", "Float");
		javaTypeMap.put("double", "Double");

		myBatisTypeMap.put("text", "VARCHAR");
		myBatisTypeMap.put("varchar", "VARCHAR");
		myBatisTypeMap.put("char", "VARCHAR");
		myBatisTypeMap.put("int", "INTEGER");
		myBatisTypeMap.put("bit", "INTEGER");
		myBatisTypeMap.put("tinyint", "INTEGER");
		myBatisTypeMap.put("bigint", "INTEGER");
		myBatisTypeMap.put("date", "DATE");
		myBatisTypeMap.put("datetime", "TIMESTAMP");
		myBatisTypeMap.put("timestamp", "TIMESTAMP");
		myBatisTypeMap.put("decimal", "DECIMAL");
		myBatisTypeMap.put("float", "FLOAT");
		myBatisTypeMap.put("double", "DOUBLE");
	}

	public String getJavaDataType()
	{
		return ColumnPojo.javaTypeMap.containsKey(this.getDataType()) ? ColumnPojo.javaTypeMap.get(this.getDataType()) : this.getDataType();
	}

	public String getMyBatisDataType()
	{
		return ColumnPojo.myBatisTypeMap.containsKey(this.getDataType()) ? ColumnPojo.myBatisTypeMap.get(this.getDataType()) : this.getDataType();
	}

	public String toGetAndSetMethod()
	{
		String fieldName = getColumnNameTransfer();
		if (fieldName == null || "".equals(fieldName.trim()))
		{
			return "";
		}

		String fieldNameNew = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String method = "";
		if (null != columnComment && !"".equals(columnComment.trim()))
		{
			method += "\t/** \r\n";
			method += "\t* 获取 " + columnComment + "\r\n";
			method += "\t*/ \r\n";
		}
		method += "\tpublic " + getJavaDataType() + " get" + fieldNameNew + "(){ return " + fieldName + " ; } \r\n";

		if (null != columnComment && !"".equals(columnComment.trim()))
		{
			method += "\t/** \r\n";
			method += "\t* 设置 " + columnComment + "\r\n";
			method += "\t*/ \r\n";
		}
		method += "\tpublic void set" + fieldNameNew + "(" + getJavaDataType() + " " + fieldName + "){ this." + fieldName + "=" + fieldName + " ; } \r\n";

		return method;
	}

	public String toFieldDesc()
	{
		String fieldDesc = "";

		if (null != columnComment && !"".equals(columnComment.trim()))
		{
			fieldDesc += "\t/** \r\n";
			fieldDesc += "\t* " + columnComment + "\r\n";
			fieldDesc += "\t*/ \r\n";
		}
		
		fieldDesc += "    @ApiModelProperty(value =\""+ columnComment + "\")\r\n";
//		fieldDesc += "    @TableField(\""+ columnName + "\")\r\n";
		if(javaTypeMap.get("date").equals(getJavaDataType())) {
			fieldDesc += "    @JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\",timezone = \"Asia/Shanghai\")\r\n";
		}
		fieldDesc += "\tprivate " + getJavaDataType() + " " + getColumnNameTransfer() + "; \r\n\r\n";
		return fieldDesc;
	}

	public String getColumnNameTransferUpper()
	{
		String columnName = getColumnNameTransfer();

		return columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
	}

	public String getColumnNameTransfer()
	{
		if (columnName == null)
		{
			return "";
		}

		String[] split = columnName.split("_");
		String ret = "";
		int i = 0;
		for (String str : split)
		{

			if (str == null || "".equals(str.trim()))
			{
				continue;
			}

			if (i == 0)
			{
				ret = str.substring(0, 1).toLowerCase() + str.substring(1);
			}
			else
			{
				ret += str.substring(0, 1).toUpperCase() + str.substring(1);
			}

			i++;

		}

		return ret;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public String getColumnComment()
	{
		return columnComment;
	}

	public void setColumnComment(String columnComment)
	{
		this.columnComment = columnComment;
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}

	public String getColumnType()
	{
		return columnType;
	}

	public void setColumnType(String columnType)
	{
		this.columnType = columnType;
	}

	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

	private String columnName;

	private String columnComment;

	private String dataType;

	private String columnType;

}
