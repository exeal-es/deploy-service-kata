package com.exeal.deployservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeploymentRepositoryShould {

    private static final String VERSION = "1.0.0";
    private static final String VERSION_V2 = "2.0.0";
    private static final boolean ANY_SUCCESS = true;
    private static final LocalDateTime ANY_TIMESTAMP = LocalDateTime.now();

    private InMemoryDeploymentRepository deploymentRepository;

    private Deployment deployment;
    private Deployment deploymentV2;

    @BeforeEach
    void setUp() {
        deploymentRepository = new InMemoryDeploymentRepository();
        deployment = new Deployment(VERSION, ANY_SUCCESS, ANY_TIMESTAMP);
        deploymentV2 = new Deployment(VERSION_V2, ANY_SUCCESS, ANY_TIMESTAMP);
    }

    @Test
    public void
    save_a_deployment() {
        deploymentRepository.save(deployment);

        Deployment savedDeployment = deploymentRepository.get(VERSION);

        assertThat(savedDeployment).isEqualTo(deployment);
    }

    @Test
    public void
    list_all_deployments() {
        deploymentRepository.save(deployment);
        deploymentRepository.save(deploymentV2);

        List<Deployment> deployments = deploymentRepository.all();

        assertThat(deployments).isEqualTo(Arrays.asList(deployment, deploymentV2));
    }
}