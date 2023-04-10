//Jquery 사용 (USE Jquery)
$(document).ready(function() {
    //API KEY
    let key = "6addc77876daf887b6419b3889757dbd"

    //test
    console.log(key)

    async function getData() {
        try {

            //ajax 요청 진행 후 결과값 대기
            let awaitResp = await $.ajax({
                url: "/data",
                method: "GET",


                //성공 시
                success: function (data) {
                    console.log(data)
                }
            });

            //if success
            console.log(awaitResp);

            //if error
            //오류 발생 시
        } catch (err) {
            console.error(err)
        }
    }

    getData();
});
