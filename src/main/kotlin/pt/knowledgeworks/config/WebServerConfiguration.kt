package pt.knowledgeworks.config

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//@Configuration
class WebServerConfiguration {



    fun addCorsConfig() : WebMvcConfigurer {
        return object : WebMvcConfigurer {

        }
    }

}