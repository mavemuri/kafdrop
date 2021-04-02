package kafdrop.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kafdrop.model.KafkaQuotaVO;
import kafdrop.service.KafkaMonitor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuotaController {
    private final KafkaMonitor kafkaMonitor;

    @Value("${kafkaproxy.URL}")
    String kafkaProxyURL;

    @Value("${kafkaproxy.cookiePath}")
    String kafkaProxyCookiePath;

    @Value("${kafkaproxy.useCookie}")
    Boolean useCookie;

    public QuotaController(KafkaMonitor kafkaMonitor) {
        this.kafkaMonitor = kafkaMonitor;
    }

    @RequestMapping("/quotas")
    public String quotas(Model model) {
        final var quotas = kafkaMonitor.getQuotas(kafkaProxyURL, useCookie? kafkaProxyCookiePath: "");
        model.addAttribute("quotas", quotas);

        return "quota-overview";
    }

    @ApiOperation(value = "getAllQuotas", notes = "Get list of all quotas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class, responseContainer = "List")
    })
    @RequestMapping(path = "/quotas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    List<KafkaQuotaVO> getAllQuotas() {
        return kafkaMonitor.getQuotas(kafkaProxyURL, useCookie? kafkaProxyCookiePath: "");
    }
}
