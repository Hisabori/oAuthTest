//JQuery
$(document).ready(function(){

    //import
    import React, {useState} from 'react';
    import { useHistory } from 'react-router-dom';

    function OauthButton(){
        const [isLoading, setIsLoading] = useState
        const history = useHistory();

        const handOauthButtonClick =  async() => {
            setIsLoading(true);

            try{

                //oauth 인증을 위한 variable settings
                const params =  new URLSearchParams({
                    response_type: 'code',
                    client_id:'<CLIENT_ID>',
                    redirect_uri: '<REDIRECT_URI>',
                    scope:'<SCOPE>'
                })

                //인증 페이지 (Oauth) 로 이동한다.
                

            }

        }


    }




})

