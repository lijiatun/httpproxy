package com.lijiatun.httpproxy;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.lijiatun.loadbalance.properties.IpProperties;
/**
 * 反向代理与负载均衡
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.lijiatun.loadbalance")
@ServletComponentScan
@EnableConfigurationProperties(IpProperties.class)
public class Application
{
	
	private static Logger log = Logger.getLogger(Application.class);
    
	public static void main(String[] args )
    {
    		SpringApplication.run(Application.class, args);
    		log.info("http  Start Success...................");
    }
}
