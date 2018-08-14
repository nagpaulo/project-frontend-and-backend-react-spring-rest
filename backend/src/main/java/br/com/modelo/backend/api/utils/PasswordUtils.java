package br.com.modelo.backend.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordUtils {
    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils(){}

    /**
     * Gerar um hash utilizando o BCrypt
     * @param senha
     * @return String
     * */
    public static String gerarBCrypt(String senha){
        if(senha == null){
            return senha;
        }

        log.info("Gerando hash com o BCript");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }

}
