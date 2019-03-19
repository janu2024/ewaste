/**
 * 
 */
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

/**
 * @author jahnavi
 *
 */
@Controller
@RequestMapping(value = "/productselling")
public class ProductSellingController {

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

	@Autowired
	private SecurityService securityService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	SoldProductsRepository soldProductsRepo;

	public static String uploadDir = "E:\\WorkSpace2\\maven.1548166232495\\ewaste\\src\\main\\resources\\static\\images\\upload";

	@GetMapping(value = "/sellProduct")
	public ModelAndView getCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("categoryList", categoryRepo.findAll());// blank object

		m.setViewName("productselling2");
		return m;

	}

	@GetMapping(value = "/getSubCategory/{categoryId}")
	@ResponseBody
	public List<ProductSubCategory> getSubCategory(@PathVariable long categoryId) {
		List<ProductSubCategory> filteredList = new ArrayList<>();
		ProductSubCategory sc;
		List<ProductSubCategory> resultList = subCategoryRepo
				.findByProductCategory(categoryRepo.findById(categoryId).get());
		for (ProductSubCategory c : resultList) {
			sc = new ProductSubCategory();
			sc.setCategoryName(c.getCategoryName());
			sc.setPsid(c.getPsid());
			filteredList.add(sc);
		}

		return filteredList;

	}

	@GetMapping(value = "/getBrand/{subCategoryId}")
	@ResponseBody
	public List<ProductBrand> getBrand(@PathVariable long subCategoryId) {
		List<ProductBrand> resultList = brandRepo.findBySubCategory(subCategoryRepo.findById(subCategoryId).get());
		ProductBrand brandObj;
		List<ProductBrand> filteredList = new ArrayList<>();
		for (ProductBrand brand : resultList) {
			brandObj = new ProductBrand();
			brandObj.setBid(brand.getBid());
			brandObj.setBrandName(brand.getBrandName());
			filteredList.add(brandObj);
		}

		return filteredList;
	}

	@GetMapping(value = "/getModel/{brandId}")
	@ResponseBody
	public List<ProductModel> getModel(@PathVariable long brandId) {
		List<ProductModel> resultList = modelRepo.findByProductBrand(brandRepo.findById(brandId).get());
		List<ProductModel> filteredList = new ArrayList<>();

		ProductModel modelObj;
		for (ProductModel model : resultList) {
			modelObj = new ProductModel();
			modelObj.setModelName(model.getModelName());
			modelObj.setMid(model.getMid());
			filteredList.add(modelObj);
		}

		return filteredList;
	}

	@GetMapping(value = "/getPricing/{modelId}")
	@ResponseBody
	public List<ProductPricing> getProductPricings(@PathVariable long modelId) {
		List<ProductPricing> resultList = pricingRepo.findByProductModel(modelRepo.findById(modelId).get());
		List<ProductPricing> filteredList = new ArrayList<>();
		ProductPricing pricingObj;
		for (ProductPricing pricing : resultList) {
			pricingObj = new ProductPricing();
			pricingObj.setNumberOfYearsOld(pricing.getNumberOfYearsOld());
			pricingObj.setPid(pricing.getPid());
			pricingObj.setProductPrice(pricing.getProductPrice());
			pricingObj.setWorking(pricing.isWorking());
			filteredList.add(pricingObj);

		}

		return filteredList;
	}

	@PostMapping(value = "/sellProduct")
	public String saveModel(@ModelAttribute SoldProducts soldProducts,
			@RequestParam("productUploadImage") MultipartFile productImage,
			@RequestParam("sellindProductBillUploadImage") MultipartFile sellindProductBill) {

		soldProducts.setPricing(pricingRepo.findById(soldProducts.getPricing().getPid()).get());
		soldProducts.setUserInfo(securityService.getLoggedInUser());
		Path mpath = Paths.get(uploadDir, productImage.getOriginalFilename());
		Path mpath2 = Paths.get(uploadDir, sellindProductBill.getOriginalFilename());

		try {
			java.nio.file.Files.write(mpath, productImage.getBytes());
			java.nio.file.Files.write(mpath2, sellindProductBill.getBytes());
			soldProducts.setProductImage("/images/upload/" + productImage.getOriginalFilename());
			soldProducts.setProductBill("/images/upload/" + sellindProductBill.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		soldProductsRepo.save(soldProducts);
		return "redirect:/productselling/sellProduct";

	}

	@GetMapping(value = "/getSoldProducts")
	@ResponseBody
	public List<SoldProducts> getSoldProducts() {
		return soldProductsRepo.findAll();
	}

}
