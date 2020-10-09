package com.boc.accuratetest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * springBoot启动类
 * @author tom
 * 	AccuratetestStart可以放到controller的上级包、同一个包，但不能放到平级包、下级包
 */
@SpringBootApplication
@MapperScan("com.boc.accuratetest.mappers")
public class AccuratetestStart extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(AccuratetestStart.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AccuratetestStart.class);
	}
}
