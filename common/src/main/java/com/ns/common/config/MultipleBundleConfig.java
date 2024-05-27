package com.ns.common.config;

import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ns.common.component.MultiResourceBundle;

@Configuration
public class MultipleBundleConfig {

    @Bean
    public MultiResourceBundle multiResourceBundle() {
        MultiResourceBundle multiResourceBundle = new MultiResourceBundle(List.of(
            ResourceBundle.getBundle("messages.messages")
        ));
        return multiResourceBundle;
    }
}
