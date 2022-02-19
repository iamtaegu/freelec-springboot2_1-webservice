package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllDesc() {
        return userRepository.findAll().stream()
                .collect(Collectors.toList());
    }

}
