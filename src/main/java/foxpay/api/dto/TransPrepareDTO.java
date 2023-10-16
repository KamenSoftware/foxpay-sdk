package foxpay.api.dto;


import lombok.Data;

@Data
public class TransPrepareDTO {

    /**
     * 商户订单号
     */
    private String order_no;

    /**
     * 收款金额
     */
    private String amount;

    /**
     * 提现地址
     */
    private String to_address;


    /**
     * 回调通知地址
     */
    private String notify_url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 手续费方式：2 交易金额 3 账户余额
     */
    private String gas_type;
}
