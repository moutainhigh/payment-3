package com.f.dpay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 服务器配置
 * 
 * @author max
 *
 */
@ConfigurationProperties(prefix="threadPool")
@Component
@Data
public class ServerConfig {

	public int corePoolSize = 1;// 后台定时任务线程配置
	
}
