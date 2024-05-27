package com.ns.common.util;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import com.ns.common.component.MultiResourceBundle;

import lombok.Getter;

public class MessagesUtils {

    @Getter
    private MultiResourceBundle multiResourceBundle;

    @Getter
    private final static MessagesUtils instance = new MessagesUtils();

    static {
        instance.multiResourceBundle = new MultiResourceBundle(List.of(ResourceBundle.getBundle("messages.messages")));
    }

    public static String getMessage(String errorCode, Object... var2) {
        String message = null;
        if (instance.multiResourceBundle != null && instance.multiResourceBundle.containsKey(errorCode)) {
            message = instance.multiResourceBundle.getString(errorCode);
        } else {
            message = errorCode;
        }

        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(message, var2);
        return formattingTuple.getMessage();
    }
}
