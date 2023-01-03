package ru.smelyi.SnowflakeShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.smelyi.SnowflakeShop.common.Images;
import ru.smelyi.SnowflakeShop.common.OrderStatus;
import ru.smelyi.SnowflakeShop.models.database.entities.*;
import ru.smelyi.SnowflakeShop.models.database.repositories.OrderRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	private final static int ADMIN_ROLE = 1;

	@GetMapping
	public String cabinet(HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}
		Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
		List<Order> orders = null;

		// orders наполняется в зависимости от того, текущий юзер администратор или нет
		for (RoleUser roleUser : optUser.get().getRoleUsers()) {
			if (roleUser.getRole().getId() == ADMIN_ROLE) {
				model.addAttribute("isAdmin", true);
				orders = orderRepository.findAll();
				break;
			}
		}
		if (orders == null) {
			model.addAttribute("isAdmin", false);
			orders = new ArrayList<>();
			orders.addAll(optUser.get().getOrders());
		}

		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.CREATED.getCode()) {
				order.setStatusDescription("Создан");
			} else if (order.getStatus() == OrderStatus.EXECUTING.getCode()) {
				order.setStatusDescription("Исполняется");
			} else if (order.getStatus() == OrderStatus.EXECUTED.getCode()) {
				order.setStatusDescription("Исполнен");
			} else if (order.getStatus() == OrderStatus.CANCELED.getCode()) {
				order.setStatusDescription("Отменён");
			}

			Double totalPrice = 0d;
			for (OrderPosition ordPos : order.getOrderPositions()) {
				totalPrice += ordPos.getPriceOne() * ordPos.getCount();
			}
			order.setTotalPrice(totalPrice);
			order.setUserFullName(optUser.get().getFullName());
		}
		optUser.ifPresent(user -> model.addAttribute("userFullName", user.getFullName()));
		optUser.ifPresent(user -> model.addAttribute("user", user));
		List<Order> finalOrders = orders;
		optUser.ifPresent(user -> model.addAttribute("orders", finalOrders));

		return "cabinet";
	}

	@PostMapping("/person_change")
	public String persChange(HttpSession session, Model model, String login, String password, String fullName, String email, String phone) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}

		Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
		User user = optUser.get();
		if (user.getLogin().equals(login) || !userRepository.existsByLogin(login)) {
			user.setLogin(login);
			if (password != null && !password.isEmpty()) {
				user.setPassword(password);
			}
			user.setFullName(fullName);
			user.setEmail(email);
			user.setPhone(phone);
			userRepository.save(user);
			model.addAttribute("userFullName", user.getFullName());
		}
		model.addAttribute("user", user);
		return "redirect:";
	}

	@GetMapping("/order/{id}")
	public String showOrder(@PathVariable int id, HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}
		Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
		optUser.ifPresent(user -> model.addAttribute("userFullName", user.getFullName()));
		boolean isAdmin = false;
		for (RoleUser roleUser : optUser.get().getRoleUsers()) {
			if (roleUser.getRole().getId() == ADMIN_ROLE) {
				isAdmin = true;
				break;
			}
		}

		Optional<Order> optOrder = orderRepository.findById(id);
		if (optOrder.isPresent() && (optOrder.get().getUser().equals(optUser.get()) || isAdmin)) {
			List<OrderPosition> orderPositions = new ArrayList<>();
			orderPositions.addAll(optOrder.get().getOrderPositions());

			Double totalPrice = 0d;
			List<Snowflake> snowflakes = new ArrayList<>();
			for (OrderPosition ordPos : orderPositions) {
				snowflakes.add(ordPos.getSnowflake());
				ordPos.setSnowflakeTitle(ordPos.getSnowflake().getTitle());
				ordPos.setSnowflakeId(ordPos.getSnowflake().getId());
				totalPrice += ordPos.getPriceOne() * ordPos.getCount();
			}
			new Images().prepareImageFiles(snowflakes);
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("orderPositions", orderPositions);
		}
		model.addAttribute("orderId", id);
		return "order";
	}
}