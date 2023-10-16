package foxpay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * code enum
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(10000, "success"),
    CONFIG_NOT_NULL(61000, "Configuration cannot be empty"),
    PARAM_NOT_NULL(61001, "The request parameter object cannot be empty"),
    RESPONSE_SIGN_ERROR(61002, "Response signature exception"),
    REQUEST_SIGN_ERROR(61003, "Request signature exception"),
    PARAM_ERROR(61004, "Parameter exception：{}"),
    CONFIG_ERROR(61005, "Configuration exception：{}"),
    FILE_ERROR(61006, "Reading file exception"),
    ;

    private final int code;

    private final String message;


}
