package com.example.oauthtest.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.http.HttpHeaders
import java.util.*
import javax.servlet.http.HttpServletRequest



@Service
class KakaoService {

    //클라이언트 id
    private val clientId = "auth_client_id"

    //redirect 되는 urk
    private val redirectUrl = "http://localhost:8777/oauthSvc/authorize"

    //응답 type
    private val responseType = "code"

    //요청 url
    private val requestUrl = "https://kauth,kakao.com/oauth/authorize"

    //토큰 link
    private val tokenUrl = "https://kauth.kakao.com/oauth/token"

    //사용자 정보를 담은 url
    private val userInfoUrl = "https://kapi.kakao.com/v2/user/me"

    //요청
    fun getAuthorizationUrl(request: HttpServletRequest): String {
        val state = UUID.randomUUID().toString()
        request.session.setAttribute("state", state)

        val builder = UrlComponentsBuilder.fromHttpUrl(requestUrl)
            .queryParam("client_id", clientId)
            .queryParam("redirect_Url", redirectUrl)
            .queryParam("response_type", responseType)
            .queryParam("state", state)
        return builder.toUrlString()
    }

    //access token 요청
    fun getAccessToken(code: String) :Map<String, Any> {
        val headers = org.springframework.http.HttpHeaders()

        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val params = LinkedMultiValueMap<String, String>()


        params.add("grant_type", "authorization_code")
        params.add("client_id", clientId)
        params.add("redirect_uri", redirectUrl)
        params.add("code", code)

        val request = HttpEntity(params,headers)
        val response = RestTemplate().postForObject(tokenUrl, request, String::class.java)
        val mapper = ObjectMapper()
        return mapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

    }


    //사용자 정보 가져오기
    fun getUserInfo(accessToken: String):Map<String,Any>{
        val headers = org.springframework.http.HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")

        val request = HttpEntity("", headers)
        val response = RestTemplate().exchange(userInfoUrl, HttpMethod.GET, request, String::class.java)
        val mapper = ObjectMapper()
        return mapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

    }


}

/*
private fun Any.toUrlString(): String {
    TODO("Not yet implemented")
}

private fun Any.queryParam(s: String, clientId: String): Any {
    TODO("Not yet implemented")
}
*/