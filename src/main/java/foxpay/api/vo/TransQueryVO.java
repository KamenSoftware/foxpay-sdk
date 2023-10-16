package foxpay.api.vo;

import foxpay.api.result.FoxPayVO;
import lombok.Data;

@Data
public class TransQueryVO extends FoxPayVO {


    /**
     * 流水号
     */
    private String trade_no;

    /**
     * 商户订单号
     */
    private String order_no;

    /**
     * 手续费方式：2 交易金额 3 账户余额
     */
    private String gas_type;

    /**
     * 交易凭证
     */
    private String trans_token;

    /**
     * 提现状态：1待提现，2处理中， 3提现成功， 4提现失败
     */
    private Integer status;

    /**
     * 提现地址
     */
    private String to_address;

    /**
     * 回调地址
     */
    private String notify_url;

    /**
     * 数量
     */
    private String amount;

    /**
     * 手续费
     */
    private String fee;

    /**
     * 余额
     */
    private String balance;

    /**
     * 支付时间
     */
    private Long pay_time;

    /**
     * 交易hash
     */
    private String tx_hash;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Long create_time;


}
