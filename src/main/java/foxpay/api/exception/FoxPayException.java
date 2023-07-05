package foxpay.api.exception;

import cn.hutool.core.util.StrUtil;
import foxpay.api.enums.CodeEnum;
import lombok.Data;

@Data
public class FoxPayException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;


    public FoxPayException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public FoxPayException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMessage();
    }

    public FoxPayException(CodeEnum codeEnum, String msg) {
        super(StrUtil.format(codeEnum.getMessage(),msg));
        this.code = codeEnum.getCode();
        this.msg = StrUtil.format(codeEnum.getMessage(),msg);
    }
}
