package com.xiongyc.generator.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xiongyc.generator.model.ColumnPojo;


public class ColumnRowMapper implements RowMapper<ColumnPojo> {

	public ColumnPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ColumnPojo column = new ColumnPojo();
		column.setColumnName(rs.getString("COLUMN_NAME"));
		column.setColumnComment(rs.getString("COLUMN_COMMENT"));
		column.setColumnType(rs.getString("COLUMN_TYPE"));
		column.setDataType(rs.getString("DATA_TYPE"));
		return column;
	}

}
