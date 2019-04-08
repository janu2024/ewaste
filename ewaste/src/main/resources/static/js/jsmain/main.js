(function($) {
	"use strict";

	/*
	 * [ Load page ] ===========================================================
	 */
	if ($(".animsition").length > 0) {
		$(".animsition").animsition({
			inClass : 'fade-in',
			outClass : 'fade-out',
			inDuration : 1500,
			outDuration : 800,
			linkElement : '.animsition-link',
			loading : true,
			loadingParentElement : 'html',
			loadingClass : 'animsition-loading-1',
			loadingInner : '<div class="loader05"></div>',
			timeout : false,
			timeoutCountdown : 5000,
			onLoadEvent : true,
			browser : [ 'animation-duration', '-webkit-animation-duration' ],
			overlay : false,
			overlayClass : 'animsition-overlay-slide',
			overlayParentElement : 'html',
			transition : function(url) {
				window.location.href = url;
			}
		});
	}
	/*
	 * [ Back to top ]
	 * ===========================================================
	 */
	var windowH = $(window).height() / 2;

	$(window).on('scroll', function() {
		if ($(this).scrollTop() > windowH) {
			$("#myBtn").css('display', 'flex');
		} else {
			$("#myBtn").css('display', 'none');
		}
	});

	$('#myBtn').on("click", function() {
		$('html, body').animate({
			scrollTop : 0
		}, 300);
	});

	/*
	 * ================================================================== [
	 * Fixed Header ]
	 */
	var headerDesktop = $('.container-menu-desktop');
	var wrapMenu = $('.wrap-menu-desktop');

	if ($('.top-bar').length > 0) {
		var posWrapHeader = $('.top-bar').height();
	} else {
		var posWrapHeader = 0;
	}

	if ($(window).scrollTop() > posWrapHeader) {
		$(headerDesktop).addClass('fix-menu-desktop');
		$(wrapMenu).css('top', 0);
	} else {
		$(headerDesktop).removeClass('fix-menu-desktop');
		$(wrapMenu).css('top', posWrapHeader - $(this).scrollTop());
	}

	$(window).on('scroll', function() {
		if ($(this).scrollTop() > posWrapHeader) {
			$(headerDesktop).addClass('fix-menu-desktop');
			$(wrapMenu).css('top', 0);
		} else {
			$(headerDesktop).removeClass('fix-menu-desktop');
			$(wrapMenu).css('top', posWrapHeader - $(this).scrollTop());
		}
	});

	/*
	 * ================================================================== [ Menu
	 * mobile ]
	 */
	$('.btn-show-menu-mobile').on('click', function() {
		$(this).toggleClass('is-active');
		$('.menu-mobile').slideToggle();
	});

	var arrowMainMenu = $('.arrow-main-menu-m');

	for (var i = 0; i < arrowMainMenu.length; i++) {
		$(arrowMainMenu[i]).on('click', function() {
			$(this).parent().find('.sub-menu-m').slideToggle();
			$(this).toggleClass('turn-arrow-main-menu-m');
		})
	}

	$(window).resize(function() {
		if ($(window).width() >= 992) {
			if ($('.menu-mobile').css('display') == 'block') {
				$('.menu-mobile').css('display', 'none');
				$('.btn-show-menu-mobile').toggleClass('is-active');
			}

			$('.sub-menu-m').each(function() {
				if ($(this).css('display') == 'block') {
					console.log('hello');
					$(this).css('display', 'none');
					$(arrowMainMenu).removeClass('turn-arrow-main-menu-m');
				}
			});

		}
	});

	/*
	 * ================================================================== [ Show /
	 * hide modal search ]
	 */
	$('.js-show-modal-search').on('click', function() {
		$('.modal-search-header').addClass('show-modal-search');
		$(this).css('opacity', '0');
	});

	$('.js-hide-modal-search').on('click', function() {
		$('.modal-search-header').removeClass('show-modal-search');
		$('.js-show-modal-search').css('opacity', '1');
	});

	$('.container-search-header').on('click', function(e) {
		e.stopPropagation();
	});

	/*
	 * ================================================================== [
	 * Isotope ]
	 */
	var $topeContainer = $('.isotope-grid');
	var $filter = $('.filter-tope-group');

	// filter items on button click
	$filter.each(function() {
		$filter.on('click', 'button', function() {
			var filterValue = $(this).attr('data-filter');
			$topeContainer.isotope({
				filter : filterValue
			});
		});

	});

	// init Isotope
	$(window).on('load', function() {
		var $grid = $topeContainer.each(function() {
			$(this).isotope({
				itemSelector : '.isotope-item',
				layoutMode : 'fitRows',
				percentPosition : true,
				animationEngine : 'best-available',
				masonry : {
					columnWidth : '.isotope-item'
				}
			});
		});
	});

	var isotopeButton = $('.filter-tope-group button');

	$(isotopeButton).each(function() {
		$(this).on('click', function() {
			for (var i = 0; i < isotopeButton.length; i++) {
				$(isotopeButton[i]).removeClass('how-active1');
			}

			$(this).addClass('how-active1');
		});
	});

	/*
	 * ================================================================== [
	 * Filter / Search product ]
	 */
	$('.js-show-filter').on('click', function() {
		$(this).toggleClass('show-filter');
		$('.panel-filter').slideToggle(400);

		if ($('.js-show-search').hasClass('show-search')) {
			$('.js-show-search').removeClass('show-search');
			$('.panel-search').slideUp(400);
		}
	});

	$('.js-show-search').on('click', function() {
		$(this).toggleClass('show-search');
		$('.panel-search').slideToggle(400);

		if ($('.js-show-filter').hasClass('show-filter')) {
			$('.js-show-filter').removeClass('show-filter');
			$('.panel-filter').slideUp(400);
		}
	});

	/*
	 * ================================================================== [ Cart ]
	 */
	$('.js-show-cart').on('click', function() {
		$('.js-panel-cart').addClass('show-header-cart');
	});

	$('.js-hide-cart').on('click', function() {
		$('.js-panel-cart').removeClass('show-header-cart');
	});

	/*
	 * ================================================================== [ Cart ]
	 */
	$('.js-show-sidebar').on('click', function() {
		$('.js-sidebar').addClass('show-sidebar');
	});

	$('.js-hide-sidebar').on('click', function() {
		$('.js-sidebar').removeClass('show-sidebar');
	});

	/*
	 * ================================================================== [ +/-
	 * num product ]
	 */
	$('.btn-num-product-down').on('click', function() {
		var numProduct = Number($(this).next().val());
		if (numProduct > 0)
			$(this).next().val(numProduct - 1);
	});

	$('.btn-num-product-up').on('click', function() {
		var numProduct = Number($(this).prev().val());
		$(this).prev().val(numProduct + 1);
	});

	/*
	 * ================================================================== [
	 * Rating ]
	 */
	$('.wrap-rating').each(function() {
		var item = $(this).find('.item-rating');
		var rated = -1;
		var input = $(this).find('input');
		$(input).val(0);

		$(item).on('mouseenter', function() {
			var index = item.index(this);
			var i = 0;
			for (i = 0; i <= index; i++) {
				$(item[i]).removeClass('zmdi-star-outline');
				$(item[i]).addClass('zmdi-star');
			}

			for (var j = i; j < item.length; j++) {
				$(item[j]).addClass('zmdi-star-outline');
				$(item[j]).removeClass('zmdi-star');
			}
		});

		$(item).on('click', function() {
			var index = item.index(this);
			rated = index;
			$(input).val(index + 1);
		});

		$(this).on('mouseleave', function() {
			var i = 0;
			for (i = 0; i <= rated; i++) {
				$(item[i]).removeClass('zmdi-star-outline');
				$(item[i]).addClass('zmdi-star');
			}

			for (var j = i; j < item.length; j++) {
				$(item[j]).addClass('zmdi-star-outline');
				$(item[j]).removeClass('zmdi-star');
			}
		});
	});

	/*
	 * ================================================================== [ Show
	 * modal1 ]
	 */
	$('.js-show-modal1').on('click', function(e) {
		e.preventDefault();
		$('.js-modal1').addClass('show-modal1');
	});

	$('.js-hide-modal1').on('click', function() {
		$('.js-modal1').removeClass('show-modal1');
	});

	$(document).ready(function() {
		if ($('.mdb-select').length > 0) {
			$('.mdb-select').materialSelect();
		}
		if ($('#example').length > 0) {
			$('#example').DataTable({
				responsive : true
			});
		}

	});

	/*
	 * select menu
	 */
	$(document).ready(function() {
		$(".btn-select").each(function(e) {
			var value = $(this).find("ul li.selected").html();
			if (value != undefined) {
				$(this).find(".btn-select-input").val(value);
				$(this).find(".btn-select-value").html(value);
			}
		});
	});

	$(document).on('click', '.btn-select', function(e) {
		e.preventDefault();
		var ul = $(this).find("ul");
		if ($(this).hasClass("active")) {
			if (ul.find("li").is(e.target)) {
				var target = $(e.target);
				target.addClass("selected").siblings().removeClass("selected");
				var value = target.html();
				$(this).find(".btn-select-input").val(value);
				$(this).find(".btn-select-value").html(value);
			}
			ul.hide();
			$(this).removeClass("active");
		} else {
			$('.btn-select').not(this).each(function() {
				$(this).removeClass("active").find("ul").hide();
			});
			ul.slideDown(300);
			$(this).addClass("active");
		}
	});

	$(document).on('click', function(e) {
		var target = $(e.target).closest(".btn-select");
		if (!target.length) {
			$(".btn-select").removeClass("active").find("ul").hide();
		}
	});

})(jQuery);

