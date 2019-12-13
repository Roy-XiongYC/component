package com.xiongyc.generator.generator;


import org.springframework.jdbc.core.JdbcTemplate;

import com.xiongyc.generator.FileExport;
import com.xiongyc.generator.model.TablePojo;

public class ControllerGenerator extends AbstractGenerator
{

	public ControllerGenerator(String packageName, String exportPath,
			JdbcTemplate jdbcTemplate)
	{
		super(packageName, exportPath, jdbcTemplate);
	}

	@Override
	public boolean doGenerator(TablePojo table)
	{

		String filePath = getExportPathWithPackage("/controller/");
		String fileName = table.getTableNameTransfer() + "Controller.java";

		String context = table.getPackageNameTransfer(".controller");
		context += "\r\n\r\n";

//		context += "import java.util.Date;\r\n";
		context += "import java.util.List;\r\n";
//		context += "import java.util.Map;\r\n";
		context += "import com.alibaba.druid.util.StringUtils;\r\n";
		context += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
		context += "import org.springframework.web.bind.annotation.DeleteMapping;\r\n";
		context += "import org.springframework.web.bind.annotation.GetMapping;\r\n";
		context += "import org.springframework.web.bind.annotation.PathVariable;\r\n";
		context += "import org.springframework.web.bind.annotation.RequestMapping;  \r\n";
//		context += "import org.springframework.web.bind.annotation.RequestMethod;  \r\n";
		context += "import org.springframework.web.bind.annotation.RequestParam;  \r\n";
//		context += "import org.springframework.web.bind.annotation.ResponseBody;  \r\n";
		context += "import org.springframework.web.bind.annotation.RequestBody;  \r\n";
		context += "import org.springframework.web.bind.annotation.PostMapping;\r\n";
		context += "\r\n\r\n\r\n";
		context += "import com.xiongyc.utils.mybatis.Criteria;  \r\n";
		context += "import com.xiongyc.utils.result.JsonResult;\r\n";
		
		context += "import org.springframework.web.bind.annotation.RestController;\r\n";
//		context += "import lombok.extern.slf4j.Slf4j;\r\n";
		context += "import io.swagger.annotations.Api;\r\n";
		context += "import com.xiongyc.sequence.service.SequenceService;\r\n";
		context += "import io.swagger.annotations.ApiOperation;\r\n";
		context += "import io.swagger.annotations.ApiParam;\r\n";
		context += "import com.baomidou.mybatisplus.plugins.Page;\r\n";
		context += "import com.xiongyc.utils.code.AppResponseCode;\r\n";
		
		context += "\r\n";

		context += "import " + table.getPackageName() + ".bean." + table.getTableNameTransfer() + ";\r\n\r\n\r\n";
		context += "import " + table.getPackageName() + ".service.I" + table.getTableNameTransfer()+ "Service;\r\n\r\n\r\n";

//		context += "@Slf4j\r\n";
		context += "@RestController\r\n";
		context += "@Api(description = \""+ table.getTableComment() +"\")\r\n";
		
		context += "@RequestMapping(value = \"/"+table.getTableNameTransferInitialLowcase()+ "\")\r\n";
		context += "public class " + table.getTableNameTransfer() + "Controller{\r\n\r\n";

		context += "  @Autowired\r\n";
		
		context += "  private I" + table.getTableNameTransfer() + "Service"+"  " +table.getTableNameTransferInitialLowcase()+"Service;\r\n\r\n\r\n";

		context += "@Autowired\r\n";
		
		context += "private SequenceService sequenceService;\r\n\r\n\r\n";
		
//		context += generateShowListPageFunc(table) + "\r\n\r\n\r\n";
		context += generateQueryPageFunc(table) + "\r\n\r\n\r\n";
		context += generateAddFunc(table) + "\r\n\r\n\r\n";
		context += generateUpdateFunc(table) + "\r\n\r\n\r\n";
		context += generateSaveFunc(table) + "\r\n\r\n\r\n";
		context += generateDetailFunc(table) + "\r\n\r\n\r\n";
		context += generateDeleteFunc(table) + "\r\n\r\n\r\n";
		context += "}";

		FileExport.export(filePath, fileName, context);

		System.out.println("Generate service class file : " + filePath + fileName);

		return true;
	}

	private String generateSaveFunc(TablePojo table) {
		/**
		 * 批量保存实体
		 */
		String context = "  @ApiOperation(value = \" 批量保存实体\")\r\n";
//		context += "  @ResponseBody \r\n";
		context += "  @PostMapping(\"/insertOrUpdateBatch\")\r\n";
		context += "  public JsonResult<Object> insertOrUpdateBatch(@RequestBody List<" + table.getTableNameTransfer() + "> list){ \r\n";
		context += "     return " +table.getTableNameTransferInitialLowcase()+ "Service.insertOrUpdateBatch(list) == null ? AppResponseCode.success() : AppResponseCode.failure(); \r\n";
		context += "  }\r\n";
		
		
		return context;
	}

