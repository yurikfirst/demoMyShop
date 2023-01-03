package ru.smelyi.SnowflakeShop.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.smelyi.SnowflakeShop.common.Images;
import ru.smelyi.SnowflakeShop.models.database.entities.Category;
import ru.smelyi.SnowflakeShop.models.database.entities.CategorySnowflake;
import ru.smelyi.SnowflakeShop.models.database.entities.Snowflake;
import ru.smelyi.SnowflakeShop.models.database.entities.User;
import ru.smelyi.SnowflakeShop.models.database.repositories.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private SnowflakeRepository snowflakeRepository;
	private static final int COUNT_FOR_AD = 6;

	@GetMapping()
	public String main(HttpSession session, String category, String search, Model model) {
		if (session.getAttribute("userId") != null) {
			userRepository.findById((int) session.getAttribute("userId")).ifPresent(user -> model.addAttribute("userFullName", user.getFullName()));
		}
		model.addAttribute("categories", categoryRepository.findAll());

		List<Snowflake> snowflakesForView = new ArrayList<>();

		if (search != null) {
			search = search.trim();
		}
		if (search != null && !search.isEmpty()) {
			snowflakesForView = snowflakeRepository.findAllByTitleContaining(search);
			model.addAttribute("categoryName", "фильтр:(" + search + ")");
		} else {
			if (category == null) {
				snowflakesForView = getAdSnowflakes();
			} else if (category.equals("0")) {
				model.addAttribute("categoryName", "Все");
				snowflakesForView = snowflakeRepository.findAll();
			} else {
				Optional<Category> optCategory = categoryRepository.findById(Integer.parseInt(category));
				if (optCategory.isPresent()) {
					model.addAttribute("categoryName", optCategory.get().getTitle());
					for (CategorySnowflake catSnow : optCategory.get().getCategorySnowflakes()) {
						snowflakesForView.add(catSnow.getSnowflake());
					}
				}
			}
		}


		if (session.getAttribute("userId") != null) {
			for (Snowflake snowflake : snowflakesForView) {
				User user = userRepository.findById((int) session.getAttribute("userId")).get();
				snowflake.setInCart(cartRepository.existsByUserAndSnowflake(user, snowflake));
			}
		}
		new Images().prepareImageFiles(snowflakesForView);
		model.addAttribute("snowflakes", snowflakesForView);
		return "main";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		if (session.getAttribute("userId") != null) {
			session.removeAttribute("userId");
		}
		return "redirect:/";
	}

	/**
	 * Получение рандомных снежинок для рекламы
	 *
	 * @return Список снежинок
	 */
	public @NotNull List<Snowflake> getAdSnowflakes() {
		// не забыть сделать рандомность
		long countSnowflakes = snowflakeRepository.count();
		int countAdSnowflakes = countSnowflakes < COUNT_FOR_AD ? (int) countSnowflakes : COUNT_FOR_AD;
		List<Snowflake> snowflakes = new ArrayList<>();

		for (int i = 1; i <= countAdSnowflakes; i++) {
			Optional<Snowflake> optSnowflake = snowflakeRepository.findById(i);
			if (optSnowflake.isPresent()) {
				snowflakes.add(optSnowflake.get());
			}
		}

		return snowflakes;
	}
}