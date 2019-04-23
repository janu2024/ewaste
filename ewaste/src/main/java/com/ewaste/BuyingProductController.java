package com.ewaste;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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

	@Autowired
	SoldProductsRepository soldProductsRepo;

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
	public ModelAndView saveModel(@ModelAttribute BuyingProduct buyingProducts) {

		buyingProducts.setPricing(repo.findById(buyingProducts.getPricing().getPid()).get());
		buyingProducts.setUserInfo(securityService.getLoggedInUser());
		BuyingProduct dbProduct=buyingRepo.save(buyingProducts);
		
		ModelAndView m = new ModelAndView();
		m.addObject("myOrders", dbProduct);//
		m.setViewName("sellingSummary");// html page
		return m;

	}

	@PostMapping(value = "/updateBuyingProductInfo")
	public String updateBuyingProductInfo(@ModelAttribute BuyingProduct soldProducts) {
		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}
		BuyingProduct dbProduct = buyingRepo.findById(soldProducts.getSpid()).get();

		dbProduct.setProductStatus(soldProducts.getProductStatus());
		buyingRepo.save(dbProduct);

		try {
			String message = "Your order has been updated. You can login to see more details";
			smtpMailSender.send(dbProduct.getUserInfo().getEmail(), "Order Update", message);

		} catch (Exception e) {

		}

		return "redirect:/productbuying/getAssignedProducts";

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

	@GetMapping(value = "/getAssignedProducts")
	public ModelAndView getAssignedProducts() {
		UserInfo userInfo = securityService.getLoggedInUser();

		if (!userInfo.getRole().equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("myOrders", buyingRepo.findByTransporterInfo(userInfo));//
		m.setViewName("assignedBuyingProducts");// html page
		return m;

	}

	@GetMapping(value = "/getAssignedProductInfo/{productId}")
	public ModelAndView getAssignedProductInfo(@PathVariable long productId) {

		String userRole = securityService.getLoggedInUser().getRole();

		if (!userRole.equalsIgnoreCase("ROLE_TRANSPORTER")) {
			return null;
		}

		ModelAndView m = new ModelAndView();
		m.addObject("productInfo", buyingRepo.findById(productId).get());//
		m.setViewName("assignedBuyingProductInfo");// html page
		return m;

	}

	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> citiesReport() throws IOException {

		List<BuyingProduct> result = buyingRepo.findAll();

		ByteArrayInputStream bis = generateReport(result);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=report.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	public static ByteArrayInputStream generateReport(List<BuyingProduct> products) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(60);
			table.setWidths(new int[] { 1, 3, 3 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Model Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Price", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("User Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for (BuyingProduct sp : products) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(sp.getPricing().getProductModel().getModelName()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(sp.getProductPrice())));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(sp.getUserInfo().getFirstName())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(table);

			document.close();

		} catch (DocumentException ex) {

		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
