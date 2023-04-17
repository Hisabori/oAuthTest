$(document).ready(function() {
    $('#login_button').click(function() {

        //kakao authentication
        Kakao.Auth.authenticate({
            redirectUri: 'http://localhost:08080/kakao_login'
        })
    })
})