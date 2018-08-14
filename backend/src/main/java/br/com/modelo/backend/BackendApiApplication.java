package br.com.modelo.backend;

import br.com.modelo.backend.api.enums.PerfilEnum;
import br.com.modelo.backend.api.security.entities.Usuario;
import br.com.modelo.backend.api.security.repositories.UsuarioRepository;
import br.com.modelo.backend.api.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BackendApiApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApiApplication.class, args);
	}

	//@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			Usuario usuario = new Usuario();
			usuario.setEmail("usuario@email.com");
			usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
			usuario.setSenha(PasswordUtils.gerarBCrypt("123456"));
			this.usuarioRepository.save(usuario);

			Usuario admin = new Usuario();
			admin.setEmail("admin@email.com");
			admin.setPerfil(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(PasswordUtils.gerarBCrypt("123456"));
			this.usuarioRepository.save(admin);

		};
	}
}
