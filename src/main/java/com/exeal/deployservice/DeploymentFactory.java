package com.exeal.deployservice;

public interface DeploymentFactory {
    Deployment createDeployment(String version, boolean success);
}
