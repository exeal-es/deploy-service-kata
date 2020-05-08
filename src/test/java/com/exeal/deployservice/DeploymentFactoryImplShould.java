package com.exeal.deployservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeploymentFactoryImplShould {

    private static final String VERSION = "x.y.z";
    private static final boolean SUCCESS = true;
    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2020, 1, 1, 1, 1, 1);

    private DeploymentFactoryImpl deploymentFactory;

    @Mock
    private Clock clock;

    @BeforeEach
    void setUp() {
        deploymentFactory = new DeploymentFactoryImpl(clock);
    }

    @Test
    public void
    create_a_deployment() {
        when(clock.now()).thenReturn(TIMESTAMP);

        Deployment deployment = deploymentFactory.createDeployment(VERSION, SUCCESS);

        assertThat(deployment.version()).isEqualTo(VERSION);
        assertThat(deployment.success()).isEqualTo(SUCCESS);
        assertThat(deployment.timestamp()).isEqualTo(TIMESTAMP);
    }

}