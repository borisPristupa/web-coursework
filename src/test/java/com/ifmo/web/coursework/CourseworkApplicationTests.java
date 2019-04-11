package com.ifmo.web.coursework;

import com.ifmo.web.coursework.data.repository.HumanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseworkApplicationTests {

    @Autowired
    HumanRepository humanRepository;
    @Autowired
    PasswordEncoder encoder;

    @Test
    public void contextLoads() {
        humanRepository.findAll().stream()
              .peek(human -> human.setPassword(encoder.encode(human.getPassword())))
              .forEach(humanRepository::save);
    }

}
