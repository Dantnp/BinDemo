package com.ntouzidis.demo.module.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ntouzidis.demo.module.common.Context;
import com.ntouzidis.demo.module.user.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.springframework.data.util.Pair.toMap;

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
    User user = context.getUser();
    String data = "";
    if (symbol != null)
      data = "?symbol=" + symbol;
    return restTemplateService.get(user, SYMBOL_PRICE_TICKER, data)
        .orElseThrow(RuntimeException::new)
        .getBody();
  }

  public String readBookPrice(String symbol) {
    User user = context.getUser();
    String data = "";
    if (symbol != null)
      data = "?symbol=" + symbol;
    return restTemplateService.get(user, SYMBOL_BOOK_TICKER, data)
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
      e.printStackTrace();
    }
    Map<String, JsonNode> bookPriceMap = new HashMap<>();

    StreamSupport.stream(Spliterators
        .spliteratorUnknownSize(bookPrices.elements(),
            Spliterator.ORDERED), false)
        .forEach(i -> bookPriceMap.put(i.get("symbol").textValue(), i));

    JsonNode ADABTC = bookPriceMap.get("ADABTC");
    JsonNode ADAETH= bookPriceMap.get("ADAETH");
    JsonNode ETHBTC= bookPriceMap.get("ETHBTC");

    JsonNode ADABNB = bookPriceMap.get("ADABNB");
    JsonNode BNBBTC = bookPriceMap.get("BNBBTC");
    JsonNode ADAUSDT = bookPriceMap.get("ADAUSDT");
    JsonNode BTCUSDT = bookPriceMap.get("BTCUSDT");

    Map<String, JsonNode> bookPriceToCalculate = new HashMap<>(bookPriceMap);
//    bookPriceToCalculate.remove();



    Map<String, Double> combMap = new HashMap<>();
    Double comb1 = ADAETH.get("bidPrice").asDouble( )* ETHBTC.get("bidPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADAETH - ETHBTC - ADABTC", comb1);

    Double comb2 = ADABNB.get("bidPrice").asDouble( )* BNBBTC.get("bidPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADABNB - BNBBTC - ADABTC", comb2);

    Double comb3 = ADAUSDT.get("bidPrice").asDouble( )* BTCUSDT.get("askPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADAUSDT - BTCUSDT - ADABTC", comb3);

//    bookPriceMap.forEach((k, v) -> calculateCombs(k, v, combMap));

    return combMap;
  }

  private void calculateCombs(String symbol, JsonNode jsonNode, Map<String, Double> combMap) {

    Double comb1 = ADAETH.get("bidPrice").asDouble( )* ETHBTC.get("bidPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADAETH - ETHBTC - ADABTC", comb1);

    Double comb2 = ADABNB.get("bidPrice").asDouble( )* BNBBTC.get("bidPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADABNB - BNBBTC - ADABTC", comb2);

    Double comb3 = ADAUSDT.get("bidPrice").asDouble( )* BTCUSDT.get("askPrice").asDouble() / ADABTC.get("askPrice").asDouble();
    combMap.put("ADAUSDT - BTCUSDT - ADABTC", comb3);
  }


}
