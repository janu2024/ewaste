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

		m.setViewName("productcategory");
		return m;

	}

	@GetMapping(value = "/getSubCategory")
	public ModelAndView getSubCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("subCategoryList", subCategoryRepo.findAll());// blank object
		m.setViewName("productsubcategory");// html page
		return m;

	}

	@GetMapping(value = "/createCategory")
	public ModelAndView createCategory(@ModelAttribute ProductCategory category) {
		ModelAndView m = new ModelAndView();
		m.setViewName("productcategory");// html page
		return m;
	}
	
	@PostMapping(value = "/createSubCategory")
	public ModelAndView createSubCategory() {
		ModelAndView m = new ModelAndView();
		m.setViewName("productsubcategory");// html page
		return m;
	}

	@PostMapping(value = "/saveCategory")
	public String saveCategory(@ModelAttribute ProductCategory category) {
		categoryRepo.save(category);
		return "redirect:/user/productcategory";
	}

	
	

	
	@PostMapping(value = "/saveSubCategory")
	public String saveSubCategory(@ModelAttribute ProductSubCategory subCategory) {
		subCategory.setProductCategory(categoryRepo.findById(subCategory.getProductCategory().getCid()).get());
		subCategoryRepo.save(subCategory);
		return "redirect:/user/productsubcategory";
	}
	

}
