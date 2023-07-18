package foxpay.api.vo;

import foxpay.api.result.FoxPayResult;
import lombok.Data;

@Data
public class TransPrepareVO extends FoxPayResult {

    /**
     * 交易凭证
     */
    private String trans_token;

}
