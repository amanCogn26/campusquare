//package com.userservice.config;
//
//import com.userservice.entity.User;
//import com.userservice.enums.Role;
//import com.userservice.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class OfficerInit implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception{
//
//        createOfficer("AMAN","aman@admin.in", "1234567890","amanadmin",User.Role.ADMIN, "aman kumar");
//        createOfficer("compilance officer","complianceofficer@gmail.com", "0987654321","complianceofficer", );
//    }
//
//    private void createOfficer(String name,String email, String phone, String rawPassword, ) {
//        if (userRepository.()) {
//            return;
//        }
//
//        User officer = new User();
//        officer.setName(name);
//        officer.setCollegeEmail(email);
//        officer.setPhone(phone);
//        officer.setPassword(passwordEncoder.encode(rawPassword));
//        officer.setRole(Role.ADMIN);
//        officer.setUsername(username);
//        officer.setRole(Role.ADMIN);
//
//        userRepository.save(officer);
//        System.out.println("Created officer: " + email);
//    }
//}

package com.userservice.config;

import com.userservice.entity.User;
import com.userservice.enums.Role;
import com.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficerInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createOfficer(
                "AMAN",
                "aman@admin.in",
                "1234567890",
                "amanadmin",
                "aman kumar",
                Role.ADMIN,
                "XYZ"
        );

        createOfficer(
                "XYZ",
                "xyz@admin.in",
                "0987654321",
                "xyzadmin",
                "xyz",
                Role.ADMIN,
                "XYZ"
        );
    }

    private void createOfficer(
            String name,
            String email,
            String phone,
            String rawPassword,
            String username,
            Role role,
            String profileImage
    ) {

        // Check if user already exists
        if (userRepository.existsByCollegeEmail(email)) {
            return;
        }

        User officer = new User();
        officer.setName(name);
        officer.setCollegeEmail(email);
        officer.setPhone(phone);
        officer.setPassword(passwordEncoder.encode(rawPassword));
        officer.setUsername(username);
        officer.setRole(role);

        userRepository.save(officer);

        System.out.println("Created officer: " + email);
    }
}