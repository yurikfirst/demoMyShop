package ru.smelyi.SnowflakeShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.smelyi.SnowflakeShop.common.Images;
import ru.smelyi.SnowflakeShop.models.database.entities.*;
import ru.smelyi.SnowflakeShop.models.database.repositories.CategoryRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.CategorySnowflakeRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.SnowflakeRepository;
import ru.smelyi.SnowflakeShop.models.database.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/snowflake")
public class SnowflakeController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SnowflakeRepository snowflakeRepository;
	@Autowired
	private CategorySnowflakeRepository categorySnowflakeRepository;
	private final static int ADMIN_ROLE = 1;

	@GetMapping("/{id}")
	public String card(@PathVariable int id, HttpSession session, Model model) {
		if (session.getAttribute("userId") != null) {
			Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
			model.addAttribute("userFullName", optUser.get().getFullName());

			for (RoleUser roleUser : optUser.get().getRoleUsers()) {
				if (roleUser.getRole().getId() == ADMIN_ROLE) {
					model.addAttribute("isAdmin", true);
					break;
				}
			}
		}

		Optional<Snowflake> optSnowflake = snowflakeRepository.findById(id);
		if (optSnowflake.isPresent()) {
			List<Category> snowflakeCategories = new ArrayList<>();
			Snowflake snowflake = optSnowflake.get();
			for (CategorySnowflake categorySnowflake : snowflake.getCategorySnowflakes()) {
				snowflakeCategories.add(categorySnowflake.getCategory());
			}
			snowflake.setCategories(snowflakeCategories);
			model.addAttribute("snowflake", snowflake);

			new Images().prepareImageFiles(new ArrayList<>() {
				{
					optSnowflake.get();
				}
			});

			List<Category> allCategories = categoryRepository.findAll();
			for (Category item : allCategories) {
				if (snowflake.getCategories().contains(item)) {
					item.setChecked(true);
				}
			}
			model.addAttribute("allCategories", allCategories);
		}

		return "snowflake";
	}

	@PostMapping("/change")
	public String change(int id, String title, Double price, String description, int count, @RequestParam(value = "categoryId[]") int[] categoryId, HttpSession session, Model model) {
		boolean isAdmin = false;
		if (session.getAttribute("userId") != null) {
			Optional<User> optUser = userRepository.findById((int) session.getAttribute("userId"));
			model.addAttribute("userFullName", optUser.get().getFullName());

			for (RoleUser roleUser : optUser.get().getRoleUsers()) {
				if (roleUser.getRole().getId() == ADMIN_ROLE) {
					isAdmin = true;
					break;
				}
			}
		}
		if (!isAdmin) {
			return "redirect:/auth";
		}

		Optional<Snowflake> optSnowflake = snowflakeRepository.findById(id);
		if (optSnowflake.isPresent()) {
			Snowflake snowflake = optSnowflake.get();
			snowflake.setTitle(title);
			snowflake.setPrice(price);
			snowflake.setDescription(description);
			snowflake.setCount(count);
			snowflakeRepository.save(snowflake);
		}

		categorySnowflakeRepository.deleteAllInBatch(categorySnowflakeRepository.findAllBySnowflake(optSnowflake.get()));
		List<CategorySnowflake> catSnows = new ArrayList<>();
		for (int catId : categoryId) {
			CategorySnowflake categorySnowflake = new CategorySnowflake();
			categorySnowflake.setSnowflake(optSnowflake.get());
			categorySnowflake.setCategory(categoryRepository.findById(catId).get());
			catSnows.add(categorySnowflake);
		}
		categorySnowflakeRepository.saveAll(catSnows);

		return "redirect:" + id;
	}
}