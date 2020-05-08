package com.exeal.deployservice;

import java.util.List;

public interface DeploymentRepository {
    void save(Deployment deployment);

    List<Deployment> all();
}
