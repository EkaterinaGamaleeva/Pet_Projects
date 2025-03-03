package com.dunice.advancedServiceKotlin.config

import com.dunice.advancedServiceKotlin.interceptor.CustomInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfigCustomInterceptor @Autowired constructor(private val customInterceptor: CustomInterceptor) :
    WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customInterceptor).addPathPatterns("/**")
    }
}
