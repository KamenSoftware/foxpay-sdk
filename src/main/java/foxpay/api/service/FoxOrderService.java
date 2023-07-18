package foxpay.api.service;

import foxpay.api.dto.*;
import foxpay.api.result.FoxPayResult;
import foxpay.api.vo.*;

public interface FoxOrderService {


    /**
     * 创建订单
     */
    OrderCreateVO orderCreate(OrderCreateDTO dto);

    /**
     * 查询订单
     */
    OrderQueryVO orderQuery(OrderQueryDTO dto);

    /**
     * 关闭订单
     */
    FoxPayResult orderClose(OrderCloseDTO dto);


    /**
     * 查询资产
     */
    BalanceQueryVO balanceQuery();


    /**
     * 提现获取凭证
     */
    TransPrepareVO transPrepare(TransPrepareDTO dto);


    /**
     * 确认提现
     */
    TransVO trans(TransDTO dto);


    /**
     * 查询提现记录
     */
    TransQueryVO getTrans(TransQueryDTO dto);
}
