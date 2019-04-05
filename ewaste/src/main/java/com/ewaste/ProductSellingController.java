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

	@Autowired
	private SmtpMailSender smtpMailSender;

	public static String uploadDir = "D:\\ewaste\\maven.1540996285866\\ewaste\\src\\main\\resources\\static\\images\\upload";

	@GetMapping(value = "/sellByCategory")
	public ModelAndView sellByCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("categoryList", categoryRepo.findAll());// blank object

		m.setViewName("sellByCategory");
		return m;

	}

	@GetMapping(value = "/sellProduct")
	public ModelAndView getCategory() {

		ModelAndView m = new ModelAndView();
		m.addObject("categoryList", categoryRepo.findAll());// blank object

		m.setViewName("productSellingCategory");
		return m;

	}

	@GetMapping(value = "/getSubCategory/{categoryId}")
	public ModelAndView getSubCategory(@PathVariable long categoryId) {
		ModelAndView m = new ModelAndView();

		List<ProductSubCategory> filteredList = new ArrayList<>();
		ProductSubCategory sc;
		List<ProductSubCategory> resultList = subCategoryRepo
				.findByProductCategory(categoryRepo.findById(categoryId).get());
		for (ProductSubCategory c : resultList) {
			sc = new ProductSubCategory();
			sc.setCategoryName(c.getCategoryName());
			sc.setPsid(c.getPsid());
			sc.setProductImage(c.getProductImage());
			filteredList.add(sc);
		}
		m.setViewName("productSellingSubCategory");
		m.addObject("subCategoryList", filteredList);// blank object

		return m;

	}

	@GetMapping(value = "/getBrand/{subCategoryId}")
	public ModelAndView getBrand(@PathVariable long subCategoryId) {
		ModelAndView m = new ModelAndView();

		List<ProductBrand> resultList = brandRepo.findBySubCategory(subCategoryRepo.findById(subCategoryId).get());
		ProductBrand brandObj;
		List<ProductBrand> filteredList = new ArrayList<>();
		for (ProductBrand brand : resultList) {
			brandObj = new ProductBrand();
			brandObj.setBid(brand.getBid());
			brandObj.setBrandName(brand.getBrandName());
			brandObj.setProductImage(brand.getProductImage());
			filteredList.add(brandObj);
		}
		m.setViewName("productSellingBrand");
		m.addObject("brandList", filteredList);// blank object

		return m;
	}

	@GetMapping(value = "/getModel/{brandId}")
	public ModelAndView getModel(@PathVariable long brandId) {
		List<ProductModel> resultList = modelRepo.findByProductBrand(brandRepo.findById(brandId).get());
		List<ProductModel> filteredList = new ArrayList<>();
		ModelAndView m = new ModelAndView();

		ProductModel modelObj;
		for (ProductModel model : resultList) {
			modelObj = new ProductModel();
			modelObj.setModelName(model.getModelName());
			modelObj.setMid(model.getMid());
			modelObj.setProductImage(model.getProductImage());

			filteredList.add(modelObj);
		}
		m.setViewName("productSellingModel");
		m.addObject("modelList", filteredList);// blank object

		return m;
	}

	@GetMapping(value = "/getSellingPrice/{modelId}")
	public ModelAndView getSellingPrice(@PathVariable long modelId) {
		ProductModel modelInfo = modelRepo.findById(modelId).get();

		List<ProductPricing> resultList = pricingRepo.findByProductModel(modelRepo.findById(modelId).get());
		List<ProductPricing> filteredList = new ArrayList<>();
		ProductPricing pricingObj;
		ModelAndView m = new ModelAndView();

		Long produdctPricing = 0l;

		for (ProductPricing pricing : resultList) {
			pricingObj = new ProductPricing();
			pricingObj.setNumberOfYearsOld(pricing.getNumberOfYearsOld());
			pricingObj.setPid(pricing.getPid());
			pricingObj.setProductPrice(pricing.getProductPrice());
			if (pricing.getProductPrice() > produdctPricing) {
				produdctPricing = pricing.getProductPrice();
			}

			pricingObj.setWorking(pricing.isWorking());
			filteredList.add(pricingObj);

		}
		m.setViewName("productSellingPrice");
		m.addObject("productPricing", filteredList);// blank object
		m.addObject("modelInfo", modelInfo);
		m.addObject("maxPricing", produdctPricing);
		return m;
	}

	@GetMapping(value = "/getSellingProductDetails/{modelId}")
	@ResponseBody
	public List<ProductPricing> getSellingProductDetails(@PathVariable long modelId) {

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
	public String saveModel(@ModelAttribute SoldProducts soldProducts) {

		soldProducts.setPricing(pricingRepo.findById(soldProducts.getPricing().getPid()).get());
		soldProducts.setUserInfo(securityService.getLoggedInUser());
		soldProductsRepo.save(soldProducts);
		return "redirect:/productselling/sellProduct";

	}

	@GetMapping(value = "/getAssignedProducts")
	public ModelAndView getAssignedProducts() {
		UserInfo userInfo = securityService.getLoggedInUser();

		if (!userInfo.getRole().equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("myOrders", soldProductsRepo.findByTransporterInfo(userInfo));//
		m.setViewName("assignedProducts");// html page
		return m;

	}

	@GetMapping(value = "/getMyProducts")
	public ModelAndView getMyProducts() {
		UserInfo userInfo = securityService.getLoggedInUser();

		if (!userInfo.getRole().equalsIgnoreCase("ROLE_USER")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("myOrders", soldProductsRepo.findByUserInfo(userInfo));//
		m.setViewName("myProducts");// html page
		return m;

	}

	@GetMapping(value = "/getAssignedProductInfo/{productId}")
	public ModelAndView getAssignedProductInfo(@PathVariable long productId) {

		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("productInfo", soldProductsRepo.findById(productId).get());//
		m.setViewName("assignedProductInfo");// html page
		return m;

	}

	@GetMapping(value = "/getSoldProducts")
	public ModelAndView transportation() {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}
		ModelAndView m = new ModelAndView();
		m.addObject("userList", soldProductsRepo.findAll());//
		m.setViewName("transportation");// html page
		return m;

	}

	@GetMapping(value = "/getSoldProductInfo/{productId}")
	public ModelAndView getSoldProductInfo(@PathVariable long productId) {

		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("productInfo", soldProductsRepo.findById(productId).get());//
		m.addObject("userList", userRepo.findByRole("ROLE_TRANSPORTER"));//

		m.setViewName("soldProductInfo");// html page
		return m;

	}

	@PostMapping(value = "/updateSellingProductInfo")
	public String updateSellingProductInfo(@ModelAttribute SoldProducts soldProducts) {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}
		SoldProducts dbProduct = soldProductsRepo.findById(soldProducts.getSpid()).get();

		dbProduct.setProductStatus(soldProducts.getProductStatus());
		soldProductsRepo.save(dbProduct);

		try {
			String message = "Your order has been updated. You can login to see more details";
			smtpMailSender.send(dbProduct.getUserInfo().getEmail(), "Order Update", message);

		} catch (Exception e) {

		}

		return "redirect:/productselling/getAssignedProducts";

	}

	@PostMapping(value = "/updateTransportInfo")
	public String updateTransportInfo(@ModelAttribute SoldProducts soldProducts) {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}
		SoldProducts dbProduct = soldProductsRepo.findById(soldProducts.getSpid()).get();
		boolean newTransporter = false;

		if (dbProduct.getTransporterInfo() == null) {
			newTransporter = true;
		}

		if (soldProducts.getTransporterInfo() == null || soldProducts.getTransporterInfo().getUid() == null) {
			dbProduct.setTransporterInfo(null);
		} else {
			dbProduct.setTransporterInfo(userRepo.findById(soldProducts.getTransporterInfo().getUid()).get());
		}

		dbProduct.setProductStatus(soldProducts.getProductStatus());
		soldProductsRepo.save(dbProduct);

		try {
			String message = "Your order has been updated. You can login to see more details";
			if (newTransporter) {
				message = "Transporter has been updated. You can login to see more details";
			}
			smtpMailSender.send(dbProduct.getUserInfo().getEmail(), "Order Update", message);

		} catch (Exception e) {

		}

		try {
			if (dbProduct.getTransporterInfo() != null) {
				String message = "An Order has been assigned to you. You can login to see more details";
				smtpMailSender.send(dbProduct.getTransporterInfo().getEmail(), "Order Update", message);

			}

		} catch (Exception e) {

		}

		return "redirect:/productselling/getSoldProducts";

	}

}
