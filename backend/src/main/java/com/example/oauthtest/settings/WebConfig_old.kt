
//NOT USE

/*
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig_old : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {

        //모든 Endpoint에 CORS 설정 적용
        registry.addMapping("/**")

            // Frontend URL
            .allowedOrigins("http://localhost:8077")

            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600L)
    }
}
*/

 */