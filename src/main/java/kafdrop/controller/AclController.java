/*
 * Copyright 2017 Kafdrop contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package kafdrop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kafdrop.model.AclVO;
import kafdrop.service.KafkaMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Tag(name = "acl-controller", description = "ACL Controller")
@Controller
public final class AclController {
  private final KafkaMonitor kafkaMonitor;
  private static final Logger LOG= LoggerFactory.getLogger(AclController.class);

  @Value("${kafkaproxy.URL}")
  String kafkaProxyURL;

  @Value("${kafkaproxy.cookiePath}")
  String kafkaProxyCookiePath;

  @Value("${kafkaproxy.useCookie}")
  Boolean useCookie;

  String kafkaProxyCookie="";

  public AclController(KafkaMonitor kafkaMonitor) {
    this.kafkaMonitor = kafkaMonitor;
  }

  @PostConstruct
  public void init() {
    if(this.useCookie) {
      try {
        this.kafkaProxyCookie= Files.readString(Path.of(this.kafkaProxyCookiePath)).replace('\n', ' ');
      }
      catch (IOException | SecurityException | OutOfMemoryError e) {
        LOG.error(e.toString());
      }
    }
  }

  @RequestMapping("/acl")
  public String acls(Model model) {
    final var acls = kafkaMonitor.getAcls(kafkaProxyURL, kafkaProxyCookie);
    model.addAttribute("acls", acls);
    return "acl-overview";
  }

  @Operation(summary = "getAllAcls", description = "Get list of all acls", operationId = "getAllTopicsUsingGET")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
  @GetMapping(path = "/acl", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody List<AclVO> getAllTopics() {
    return kafkaMonitor.getAcls(kafkaProxyURL, kafkaProxyCookie);
  }
}
