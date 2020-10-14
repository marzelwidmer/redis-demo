# Redis Demo
Redis is an open source in-memory data structure. 
Redis fits well for a database cache and is not common, but it can be used as a message broker using the publish-subscribe feature, it can be useful to decouple applications.
There are some interesting features supported by Redis such as transactions, atomic operations, and support for time-to-live keys. Time-to-live is useful for giving a time for the key, the eviction strategy is always hard to implement, and Redis has a built-in solution for us.


# Create a Docker network for our application
To create isolation for our application, we will create a custom Docker network.
```
docker network create twitter
```

Let's check the network by typing the following command:
```
docker network list
```

# Pulling the Redis image from the Docker Hub
The first thing we need to do is the download the Redis image from the Docker Hub by typing the following command:
```
docker pull redis:6.0.8-alpine
```

We can check the result using the following command:
```
docker images
```

# Running the Redis instance
We will start the Redis instance for our application with the command : 
```
docker run -d --name redis-alpine --net twitter -p 6379:6379 redis:6.0.8-alpine
```

# Install Redis CLI on OSX
There is also a `brew` Redis on OSX with `redis-cli` this is also possible to run this Redis instance instead of the Docker one.
```
brew install redis
```

# Redis CLI
```
redis-cli
```

## Commands
### SET 
It sets the key and holds the value.
```
SET "user:id:10" "john"
```

### GET
Gets the value of the requested key.
```
GET "user:id:10"
```
### Increment key
```
INCR "users"
```
### Decrement key
```
DECR "users"
```
### Increment key with argument
```
INCRBY "users" 2
```
### Lists
Lists of strings. They are ordered by the insertion order. Redis also offers instructions to add new elements at the head or tail of the list.
Lists can be useful for storing groups of things, groups of categories, for example, grouped by the categories key.

#### LPUSH 
Insert the new element at the head of the key.

```
LPUSH "categories" "pizza"
LPUSH "categories" "pasta"
```

#### LRANGE 
It returns the specified elements of the key. The command arguments are the key, the start index, and finally the stop element. 
The -1 on the stop argument will return the whole list:
```
LRANGE "categories" 0 -1  
LRANGE "categories" 0 2
```
  
#### RPUSH 
Insert the new element at the tail of the key. 
The command supports multiple arguments as well, in this case, the values will respect the respective order.
```
RPUSH "categories" "kitchen"
RPUSH "categories" "room"
LRANGE "categories" 0 -1
```

#### LSET 
It sets the element on the requested index.
The new value of the zero index is series.
```
LSET "categories" 0 "series"
```
  

### Sets
A set is a collection of strings. 
They have a property which does not allow repeated values. It means that if we add the pre-existing value on the sets, it will result in the same element, in this case, 
the advantage is not necessary to verify if the element exists on the set. Another important characteristic is that the sets are unordered. This behavior is different from the Redis lists.
It can be useful in different use cases such as count the unique visitor, track the unique IPs, and much more.

#### SADD
It adds the element in a requested key. Also, the return of this command is the number of the element added to the set:
```
SADD "unique-visitors" "john"
SADD "unique-visitors" "jane"
```
     
#### SMEMBERS 
 It returns all the members of a requested key:
```
SMEMBERS "unique-visitors"
```       
The command will return `john` and `jane` because those are the values stored in the unique-visitors key.
 
 #### SCARD
It returns the numbers of elements of a requested key:
```
SCARD "unique-visitors"
```
The command will return the number of elements stored in the requested keys, in this case, the output will be 2.


# Gradle
## Build Docker image with buildpacks
```
./gradlew clean bootBuildImage
```

## Build and start Spring Boot with Gradle
```
./gradlew bootRun
```

## Scan Builds
```
./gradlew build --scan
```


# Install Redis on Minikube
## Add Bitnami Helm Repo
```
helm repo add bitnami https://charts.bitnami.com/bitnami
```
## Install Redis
```
helm install redis bitnami/redis


To get your password run:

    export REDIS_PASSWORD=$(kubectl get secret --namespace default redis -o jsonpath="{.data.redis-password}" | base64 --decode)

To connect to your Redis server:

1. Run a Redis pod that you can use as a client:
   kubectl run --namespace default redis-client --rm --tty -i --restart='Never' \
    --env REDIS_PASSWORD=$REDIS_PASSWORD \
   --image docker.io/bitnami/redis:6.0.8-debian-10-r0 -- bash

2. Connect using the Redis CLI:
   redis-cli -h redis-master -a $REDIS_PASSWORD
   redis-cli -h redis-slave -a $REDIS_PASSWORD

To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace default svc/redis-master 6379:6379 &
    redis-cli -h 127.0.0.1 -p 6379 -a $REDIS_PASSWORD


```
### Install Redis Cluster
```
helm install redis bitnami/redis-cluster
```


# Setup Redis 5.0.5 on OpenShift 3.11 
```
docker login registry.redhat.io
docker login  -u `oc whoami`  -p `oc whoami -t` registry.apps.c3smonkey.ch/openshift
docker pull registry.redhat.io/rhscl/redis-5-rhel7
docker tag registry.redhat.io/rhscl/redis-5-rhel7 registry.apps.c3smonkey.ch/openshift/redis:5.0.5
docker push registry.apps.c3smonkey.ch/openshift/redis:5.0.5
oc tag redis:5.0.5 redis:latest
```

# Install Redis on Openshift
```
./openshift/installs/redis/deploy-redis.sh
```

# Remote Shell in Redis POD
```bash
oc rsh redis-1-j8d67
```
# Open Redis CLI
```bash
sh-4.2$ redis-cli
```

# Authenticate 
```bash
127.0.0.1:6379> auth <password>
```

# Retrieving All Existing Keys 
The Keys are only temporaries when the rate limit has been recognized
https://chartio.com/resources/tutorials/how-to-get-all-keys-in-redis/
```bash
127.0.0.1:6379> KEYS *
1) "request_rate_limiter.{1}.timestamp"
2) "request_rate_limiter.{1}.tokens"
127.0.0.1:6379>
```








# Read ConfigMap - RBAC policy  
```
oc policy add-role-to-user view system:serviceaccount:dev:default
```

# Deploy to OpenShift
```
skaffold run -p openshift
```
