//jquery 호출
/*$(document).ready(function () {

    async function callLoginAPI() {

        //응답
        const response = await fetch("http://localhost:8777/api/login", {

            //value 를 url 상에 넣어서 보내는 GET 방식을 사용한다.
            //TIP: GET 방식의 경우 전송 시 값이 노출 되기 때문에 민감한 정보의 경우 url 상에 value 가 노출 되지 않는 POST 방식으로 진행 하여야 한다.
            method: "GET",
            headers: {

                //json 파일
                "Content-Type:": "application/json",
            },
        });


        //응답 결과가 정상일 경우 (response: OK)
        if (response.ok) {

            //대기 (await)
            const data = await response.json();
            console.log(data)
            alert(data)

            //data 가져오기에 실패한 경우 (response: error)
        } else {
            console.log("API 데이터 가져 오기에 실패하였습니다");
        }
    }
}*/


//2023-04-26 14:00 수정 내용
/*
    -> callLoginAPI 함수를 async->일반 함수로 변경
    -> ajax 요청 진행 시 fetch 대신 jquery 의 $.ajax() 사용
    -> 응답 data 를 JSON.stringify()함수를 사용해 json 객체를 문자열로 전환 및 전달
    -> callLoginAPI 함수를 실행 할 때마다 호출 되도록 변경 (이벤트 리스너 변경)
        -> 클릭 이벤트의 경우, #login-button 요소의 클릭 이벤트를 사용함.
*/


//use Jquery
$(document).ready(function(){

    function callLoginAPI(){


        //Jquery AJAX
        $.ajax({

            //주소
            uri: "http://localhost:8777/api/login",

            //전송 방식
            method: "GET",

            //데어터 타입
            dataType: "json",

            //ajax 요청 성공 시
            success: function (data) {
                console.log(data);
                alert(JSON.stringify(data));
            },
            error: function (){
                console.log("API DATA 가져오는 중 에러가 발생하였습니다.")
            }
        })
    }

    //EVENT 함수 추가하여 callLoginAPI 실행 시 마다 CALL 하도록 설정
    $("#login-button").on("click",callLoginAPI);
})
