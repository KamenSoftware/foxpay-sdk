package foxpay.api.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import foxpay.api.config.properties.FoxPayConfigProperties;
import foxpay.api.constants.FoxPayHeaderConstant;
import foxpay.api.enums.CodeEnum;
import foxpay.api.exception.FoxPayException;
import foxpay.api.result.FoxPay;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FoxPayRequestUtil {

    /**
     * 请求封装
     *
     * @param url   请求地址
     * @param param JSON请求参数
     */
    public static FoxPay orderRequest(String url, Object param, FoxPayConfigProperties config) {
        if (StrUtil.isBlank(config.getAppId())) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "appId");
        }
        if (StrUtil.isBlank(config.getUrl())) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "url");
        }
        String privateKey = null;
        String publicKey = null;
        if (StrUtil.isNotBlank(config.getPublicKeyFile()) && StrUtil.isNotBlank(config.getPrivateKeyFile())) {
            if (!config.getPublicKeyFile().endsWith(".pem") || !config.getPrivateKeyFile().endsWith(".pem")) {
                throw new FoxPayException(CodeEnum.FILE_ERROR);
            }

            try {
                FileReader publicFile = new FileReader(config.getPublicKeyFile());
                publicKey = RSAExample.extractFromPem(publicFile.readString());

                FileReader privateFile = new FileReader(config.getPrivateKeyFile());
                privateKey = RSAExample.extractFromPem(privateFile.readString());
            } catch (Exception e) {
                throw new FoxPayException(CodeEnum.FILE_ERROR);
            }

        } else {
            privateKey = config.getPrivateKey();
            publicKey = config.getPublicKey();
        }

        if (StrUtil.isBlank(config.getPrivateKey())) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "privateKey");
        }
        if (StrUtil.isBlank(config.getPublicKey())) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "publicKey");
        }

        url = config.getUrl() + url;
        url = URLUtil.normalize(url); //格式化url

        return orderRequest(url, param, config.getAppId(), publicKey, privateKey);
    }


    public static FoxPay orderRequest(String url, Object param, String appId, String publicKey, String privateKey) {
        //请求头参数
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(FoxPayHeaderConstant.appId, appId);

        if (param != null) {
            //加密请求参数
            String requestParam = SecretSignUtil.sortParam(param);

            try {
                String sign = RSAExample.sign(requestParam.getBytes(), privateKey);
                log.warn("request sign：{}", sign);
                headerMap.put(FoxPayHeaderConstant.sign, sign);
            } catch (Exception e) {
                throw new FoxPayException(CodeEnum.REQUEST_SIGN_ERROR);
            }
        }
        //构建请求参数
        HttpResponse result = HttpRequest.post(url).headerMap(headerMap, true).body(JSON.toJSONString(param))//body内容
                .timeout(20000)//超时(毫秒)
                .execute();

        String body = result.body();
        log.warn("body：{}", body);

        JSONObject object = JSON.parseObject(body);
        String msg = object.getString("message");
        Integer code = object.getInteger("code");
        String data = object.getString("data");

        if (code == CodeEnum.SUCCESS.getCode()) { //操作成功
            if (ObjectUtil.isNotEmpty(data)) {
                String responseSig = result.header(FoxPayHeaderConstant.sign); //响应签名
                log.warn("response sig：{}", responseSig);
                //校验响应签名
                Map<String, Object> params = JSONObject.parseObject(data, new TypeReference<Map<String, Object>>() {
                });

                try {
                    boolean is = RSAExample.verify(SecretSignUtil.sortParam(params).getBytes(), responseSig.getBytes(), publicKey);
                    log.warn("verify response sig：{}", is);
                    if (!is) {
                        throw new FoxPayException(CodeEnum.RESPONSE_SIGN_ERROR);
                    }
                } catch (Exception e) {
                    throw new FoxPayException(CodeEnum.RESPONSE_SIGN_ERROR);
                }
            }
        }
        FoxPay foxPay = new FoxPay();
        foxPay.setMessage(msg);
        foxPay.setCode(code);
        foxPay.setData(data);
        return foxPay;
    }
}
