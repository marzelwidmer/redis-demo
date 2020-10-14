#!/bin/bash
kubectl get secrets,sts,po,deployment,pvc,svc,netpol -l 'app in (redis)' -n dev
