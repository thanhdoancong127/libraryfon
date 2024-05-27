package com.ns.borrowing.config;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Configuration;

import com.ns.common.util.MessagesUtils;

import jakarta.annotation.PostConstruct;

@Configuration
public class PushingDataConfig {

    @PostConstruct
    public void multiResourceBundle() {
        MessagesUtils.getInstance().getMultiResourceBundle().addResourceBundle(ResourceBundle.getBundle("messages.borrowing"));
    }
}
