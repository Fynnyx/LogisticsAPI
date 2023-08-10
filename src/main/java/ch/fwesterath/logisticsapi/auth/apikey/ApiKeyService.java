package ch.fwesterath.logisticsapi.auth.apikey;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public ApiKey getApiKeyByKey(String key) {
        try {
            Optional<ApiKey> apiKey = apiKeyRepository.findByKey(key);
            if (apiKey.isPresent()) {
                return apiKey.get();
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "Api key not found with key " + key);
    }

    public ApiKey getApiKeyById(Long id) {
        try {
            Optional<ApiKey> apiKey = apiKeyRepository.findById(id);
            if (apiKey.isPresent()) {
                return apiKey.get();
            }
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        throw new ApiExceptionResponse(HttpStatus.BAD_REQUEST, "Api key not found with id " + id.toString());

    }

    public Iterable<ApiKey> getAllApiKeys() {
        try {
            return apiKeyRepository.findAll();
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ApiKey createApiKey(ApiKey apiKey) {
        try {
//            Generate a random key
            apiKey.setKey(ApiKeyGenerator.generateApiKey());
            return apiKeyRepository.save(apiKey);
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
