package com.isaac.login_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Override
	public boolean passwordsMatch(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Las contraseñas no coinciden");
		}
		return true;
	}

	@Override
	public boolean isEmailNotInUse(User user) throws Exception {
		Optional<User> userByEmail = repo.findByEmail(user.getEmail());
		if (userByEmail.isPresent()) {
			throw new Exception("El email proporcionado ya está en uso");
		}
		return true;
	}

	@Override
	@Transactional
	public void createUser(User user) throws Exception {
		if (isEmailNotInUse(user) && passwordsMatch(user)) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
			String encryptedPassword = encoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			user.setConfirmPassword(encryptedPassword);
			repo.save(user);
		}
	}
}
