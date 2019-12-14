package com.xiongyc.generator.generator;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.model.TablePojo;

public class ServiceGenerator extends AbstractGenerator
{

	public ServiceGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);

	}

	@Override
	public boolean doGenerator(TablePojo table)
	{
		generateInterface(table);
		generateImpl(table);

		return true;
	}

	private boolean generateInterface(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/service/");
		String fileName = "I" + table.getTableNameTransfer() + "Service.java";

		String context = table.getPackageNameTransfer(".service");
		context += "\r\n\r\n";
		context += "import java.util.List;\r\n";
		//context += "import java.util.Map;\r\n";
		context += "import com.xiongyc.utils.mybatis.Criteria;\r\n";
		context += "import com.github.pagehelper.PageInfo;\r\n";
		context += "import " + table.getPackageName() + ".bean." + table.getTableNameTransfer() + ";\r\n\r\n\r\n";

		context += "public interface I" + table.getTableNameTransfer() + "Service{\r\n\r\n";

		context += "/**\r\n";
		context += "* 查询集合 \r\n";
		context += "* @param param\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "PageInfo<"+ table.getTableNameTransfer() +"> queryPage(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "/**\r\n";
		context += "* 查询集合 \r\n";
		context += "* @param param\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "List<"+ table.getTableNameTransfer() +"> queryList(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";
		
		context += "/**\r\n";
		context += "* 查询集合总记录数 \r\n";
		context += "* @param param\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "Integer queryPageCount(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "/**\r\n";
		context += "* 查询实体 \r\n";
		context += "* @param id\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "" + table.getTableNameTransfer() + " queryEntityById(String " + table.getColumns().get(0).getColumnNameTransfer() + ");\r\n\r\n";

		context += "/**\r\n";
		context += "* 新增实体 \r\n";
		context += "* @param record\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String insert(" + table.getTableNameTransfer() + " record);\r\n\r\n";

		context += "/**\r\n";
		context += "* 批量新增实体 \r\n";
		context += "* @param list\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String insertBatch(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "/**\r\n";
		context += "* 更新实体 \r\n";
		context += "* @param param\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String updateByCriteria(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "/**\r\n";
		context += "* 批量更新实体 \r\n";
		context += "* @param param\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String updateBatchByCriteria(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "/**\r\n";
		context += "* 批量保存实体 \r\n";
		context += "* @param list\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String insertOrUpdateBatch(List<" + table.getTableNameTransfer() + "> list);\r\n\r\n";
		
		context += "/**\r\n";
		context += "* 删除实体 \r\n";
		context += "* @param id\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String deleteById(String " + table.getColumns().get(0).getColumnNameTransfer() + ");\r\n\r\n";

		context += "/**\r\n";
		context += "* 删除实体 \r\n";
		context += "* @param model\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String deleteByCriteria(Criteria<" + table.getTableNameTransfer() + "> param);\r\n\r\n";

		context += "/**\r\n";
		context += "* 批量删除实体 \r\n";
		context += "* @param ids\r\n";
		context += "* @return\r\n";
		context += "*/\r\n";
		context += "String deleteBatchByIds(String[] ids);\r\n\r\n";
		
		context += "\r\n\r\n";
		context += "}\r\n";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate service class file : " + filePath + fileName);

		return true;
	}

	private boolean generateImpl(TablePojo table)
	{
		String filePath = getExportPathWithPackage("/service/impl/");
		String fileName = table.getTableNameTransfer() + "ServiceImpl.java";

		String context = table.getPackageNameTransfer(".service.impl");
		context += "\r\n\r\n";
		context += "import java.util.List;\r\n";
		//context += "import java.util.Map;\r\n";
		context += "import com.xiongyc.utils.code.AppResponseCode;\r\n";
		context += "import com.github.pagehelper.PageInfo;\r\n";
		context += "import com.github.pagehelper.PageHelper;\r\n";
		context += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
		context += "import org.springframework.stereotype.Service;\r\n";
		context += "import org.springframework.transaction.annotation.Transactional;\r\n";
		
		context += "import com.xiongyc.utils.mybatis.Criteria;\r\n";
		context += "import " + table.getPackageName() + ".bean." + table.getTableNameTransfer() + ";\r\n";
		context += "import " + table.getPackageName() + ".service.I" + table.getTableNameTransfer() + "Service;\r\n\r\n\r\n";
		context += "import " + table.getPackageName() + ".dao.I" + table.getTableNameTransfer() + "Dao;\r\n\r\n\r\n";

		
		
		
		//context += "import com.hjzx.system.service.ISysDictService;\r\n";

		context += "@Service\r\n";
		context += "@Transactional\r\n";
		context += "public class " + table.getTableNameTransfer() + "ServiceImpl implements I" + table.getTableNameTransfer() + "Service{\r\n\r\n";

		context += "@Override\r\n";
		context += "  public PageInfo<" + table.getTableNameTransfer() +"> queryPage(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		//context += "    List<" + table.getTableNameTransfer() + "> list = " + table.getTableNameTransferInitialLowcase() + "Dao.queryPage(param); \r\n";
		//context += "    List<Map<String, Object>> ret = dictService.translateToMapList(list);\r\n";
//		context += "    page.setRecords( " + table.getTableNameTransferInitialLowcase() + "Dao.queryPage(page,param));\r\n";
//		context += "    return page;\r\n";
		context += "    PageHelper.startPage(param.getPageNum(), param.getPageSize());\r\n";
		context += "    return  new PageInfo<" + table.getTableNameTransfer() +">( " + table.getTableNameTransferInitialLowcase() + "Dao.queryPage(param));\r\n";
		//context += "    return ret;";
		context += "  }\r\n\r\n";

		context += "  public List<" + table.getTableNameTransfer() +"> queryList(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "    return  " + table.getTableNameTransferInitialLowcase() + "Dao.queryList(param);\r\n";
		context += "  }\r\n\r\n";
		
		context += "@Override\r\n";
		context += "  public Integer queryPageCount(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "    return (Integer) " + table.getTableNameTransferInitialLowcase() + "Dao.queryPageCount(param);\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public " + table.getTableNameTransfer() + " queryEntityById(String id){\r\n";
		context += "    return " + table.getTableNameTransferInitialLowcase() + "Dao.queryEntityById(id);\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public String insert(" + table.getTableNameTransfer() + " record){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.insert(record);\r\n";
		context += "    if ( result != 1) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_INSERT_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public String insertBatch(List<" + table.getTableNameTransfer() + "> list){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.insertBatch(list);\r\n";
		context += "    if ( result != list.size()) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_INSERT_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";
		
		context += "@Override\r\n";
		context += "  public String updateByCriteria(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.updateByCriteria(param);\r\n";
		context += "    if ( result != 1) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_UPDATE_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public String updateBatchByCriteria(List<" + table.getTableNameTransfer() + "> list){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.updateBatchByCriteria(list);\r\n";
		context += "    if ( result == 0) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_UPDATE_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";
		
		context += "@Override\r\n";
		context += "  public String insertOrUpdateBatch(List<" + table.getTableNameTransfer() + "> list){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.insertOrUpdateBatch(list);\r\n";
		context += "    if ( result != list.size()) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_INSERT_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";
		
		context += "@Override\r\n";
		context += "  public String deleteById(String id){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.deleteById(id);\r\n";
		context += "    if ( result != 1) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_DEL_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public String deleteByCriteria(Criteria<" + table.getTableNameTransfer() + "> param){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.deleteByCriteria(param);\r\n";
		context += "    if ( result < 1) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_DEL_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";

		context += "@Override\r\n";
		context += "  public String deleteBatchByIds(String[] ids){\r\n";
		context += "    int result = " + table.getTableNameTransferInitialLowcase() + "Dao.deleteBatchByIds(ids);\r\n";
		context += "    if ( result == 0) { \r\n";
		context += "      return AppResponseCode.RESPONSE_CODE_DEL_FALSE.getMessage();";
		context += "    } \r\n";
		context += "    return null;\r\n";
		context += "  }\r\n\r\n";
		
		//context += "public void set" + table.getTableNameTransfer() + "Repository(I" + table.getTableNameTransfer() + "Repository repository) {this." + table.getTableNameTransferInitialLowcase() + "Repository = repository;}\r\n";

		//context += "public void setDictService(ISysDictService dictService) {this.dictService = dictService;}\r\n";

		//context += "public void setMessageService(IMessageService messageService) {this.messageService = messageService;}\r\n";
		
		context += "@Autowired\r\n";
		
		context += "   private I" + table.getTableNameTransfer() + "Dao " + table.getTableNameTransferInitialLowcase() + "Dao;\r\n\r\n";
		
		//context += "private ISysDictService dictService;\r\n\r\n";
		
		//context += "private IMessageService messageService;\r\n\r\n";
		
		context += "}\r\n";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate repository class file : " + filePath + fileName);
		return true;
	}

}
