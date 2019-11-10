package com.ntouzidis.demo.module.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableList;
import com.ntouzidis.demo.module.common.Context;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliteratorUnknownSize;

@Service
public class BinanceService {

  private final static String SYMBOL_PRICE_TICKER = "/api/v3/ticker/price";
  private final static String SYMBOL_BOOK_TICKER = "/api/v3/ticker/bookTicker";

  private ObjectMapper mapper = new ObjectMapper();
  private final Context context;
  private final RestTemplateService restTemplateService;

  public BinanceService(Context context,
                        RestTemplateService restTemplateService) {
    this.context = context;
    this.restTemplateService = restTemplateService;
  }

  public String readPrice(String symbol) {
    String data = "";
    if (symbol != null)
      data = "?symbol=" + symbol;
    return restTemplateService.get(SYMBOL_PRICE_TICKER, data)
        .orElseThrow(RuntimeException::new)
        .getBody();
  }

  public String readBookPrice(String symbol) {
    String data = "";
    if (symbol != null)
      data = "?symbol=" + symbol;
    return restTemplateService.get(SYMBOL_BOOK_TICKER, data)
        .orElseThrow(RuntimeException::new)
        .getBody();
  }

  public Map<String, Double> getdiff() {
    readBookPrice(null);
    String bookPriceResponse = readBookPrice(null);
    ArrayNode bookPrices = mapper.createArrayNode();
    try {
      bookPrices = (ArrayNode) mapper.readTree(bookPriceResponse);
    } catch (IOException e) {
    }

    List<String> baseCoins = ImmutableList.of("BTC", "ETH", "BNB", "USDT");
    Map<String, JsonNode> bookPriceMap = new HashMap<>();

    StreamSupport.stream(spliteratorUnknownSize(bookPrices.elements(), Spliterator.ORDERED), true)
        .forEach(i -> bookPriceMap.put(i.get("symbol").textValue(), i));

    Set<String> coins = new HashSet<>();
    bookPriceMap.entrySet().stream()
        .filter(i -> baseCoins.stream().anyMatch(i.getKey()::contains))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        .forEach((k, v) ->
            baseCoins.forEach(i -> {
              if (k.contains(i))
                coins.add(k.replace(i, ""));
            })
        );

    return calculateCombs(coins, bookPriceMap);
  }

  private Map<String, Double> calculateCombs(Set<String> coins, Map<String, JsonNode> bookPriceMap) {
    Map<String, Double> combMap = new HashMap<>();
    coins.forEach(coin -> {
      JsonNode ETHBTC = bookPriceMap.get("ETHBTC");
      JsonNode BNBBTC = bookPriceMap.get("BNBBTC");
      JsonNode BTCUSDT = bookPriceMap.get("BTCUSDT");

      JsonNode _ETH = bookPriceMap.get(coin + "ETH");
      JsonNode _BNB = bookPriceMap.get(coin + "BNB");
      JsonNode _USDT = bookPriceMap.get(coin + "USDT");
      JsonNode _BTC = bookPriceMap.get(coin + "BTC");

      if (_ETH != null && ETHBTC != null && _BTC != null) {
        Double comb1 = _ETH.get("bidPrice").asDouble( ) * ETHBTC.get("bidPrice").asDouble() / _BTC.get("askPrice").asDouble();
        combMap.put(coin + "ETH - ETHBTC - " + coin + "BTC", comb1);
      }
      if (_BNB != null && BNBBTC != null && _BTC != null) {
        Double comb2 = _BNB.get("bidPrice").asDouble() * BNBBTC.get("bidPrice").asDouble() / _BTC.get("askPrice").asDouble();
        combMap.put(coin + "BNB - BNBBTC - " + coin + "BTC", comb2);
      }
      if (_USDT != null && BTCUSDT != null && _BTC != null) {
        Double comb3 = _USDT.get("bidPrice").asDouble( ) * BTCUSDT.get("askPrice").asDouble() / _BTC.get("askPrice").asDouble();
        combMap.put(coin + "USDT - BTCUSDT - " + coin + "BTC", comb3);
      }
    });
    return filterAndSort(combMap);
  }

  private Map<String, Double> filterAndSort(Map<String, Double> combMap) {
    return combMap.entrySet()
        .stream()
        .filter(i -> i.getValue() > 0)
        .filter(i -> i.getValue() < 2)
        .sorted((x1, x2) -> x2.getValue().compareTo(x1.getValue()))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (oldValue, newValue) -> oldValue,
            LinkedHashMap::new)
        );
  }


}
