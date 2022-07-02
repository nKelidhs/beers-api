package com.beersAPI.beers.Config;

import com.beersAPI.beers.Helper.StringToBeerTypeEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // We need this formatter in order to automatically convert BearType to String when we
    // retrieve beers from the database.
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToBeerTypeEnumConverter());
    }
}
