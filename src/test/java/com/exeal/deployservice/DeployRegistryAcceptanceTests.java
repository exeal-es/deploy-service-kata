package com.exeal.deployservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeployRegistryAcceptanceTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void do_something() {
    }
}