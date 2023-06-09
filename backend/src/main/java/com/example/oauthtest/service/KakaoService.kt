
package com.example.oauthtest.service

import org.springframework.http.MediaType

import org.springframework.http.HttpEntity

//불필요한 코드 정리
//import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

//Jackson Library 추가
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value

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
class KakaoServiceImpl : KaKaoService {


    //클라이언트 id
    //private val clientId = "auth_client_id"

    //client id 하드 코딩 이슈 수정
    //@Value("\${KaKao.clientId}")

    //BUG 수정 23-04-06
    @Value("\${KAKAO_CLIENT_ID}")
    private lateinit var clientId =  String

    //client secret value
    //val clientSecret = "insert client secret"

    //secret key 하드 코딩 이슈 수정 (환경 변수 setting)
    //private val clientSecret = System.getenv("KAKAO_CLIENT_SECRET")

    //환경 변수 최적화 23-04-06
    @Value("\${KAKAO_CLIENT_SECRET}")
    private lateinit var clientSecret: String

    //redirect 되는 url
    //@Value("\${Kakao.redirectUri}")
    private val redirectUri = "http://localhost:8777/oauthSvc/authorize"



    //응답 type
    //@Value("\${Kakao.responseType}")
    private val responseType = "code"

    //요청 url
    private val requestUrl = "https://kauth.kakao.com/oauth/authorize"

    //토큰 link
    private val accessTokenUrl = "https://kauth.kakao.com/oauth/token"

    //기존에 import 되어 있는 HttpHeaders 사용
    val headers = HttpHeaders()


    //RestTemplate

    private fun restTemplate(): RestTemplate {
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
    //fun getAccessToken(code: String, kakaoRedirectUri: String?, kakaoClientid: String?): Map<String, Any>? {

    //카멜케이스로 변경 23-04-26 (....kakaoClientid -> ....kakaoClientId)
    fun getAccessToken(code: String,kakaoRedirectUri: String?, kakaoClientId: String?): Map<String,any>?{
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

        val result: Map<String, Any>? =
            objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})
            //예외 처리
            //GetAccessToken 메소드에서 access token 필드가 존재 하는지 확인 -> 없을 경우 예외 처리
        if (result != null) {
            if (!result.containsKey("Access_Token")){
                throw RuntimeException("토큰값 $result 을(를) 가져오는 중 예외가 발생 하여 실패하였습니다. (error: $result)")
            }
        }
        //결과값 리턴
        return result

        //return objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

    }

   //사용자 정보 가져오기
    fun getUserInfo(accessToken: String): Map<String, Any>? {
        val headers = org.springframework.http.HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")

        val request = HttpEntity("", headers)
        val response = restTemplate().exchange(userInfoUrl, HttpMethod.GET, request, String::class.java)
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(response.body, object : TypeReference<Map<String, Any>>() {})

    }


}

private operator fun Unit.not(): Boolean {
    TODO("Not yet implemented")
}

private fun Unit.containsKey(s: String) {

}


/*
private fun Any.toUrlString(): String {
    TODO("Not yet implemented")
}

private fun Any.queryParam(s: String, clientId: String): Any {
    TODO("Not yet implemented")
}
*/