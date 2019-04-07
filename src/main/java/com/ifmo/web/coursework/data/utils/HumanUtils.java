package com.ifmo.web.coursework.data.utils;

import com.ifmo.web.coursework.data.entity.Human;
import com.ifmo.web.coursework.data.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HumanUtils {
    private final HumanRepository humanRepository;

    public Human getCurrentHuman() {
        return humanRepository.findByLogin(getCurrentLogin())
                .orElseThrow(() ->
                        new IllegalStateException("No user " + getCurrentLogin() + " found in DB, but present in SecurityContext"));
    }

    public String getCurrentLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public int getCurrentId() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(o -> o.getAuthority().startsWith("ROLE_USER_"))
                .findFirst().orElseThrow(() -> new IllegalStateException("User " + getCurrentLogin() +
                        " does not have authority ROLE_USER_%ID%")).getAuthority();
        return Integer.valueOf(role.split("_")[2]);
    }

    @Autowired
    public HumanUtils(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }
}
