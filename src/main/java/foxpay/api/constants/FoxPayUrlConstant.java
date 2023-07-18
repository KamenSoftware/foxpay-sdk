package foxpay.api.constants;

/**
 * 请求URL常量
 */
public interface FoxPayUrlConstant {


    /**
     * 创建订单
     */
    String ORDER_CREATE = "/sdk/application/createApplicationOrder";

    /**
     * 查询订单
     */
    String ORDER_QUERY = "/sdk/application/getApplicationOrder";

    /**
     * 关闭订单
     */
    String CLOSE_ORDER = "/sdk/application/closeApplicationOrder";

    /**
     * 查询商户资产
     */
    String GET_BALANCE = "/sdk/application/getBalance";

    /**
     * 提现获取凭证
     */
    String TRANS_PREPARE = "/sdk/application/transPrepare";

    /**
     * 确认提现
     */
    String TRANS = "/sdk/application/trans";


    /**
     * 查询提现订单
     */
    String GET_TRANS = "/sdk/application/getTrans";
}
