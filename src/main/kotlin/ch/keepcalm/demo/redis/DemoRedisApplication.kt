package ch.keepcalm.demo.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@SpringBootApplication
@EnableCaching
class DemoRedisApplication

fun main(args: Array<String>) {
    runApplication<DemoRedisApplication>(*args)
}

@RestController
@RequestMapping(value = ["/api/persons"], produces = [MediaType.APPLICATION_JSON_VALUE])
class Controller(private val cache: Cache) {

    @GetMapping(path = ["/{name}"])
    fun getPersonId(@PathVariable("name") key: String) = cache.getUUID(key)
}

@CacheConfig(cacheNames = ["cache1"])
@Service
class Cache {
    @Cacheable(value = ["cache1"], key = "#key")
    fun getUUID(key: String): UUID? = UUID.randomUUID().also {
        Thread.sleep(3000)
        println("Generated $it")
    }
}

