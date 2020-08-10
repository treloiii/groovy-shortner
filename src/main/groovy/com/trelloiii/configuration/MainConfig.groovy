package com.trelloiii.configuration;

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration
@EnableScheduling
class MainConfig {

    @Value('${spring.flyway.url}')
    def url
    @Value('${spring.flyway.user}')
    def user
    @Value('${spring.flyway.password}')
    def password
    @Bean
    Map<String,String> myDataSource(){
        return [
                url:url,
                user:user,
                password:password
        ] as Map<String, String>
    }
}
