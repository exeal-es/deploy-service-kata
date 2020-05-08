package com.exeal.deployservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewDeploymentDto {
    String version;
    boolean success;
}
