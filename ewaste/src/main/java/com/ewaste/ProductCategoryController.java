package com.ewaste;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@GetMapping(value = "/ProductName")
	public ModelAndView ProductName() {

		ModelAndView m = new ModelAndView();
		// m.addObject("productnameList", subCategoryRepo.findAll());// blank object
		m.setViewName("productname");// html page
		return m;

	}

	@GetMapping(value = "/ProductPrice")
	public ModelAndView ProductPrice() {

		ModelAndView m = new ModelAndView();
		m.addObject("productCategory", categoryRepo.findAll());// blank object
		m.setViewName("productprice");// html page
		return m;

	}

	@GetMapping(value = "/getSubCategoryFromCategory/{categoryId}")
	@ResponseBody
	public List<ProductSubCategory> getSubCategoryFromCategory(@PathVariable(name = "categoryId") Long categoryId) {
		List<ProductSubCategory> resultList = categoryRepo.findById(categoryId).get().getSubCategory();
		List<ProductSubCategory> filteredList = new ArrayList<>();
		ProductSubCategory sc;

		for (ProductSubCategory c : resultList) {
			sc = new ProductSubCategory();
			sc.setCategoryName(c.getCategoryName());
			sc.setPsid(c.getPsid());
			filteredList.add(sc);
		}

		return filteredList;
	}

	@GetMapping(value = "/manageCategory")
	public ModelAndView createCategory() {
		ModelAndView m = new ModelAndView();
		m.addObject("category", new ProductCategory());
		m.setViewName("manageCategory");// html page
		return m;
	}

	@GetMapping(value = "/manageCategory/{categoryId}")
	public ModelAndView editCategory(@PathVariable long categoryId) {
		ModelAndView m = new ModelAndView();
		m.addObject("category", categoryRepo.findById(categoryId).get());
		m.setViewName("manageCategory");// html page
		return m;
	}

	@GetMapping(value = "/getSubCategory")
	public ModelAndView getSubCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("subCategoryList", subCategoryRepo.findAll());// blank object
		m.setViewName("productsubcategory");// html page
		return m;

	}

	@GetMapping(value = "/manageSubCategory")
	public ModelAndView createSubCategory() {
		ModelAndView m = new ModelAndView();
		m.addObject("subCategory", new ProductSubCategory());
		m.addObject("categoryList", categoryRepo.findAll());

		m.setViewName("manageSubCategory");// html page
		return m;
	}

	@PostMapping(value = "/saveCategory")
	public String saveCategory(@ModelAttribute ProductCategory category) {
		categoryRepo.save(category);
		return "redirect:/category/getCategory";
	}

	@PostMapping(value = "/saveSubCategory")
	public String saveSubCategory(@ModelAttribute ProductSubCategory subCategory) {
		subCategory.setProductCategory(categoryRepo.findById(subCategory.getProductCategory().getCid()).get());
		subCategoryRepo.save(subCategory);
		return "redirect:/category/getSubCategory";
	}

}
