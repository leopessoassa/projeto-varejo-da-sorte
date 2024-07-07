package com.virtualize.varejodasorte.api.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.virtualize.varejodasorte.api.util.DateUtil;
import com.virtualize.varejodasorte.api.util.converter.StringToLocalDateConverter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJson() {

        return builder -> {
            builder.serializerByType(Date.class, new JsonSerializer<Date>() {

                @Override
                public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                    gen.writeString(DateUtil.format(value, YYYY_MM_DD));
                }
            });
            builder.serializerByType(LocalDate.class, new JsonSerializer<LocalDate>() {

                @Override
                public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider)
                        throws IOException {
                    gen.writeString(DateUtil.format(value, YYYY_MM_DD));
                }
            });
            builder.modules(new Jdk8Module());
            builder.modules(new ParameterNamesModule());
            builder.modules(new JavaTimeModule());
        };
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
    }
}
