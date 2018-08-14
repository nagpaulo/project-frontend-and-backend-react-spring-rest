import axios from 'axios';
import { toastr } from 'react-redux-toastr';
import { reset as resetForm  } from 'redux-form';
import { APPLICATION } from '../../main/config/configServer';
import { sessionService } from 'redux-react-session';
import history from '../../main/config/history';

const URL = APPLICATION.URL_TOKEN_OAUTH;

export function authentication(values) {
    return dispatch => {
        let axiosConfig = {
            "async": true,
            "crossDomain": true,
            "method": "POST",
            "headers": {
                "Accept": "application/json",
                "Content-Type": "application/json",
                "Cache-Control": "no-cache",
            },
            "processData": false,
        };

        var body = {
            "email": values.usuario,
            "senha": values.senha
        }


        if(values.usuario && values.senha){
            axios.post(URL,body,axiosConfig)
                .then(resp => {
                    sessionService.saveSession(resp.data);
                    sessionService.saveUser(resp.data.data.token);
                    dispatch([
                        resetForm('loginForm')
                    ]);
                    history.push('/home');
                }).catch(function (error) {
                    sessionService.deleteSession();
                    sessionService.deleteUser();
                    if(error.response.status = 400){                       
                        toastr.error('Acesso negado',`Você deve estar autenticado no sistema para acessar.`) 
                    }else{
                        toastr.error('Error', error.message);
                    }
                });
        }else{
            if(!body.email) toastr.warning('Campo Obrigatório!','Usuario não informado.');
            if(!body.senha) toastr.warning('Campo Obrigatório!','Senha não informado.')
        }
    }
}

export function recuperarSenha(values){
    console.log(values);
    return {
        type: 'RECUPERAR_SENHA'
    }
}

export function logout(){
    sessionService.deleteSession();
    sessionService.deleteUser();    
    return {
        type: 'LOGOUT'
    }
}