package com.lifeix.football.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.lifeix.football.common.util.ProfileUtil;

public class ApplicationUtil {

	
	public static void run(Class<?> applicationClass,String[] args){
		Logger logger = LoggerFactory.getLogger(applicationClass);
        SpringApplication app = new SpringApplication(applicationClass);
        /**
         * 应用程序事件,一般情况处理ApplicationContext 没有初始化的事件 e.g ApplicationStartedEvent
         * ApplicationEnvironmentPreparedEvent 初始化后，可以使用@Bean
         */
        ApplicationListener<?> listeners = new ApplicationListener<ApplicationEvent>() {

            @Override
            public void onApplicationEvent(ApplicationEvent event) {
//                logger.info(event.toString());
            }
        };
        app.addListeners(listeners);
        /**
         * 所有环境都必须加载的配置我们约定为common，参见application-common.properties 环境变量的命名 ENV_PROFILE_项目名
         * 通过环境变量获得加载相应的配置文件
         */
        String[] profiles = ProfileUtil.getProfiles("PROFILE_ENV");
        app.setAdditionalProfiles(profiles);
        app.run(args);
	}
}
