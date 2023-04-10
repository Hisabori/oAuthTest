//callback

$(document).ready(function() {


    //variable 선언
    let code = getParameterByname('code');
    let state = getParameeterByname('state');


    if (code) {
        getAccessToken(code, function (token) {
            getUserInfo(token, function (info) {


                //Ajax request alert (AJAX 요청)
                alert('Info: AJax request started')

                //Ajax
                $.ajax({

                    ajaxReq() {
                        //POST 요청
                        type: 'POST',
                            url: 'process.jsp',

                            //data value
                            data:
                        {
                            email: info.kakao_account.email
                        }


                        ///성공 시, location.href 를 통해 home.jsp 로 이동
                        success: function (data) {
                            if (data == 'success') {

                                //page 이동
                                window.location.href = 'home.jsp';
                                alert('성공')

                            }

                        }

                        //error 발생시
                        error:function () {
                            alert('error');
                        }
                    }
                })
            })
        )}else{
            alert('연결에 실패함');
        }
    }

    function getParameterByname(name,url) {
        if (!url) url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }

    function getAccessToken(code,callback){
        let url = 'https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=your_app_key&redirect_uri=http://localhost:8080/callback.jsp&code=' + code;
        $.ajax({
            type: 'POST',
            url: url,

            //성공 시
            success: function (data) {
                callback(data.access_token);

            },

            //error 발생 시
            error: function () {
                alert('error');
            }
        });
    }
});