package com.tjy.logging.configure;

import com.tjy.logging.aop.SysLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 */
@Configuration
public class SysLogAutoConfigure {
	@Bean
	public SysLogAspect controllerLogAspect(){
		return new SysLogAspect();
	}
}
