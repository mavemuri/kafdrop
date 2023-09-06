package kafdrop.controller;

import kafdrop.model.KafkaQuotaVO;
import kafdrop.service.KafkaMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
public class QuotaController {
    private final KafkaMonitor kafkaMonitor;
    private static final Logger LOG= LoggerFactory.getLogger(QuotaController.class);

    @Value("${kafkaproxy.URL}")
    String kafkaProxyURL;

    @Value("${kafkaproxy.cookiePath}")
    String kafkaProxyCookiePath;

    @Value("${kafkaproxy.useCookie}")
    Boolean useCookie;

    String kafkaProxyCookie="";

    public QuotaController(KafkaMonitor kafkaMonitor) {
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

    @RequestMapping("/quotas")
    public String quotas(Model model) {
        final var quotas = kafkaMonitor.getQuotas(kafkaProxyURL, kafkaProxyCookie);
        model.addAttribute("quotas", quotas);

        return "quota-overview";
    }

    @Operation(description = "Get list of all quotas")
    // @ApiResponses(value = {
    //         @ApiResponse(statusCode = 200, message = "Success", response = String.class, responseContainer = "List")
    // })
    @RequestMapping(path = "/quotas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    List<KafkaQuotaVO> getAllQuotas() {
        return kafkaMonitor.getQuotas(kafkaProxyURL, kafkaProxyCookie);
    }
}
