package com.virtualize.varejo_da_sorte.api.util.validator;

import com.virtualize.varejo_da_sorte.api.controllers.message.MessageTO;

public interface MessageRule {

    boolean condition();

    MessageTO message();
}
