package foxpay.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequestUtil {

    public static String post(String uri, Map<String, String> heard, String postData) throws Exception {
        // 目标URL
        URL url = new URL(uri);
        // 创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置HTTP方法为POST
        connection.setRequestMethod("POST");
        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json");

        //添加请求头
        for (String s : heard.keySet()) {
            connection.setRequestProperty(s, heard.get(s));
        }
        // 开启输出流，用于发送POST请求的数据
        connection.setDoOutput(true);
        // 发送请求数据
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(postData.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        // 发起请求并获取响应
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 输出响应结果
        System.out.println("Response Body: " + response.toString());

        // 关闭连接
        connection.disconnect();

        return response.toString();
    }
}
