package foxpay.api.dto;

import foxpay.api.enums.LocaleEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCreateDTO implements Serializable {

    /**
     * 商户订单标题
     */
    private String subject;

    /**
     * 商户订单号
     */
    private String order_no;

    /**
     * 数量
     */
    private String amount;

    /**
     * 回调地址
     */
    private String notify_url;

    /**
     * 订单支付成功重定向地址
     */
    private String redirect_url;

    /**
     * 超时时间(秒) 默认30秒
     */
    private Long time_out = 30L;

    /**
     * LocaleEnum
     * 国际化：中文简体（zh-CN），中文繁体(zh-TW)，英文(en-US)，日文(ja-JP)
     */
    private String locale = LocaleEnum.ZH_TW.getLocale();

    /**
     * 备注
     */
    private String remark;

}