	private String generateUpdateFunc(TablePojo table) {
		/**
		 * 批量更新
		 */
		String context = "  @ApiOperation(value = \"批量更新数据\")\r\n";
//		context += "  @ResponseBody \r\n";
		context += "  @PostMapping(\"/updateBatchByCriteria\")\r\n";
		context += "  public JsonResult<Object> updateBatchByCriteria(@RequestBody List<" + table.getTableNameTransfer() + "> list){ \r\n";
		context += "     return " +table.getTableNameTransferInitialLowcase()+ "Service.updateBatchByCriteria(list) == null ? AppResponseCode.success() : AppResponseCode.failure(); \r\n";
		context += "  }\r\n";
		
		
		return context;
	}

	private String generateDeleteFunc(TablePojo table)
	{
		/**
		 * 删除实体
		 */
		String context = "  @ApiOperation(value = \"根据主键删除\")\r\n";
//		context += "  @ResponseBody\r\n";
		context += "  @DeleteMapping(\"/deleteEntityById/{id}\")\r\n";
		context += "  public JsonResult<Object> deleteEntityById(@ApiParam(name = \"id\", value = \"主键ID\") @PathVariable(name = \"id\") String id) {\r\n";
		context += "    return "+table.getTableNameTransferInitialLowcase()+"Service.deleteById(id) == null ? AppResponseCode.success() : AppResponseCode.failure();\r\n";
		context += "  }\r\n";

		context += "\r\n\r\n\r\n";
		
		/**
		 * 根据model删除
		 */
		context += "  @ApiOperation(value = \"根据model删除\")\r\n";
//		context += "  @ResponseBody\r\n";
		context += "  @PostMapping(\"/deleteEntityByCriteria\")\r\n";
		context += "  public JsonResult<Object> deleteEntityByCriteria(@ApiParam(name = \"id\", value = \"主键ID\") @RequestParam(required = false) String id) {\r\n";
		context += "     Criteria<" + table.getTableNameTransfer() + "> param = new Criteria<" + table.getTableNameTransfer() + ">();\r\n";
		context += "     param.addParam(\"" + table.getColumns().get(0).getColumnNameTransfer() + "\",id);\r\n";
		context += "     // TODO Auto-generated method stub\r\n";
		context += "     return "+table.getTableNameTransferInitialLowcase()+"Service.deleteByCriteria(param) == null ? AppResponseCode.success() : AppResponseCode.failure();\r\n";
		context += "  }\r\n";

		context += "\r\n\r\n\r\n";
		
		/**
		 * 批量删除实体
		 */
		context += "  @ApiOperation(value = \"根据主键批量删除\")\r\n";
//		context += "  @ResponseBody\r\n";
		context += "  @PostMapping(\"/deleteBatchByIds\")\r\n";
		context += "  public JsonResult<Object> deleteBatchByIds(@ApiParam(name = \"ids\", value = \"主键ID\") @RequestParam String[] ids) {\r\n";
		context += "    return "+table.getTableNameTransferInitialLowcase()+"Service.deleteBatchByIds(ids) == null ? AppResponseCode.success() : AppResponseCode.failure();\r\n";
		context += "  }\r\n";

		context += "\r\n\r\n\r\n";
		return context;

	}


	private String generateDetailFunc(TablePojo table)
	{
		
		String context = "  @ApiOperation(value = \"详情\")\r\n";
		context += "  @GetMapping(\"/queryEntityById\")\r\n";
		context += "  public JsonResult<"+ table.getTableNameTransfer() +"> queryEntityById(@ApiParam(name = \"id\", value = \"主键ID\") @RequestParam(required = false) String id) { \r\n";
		context += "    return AppResponseCode.success(" +table.getTableNameTransferInitialLowcase()+"Service.queryEntityById(id)); \r\n";
		context += "  }\r\n";

		return context;
	}

	private String generateQueryPageFunc(TablePojo table)
	{
		/**
		 * 查询
		 */
		String context = "  @ApiOperation(value = \"翻页查询列表\")\r\n";
		context += "  @GetMapping(\"/queryPage\")\r\n";
		context += "  public JsonResult<Page<"+ table.getTableNameTransfer() +">> queryPage(@ApiParam(name = \"currentPage\", value = \"页码\") @RequestParam(name = \"currentPage\", required = false, defaultValue = \"1\") Integer currentPage,\r\n" + 
				"			@ApiParam(name = \"pageSize\", value = \"页面大小\") @RequestParam(name = \"pageSize\", required = false, defaultValue = \"10\") Integer pageSize,\r\n" + 
				"			@ApiParam(name = \"id\", value = \"主键ID\") @RequestParam(required = false) String id){ ";
		context += "     Criteria<" + table.getTableNameTransfer() + "> param = new Criteria<" + table.getTableNameTransfer() + ">();\r\n";
		
		context += "	  Page<" + table.getTableNameTransfer() + "> page = new Page<" + table.getTableNameTransfer() + ">(currentPage, pageSize);\r\n\r\n\r\n";
		//context += "     page.setAsc(true); \r\n\r\n\r\n";
		context += "     if (!StringUtils.isEmpty(id)) {\r\n";
		context += "        param.addParam(\"" + table.getColumns().get(0).getColumnNameTransfer() + "\",id);\r\n";
		context += "     }\r\n"; 
		context += "     param.setOrderBy(\""+table.getColumns().get(0).getColumnName()+" desc\");\r\n"; 
		context += "     return AppResponseCode.success("+table.getTableNameTransferInitialLowcase()+"Service.queryPage(page,param)); \r\n";

		context += "  }\r\n";

		return context;
	}

