package foxpay.api.enums;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常枚举
 */
@Getter
@AllArgsConstructor
public enum LocaleEnum {
    ZH_CN("zh-CN", "中文简体"),
    ZH_TW("zh-TW", "中文繁体"),
    EN_US("en-US", "英文"),
    JA_JP("ja-JP", "日文"),
    ;

    private final String locale;

    private final String message;


    @JSONField
    public String getLocale() {
        return locale;
    }

}
