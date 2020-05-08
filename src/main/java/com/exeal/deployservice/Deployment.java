package com.exeal.deployservice;

import java.time.LocalDateTime;

public class Deployment implements Comparable<Deployment> {
    private final String version;
    private final boolean success;
    private final LocalDateTime timestamp;

    public Deployment(String version, boolean success, LocalDateTime timestamp) {
        this.version = version;
        this.success = success;
        this.timestamp = timestamp;
    }

    public String version() {
        return version;
    }

    public boolean success() {
        return success;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Deployment o) {
        return version.compareTo(o.version);
    }
}