	@SuppressWarnings("unused")
	private String generateShowListPageFunc(TablePojo table)
	{

		String context = "  @RequestMapping(value = \"/showList\", method = { RequestMethod.GET })\r\n";
		context += "  public String showList() { \r\n";
		context += "      return \"" + table.getPackageName().substring(table.getPackageName().lastIndexOf(".")+1) + "/"+table.getTableNameTransferInitialLowcase()+"/" + table.getTableNameTransferInitialLowcase().substring(0, 1).toUpperCase()+table.getTableNameTransfer().substring(1)+ "List\"; \r\n";
		context += "  }";

		return context;

	}

	private String generateAddFunc(TablePojo table)
	{
		/**
		 * 保存
		 */
		String context = "  @ApiOperation(value = \"保存数据\")\r\n";
//		context += "  @ResponseBody \r\n";
		context += "  @PostMapping(\"/addOrUpdate\")\r\n";
//		context += "  public JsonResult<Object> addOrUpdate(";
//		for (int i = 0; i < table.getColumns().size(); i++)
//		{
//			context += "@RequestParam(required=false) " + table.getColumns().get(i).getJavaDataType() + " " + table.getColumns().get(i).getColumnNameTransfer() + ",";
//		}
//		context = context.substring(0, context.length() - 1);
//		context += "){ \r\n";
//		context += "     " + table.getTableNameTransfer() + " " + table.getTableNameTransferInitialLowcase() + " = new " + table.getTableNameTransfer() + "();\r\n\r\n";
		context += "  public JsonResult<Object> addOrUpdate(@RequestBody " + table.getTableNameTransfer() + " " + table.getTableNameTransferInitialLowcase() +"){ \r\n";
        context += "     " +"String ret=null;\r\n";
//		for (int i = 0; i < table.getColumns().size(); i++)
//		{
//			context += "     " + table.getTableNameTransferInitialLowcase() + ".set" + table.getColumns().get(i).getColumnNameTransferUpper() + "(" + table.getColumns().get(i).getColumnNameTransfer() + ");\r\n";
//		}
		
		context += "     if (!StringUtils.isEmpty("+table.getTableNameTransferInitialLowcase() + ".get" + table.getColumns().get(0).getColumnNameTransferUpper() + "())) {\r\n";
		context += "        Criteria<"+table.getTableNameTransfer()+"> param = new Criteria<"+table.getTableNameTransfer()+">();\r\n";
		context += "        param.addParam(\"" + table.getColumns().get(0).getColumnNameTransfer() + "\", " + table.getTableNameTransferInitialLowcase() + ".get" + table.getColumns().get(0).getColumnNameTransferUpper() + "());\r\n";
		context += "        param.setRecord(" + table.getTableNameTransferInitialLowcase() + "); \r\n";
		context += "        ret = "+table.getTableNameTransferInitialLowcase()+"Service.updateByCriteria(param); \r\n";
		context += "     }else {\r\n"; 
		context += "        " + table.getTableNameTransferInitialLowcase() + ".set" + table.getColumns().get(0).getColumnNameTransferUpper() + "(sequenceService.getUpdateQuerySeq(\"" + table.getTableNameInitials() +"\", \"" + table.getTableName() +"\"));\r\n";
		context += "        ret =" +table.getTableNameTransferInitialLowcase()+ "Service.insert(" + table.getTableNameTransferInitialLowcase() + "); \r\n";
		context += "     } \r\n";
		context += "     return ret == null ? AppResponseCode.success() : AppResponseCode.failure(); \r\n";
		context += "  }\r\n\r\n\r\n\r\n";

		/**
		 * 批量新增
		 */
		context += "  @ApiOperation(value = \"批量新增数据\")\r\n";
//		context += "  @ResponseBody \r\n";
		context += "  @PostMapping(\"/addBatch\")\r\n";
		context += "  public JsonResult<Object> addBatch(@RequestBody List<" + table.getTableNameTransfer() + "> list){ \r\n";
		context += "     for (" + table.getTableNameTransfer() + " " + table.getTableNameTransferInitialLowcase() +" : list) {\r\n";
		context += "        " + table.getTableNameTransferInitialLowcase() + ".set" + table.getColumns().get(0).getColumnNameTransferUpper() + "(sequenceService.getUpdateQuerySeq(\"" + table.getTableNameInitials() +"\", \"" + table.getTableName() +"\"));\r\n";
		context += "     } \r\n";
		
		context += "     return " +table.getTableNameTransferInitialLowcase()+ "Service.insertBatch(list) == null ? AppResponseCode.success() : AppResponseCode.failure(); \r\n";
		context += "  }\r\n";
		
		
		return context;
	}
}
