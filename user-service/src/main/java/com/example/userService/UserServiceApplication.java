package com.example.userService;

import com.example.common.entity.User;
import com.example.common.enums.Active;
import com.example.common.enums.Role;
import com.example.common.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example.common"})
public class UserServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;

    public UserServiceApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run (String... args) {
		final String pass = "$2a$10$2529eBq3R6Y41t03Mku2I.2Nh3W0p25lt.s.85mG0kiAvxI4bsAHa";
		var admin = User.builder()
				.username("admin")
				.email("shikha1266@gmail.com")
				.password(pass)
				.role(Role.ADMIN)
				.active(Active.ACTIVE)
				.build();
		if (userRepository.findByUsername("admin").isEmpty()) {
			userRepository.save(admin);
		}

	}

}
