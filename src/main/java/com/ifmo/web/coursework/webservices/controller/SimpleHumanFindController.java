package com.ifmo.web.coursework.webservices.controller;

import com.ifmo.web.coursework.entity.Human;
import com.ifmo.web.coursework.repository.HumanRepository;
import com.ifmo.web.coursework.webservices.exception.HumanNotFoundException;
import com.ifmo.web.coursework.webservices.exception.PageNotFoundException;
import com.ifmo.web.coursework.webservices.response.HumanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleHumanFindController {

    @Autowired
    private HumanRepository humanRepository;

    @GetMapping("/find")
    public HumanResponse greeting(@RequestParam(value = "login", defaultValue = "boris3") String login) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName() + " logged");
        return HumanResponse.fromHuman(humanRepository.findByLogin(login)
                .orElseThrow(() -> new HumanNotFoundException(login)));
    }

    @GetMapping
    public Human wrongWay(HttpServletRequest request) {
        throw new PageNotFoundException(request.getServletPath());
    }
}