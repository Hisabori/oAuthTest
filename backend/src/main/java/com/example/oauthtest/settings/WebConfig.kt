import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        // Add a mapping for all endpoints.
        //모든 endpoint에 지정
        registry.addMapping("/**")

        // Allow all origins

        registry.allowedOrigins("*")

        // Allow all methods.
        registry.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

        // Allow all headers.
        registry.allowedHeaders("*")

        // Allow credentials.
        registry.allowCredentials(true)

        // Set the max age of the CORS response.
        registry.maxAge(3600L)
    }
}
