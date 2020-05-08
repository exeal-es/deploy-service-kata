package com.exeal.deployservice;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDeploymentRepository implements DeploymentRepository {

    private SortedMap<String, Deployment> deployment = new TreeMap<>();

    @Override
    public void save(Deployment deployment) {
        this.deployment.put(deployment.version(), deployment);
    }

    @Override
    public List<Deployment> all() {
        return new ArrayList<>(deployment.values());
    }

    public Deployment get(String version) {
        return deployment.get(version);
    }
}
