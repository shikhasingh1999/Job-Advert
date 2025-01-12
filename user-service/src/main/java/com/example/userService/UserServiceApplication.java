package com.example.userService;

import com.example.common.entity.User;
import com.example.common.enums.Active;
import com.example.common.enums.Role;
import com.example.common.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.example.common", "com.example.userService"})
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

//	the method signature public void run(String... args) is typically used as
//	part of the CommandLineRunner or ApplicationRunner interfaces. These interfaces
//	are provided by Spring Boot to allow you to execute code after the Spring
//	application context is fully initialized.

//	the run method is the main method that gets executed after the application has started

//	The args parameter contains any command-line arguments passed to the application.

//  Uses --->
//	Initialization Logic: It's commonly used for running startup logic
//	(e.g., loading data into a database, performing calculations, or running
//	initial setups) right after the application context is initialized.
//	Command-Line Arguments: The args array lets you access any command-line
//	arguments passed to the application when starting it. This is useful for
//	applications where arguments influence behavior, such as batch jobs or
//	other tools that need to be configured via the command line.
// Application startup tasks: Running tasks like checking system health,
// logging initial messages, or performing an initial setup (like loading
// configuration data from files).
}
