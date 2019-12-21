package com.xiongyc.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Autowired
	private SwaggerProperties swaggerProperties;

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).build();
	}

	public ApiInfo apiInfo() {
		String[] contact = swaggerProperties.getContact().split(",");
		return new ApiInfoBuilder().title(swaggerProperties.getTitle())// 标题
				.description(swaggerProperties.getDescription())// 声明描述
				.version(swaggerProperties.getVersion()) // 版本信息
				.contact(new Contact(contact[0], contact[1], contact[2]))// 联系方式
				.version(swaggerProperties.getVersion())// 版本
				.build();
	}

}