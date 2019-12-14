package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.model.TablePojo;

public class DaoGenerator extends AbstractGenerator
{

	public DaoGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);

		System.out.println("init dao class generator success.");
	}

	@Override
	public boolean doGenerator(TablePojo table)
	{
		generateInterface(table);
//		generateImpl(table);

		return true;
	}

	private boolean generateInterface(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/dao/");
		String fileName = "I" + table.getTableNameTransfer() + "Dao.java";

		String context = table.getPackageNameTransfer(".dao");
		context += "\r\n\r\n";
		context += "import java.util.List;\r\n";
		context += "import com.xiongyc.utils.mybatis.Criteria;\r\n";
		context += "import org.springframework.stereotype.Repository;\r\n";
		context += "import com.xiongyc.utils.mybatis.MyDao;\r\n";
		context += "import " + table.getPackageName() + ".bean." + table.getTableNameTransfer() + ";\r\n\r\n\r\n";

		context += "@Repository\r\n";
		
		context += "public interface I" + table.getTableNameTransfer() + "Dao extends MyDao<"+ table.getTableNameTransfer() +">{\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 查询集合 \r\n";
		context += "\t* @param param\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tList<" + table.getTableNameTransfer() + "> queryPage(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 查询集合 \r\n";
		context += "\t* @param param\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tList<" + table.getTableNameTransfer() + "> queryList(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";
		
		context += "\t/**\r\n";
		context += "\t* 查询集合总记录数 \r\n";
		context += "\t* @param param\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger queryPageCount(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 查询实体 \r\n";
		context += "\t* @param id\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\t" + table.getTableNameTransfer() + " queryEntityById(String "+table.getColumns().get(0).getColumnNameTransfer()+");\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 新增实体 \r\n";
		context += "\t* @param record\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tint insert(" + table.getTableNameTransfer() + " record);\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 批量新增实体 \r\n";
		context += "\t* @param list\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger insertBatch(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "\t/**\r\n";
		context += "\t* 更新实体 \r\n";
		context += "\t* @param param\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger updateByCriteria(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 批量更新实体 \r\n";
		context += "\t* @param list\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger updateBatchByCriteria(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "\t/**\r\n";
		context += "\t* 批量保存实体 \r\n";
		context += "\t* @param list\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger insertOrUpdateBatch(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "\t/**\r\n";
		context += "\t* 删除实体 \r\n";
		context += "\t* @param id\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger deleteById(String "+table.getColumns().get(0).getColumnNameTransfer()+");\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 删除实体 \r\n";
		context += "\t* @param id\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger deleteByCriteria(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "\t/**\r\n";
		context += "\t* 删除实体 \r\n";
		context += "\t* @param ids\r\n";
		context += "\t* @return\r\n";
		context += "\t*/\r\n";
		context += "\tInteger deleteBatchByIds(String[] array);\r\n\r\n";
		
//		context += "\tstatic final String MAPPER_NAMESPACE = \"" + table.getPackageName() + ".dao." + table.getTableNameTransfer() + "Mapper\";\r\n";
		context += "\r\n\r\n";
		context += "}\r\n";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate dao class file : " + filePath + fileName);

		return true;
	}

	@SuppressWarnings("unused")
	private boolean generateImpl(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/dao/impl/");
		String fileName = table.getTableNameTransfer() + "DaoImpl.java";

		String context = table.getPackageNameTransfer(".dao.impl");
		context += "\r\n\r\n";
		context += "import java.util.List;\r\n";
		context += "import org.mybatis.spring.SqlSessionTemplate;\r\n";
		context += "import org.apache.ibatis.session.RowBounds;\r\n";

		context += "import com.hjzx.framework.mybatis.Criteria;\r\n";
		context += "import " + table.getPackageName() + ".bean." + table.getTableNameTransfer() + ";\r\n";
		context += "import " + table.getPackageName() + ".dao.I" + table.getTableNameTransfer() + "Dao;\r\n\r\n\r\n";

		context += "public class " + table.getTableNameTransfer() + "DaoImpl implements I" + table.getTableNameTransfer() + "Dao{\r\n\r\n";

		context += "\tpublic List<" + table.getTableNameTransfer() + "> queryPage(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "\t\tRowBounds r = null;\r\n";
		context += "\t\tif (param != null) r = new RowBounds(param.getStartIndex(), param.getPageSize());\r\n";
		context += "\t\treturn sqlSessionTemplate.selectList(MAPPER_NAMESPACE + \".queryPage\", param, r);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic Integer queryPageCount(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "\t\treturn (Integer) sqlSessionTemplate.selectOne(MAPPER_NAMESPACE+ \".queryPageCount\", param);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic " + table.getTableNameTransfer() + " queryEntityById(String id){\r\n";
		context += "\t\treturn sqlSessionTemplate.selectOne(MAPPER_NAMESPACE + \".queryEntity\",id);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic Integer insert(" + table.getTableNameTransfer() + " record){\r\n";
		context += "\t\treturn sqlSessionTemplate.insert(MAPPER_NAMESPACE + \".insert\", record);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic Integer updateByCriteria(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "\t\treturn sqlSessionTemplate.update(MAPPER_NAMESPACE + \".updateByCriteria\", param);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic Integer deleteById(String id){\r\n";
		context += "\t\treturn sqlSessionTemplate.delete(MAPPER_NAMESPACE + \".deleteById\",id);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic Integer deleteByCriteria(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "\t\treturn sqlSessionTemplate.delete(MAPPER_NAMESPACE + \".deleteByCriteria\", param);\r\n";
		context += "\t}\r\n\r\n";

		context += "\tpublic void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {this.sqlSessionTemplate = sqlSessionTemplate;}\r\n";

		context += "\tprivate SqlSessionTemplate sqlSessionTemplate; \r\n\r\n";

		context += "}\r\n";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate dao class file : " + filePath + fileName);
		return true;
	}
}
