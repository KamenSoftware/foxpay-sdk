package foxpay.api.service;

import foxpay.api.dto.OrderCreateDTO;
import foxpay.api.dto.OrderQueryDTO;
import foxpay.api.result.FoxPayResult;
import foxpay.api.vo.FoxBalanceVO;
import foxpay.api.vo.FoxOrderVO;

public interface FoxOrderService {


    /**
     * 创建订单
     */
    FoxOrderVO orderCreate(OrderCreateDTO dto);

    /**
     * 查询订单
     */
    FoxOrderVO orderQuery(OrderQueryDTO dto);

    /**
     * 关闭订单
     */
    FoxPayResult closeOrder(OrderQueryDTO dto);


    /**
     * 查询资产
     */
    FoxBalanceVO getBalance();

}
