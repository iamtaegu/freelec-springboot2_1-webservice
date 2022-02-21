package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersApiController {

    private final UsersService usersService;

    @GetMapping("/api/v1/users/list")
    public List<User> findAll() {return usersService.findAllDesc(); }

}
