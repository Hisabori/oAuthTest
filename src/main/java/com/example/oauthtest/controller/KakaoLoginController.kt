package com.example.oauthtest.controller

import com.example.oauthtest.service.KakaoService
import org.springframework.ui.Model
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class KakaoLoginController {


    @Autowired
    lateinit var KakaoService: KakaoService

    @RequestMapping("/login")
    fun KakaoLogin(request: HttpServletRequest, model: Model):String{

        val redirectUrl = KakaoService.getAuthorizationUrl(request.toString())
        model.addAttribute("url",redirectUrl)

        //return 'URL'
        return "login"
    }

    //oauth--requestMapping
    @RequestMapping("/oauth")

    //CALLBACK
    fun kakaoCallback(@RequestParam("code") code: String, request: HttpServletRequest, response: HttpServletResponse): String{

        //Kauth_Token_Access
        val token = KakaoService.getAccessToken(code, request, response)
        val userInfo = KakaoService.getUserInfo(token?.get("access_token") as String)

        if(userInfo?.get("email") == null as Boolean){
            return "redirect:/login?error=email"
        } else{
            val email = userInfo?.get("email") as String

            //Kauth_Proc_Success (Kakao 로그인 성공 시:)
            return "redirect:/home"
        }
    }
}