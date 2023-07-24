#  FoxPay Java SDK

## 简介

- 此sdk为方便java开发人员对接foxpay平台收银台功能

- 已实现功能
  - 创建订单
  - 查询订单
  - 关闭订单
  - 查询资产
  - 资产提现

    

## 版本要求

 Java 要求 JDK 8 及以上。



## 安装

### 手动安装

JAR源码下载：[foxpay-sdk](https://github.com/dasen-software/foxpay-sdk.git )

请根据版本号下载相应的 JAR 文件并导入至工程。



#### 依赖库

- hutool-core
- hutool-http
- lombok
- fastjson



### Maven安装

在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>ai.foxpay.api</groupId>
    <artifactId>foxpay-sdk</artifactId>
    <version>1.0</version>
</dependency>
```



## 项目使用

### yml配置

```yml
fox:
  pay:
    app-id:  客户端身份标识
    private-key:  私钥加密(默认方式)
    #private-key-file:  私钥文件地址
    public-key: 公钥验签(默认方式)
    #public-key-file:  公钥文件地址
    url: 请求url

```



### 使用示例

```java
import foxpay.api.dto.OrderCreateDTO;
import foxpay.api.dto.OrderQueryDTO;
import foxpay.api.dto.TransDTO;
import foxpay.api.dto.TransPrepareDTO;
import foxpay.api.service.FoxOrderService;
import foxpay.api.vo.OrderCreateVO;
import foxpay.api.vo.OrderQueryVO;
import foxpay.api.vo.TransPrepareVO;
import foxpay.api.vo.TransVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class BasicController {

  @Autowired
  private FoxOrderService foxOrderService;

  //创建订单
  @GetMapping("/orderCreate")
  public void orderCreate(@RequestParam String tradeNo) {
    OrderCreateDTO dto = new OrderCreateDTO();
    dto.setAmount("1.1");
    dto.setRemark("test");
    dto.setSubject("test");
    dto.setOrder_no(tradeNo);
    OrderCreateVO result = foxOrderService.orderCreate(dto);
    System.out.println(result.getMessage());
    System.out.println(result.getCode());
    System.out.println(result);
    System.out.println(result.getAmount());
  }


  //查询订单
  @GetMapping("/orderQuery")
  public void orderQuery(@RequestParam String tradeNo) {
    OrderQueryDTO dto = new OrderQueryDTO();
    dto.setOrder_no(tradeNo);
    OrderQueryVO result = foxOrderService.orderQuery(dto);
    System.out.println(result.getMessage());
    System.out.println(result.getCode());
    System.out.println(result);
    System.out.println(result.getAmount());
  }


  //提现获取凭证
  @GetMapping("/transPrepare")
  public void transPrepare(@RequestParam String orderNo) {
    TransPrepareDTO dto = new TransPrepareDTO();
    dto.setAmount("12");
    dto.setTo_address("地址");
    dto.setRemark("test");
    dto.setOrder_no(orderNo);
    TransPrepareVO result = foxOrderService.transPrepare(dto);
    System.out.println(result.getMessage());
    System.out.println(result.getCode());
    System.out.println(result);
    System.out.println(result.getTrans_token());
  }

  //确认提现
  @GetMapping("/trans")
  public void trans(@RequestParam String transToken) {
    TransDTO dto = new TransDTO();
    dto.setTrans_token(transToken);
    TransVO result = foxOrderService.trans(dto);
    System.out.println(result.getMessage());
    System.out.println(result.getCode());
    System.out.println(result);
  }
}

```

