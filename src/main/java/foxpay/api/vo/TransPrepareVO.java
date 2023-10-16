package foxpay.api.vo;

import foxpay.api.result.FoxPayVO;
import lombok.Data;

@Data
public class TransPrepareVO extends FoxPayVO {

    /**
     * 交易凭证
     */
    private String trans_token;

}
