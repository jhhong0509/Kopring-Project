package com.example.forsubmit.global.property

import com.example.forsubmit.BASE_PACKAGE
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationPropertiesScan(basePackages = [BASE_PACKAGE])
@Configuration
class PropertyConfig