package com.example.secondservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
// @RequestMapping("/") // Zuul Mapping
@RequestMapping("/second-service")
@RequiredArgsConstructor
public class SecondServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to the Second Service";
    }

    @GetMapping("/filter")
    public String filterCheck(@RequestHeader("Test-req-Header")String header) {
      log.info("*********** Request Header: " + header);
      return "Second Service Filter Test";
    }

    @GetMapping("/custom-filter")
    public String customFilterCheck() {
        log.info("Second-Service Called");
        return "Second Service Custom Filter Test";
    }

    // Load Balance Test
    @GetMapping("/port")
    public String printPortforCheckLoadBalancer(HttpServletRequest request) {
        log.info("Second Service port -> {} ",env.getProperty("local.server.port"));
        return String.format("Second Service on %s", request.getServerPort());
    }
}
