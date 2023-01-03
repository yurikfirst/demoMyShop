package ru.smelyi.SnowflakeShop.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.smelyi.SnowflakeShop.common.OrderStatus;
import ru.smelyi.SnowflakeShop.models.database.entities.*;
import ru.smelyi.SnowflakeShop.models.database.repositories.*;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SnowflakeRepository snowflakeRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderPositionRepository orderPositionRepository;

	@GetMapping
	public String main(HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}

		Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
		optUser.ifPresent(user -> model.addAttribute("userFullName", user.getFullName()));

		List<Cart> cartGoods = cartRepository.findAllByUser(optUser.get());
		List<Snowflake> snowflakes = new ArrayList<>();
		int totalPrice = 0;
		for (Cart cartGood : cartGoods) {
			totalPrice += cartGood.getSnowflake().getPrice() * cartGood.getCount();
			snowflakes.add(cartGood.getSnowflake());
			snowflakes.get(snowflakes.size() - 1).setCountInCart(cartGood.getCount());
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("snowflakes", snowflakes);
		return "cart";
	}

	@PostMapping
	@ResponseBody
	public String actionToCart(HttpSession session, int snowflakeId) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}

		try {
			Optional<Snowflake> a = snowflakeRepository.findById(snowflakeId);
			if (a.isPresent()) {
				if (a.get().getCount() > 0) {
					User user = userRepository.findById((int) session.getAttribute("userId")).get();
					Cart cart = new Cart(user, a.get(), 1);
					boolean isInCart = false;
					if (!cartRepository.existsByUserAndSnowflake(user, a.get())) {
						cartRepository.save(cart);
						isInCart = true;
					} else {
						cartRepository.delete(cartRepository.findByUserAndSnowflake(user, a.get()));
					}
					return "{\"id\":\"" + snowflakeId + "\",\n\"isInCart\":\"" + isInCart + "\"}";
				}
				return "Такой снежинки нет в наличии";
			}
			return "Снежинка не найдена";
		} catch (Exception ex) {
			return ex.getMessage();
//			return "Произошла ошибка";
		}
	}

	@PostMapping("/change")
	@ResponseBody
	public String cartChange(HttpSession session, int snowflakeId, int count) {
		if (count > 100) {
			count = 100;
		}
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}

		try {
			User user = userRepository.findById((int) session.getAttribute("userId")).get();
			Snowflake snowflake = snowflakeRepository.findById(snowflakeId).get();
			Cart cart = cartRepository.findByUserAndSnowflake(user, snowflake);
			cart.setCount(count);
			cartRepository.save(cart);

			List<Cart> carts = cartRepository.findAllByUser(user);
			int totalPrice = 0;
			for (Cart item : carts) {
				totalPrice += item.getSnowflake().getPrice() * item.getCount();
			}

			return "{\"id\":\"" + snowflakeId + "\"," +
					"\n\"totalPriceOfChanged\":\"" + (snowflake.getPrice() * count) + "\"," +
					"\n\"count\":\"" + count + "\"," +
					"\n\"totalPrice\":\"" + totalPrice + "\"}";
		} catch (Exception ex) {
			return "Произошла ошибка";
		}
	}

	@GetMapping("/clear")
	public String clear(HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}
		try {
			User user = userRepository.findById((int) session.getAttribute("userId")).get();
			cartRepository.deleteAllInBatch(cartRepository.findAllByUser(user));
		} catch (Exception ex) {
			return "redirect:/cart";
		}

		return "redirect:/";
	}

	@GetMapping("/createOrder")
	public String createOrder(@NotNull HttpSession session, Model model) {
		if (session.getAttribute("userId") == null) {
			return "redirect:auth";
		}

		Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
		optUser.ifPresent(user -> model.addAttribute("userFullName", user.getFullName()));

		List<Cart> cartGoods = cartRepository.findAllByUser(optUser.get());
		List<Snowflake> snowflakes = new ArrayList<>();
		for (Cart cartGood : cartGoods) {
			snowflakes.add(cartGood.getSnowflake());
			snowflakes.get(snowflakes.size() - 1).setCountInCart(cartGood.getCount());
		}
		addOrder(optUser.get(), snowflakes);
		cartRepository.deleteAllInBatch(cartRepository.findAllByUser(optUser.get()));

		// уменьшение количества на складе
		for (Snowflake snowflake : snowflakes) {
			snowflake.setCount(snowflake.getCount() - snowflake.getCountInCart());
			snowflakeRepository.save(snowflake);
		}

		return "redirect:/cabinet";
	}

	/**
	 * Добавляет новый заказ в БД, используя содержимое корзины
	 */
	public void addOrder(User user, List<Snowflake> snowflakes) {
		Order order = new Order();
		order.setUser(user);
		order.setCreateDate(Instant.now());
		order.setStatus(OrderStatus.CREATED.getCode());
		orderRepository.save(order);

		List<OrderPosition> orderPositions = new ArrayList<>();
		for (Snowflake snowflake : snowflakes) {
			if (snowflake.getCountInCart() > 0) {
				OrderPosition orderPos = new OrderPosition();
				orderPos.setOrder(order);
				orderPos.setCount(snowflake.getCountInCart());
				orderPos.setPriceOne(snowflake.getPrice());
				orderPos.setSnowflake(snowflake);
				orderPositions.add(orderPos);
			}
		}
		orderPositionRepository.saveAll(orderPositions);
	}
}