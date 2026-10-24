package com.hospital.doctors.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class config {
    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }
}