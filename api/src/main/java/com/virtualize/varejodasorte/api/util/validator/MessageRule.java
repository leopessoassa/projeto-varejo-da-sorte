package com.virtualize.varejodasorte.api.util.validator;

import com.virtualize.varejodasorte.api.controllers.message.MessageTO;

public interface MessageRule {

    boolean condition();

    MessageTO message();
}
