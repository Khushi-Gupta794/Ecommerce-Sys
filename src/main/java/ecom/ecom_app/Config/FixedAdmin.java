package ecom.ecom_app.Config;

import ecom.ecom_app.Entity.User;
import ecom.ecom_app.Repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FixedAdmin {


    @Bean
    CommandLineRunner initAdmin(UserRepo userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByEmail("admin@ecom.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@ecom.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepo.save(admin);
            }
        };
    }

}
