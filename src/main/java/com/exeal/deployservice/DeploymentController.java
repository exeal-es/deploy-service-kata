package com.exeal.deployservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/deployments")
public class DeploymentController {
    private DeploymentRepository deploymentRepository;
    private DeploymentFactory deploymentFactory;

    @Autowired
    public DeploymentController(DeploymentRepository deploymentRepository, DeploymentFactory deploymentFactory) {
        this.deploymentRepository = deploymentRepository;
        this.deploymentFactory = deploymentFactory;
    }

    @PostMapping
    public ResponseEntity<?> createDeployment(@RequestBody NewDeploymentDto newDeploymentDto) {
        Deployment deployment = deploymentFactory.createDeployment(newDeploymentDto.getVersion(), newDeploymentDto.isSuccess());
        deploymentRepository.save(deployment);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DeploymentDto>> listDeployments() {
        List<Deployment> deployments = deploymentRepository.all();
        List<DeploymentDto> deploymentDtos = deployments.stream().map(this::mapToDeploymentDto).collect(toList());
        return ResponseEntity.ok(deploymentDtos);
    }

    private DeploymentDto mapToDeploymentDto(Deployment deployment) {
        return DeploymentDto.builder()
                .version(deployment.version())
                .success(deployment.success())
                .timestamp(deployment.timestamp())
                .build();
    }
}
