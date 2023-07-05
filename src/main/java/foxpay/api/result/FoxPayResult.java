package foxpay.api.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class FoxPayResult implements Serializable {

    private int code;

    private String message;

}