function loadProductSubCategory() {
	$("#productSC").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productSC"));
	$.ajax({
		url : "/category/getSubCategoryFromCategory/"
				+ $("#productCategoryId").val(),
		type : "get",
		success : function(data) {
			$(data).each(function() {
				$("<option />", {
					val : this.psid,
					text : this.categoryName
				}).appendTo($("#productSC"));
			});
		}
	});
}

function loadProductSubCategoryForSelling() {
	$("#productSC").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productSC"));
	$("#productBrand").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productBrand"));
	$("#productModel").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productModel"));
	$("#numberOfYears").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#numberOfYears"));
	$("#isWorking").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#isWorking"));
	$
			.ajax({
				url : "/productselling/getSubCategory/"
						+ $("#productCategoryId").val(),
				type : "get",
				success : function(data) {
					$(data).each(function() {
						$("<option />", {
							val : this.psid,
							text : this.categoryName
						}).appendTo($("#productSC"));
					});
				}
			});
}

function loadProductBrandForSelling() {
	$("#productBrand").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productBrand"));
	$("#productModel").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productModel"));
	$("#numberOfYears").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#numberOfYears"));
	$("#isWorking").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#isWorking"));
	$.ajax({
		url : "/productselling/getBrand/" + $("#productSC").val(),
		type : "get",
		success : function(data) {
			$(data).each(function() {
				$("<option />", {
					val : this.bid,
					text : this.brandName
				}).appendTo($("#productBrand"));
			});
		}
	});
}
var productPricingInfo = [];
function loadProductModelForSelling() {
	$("#productModel").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#productModel"));
	$("#numberOfYears").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#numberOfYears"));
	$("#isWorking").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#isWorking"));

	$.ajax({
		url : "/productselling/getModel/" + $("#productBrand").val(),
		type : "get",
		success : function(data) {
			$(data).each(function() {
				$("<option />", {
					val : this.mid,
					text : this.modelName
				}).appendTo($("#productModel"));
			});
		}
	});
}
var productPricingData = [];
function loadProductPricingForSelling() {
	$("#oldPrductDiv").removeClass("d-none");
	$("#sellProductBtn").addClass("d-none");

	$("#numberOfYears").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#numberOfYears"));
	$("#isWorking").empty();
	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#isWorking"));
	$.ajax({
		url : "/productselling/getSellingProductDetails/"
				+ $("#productModel").val(),
		type : "get",
		success : function(data) {
			productPricingData = data;
			$(data).each(function() {
				$("<option />", {
					val : this.numberOfYearsOld,
					text : this.numberOfYearsOld
				}).appendTo($("#numberOfYears"));
			});
		}
	});
}

