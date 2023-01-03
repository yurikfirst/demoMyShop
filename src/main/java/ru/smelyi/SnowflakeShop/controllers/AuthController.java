package ru.smelyi.SnowflakeShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.smelyi.SnowflakeShop.models.database.entities.RoleUser;
import ru.smelyi.SnowflakeShop.models.database.entities.User;
import ru.smelyi.SnowflakeShop.models.database.repositories.RoleRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.RoleUserRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.time.Instant;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleUserRepository roleUserRepository;
	private final static int USER_ROLE = 2;

	@GetMapping
	public String main(Model model) {
		return "auth";
	}

	@PostMapping
	@ResponseBody
	public String auth(HttpSession session, String action, String login, String password) {
		switch (action) {
			case "enter":
				return enter(session, login, password);
			case "register":
				return register(session, login, password);
			default:
				return "err";
		}
	}

	/**
	 * Выполнение входа пользователя
	 */
	private String enter(HttpSession session, String login, String password) {
		if (userRepository.existsByLoginAndPassword(login, password)) {
			User user = userRepository.findByLogin(login);
			session.setAttribute("userId", user.getId());
			user.setLastLoginDate(Instant.now());
			userRepository.save(user);
			return "ok";
		}
		return "err";
	}

	/**
	 * Выполнение регистрации пользователя
	 */
	private String register(HttpSession session, String login, String password) {
		User user = new User(login, password, login, null, null);
		if (userRepository.existsByLogin(login)) {
			return "err";
		} else {
			userRepository.save(user);

			RoleUser roleUser = new RoleUser();
			roleUser.setUser(user);
			roleUser.setRole(roleRepository.findById(USER_ROLE).get());
			roleUserRepository.save(roleUser);
			return "ok";
		}
	}
}