//index.js

$(document).ready(function(){

    //의존성 파일 (dependencies.js)를 불러 온다.
    $.getScript('/dependencies/dependencies.js', function(){

        //dependencies 사용
        kakao.init('api_key');
    });
});