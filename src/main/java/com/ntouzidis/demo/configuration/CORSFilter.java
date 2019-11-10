package com.ntouzidis.demo.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Component
@Order(HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

  private static final String DELIMITER = ",";

  @Override
  public void init(FilterConfig fc) {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) resp;
    HttpServletRequest request = (HttpServletRequest) req;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
        "x-requested-with" + DELIMITER +
            "authorization" + DELIMITER +
            "Content-Type" + DELIMITER +
            "Authorization" + DELIMITER +
            "credential" + DELIMITER +
            "X-XSRF-TOKEN" + DELIMITER +
            "X-Forwarded-For" + DELIMITER +
            "X-Forwarded-Proto" + DELIMITER);

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, resp);
    }
  }

  @Override
  public void destroy() {
  }

}