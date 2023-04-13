
package com.example.oauthtest.service

import org.springframework.http.MediaType

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

//Jackson Library 추가
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

//RestTemplate
//RestTemplate 에서 HttpComponentsClientHttpRequestFactory 사용 하도록 변경
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory

//RestTemplate 생성 시. DefaultUriBuilderFactory 사용 하도록 변경
import org.springframework.web.util.DefaultUriBuilderFactory

//RestTemplate 생성 시, StringHttpMessageConverter 사용 하도록 변경
import org.springframework.http.converter.StringHttpMessageConverter
import java.nio.charset.Charset


//사용자 정보를 담은 url

private const val userInfoUrl = "https://kapi.kakao.com/v2/user/me"

@Service
class KakaoService {


    //클라이언트 id
    private val clientId = "auth_client_id"

    //redirect 되는 urk
    val redirectUrl = "http://localhost:8777/oauthSvc/authorize"

    //응답 type
    val responseType = "code"

    //요청 url
    val requestUrl = "https://kauth,kakao.com/oauth/authorize"

    //토큰 link
    val accessTokenUrl = "https://kauth.kakao.com/oauth/token"

    //client secret value
    val clientSecret = "insert client secret"

    //RestTemplate

    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory()

        //UTF-8 인코딩
        restTemplate.messageConverters.add(0, StringHttpMessageConverter(Charset.forName("UTF-8")))
        return restTemplate
    }

    //요청
    fun getAuthorizationUrl(state: String): String {
        val builder = DefaultUriBuilderFactory(requestUrl)
            .builder()
            .queryParam("auth_client_id", clientId)
            .queryParam("redirect_uri", redirectUrl)
            .queryParam("response_type", responseType)
            .queryParam("state", state)
        return builder.build().toString()
    }

    //access token 요청
    fun getAccessToken(code: String, kakaoClientid: String?, kakaoRedirectUri: String?): Map<String, Any> {
        val headers = HttpHeaders()

        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val params = LinkedMultiValueMap<String, String>()


        params.add("grant_type", "authorization_code")
        params.add("client_id", kakaoClientid)
        params.add("redirect_uri", kakaoRedirectUri)
        params.add("code", code)

        val request = HttpEntity(params, headers)
        val response = RestTemplate().exchange(accessTokenUrl, HttpMethod.POST, request, String::class.java)
        val objectMapper = ObjectMapper()

        val result: Map<String, Any> =
            objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})
            //예외 처리
            //GetAccessToken 메소드에서 access token 필드가 존재 하는지 확인 -> 없을 경우 예외 처리
            if (!result.containsKey("Access_Token")){
                throw RuntimeException("토큰값 $result 을(를) 가져오는 중 예외가 발생 하여 실패하였습니다. (error: $result)")
            }
        //결과값 리턴
        return result

        //return objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

    }

   //사용자 정보 가져오기
    fun getUserInfo(accessToken: String):Map<String,Any>{
        val headers = org.springframework.http.HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")

        val request = HttpEntity("", headers)
        val response = restTemplate().exchange(userInfoUrl, HttpMethod.GET, request, String::class.java)
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

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