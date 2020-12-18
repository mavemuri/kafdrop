package kafdrop.config;

import kafdrop.util.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AESGCMConfiguration {
  @Component
  @ConfigurationProperties(prefix = "aes")
  public static final class AESGCMProperties {

    @Value("${aes.key.file.path}")
    private String aesKeyFilePath;

    public String getKeyFilePath() {
      return aesKeyFilePath;
    }
  }
}
