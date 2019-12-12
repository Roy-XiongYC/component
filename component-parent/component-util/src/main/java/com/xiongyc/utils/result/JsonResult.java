package com.xiongyc.utils.result;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author YouCai.Xiong
 * @param <T>
 * @Date 2018年11月17日 - 下午10:16:35
 * @Info 初始版本
 * @Version 1.0
 */
@ApiModel("返回结果")
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = 8951241890462664051L;

	@ApiModelProperty(value = "消息")
	private String message;

	@ApiModelProperty(value = "状态码")
	private String code = "200";

	@ApiModelProperty(value = "数据")
	private T data = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
