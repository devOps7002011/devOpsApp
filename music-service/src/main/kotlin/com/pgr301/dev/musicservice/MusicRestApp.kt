package com.pgr301.dev.musicservice

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication(scanBasePackages = ["com.pgr301.dev.musicservice"])
@EnableSwagger2
class MusicRestApp{




    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build()
    }
}

fun main(args: Array<String>) {


    var logger = LoggerFactory.getLogger(MusicRestApp::class.java)
    SpringApplication.run(MusicRestApp::class.java, *args)
    logger.info("Testing logz.io!");
}