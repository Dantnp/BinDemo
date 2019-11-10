package com.ntouzidis.demo.module.binance;

import com.ntouzidis.demo.module.user.entity.User;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class RestTemplateService {

 private Logger log = LoggerFactory.getLogger(RestTemplateService.class);

 private  final RestTemplate restTemplate;

  public RestTemplateService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Optional<HttpEntity<String>> get(String path, String data) {
    return call(HttpMethod.GET, path, data);
  }

  public Optional<HttpEntity<String>> post(String path, String data) {
    return call(HttpMethod.POST, path, data);
  }

  public Optional<HttpEntity<String>> delete(String path, String data) {
    return call(HttpMethod.DELETE, path, data);
  }

  private Optional<HttpEntity<String>> call(HttpMethod method, String path, String data) {
    try {
//      String signature = calculateSignature(user, method, path, data);
      String url = "https://api.binance.com" + path + data;
      HttpEntity httpEntity = new HttpEntity<>(getHeaders("", ""));
      return Optional.of(restTemplate.exchange(url, method, httpEntity, String.class));

    } catch (HttpClientErrorException e) {
      System.out.println("failed");
    }
    return Optional.empty();
  }

//  private String calculateSignature(User user, HttpMethod method, String path, String data) {
//    try {
//      checkNotNull(user, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "User"));
//      checkNotNull(method, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Method"));
//      checkNotNull(path, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Path"));
//      checkNotNull(data, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Data"));

//      String apiSecret = user.getApiSecret();
//      String message = method.name() + path + EXPIRES_SECONDS + data;
//
//      Mac sha256HMAC = Mac.getInstance(HMAC_SHA_256);
//      SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(), HMAC_SHA_256);
//      sha256HMAC.init(secretKey);
//
//      return Hex.encodeHexString(sha256HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
//    } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
//      throw new RuntimeException("failed", e);
//    }
//  }

  private HttpHeaders getHeaders(String apiKey, String signature) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(APPLICATION_JSON));
//    headers.setContentType(APPLICATION_FORM_URLENCODED);
//    headers.set(X_REQUESTED_WITH_HEADER, XML_HTTP_REQUEST);
//    headers.set(API_EXPIRES_HEADER, String.valueOf(EXPIRES_SECONDS));
//    headers.set(API_KEY_HEADER, simpleEncryptorService.decrypt(apiKey));
//    headers.set(API_SIGNATURE_HEADER, signature);
    return headers;
  }
}
