package com.smplfinance.magiclinkapi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SwaggerConfiguration implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy 'at' HH:mm:ss z");
		Date date = new Date();
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		String termsOfServiceUrl = "http://" + env.getProperty("hostname") + "/" + "magiclink-api";
		String licenceUrl = "http://" + env.getProperty("hostname") + "/" + "magiclink-api" + "/postman";
		return new ApiInfoBuilder().title("Magic Link - API")
				.description("API for magiclink Application - " + " Last Updated at " + simpleDateFormat.format(date))
				.version("4.0").termsOfServiceUrl(termsOfServiceUrl).license("Postman Collection")
				.licenseUrl(licenceUrl).build();
	}
}