function loadWorkingNonWorkingInfo() {
	$("#oldPrductDiv").addClass("d-none");

	$("#isWorking").empty();
	$("#workingProductDiv").removeClass("d-none");
	var numberOfYears = $("#numberOfYears").val();

	$("#yearsOldInfo").html(numberOfYears + " Year(s) old");
	$("#myProductStatus").removeClass("d-none");

	$("<option />", {
		val : "",
		text : ""
	}).appendTo($("#isWorking"));
	var arrayItem = [];
	$(productPricingData).each(
			function() {
				if (numberOfYears == this.numberOfYearsOld
						&& arrayItem.indexOf(this.working) == -1) {
					arrayItem.push(this.working);
					$("<option />", {
						val : this.working,
						text : this.working == false ? 'No' : 'Yes'
					}).appendTo($("#isWorking"));
				}
			});
}

function showProductPricing() {
	$("#workingProductDiv").addClass("d-none");

	var numberOfYears = $("#numberOfYears").val();
	var isWorking = $("#isWorking").val();
	var productPrice = 0;
	var pricingId = 0;
	$(productPricingData).each(function() {
		var fieldVal = this.working == false ? 'false' : 'true';
		if (numberOfYears == this.numberOfYearsOld && isWorking == fieldVal) {
			productPrice = this.productPrice;
			pricingId = this.pid;

		}
	});

	if (isWorking == 'false' || isWorking == false) {
		$("#workingStatusInfo").html("Not Working");
	} else {
		$("#workingStatusInfo").html("Working");
	}

	if (pricingId == 0) {
		$("#productPricingDiv").html(
				"Sorry! We are not accepting a product with this status.");
		$("#sellingProductId").val(0);

		return;
	}

	$("#productPricingDiv").html(
			"Congratulations! You will get Rs. " + productPrice);
	$("#sellingProductId").val(pricingId);
	$("#submitSellingProduct").removeClass("d-none");

}