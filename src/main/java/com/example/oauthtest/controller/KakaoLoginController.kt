@Controller

package com.example.oauthtest.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class KakaoLoginController {


    @Autowired
    lateinit var KakkaoService: KakaoService

    @RequestMapping("/login")
    fun KakaoLogin(request: HttpServletRequest, model: Model):String{

        val redirectUrl = KakaoService.getAuthorizationUrl(request)
        model.addAttribute("url",redirectUrl)

        //return 'URL'
        return "login"
    }

    //oauth--requestMapping
    @RequestMapping("/oauth")

    //CALLBACK
    fun kakaoCallback(@RequestParam("code") code: String, request: HttpServletRequest, response: HttpServletResponse): String{

        //Kauth_Token_Access
        val token = KakaoService.getAccessToken(code)
        val userInfo = KakaoService.getUserInfo(token["access_token"] as String)

        if(userInfo ["email" == null]){
            return "redirect:/login?error=email"
        } else{
            val email = userInfo["email"] as String

            //Kauth_Proc_Success (Kakao 로그인 성공 시:)
            return "redirect:/home"
        }
    }
}