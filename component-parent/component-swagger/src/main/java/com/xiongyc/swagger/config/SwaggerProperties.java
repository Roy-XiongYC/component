package com.xiongyc.swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerProperties {

	/**
	 * swagger 扫描的包
	 */
	@Value("${api.swagger.basePackage}")
	private String basePackage;

	/**
	 * swagger api 标题
	 */
	@Value("${api.swagger.title}")
	private String title;

	/**
	 * swagger api 声明描述
	 */
	@Value("${api.swagger.title}")
	private String description;

	/**
	 * 联系方式
	 */
	@Value("${api.swagger.contact}")
	private String contact;

	/**
	 * 版本信息
	 */
	@Value("${api.swagger.version}")
	private String version;

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
