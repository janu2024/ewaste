package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/category")
public class ProductCategoryController {

	@Autowired
	ProductCategoryRepository categoryRepo;

	@Autowired
	ProductSubCategoryRepository subCategoryRepo;

	@GetMapping(value = "/getCategory")
	public ModelAndView getCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("categoryList", categoryRepo.findAll());// blank object

		m.setViewName("index");
		return m;

	}

	@GetMapping(value = "/getSubCategory")
	public ModelAndView getSubCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("subCategoryList", subCategoryRepo.findAll());// blank object
		m.setViewName("index");// html page
		return m;

	}

	@GetMapping(value = "/createCategory")
	public String createCategory(@ModelAttribute ProductCategory category) {
		categoryRepo.save(category);
		return "redirect:/user/index";
	}

	@PostMapping(value = "/saveCategory")
	public String saveCategory(@ModelAttribute ProductCategory category) {
		categoryRepo.save(category);
		return "redirect:/user/index";
	}

	@PostMapping(value = "/createSubCategory")
	public String createSubCategory() {
		return "redirect:/user/index";
	}
	

	
	@PostMapping(value = "/saveSubCategory")
	public String saveSubCategory(@ModelAttribute ProductSubCategory subCategory) {
		subCategory.setProductCategory(categoryRepo.findById(subCategory.getProductCategory().getCid()).get());
		subCategoryRepo.save(subCategory);
		return "redirect:/user/index";
	}
	

}
