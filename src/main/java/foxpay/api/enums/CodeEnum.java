package foxpay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举
 */
@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(10000, "成功"),
    CONFIG_NOT_NULL(61000, "配置不能为空"),
    PARAM_NOT_NULL(61001, "请求参数对象不能为空"),
    RESPONSE_SIGN_ERROR(61002, "响应签名异常"),
    REQUEST_SIGN_ERROR(61003, "请求签名异常"),
    PARAM_ERROR(61004, "参数异常：{}"),
    CONFIG_ERROR(61005, "配置异常：{}"),
    FILE_ERROR(61006, "读取文件异常"),
    ;

    private final int code;

    private final String message;


}
