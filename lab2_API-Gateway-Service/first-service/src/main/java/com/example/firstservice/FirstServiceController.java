package com.example.firstservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/first-service")
@RequiredArgsConstructor
public class FirstServiceController {

    private final Environment env;

    // Load Balance Test
    @GetMapping("/port")
    public String printPortForCheckLoadBalancer(HttpServletRequest request) {
        log.info("First Service port -> {} ",env.getProperty("local.server.port"));
        return String.format("First Service on %s", request.getServerPort());
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to the First Service";
    }

    @GetMapping("/filter")
    public String filterCheck(@RequestHeader("Test-req-Header")String header) {
        log.info("*********** Request Header: " + header);
        return "First Service Filter Test";
    }

    @GetMapping("/custom-filter")
    public String customFilterCheck() {
        log.info("First-Service Called");
        return "First Service Custom Filter Test";
    }
}
