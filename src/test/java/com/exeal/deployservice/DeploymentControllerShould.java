package com.exeal.deployservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeploymentControllerShould {

    private static final String VERSION = "x.y.z";
    private static final boolean SUCCESS = true;

    @Mock
    private DeploymentRepository deploymentRepository;
    @Mock
    private Deployment deployment;
    @Mock
    private DeploymentFactory deploymentFactory;
    private DeploymentController deploymentController;

    @BeforeEach
    void setUp() {
        deploymentController = new DeploymentController(deploymentRepository, deploymentFactory);
    }

    @Test
    public void
    create_a_deployment() {
        given(deploymentFactory.createDeployment(VERSION, SUCCESS)).willReturn(deployment);

        NewDeploymentDto newDeploymentDto = NewDeploymentDto.builder()
                .version(VERSION)
                .success(SUCCESS)
                .build();
        ResponseEntity<?> response = deploymentController.createDeployment(newDeploymentDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(deploymentRepository).save(deployment);
    }

    @Test
    public void
    list_all_deployments() {
        given(deploymentRepository.all())
                .willReturn(Arrays.asList(
                        new Deployment("1.0.0", true, LocalDateTime.of(2020, 3, 15, 10, 12, 34)),
                        new Deployment("2.0.0", false, LocalDateTime.of(2020, 3, 18, 15, 49, 57))
                ));

        ResponseEntity<List<DeploymentDto>> response = deploymentController.listDeployments();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(
                        Arrays.asList(
                                DeploymentDto.builder()
                                        .version("1.0.0")
                                        .success(true)
                                        .timestamp(LocalDateTime.of(2020, 3, 15, 10, 12, 34))
                                        .build(),
                                DeploymentDto.builder()
                                        .version("2.0.0")
                                        .success(false)
                                        .timestamp(LocalDateTime.of(2020, 3, 18, 15, 49, 57))
                                        .build()
                        )
                );
    }
}
