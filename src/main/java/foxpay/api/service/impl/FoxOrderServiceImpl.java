package foxpay.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import foxpay.api.config.properties.FoxPayConfigProperties;
import foxpay.api.constants.FoxPayUrlConstant;
import foxpay.api.dto.OrderCreateDTO;
import foxpay.api.dto.OrderQueryDTO;
import foxpay.api.enums.CodeEnum;
import foxpay.api.exception.FoxPayException;
import foxpay.api.result.FoxPay;
import foxpay.api.result.FoxPayResult;
import foxpay.api.service.FoxOrderService;
import foxpay.api.util.FoxPayRequestUtil;
import foxpay.api.vo.FoxBalanceVO;
import foxpay.api.vo.FoxOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class FoxOrderServiceImpl implements FoxOrderService {

    @Autowired
    private FoxPayConfigProperties foxPayConfigProperties;

    /**
     * 创建订单
     */
    @Override
    public FoxOrderVO orderCreate(OrderCreateDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "order_no");
        }
        if (StrUtil.isBlank(dto.getSubject())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "subject");
        }

        try {
            BigDecimal amount = new BigDecimal(dto.getAmount());
            if (amount.scale() > 2) {
                throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
            }
            if (amount.compareTo(BigDecimal.ONE) <= 0) {
                throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
            }
        } catch (Exception e) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }


        FoxPay foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.ORDER_CREATE, dto, foxPayConfigProperties);

        FoxOrderVO result = new FoxOrderVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), FoxOrderVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }

    /**
     * 查询订单
     */
    @Override
    public FoxOrderVO orderQuery(OrderQueryDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no()) && StrUtil.isBlank(dto.getTrade_no())) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        FoxPay foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.ORDER_QUERY, dto, foxPayConfigProperties);

        FoxOrderVO result = new FoxOrderVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), FoxOrderVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }


    /**
     * 关闭订单
     */
    @Override
    public FoxPayResult closeOrder(OrderQueryDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no()) && StrUtil.isBlank(dto.getTrade_no())) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        FoxPay foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.CLOSE_ORDER, dto, foxPayConfigProperties);

        FoxOrderVO result = new FoxOrderVO();
        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }


    /**
     * 查询资产
     */
    @Override
    public FoxBalanceVO getBalance() {
        FoxPay foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.ORDER_CREATE, null, foxPayConfigProperties);
        FoxBalanceVO result = new FoxBalanceVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), FoxBalanceVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;

    }
}
