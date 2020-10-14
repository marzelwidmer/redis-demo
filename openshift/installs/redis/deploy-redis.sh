#!/bin/bash

echo "deploy ----------------- > dev       ----------> secrets"
kubesec decrypt secrets/dev-secret.enc.yaml | kubectl apply -n dev  -f -


echo "deploy ----------------- > dev       ----------> REDIS"
kustomize build base | kubectl apply -n dev  -f -
