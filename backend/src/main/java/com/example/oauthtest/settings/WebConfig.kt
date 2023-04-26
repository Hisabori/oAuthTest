import org.springframework.context.annotation.AnnotationConfigRegistry
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CoreRegistry
import org.springframwork.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer{
    override fun addCoreMappings(registry: CoreRegistry){

        //전체 endpoint에 CORS 설정을 적용함
        registry.addMapping("/**")

            //frontend url
            .allowedOrigins("http://localhost:8077")

            .allowedMethods("GET", "POST", "PUT",)
    }
}