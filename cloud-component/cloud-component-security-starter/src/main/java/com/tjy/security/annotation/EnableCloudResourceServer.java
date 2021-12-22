package com.tjy.security.annotation;

import com.tjy.security.configure.CloudResourceServerConfigure;
import com.tjy.security.configure.TokenStoreConfigure;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableResourceServer //开启资源服务器
@Import({CloudResourceServerConfigure.class, TokenStoreConfigure.class})
public @interface EnableCloudResourceServer {

}
