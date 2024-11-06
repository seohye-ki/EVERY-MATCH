package com.everymatch.mvc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.everymatch.mvc.model.dao")
public class DBConfig {

}
