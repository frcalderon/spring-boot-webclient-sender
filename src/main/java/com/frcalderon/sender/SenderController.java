package com.frcalderon.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/send")
public class SenderController {

    @Autowired
    private WebClient webClient;

    @PostMapping
    public String postSend(@RequestBody String data) {
        System.out.println(data);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("DATA", data);
        String response = webClient.post()
                .uri("/receive")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
//                .body(BodyInserters.fromFormData("data", data))
//                .body(Mono.just(data), String.class)
                .bodyValue(URLEncoder.encode(formData.toString(), StandardCharsets.ISO_8859_1))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
