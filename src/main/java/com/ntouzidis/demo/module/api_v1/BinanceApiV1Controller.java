package com.ntouzidis.demo.module.api_v1;

import com.ntouzidis.demo.module.binance.BinanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.ntouzidis.demo.module.common.constants.ControllerPathConstants.BINANCE_CONTROLLER_PATH;

@RestController
@RequestMapping(
    value = BINANCE_CONTROLLER_PATH,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class BinanceApiV1Controller {

  private final BinanceService binanceService;

  public BinanceApiV1Controller(BinanceService binanceService) {
    this.binanceService = binanceService;
  }

  @GetMapping("/symbol/price")
  public ResponseEntity<String> getPrice(
      @RequestParam(value = "symbol", required = false) String symbol
  ) {
    String symbolPrice = binanceService.readPrice(symbol);
    return new ResponseEntity(symbolPrice, HttpStatus.OK);
  }


  @GetMapping("/symbol/book_price")
  public ResponseEntity<String> getBookPrice(
      @RequestParam(value = "symbol", required = false) String symbol
  ) {
    String bookPrice = binanceService.readBookPrice(symbol);
    return new ResponseEntity(bookPrice, HttpStatus.OK);
  }

  @GetMapping("/symbol/diff")
  public ResponseEntity<Map<String, Double>> getDiff(
      @RequestParam(value = "symbol", required = false) String symbol
  ) {
    Map<String, Double> bookPrice = binanceService.getCombinations();
    return new ResponseEntity(bookPrice, HttpStatus.OK);
  }

}

