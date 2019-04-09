package com.ewaste;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/category")
public class ProductCategoryController {

	String uploadPath = "C:\\Users\\admin\\Documents\\workspace-sts-3.9.5.RELEASE\\maven.1544709332374\\ewaste\\src\\main\\resources\\static\\images\\upload";
	@Autowired
	ProductCategoryRepository categoryRepo;

	@Autowired
	ProductSubCategoryRepository subCategoryRepo;

	@Autowired
	ProductBrandRepository brandRepo;

	@Autowired
	ProductModelRepository modelRepo;

	@Autowired
	ProductPricingRepository pricingRepo;

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
	public String saveCategory(@ModelAttribute ProductCategory category,
			@RequestParam("productUploadImage") MultipartFile productImage) {
		Path mpath = Paths.get(uploadPath, productImage.getOriginalFilename());
		try {
			java.nio.file.Files.write(mpath, productImage.getBytes());
			category.setProductImage("/images/upload/" + productImage.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		categoryRepo.save(category);
		return "redirect:/admin/category/getCategory";
	}

	@PostMapping(value = "/saveSubCategory")
	public String saveSubCategory(@ModelAttribute ProductSubCategory subCategory,
			@RequestParam("productUploadImage") MultipartFile productImage) {
		subCategory.setProductCategory(categoryRepo.findById(subCategory.getProductCategory().getCid()).get());

		Path mpath = Paths.get(uploadPath, productImage.getOriginalFilename());
		try {
			java.nio.file.Files.write(mpath, productImage.getBytes());
			subCategory.setProductImage("/images/upload/" + productImage.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		subCategoryRepo.save(subCategory);
		return "redirect:/admin/category/getSubCategory";
	}

	@GetMapping(value = "/getBrand")
	public ModelAndView getBranad() {

		ModelAndView m = new ModelAndView();
		m.addObject("brandList", brandRepo.findAll());// blank object
		m.setViewName("productBrand");// html page
		return m;

	}

	@PostMapping(value = "/saveBrand")
	public String saveBrand(@ModelAttribute ProductBrand brand,
			@RequestParam("productUploadImage") MultipartFile productImage) {
		brand.setSubCategory(subCategoryRepo.findById(brand.getSubCategory().getPsid()).get());

		Path mpath = Paths.get(uploadPath, productImage.getOriginalFilename());
		try {
			java.nio.file.Files.write(mpath, productImage.getBytes());
			brand.setProductImage("/images/upload/" + productImage.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		brandRepo.save(brand);
		return "redirect:/admin/category/getBrand";
	}

	@GetMapping(value = "/manageBrand")
	public ModelAndView manageBrand() {
		ModelAndView m = new ModelAndView();
		m.addObject("brand", new ProductBrand());
		m.addObject("subCategoryList", subCategoryRepo.findAll());
		m.setViewName("manageBrand");// html page
		return m;
	}

	@GetMapping(value = "/manageBrand/{brandId}")
	public ModelAndView editBrand(@PathVariable long brandId) {
		ModelAndView m = new ModelAndView();
		m.addObject("brand", brandRepo.findById(brandId).get());
		m.addObject("subCategoryList", subCategoryRepo.findAll());
		m.setViewName("manageBrand");// html page
		return m;
	}

	@GetMapping(value = "/getModel")
	public ModelAndView getModel() {

		ModelAndView m = new ModelAndView();
		m.addObject("modelList", modelRepo.findAll());// blank object
		m.setViewName("productModel");// html page
		return m;

	}

	@GetMapping(value = "/manageModel") 
	public ModelAndView manageModel() {
		ModelAndView m = new ModelAndView();
		m.addObject("model", new ProductModel());
		m.addObject("brandList", brandRepo.findAll());
		m.setViewName("manageModel");// html page
		return m;
	}

	@GetMapping(value = "/manageModel/{modelId}")
	public ModelAndView editModel(@PathVariable long modelId) {
		ModelAndView m = new ModelAndView();
		m.addObject("model", modelRepo.findById(modelId).get());
		m.addObject("brandList", brandRepo.findAll());
		m.setViewName("manageModel");// html page
		return m;
	}

	@PostMapping(value = "/saveModel")
	public String saveModel(@ModelAttribute ProductModel model,
			@RequestParam("productUploadImage") MultipartFile productImage) {
		Path mpath = Paths.get(uploadPath, productImage.getOriginalFilename());
		try {
			java.nio.file.Files.write(mpath, productImage.getBytes());
			model.setProductImage("/images/upload/" + productImage.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		model.setProductBrand(brandRepo.findById(model.getProductBrand().getBid()).get());
		modelRepo.save(model);
		return "redirect:/admin/category/getModel";
	}

	@GetMapping(value = "/getPricing")
	public ModelAndView getPricing() {

		ModelAndView m = new ModelAndView();
		m.addObject("pricingList", pricingRepo.findAll());// blank object
		m.setViewName("productPricing");// html page
		return m;

	}

	@GetMapping(value = "/managePricing")
	public ModelAndView managePricing() {
		ModelAndView m = new ModelAndView();
		m.addObject("productPricing", new ProductPricing());
		m.addObject("modelList", modelRepo.findAll());
		m.setViewName("managePricing");// html page
		return m;
	}

	@PostMapping(value = "/savPricing")
	public String saveModel(@ModelAttribute ProductPricing pricing) {
		pricing.setProductModel(modelRepo.findById(pricing.getProductModel().getMid()).get());
		pricingRepo.save(pricing);
		return "redirect:/admin/category/getPricing";
	}

	@GetMapping(value = "/managePricing/{pricingId}")
	public ModelAndView editPricing(@PathVariable long pricingId) {
		ModelAndView m = new ModelAndView();
		m.addObject("productPricing", pricingRepo.findById(pricingId).get());
		m.addObject("modelList", modelRepo.findAll());
		m.setViewName("managePricing");// html page
		return m;
	}

	@GetMapping(value = "/manageSubCategory/{subCatId}")
	public ModelAndView editSubCategory(@PathVariable long subCatId) {
		ModelAndView m = new ModelAndView();
		m.addObject("subCategory", subCategoryRepo.findById(subCatId).get());
		m.addObject("categoryList", categoryRepo.findAll());

		m.setViewName("manageSubCategory");// html page
		return m;
	}

}
