package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("CustomOAuth2UserService loadUser start");

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("CustomOAuth2UserService loadUser registrationId=" + registrationId);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        System.out.println("CustomOAuth2UserService loadUser userNameAttributeName=" + userNameAttributeName);

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        System.out.println("@@@@@@@@@@@@@@ CustomOAuth2UserService User=" + user.toString());
        System.out.println("@@@@@@@@@@@@@@ CustomOAuth2UserService User=" + user.getName());
        System.out.println("@@@@@@@@@@@@@@ CustomOAuth2UserService User=" + user.getEmail());
        System.out.println("@@@@@@@@@@@@@@ CustomOAuth2UserService User=" + user.getPicture());

        SessionUser sessionUser = new SessionUser(user);
        System.out.println("@@@@@@@@@@@@@@ CustomOAuth2UserService sessionUser=" + sessionUser.getName());
        httpSession.setAttribute("user", sessionUser);
        // 여기가 이슈네.
        // 아래 print가 먼저 찍힘
        //httpSession.setAttribute("user", new SessionUser(user));

        System.out.println("CustomOAuth2UserService loadUser end");

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
