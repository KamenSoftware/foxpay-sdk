package foxpay.api.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import foxpay.api.config.properties.FoxPayConfigProperties;
import foxpay.api.constants.FoxPayUrlConstant;
import foxpay.api.dto.*;
import foxpay.api.enums.CodeEnum;
import foxpay.api.exception.FoxPayException;
import foxpay.api.result.FoxPayResult;
import foxpay.api.service.FoxOrderService;
import foxpay.api.util.FoxPayRequestUtil;
import foxpay.api.vo.*;
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
    public OrderCreateVO orderCreate(OrderCreateDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "order_no");
        }
        if (StrUtil.isBlank(dto.getSubject())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "subject");
        }
        if (StrUtil.isBlank(dto.getAmount()) || !NumberUtil.isNumber(dto.getAmount())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }
        BigDecimal amount = NumberUtil.toBigDecimal(dto.getAmount());
        if (amount.scale() > 2) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }
        if (amount.compareTo(BigDecimal.ONE) <= 0) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }

        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.ORDER_CREATE, dto, foxPayConfigProperties);

        OrderCreateVO result = new OrderCreateVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), OrderCreateVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }

    /**
     * 查询订单
     */
    @Override
    public OrderQueryVO orderQuery(OrderQueryDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no()) && StrUtil.isBlank(dto.getTrade_no())) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.ORDER_QUERY, dto, foxPayConfigProperties);

        OrderQueryVO result = new OrderQueryVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), OrderQueryVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }


    /**
     * 关闭订单
     */
    @Override
    public FoxPayResult orderClose(OrderCloseDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no()) && StrUtil.isBlank(dto.getTrade_no())) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        return FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.CLOSE_ORDER, dto, foxPayConfigProperties);
    }


    /**
     * 查询资产
     */
    @Override
    public BalanceQueryVO balanceQuery() {
        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.GET_BALANCE, null, foxPayConfigProperties);
        BalanceQueryVO result = new BalanceQueryVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), BalanceQueryVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;

    }

    /**
     * 提现获取凭证
     */
    @Override
    public TransPrepareVO transPrepare(TransPrepareDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "order_no");
        }
        if (StrUtil.isBlank(dto.getTo_address())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "to_address");
        }
        if (StrUtil.isBlank(dto.getAmount()) || !NumberUtil.isNumber(dto.getAmount())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }
        BigDecimal amount = NumberUtil.toBigDecimal(dto.getAmount());
        if (amount.scale() > 2) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }
        if (amount.compareTo(BigDecimal.ONE) <= 0) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "amount");
        }
        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.TRANS_PREPARE, dto, foxPayConfigProperties);
        TransPrepareVO result = new TransPrepareVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), TransPrepareVO.class);
        }
        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }

    /**
     * 确认提现
     */
    @Override
    public TransVO trans(TransDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getTrans_token())) {
            throw new FoxPayException(CodeEnum.PARAM_ERROR, "trans_token");
        }
        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.TRANS, dto, foxPayConfigProperties);
        TransVO result = new TransVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), TransVO.class);
        }
        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }

    /**
     * 查询提现记录
     */
    @Override
    public TransQueryVO getTrans(TransQueryDTO dto) {
        if (dto == null) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        if (StrUtil.isBlank(dto.getOrder_no()) && StrUtil.isBlank(dto.getTrade_no())) {
            throw new FoxPayException(CodeEnum.PARAM_NOT_NULL);
        }
        FoxPayResult foxPay = FoxPayRequestUtil.orderRequest(FoxPayUrlConstant.GET_TRANS, dto, foxPayConfigProperties);

        TransQueryVO result = new TransQueryVO();
        if (StrUtil.isNotBlank(foxPay.getData())) {
            result = JSON.parseObject(foxPay.getData(), TransQueryVO.class);
        }

        result.setCode(foxPay.getCode());
        result.setMessage(foxPay.getMessage());
        return result;
    }
}
