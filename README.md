#  FoxPay Java SDK

## 简介

- 此sdk为方便java开发人员对接foxpay平台收银台功能

- 已实现功能

  - 创建订单

  - 查询订单

  - 关闭订单

  - 查询资产

    

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
    <groupId>foxpay.api</groupId>
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
    #public-key-file: 公钥验签(默认方式)
    url: 请求url

```



### 使用示例

```java
    @Autowired
    private FoxOrderService foxOrderService;

    @GetMapping("/create")
    public void create() {
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setAmount("1.1");
        dto.setRemark("test");
        dto.setSubject("test");
        dto.setOrder_no("test");
        FoxOrderVO result = foxOrderService.orderCreate(dto);
        System.out.println(result.getMessage());
        System.out.println(result.getCode());
        System.out.println(result);
    }
```

