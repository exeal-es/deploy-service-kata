# Requirements

## Scenario: Deployment history

- Given version 1 was deployed at date D1 with success
- And version 2 was deployed at date D1 with rollback
- When I fetch deployment history
- Then the deployment list contains these deployments:
  - v1:D1:success
  - v2:D2:rollback