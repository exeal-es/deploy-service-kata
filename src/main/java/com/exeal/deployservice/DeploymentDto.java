package com.exeal.deployservice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeploymentDto {
    String version;
    boolean success;
    LocalDateTime timestamp;
}
