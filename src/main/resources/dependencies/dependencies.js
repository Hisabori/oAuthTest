//dependencies (의존성)


let dependencies = {
    jquery: 'https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js',
    kakao_sdk: 'https://developers.kakao.com/sdk/js/kakao.js',
};

function loadDependencies(){
    let scripts = Object.values(dependencies);
    $.each(scripts, function(i,script) {
        $.getScript(script);
    });
}

$(document).ready(function(){
    loadDependencies();
})