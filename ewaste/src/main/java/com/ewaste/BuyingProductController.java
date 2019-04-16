package com.ewaste;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/productbuying")
public class BuyingProductController {

	@Autowired
	BuyingProductPriceRepository repo;

	@Autowired
	ProductModelRepository modelRepo;

	@Autowired
	private SecurityService securityService;

	@Autowired
	BuyingProductRepository buyingRepo;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@Autowired
	UserRepository userRepo;

	String uploadDir = "E:\\WorkSpace2\\maven.1548166232495\\ewaste\\src\\main\\resources\\static\\images\\upload";

	@GetMapping(value = "/getPricing")
	public ModelAndView getPricing() {

		ModelAndView m = new ModelAndView();
		m.addObject("pricingList", repo.findAll());// blank object
		m.setViewName("buyingProductPricing");// html page
		return m;

	}

	@GetMapping(value = "/showBuyingProducts")
	public ModelAndView showAvailableProducts() {

		ModelAndView m = new ModelAndView();
		m.addObject("productList", repo.findByStatus("AVAILABLE"));// blank object
		m.setViewName("showBuyingProducts");// html page
		return m;

	}

	@GetMapping(value = "/showBuyingProducts/{pricingId}")
	public ModelAndView showAvailableProductDetails(@PathVariable long pricingId) {

		ModelAndView m = new ModelAndView();
		m.addObject("modelInfo", repo.findById(pricingId).get());// blank object
		m.setViewName("showBuyingProductDetails");// html page
		return m;

	}

	@GetMapping(value = "/managePricing")
	public ModelAndView managePricing() {
		ModelAndView m = new ModelAndView();
		m.addObject("productPricing", new BuyingProductPrice());
		m.addObject("modelList", modelRepo.findAll());
		m.setViewName("manageBuyingPricing");// html page
		return m;
	}

	@GetMapping(value = "/managePricing/{pricingId}")
	public ModelAndView editPricing(@PathVariable long pricingId) {
		ModelAndView m = new ModelAndView();
		m.addObject("productPricing", repo.findById(pricingId).get());
		m.addObject("modelList", modelRepo.findAll());
		m.setViewName("manageBuyingPricing");// html page
		return m;
	}

	@PostMapping(value = "/saveBuyingPrice")
	public String saveModel(@ModelAttribute BuyingProductPrice pricing, @RequestParam("img1") MultipartFile img1,
			@RequestParam("img2") MultipartFile img2, @RequestParam("img3") MultipartFile img3,
			@RequestParam("img4") MultipartFile img4) {
		pricing.setProductModel(modelRepo.findById(pricing.getProductModel().getMid()).get());

		Path mpath = Paths.get(uploadDir, img1.getOriginalFilename());
		try {
			java.nio.file.Files.write(mpath, img1.getBytes());
			pricing.setProductImage("/images/upload/" + img1.getOriginalFilename());

			mpath = Paths.get(uploadDir, img2.getOriginalFilename());
			java.nio.file.Files.write(mpath, img2.getBytes());
			pricing.setProductImage2("/images/upload/" + img2.getOriginalFilename());

			mpath = Paths.get(uploadDir, img3.getOriginalFilename());
			java.nio.file.Files.write(mpath, img3.getBytes());
			pricing.setProductImage3("/images/upload/" + img3.getOriginalFilename());

			mpath = Paths.get(uploadDir, img4.getOriginalFilename());
			java.nio.file.Files.write(mpath, img4.getBytes());
			pricing.setProductImage4("/images/upload/" + img4.getOriginalFilename());

		} catch (Exception e) {
			System.out.print(e);
		}

		repo.save(pricing);
		return "redirect:/productbuying/getPricing";
	}

	@PostMapping(value = "/buyProduct")
	public String saveModel(@ModelAttribute BuyingProduct buyingProducts) {

		buyingProducts.setPricing(repo.findById(buyingProducts.getPricing().getPid()).get());
		buyingProducts.setUserInfo(securityService.getLoggedInUser());
		buyingRepo.save(buyingProducts);
		return "redirect:/productbuying/showBuyingProducts";

	}

	@PostMapping(value = "/updateTransportInfo")
	public String updateTransportInfo(@ModelAttribute BuyingProduct buyingProduct) {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}
		BuyingProduct dbProduct = buyingRepo.findById(buyingProduct.getSpid()).get();
		boolean newTransporter = false;

		if (dbProduct.getTransporterInfo() == null) {
			newTransporter = true;
		}

		if (buyingProduct.getTransporterInfo() == null || buyingProduct.getTransporterInfo().getUid() == null) {
			dbProduct.setTransporterInfo(null);
		} else {
			dbProduct.setTransporterInfo(userRepo.findById(buyingProduct.getTransporterInfo().getUid()).get());
		}

		dbProduct.setProductStatus(buyingProduct.getProductStatus());
		buyingRepo.save(dbProduct);

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

		return "redirect:/productbuying/getbuyingProducts";

	}

	@GetMapping(value = "/getbuyingProducts/{productId}")
	public ModelAndView getbuyingProducts(@PathVariable long productId) {

		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("productInfo", buyingRepo.findById(productId).get());//
		m.addObject("userList", userRepo.findByRole("ROLE_TRANSPORTER"));//

		m.setViewName("buyingProductInfo");// html page
		return m;

	}

	@GetMapping(value = "/getbuyingProducts")
	public ModelAndView getbuyingProducts() {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_ADMIN")) {
			return null;
		}
		ModelAndView m = new ModelAndView();
		m.addObject("userList", buyingRepo.findAll());//
		m.setViewName("buyingTransportation");// html page
		return m;

	}
}
