#!/bin/bash

kubesec c redis -a version=$(git rev-parse --short HEAD) -l app=redis -l appGroup=infra -d file:database-password=dev.secret  > secrets/dev-secret.enc.yaml

