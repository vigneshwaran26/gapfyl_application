package com.gapfyl;

import com.gapfyl.repository.UserRepository;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class GapfylApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(GapfylApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity existing = userRepository.findOneByEmail("gapfyl.official@gmail.com");
		if (existing == null) {
			UserEntity user = new UserEntity();
			user.setFirstName("Gapfyl");
			user.setLastName("Education");
			user.setEmail("gapfyl.official@gmail.com");
			user.setMobile("9944305905");
			user.setRoles(Arrays.asList(UserRole.ROLE_SUPER_ADMIN.name()));
			user.setPassword(passwordEncoder.encode("password"));
			userRepository.save(user);
		}
	}
}
