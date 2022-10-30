package com.jojoldu.book.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.env.MockEnvironment;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile이_조회된다() throws Exception {
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();

        //then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
     }
}
