package com.ifmo.web.coursework;

import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CourseworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseworkApplication.class, args);
    }

    private static Logger log = LoggerFactory.getLogger(CourseworkApplication.class);

    @Bean
    public CommandLineRunner demo(HumanRepository humanRepository, PasswordEncoder encoder) {
        return args -> {
            if (args.length > 0 && "encode".equals(args[0])) {
                humanRepository.findAll().stream()
                        .peek(human -> human.setPassword(encoder.encode(human.getPassword())))
                        .forEach(humanRepository::save);
                log.info("All passwords encoded successfully!");
            }

            for (Human human : humanRepository.findAll()) {
                log.info(human.getHumanId().toString());
            }
            log.info("-------------------");

            Optional<Human> human = humanRepository.findByVkId("nursat.baigenzheeb");
            if (human.isPresent()) {
                log.info(human.get().getHumanId().toString());
            } else {
                log.info("No Nursat");
            }


            List<Human> humans = humanRepository.findAllByCountryId(1);
            for (Human hum : humans) {
                log.info(hum.getHumanId().toString());
            }
        };
    }


}
