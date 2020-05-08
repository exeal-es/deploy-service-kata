package com.exeal.deployservice;

import org.springframework.stereotype.Service;

@Service
public class DeploymentFactoryImpl implements DeploymentFactory {

    private final Clock clock;

    public DeploymentFactoryImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Deployment createDeployment(String version, boolean success) {
        return new Deployment(version, success, clock.now());
    }
}
