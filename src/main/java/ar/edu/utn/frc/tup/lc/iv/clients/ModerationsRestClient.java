package ar.edu.utn.frc.tup.lc.iv.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * This class is a REST client for the moderation service.
*/
@Service
public class ModerationsRestClient {


    /**
    * The REST template used to send requests to the moderation service.
    */
    @Autowired
    private RestTemplate restTemplate;


    /**
    * The URL of the moderation service.
    */
    @Value("${moderations.service.url}")
    private String moderationsServiceUrl;

    /**
     * Sends the query parameters to the moderation service using multipart/form-data.
     *
     * @param plotId         the plot ID
     * @param description    the description
     * @param sanctionTypeId the sanction type ID
     */
    public void sendModeration(String plotId, String description, String sanctionTypeId) {
        String urlWithParams = String.format("%s/claims?plot_id=%s&description=%s&sanction_type_entity_id=%s",
                moderationsServiceUrl, plotId, description, sanctionTypeId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("file", new ByteArrayResource(new byte[0]) {
            @Override
            public String getFilename() {
                return "dummy.txt";
            }
        });

        body.add("plot_id", plotId);
        body.add("description", description);
        body.add("sanction_type_entity_id", sanctionTypeId);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlWithParams, entity, String.class);
        System.out.println("Response: " + response.getBody());
    }
}
