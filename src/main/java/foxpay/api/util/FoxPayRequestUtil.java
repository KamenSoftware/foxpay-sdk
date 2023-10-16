package foxpay.api.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import foxpay.api.config.properties.FoxPayConfigProperties;
import foxpay.api.constants.FoxPayHeaderConstant;
import foxpay.api.enums.CodeEnum;
import foxpay.api.enums.FoxPayUrlEnum;
import foxpay.api.exception.FoxPayException;
import foxpay.api.result.FoxPayResult;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FoxPayRequestUtil {


    public static FoxPayResult orderRequest(FoxPayUrlEnum urlEnum, Object param, FoxPayConfigProperties config) {
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
        if (StrUtil.isBlank(privateKey)) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "privateKey");
        }
        if (StrUtil.isBlank(publicKey)) {
            throw new FoxPayException(CodeEnum.CONFIG_ERROR, "publicKey");
        }
        return orderRequest(urlEnum, param, publicKey, privateKey, config);
    }


    public static FoxPayResult orderRequest(FoxPayUrlEnum urlEnum, Object param, String publicKey, String privateKey, FoxPayConfigProperties config) {
        //请求头参数
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(FoxPayHeaderConstant.appId, config.getAppId());

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
        String url = config.getUrl() + urlEnum.getUrl();
        url = URLUtil.normalize(url); //格式化url
        HttpResponse result = null;
        if (urlEnum.getMethod() == Method.GET) {

            //构建请求参数
            result = HttpRequest.get(url).headerMap(headerMap, true)
                    .form(BeanUtil.beanToMap(param, false, true))//body内容
                    .timeout(3000)//超时(毫秒)
                    .execute();
        }
        if (urlEnum.getMethod() == Method.POST) {
            //构建请求参数
            result = HttpRequest.post(url).headerMap(headerMap, true)
                    .body(JSON.toJSONString(param))//body内容
                    .timeout(3000)//超时(毫秒)
                    .execute();
        }
        log.warn("request URL：{}", url);

        String body = result.body();
        log.warn("response body：{}", body);

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
        FoxPayResult foxPay = new FoxPayResult();
        foxPay.setMessage(msg);
        foxPay.setCode(code);
        foxPay.setData(data);
        return foxPay;
    }
}
