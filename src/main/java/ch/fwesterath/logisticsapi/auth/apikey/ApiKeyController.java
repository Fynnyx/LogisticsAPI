package ch.fwesterath.logisticsapi.auth.apikey;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-key")
//remove from openapi docs
@Hidden
public class ApiKeyController {

    @Autowired
    private ApiKeyService apiKeyService;

    @PostMapping()
    public ApiKey createApiKey(@RequestBody ApiKey apiKey) {
//        System.out.println(apiKey);
        return apiKeyService.createApiKey(apiKey);
    }

}
