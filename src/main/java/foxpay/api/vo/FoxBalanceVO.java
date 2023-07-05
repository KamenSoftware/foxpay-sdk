package foxpay.api.vo;

import foxpay.api.result.FoxPayResult;
import lombok.Data;

/**
 * sdk订单信息
 */
@Data
public class FoxBalanceVO extends FoxPayResult {


    /**
     * erc20地址
     */
    private String erc20_address;

    /**
     * trc20地址
     */
    private String trc20_address;

    /**
     * 可用资产
     */
    private String money;

    /**
     * 交易冻结资产
     */
    private String trans_freeze_money;


}
