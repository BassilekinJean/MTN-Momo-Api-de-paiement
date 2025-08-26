package com.paie.bassilekinjean.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paie.bassilekinjean.dtos.TransactionDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestPaymentService {

    private final RestTemplate restTemplate;

    @Value("${mtn.momo.api.key}")
    String apiKey;

    @Value("${mtn.momo.api.user}")
    String apiUser;

    String callBackUrl = "https://eokmkabgilyizwt.m.pipedream.net";

    public ResponseEntity<String> requestPayment(TransactionDto transactionDto) throws JsonMappingException, JsonProcessingException {
        String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache");
        headers.set("X-Reference-Id", generateUuidV4());
        headers.set("X-Target-Environment", "sandbox");
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        headers.set("X-Callback-Url", callBackUrl);
        headers.set("Content-type", "application/json");

        ResponseEntity<String> response = generateToken();
        String responseBody = response.getBody();

        // utilisez Jackson pour extraire le token :
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(responseBody);
        String token = json.get("access_token").asText();

        headers.setBearerAuth(token);

        HttpEntity<TransactionDto> entity = new HttpEntity<>(transactionDto, headers);

        try {
            return restTemplate.postForEntity(url, entity, String.class);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception lors de la demande de paiement de: " +transactionDto.payer().getPartyId() + ex.getMessage());
        }
    }

    private String generateUuidV4(){
        return java.util.UUID.randomUUID().toString();
    }

    public ResponseEntity<String> generateToken() {
        String url = "https://sandbox.momodeveloper.mtn.com/collection/token/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", apiKey);
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setBasicAuth(apiUser, apiKey);

        String body = "{\n    \"providerCallbackHost\": \""+ callBackUrl +"\"\n}";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForEntity(url, entity, String.class);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception lors de l'obtention du Token: " + ex.getMessage());
        }
    }
}
