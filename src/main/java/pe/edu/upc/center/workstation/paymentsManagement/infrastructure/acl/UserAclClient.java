package pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl;

import pe.edu.upc.center.workstation.paymentsManagement.infrastructure.acl.dto.UserMinimalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Component("paymentsUserAclClient")
public class UserAclClient {

    private static final Logger log = LoggerFactory.getLogger(UserAclClient.class);

    private final RestClient restClient;
    private final String baseUrl;

    public UserAclClient(
            RestClient.Builder restClientBuilder,
            @Value("${app.users.api.base-url:http://localhost:8091}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public Optional<UserMinimalDto> getUserMinimal(Long userId) {
        try {
            log.info("ACL: Calling GET {}/api/v1/users/{}", baseUrl, userId);

            UserMinimalDto user = restClient.get()
                    .uri("/api/v1/users/{userId}", userId)
                    .retrieve()
                    .body(UserMinimalDto.class);

            log.info("ACL: User found: {}", user);
            return Optional.ofNullable(user);

        } catch (RestClientException e) {
            log.warn("ACL: User not found or service unavailable: {}", userId);
            return Optional.empty();
        }
    }

    public boolean existsUser(Long userId) {
        return getUserMinimal(userId).isPresent();
    }
}