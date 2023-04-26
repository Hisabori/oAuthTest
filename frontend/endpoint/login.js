//jquery 호출
$(document).ready(function () {

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
}