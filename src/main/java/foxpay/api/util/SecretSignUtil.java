package foxpay.api.util;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class SecretSignUtil {

    /**
     * 排序参数
     */
    public static String sortParam(Map<String, Object> paramValues) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> keys = new ArrayList<String>(paramValues.keySet());
        Collections.sort(keys);
        int i = 0;
        for (String key : keys) {
            stringBuilder.append(key).append("=").append(paramValues.get(key).toString());
            if (i != keys.size() - 1) {
                stringBuilder.append("&");
            }
            i++;
        }
        log.warn("sort：{}", stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 排序参数
     */
    public static String sortParam(Object obj) {
        Map<String, Object> paramValues = BeanUtil.beanToMap(obj, false, true);
        return sortParam(paramValues);
    }


}
