package pe.edu.upc.center.workstation.reviewsManagement.infrastructure.acl;

import pe.edu.upc.center.workstation.reviewsManagement.infrastructure.acl.dto.SpaceMinimalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Component
public class SpaceAclClient {

    private static final Logger log = LoggerFactory.getLogger(SpaceAclClient.class);

    private final RestClient restClient;
    private final String baseUrl;

    public SpaceAclClient(
            RestClient.Builder restClientBuilder,
            @Value("${app.spaces.api.base-url:http://localhost:8091}") String baseUrl
    ) {
        this.baseUrl = baseUrl;
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public Optional<SpaceMinimalDto> getSpaceMinimal(Long spaceId) {
        try {
            log.info("ACL: Calling GET {}/api/v1/spaces/{}", baseUrl, spaceId);

            SpaceMinimalDto space = restClient.get()
                    .uri("/api/v1/spaces/{spaceId}", spaceId)
                    .retrieve()
                    .body(SpaceMinimalDto.class);

            log.info("ACL: Space found: {}", space);
            return Optional.ofNullable(space);

        } catch (RestClientException e) {
            log.warn("ACL: Space not found or service unavailable: {}", spaceId);
            return Optional.empty();
        }
    }

    public boolean existsSpace(Long spaceId) {
        return getSpaceMinimal(spaceId).isPresent();
    }
}