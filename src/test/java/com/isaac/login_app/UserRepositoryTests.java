package com.isaac.login_app;

import static org.assertj.core.api.Assertions.assertThat;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepo;

    @Test
    public void createUserTest() {
        User user = new User();
        user.setFirstName("Rebeca");
        user.setLastName("Salazar");
        user.setEmail("rebecaS@gmailcom");
        user.setPassword("rebeSal21");

        User userSaved = userRepo.save(user);
        User existUser = entityManager.find(User.class, userSaved.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(existUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(existUser.getPassword()).isEqualTo(user.getPassword());
    }

}
