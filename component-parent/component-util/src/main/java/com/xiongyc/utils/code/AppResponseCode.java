package com.xiongyc.utils.code;

import com.xiongyc.utils.result.JsonResult;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2018年11月19日 - 下午2:34:10
 * @Info 初始版本 统一管理返回code 信息 1开头为通用返回code 其他为个人
 * @Version 1.0
 */
public enum AppResponseCode {

	RESPONSE_RETURN_SYSTEM_FEIGN_ERROR("404", "网络异常"), RESPONSE_RETURN_CODE_SUCCESS("200", "请求成功、结果处理成功"),
	RESPONSE_CODE_FAILURE("10000", "请求失败、结果处理失败"), RESPONSE_CODE_DEL_FALSE("10001", "删除失败"),
	RESPONSE_CODE_UPDATE_FALSE("10002", "更新失败"), RESPONSE_CODE_INSERT_FALSE("10003", "保存失败");

	// 状态码
	private String code;
	// 返回消息
	private String message;

	// 构造方法
	private AppResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static <T> JsonResult<T> buildResponseMsg(AppResponseCode appResponseCode) {
		JsonResult<T> result = new JsonResult<T>();
		result.setCode(appResponseCode.getCode());
		result.setMessage(appResponseCode.getMessage());
		result.setData(null);
		return result;
	}

	private static <T> JsonResult<T> buildResponseMsg(AppResponseCode appResponseCode, T data) {
		JsonResult<T> result = new JsonResult<T>();
		result.setCode(appResponseCode.getCode());
		result.setMessage(appResponseCode.getMessage());
		result.setData(data);
		return result;
	}

	public static <T> JsonResult<T> success(AppResponseCode appResponseCode, T data) {
		return buildResponseMsg(appResponseCode, data);
	}

	public static <T> JsonResult<T> success(T data) {
		return buildResponseMsg(AppResponseCode.RESPONSE_RETURN_CODE_SUCCESS, data);
	}

	public static <T> JsonResult<T> success() {
		return success(null);
	}

	public static <T> JsonResult<T> failure() {
		return failure(AppResponseCode.RESPONSE_CODE_FAILURE);
	}

	public static <T> JsonResult<T> failure(AppResponseCode appResponseCode) {
		return buildResponseMsg(appResponseCode);
	}

	public static <T> JsonResult<T> failure(AppResponseCode appResponseCode, T data) {
		return buildResponseMsg(appResponseCode, data);
	}

}