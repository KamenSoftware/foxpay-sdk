package foxpay.api.config;


import foxpay.api.config.properties.FoxPayConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("foxpay.api")
@EnableConfigurationProperties(value = {FoxPayConfigProperties.class})
public class FoxPayAutoConfiguration {
}
