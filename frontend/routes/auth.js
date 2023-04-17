//Jquery
$(document).ready(function() {
    const express = require("express");
    const axios = require("axios");


    const router = express.Router();

    //kakao oAuth infomation
    const KAKAO_CLIENT_ID = "INSERT YOUR KAKAO_CLIENT_ID"
    const KAKAO_CLIENT_SECRET = "INSERT YOUR KAKAO_CLIENT"

    //redirect to kakao account authorization page
    router.get("/kakao", (req, res) => {
        const REDIRECT_URI = 'http://${req.headers.host}/auth/kakao/callback';
        const KAKAO_AUTH_URL = 'https://kauth.kakao.com/oauth/authorize?client_id=${REDIRECT_URI}&response_type=code';
        res.redirect(KAKAO_AUTH_URL);
    });

    //kakao OAuth Callback
    router.get("/kakao/callback", async(req, res) => {
        const AUTH_CODE =  req.query.code;
        const REDIRECT_URI = 'http://${req.headers.host}auth/kakao/callback';
        const TOKEN_API_URL = 'https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${KAKAO_CLIENT_ID}&client_secret=${KAKAO_CLIENT_SECRET}&redirect_uri=${REDIRECT_URI}&code=${AUTH_CODE}';

        try{
            //register token from KAKAO API
            const tokenResponse = await axios.post(TOKEN_API_URL);
            const accessToken = tokenResponse.data.access_token;


            //get user info from KAKAO API
            const profileResponse = await axios.get(
                "https://kapi.kakao.com/v2/user/me",
                {
                    headers: {
                        Authorization: 'Bearer ${accessToken}',
                    },
                }
            );

            const {id, properties} = profileResponse.data;
            const {nickname, profile_image} = properties;

            //save user info to session
            req.session.user = {id, nickname, profile_image};
            res.redirect("/");
        } catch(error){
            console.log(error);
            res.status(500).send("kakao로그인 실패 (oauth 인증 실패)");
        }
    });

    module.exports = router;

})