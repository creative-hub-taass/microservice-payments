# microservice-payments

[![Docker Image CI](https://github.com/creative-hub-taass/microservice-payments/actions/workflows/docker-image.yml/badge.svg)](https://github.com/creative-hub-taass/microservice-payments/actions/workflows/docker-image.yml)

Microservizio pagamenti

## Linux / Mac (bash)

```shell
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build
```

## Windows (Powershell)

```powershell
$env:COMPOSE_DOCKER_CLI_BUILD=1; $env:DOCKER_BUILDKIT=1; docker-compose up --build
```

## Kubernetes (Okteto)

### Linux / Mac (bash)

```shell
okteto kubeconfig
export GATEWAY_URL=https://api-gateway-acontenti.cloud.okteto.net
export PUBLICATIONS_URL=
export USERS_URL=
export PAYMENTS_URL=
for f in ./orchestration/*.yaml; do cat $f | envsubst | kubectl apply -f -; done
```

### Windows (Powershell)

```powershell
okteto kubeconfig
$env:GATEWAY_URL = "https://api-gateway-acontenti.cloud.okteto.net"
Resolve-Path .\orchestration\*.yaml | Select -ExpandProperty Path | %{Get-Content $_ | envsubst | kubectl apply -f -}
```
