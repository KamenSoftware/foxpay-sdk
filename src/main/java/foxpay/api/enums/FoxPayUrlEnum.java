package foxpay.api.enums;

import cn.hutool.http.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举
 */
@Getter
@AllArgsConstructor
public enum FoxPayUrlEnum {

    /**
     * 创建订单
     */
    ORDER_CREATE("/sdk/application/createApplicationOrder", Method.POST),

    /**
     * 查询订单
     */
    ORDER_QUERY("/sdk/application/getApplicationOrder", Method.POST),

    /**
     * 关闭订单
     */
    CLOSE_ORDER("/sdk/application/closeApplicationOrder", Method.POST),

    /**
     * 查询商户资产
     */
    GET_BALANCE("/sdk/application/getBalance", Method.GET),

    /**
     * 提现获取凭证
     */
    TRANS_PREPARE("/sdk/application/transPrepare", Method.POST),

    /**
     * 确认提现
     */
    TRANS("/sdk/application/trans", Method.POST),


    /**
     * 查询提现订单
     */
    GET_TRANS("/sdk/application/getTrans", Method.POST);

    private final String url;

    private final Method method;


}
