
(function ($) {

  "use strict";

  var OptionManager = (function () {
    var objToReturn = {};

    var defaultOptions = {
      classCartIcon: 'my-cart-icon',
      classCartBadge: 'my-cart-badge',
      classProductQuantity: 'my-product-quantity',
      classProductRemove: 'my-product-remove',
      classCheckoutCart: 'my-cart-checkout',
      affixCartIcon: true,
      showCheckoutModal: true,
      clickOnAddToCart: function($addTocart) { },
      clickOnCartIcon: function($cartIcon, products, totalPrice, totalQuantity) { },
      checkoutCart: function(products, totalPrice, totalQuantity) { },
      getDiscountPrice: function(products, totalPrice, totalQuantity) { return null; }
    };


    var getOptions = function (customOptions) {
      var options = $.extend({}, defaultOptions);
      if (typeof customOptions === 'object') {
        $.extend(options, customOptions);
      }
      return options;
    }

    objToReturn.getOptions = getOptions;
    return objToReturn;
  }());

	function getCookie(cname) {
    var name = cname + '=';
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return '';
	}
  var ProductManager = (function(){
    var objToReturn = {};
    var taiKhoan = getCookie('taiKhoan');

    /*
    PRIVATE
    */
    localStorage.products = localStorage.products ? localStorage.products : "";
    localStorage.username = localStorage.username ? localStorage.username : "";
    localStorage.username = taiKhoan;

    var getIndexOfProduct = function(id){
      var productIndex = -1;
      var products = getAllProducts();
      $.each(products, function(index, value){
        if(value.id == id){
          productIndex = index;
          return;
        }
      });
      return productIndex;
    }
    var setAllProducts = function(products){
      localStorage.products = JSON.stringify(products);
    }
    var addProduct = function(id, name, summary, price, quantity, image) {
      var products = getAllProducts();
      products.push({
        id: id,
        name: name,
        summary: summary,
        price: price,
        quantity: quantity,
        image: image,
        total : parseFloat(price) * parseInt(quantity),
        username : localStorage.username
      });
      setAllProducts(products);
    }

    /*
    PUBLIC
    */
    var getAllProducts = function(){
      try {
        var products = JSON.parse(localStorage.products);
        var user_products = [];
        products.forEach(function(item){
          var index = products.findIndex(pr => pr.username == localStorage.username)
          if(index != -1){
            user_products.push(item);
          }
        })

        return user_products;
      } catch (e) {
        return [];
      }
      
    }
    var updateProductKey = function(id,quantity){
      var productIndex =getIndexOfProduct(id);
      var products = getAllProducts();
      products[productIndex].quantity =  parseInt(quantity);
      setAllProducts(products);

    }

    var updatePoduct = function(id, quantity) {
      var productIndex = getIndexOfProduct(id);
      if(productIndex < 0){
        return false;
      }
      var products = getAllProducts();
      products[productIndex].quantity = parseInt(products[productIndex].quantity) +  parseInt(quantity);
      setAllProducts(products);
      return true;
    }
    var setProduct = function(id, name, summary, price, quantity, image) {
      
      if(typeof id === "undefined"){
        console.error("id required")
        return false;
      }
      if(typeof name === "undefined"){
        console.error("name required")
        return false;
      }
      if(typeof image === "undefined"){
        console.error("image required")
        return false;
      }
      if(!$.isNumeric(price)){
        console.error("price is not a number")
        return false;
      }
      if(!$.isNumeric(quantity)) {
        console.error("quantity is not a number");
        return false;
      }
      summary = typeof summary === "undefined" ? "" : summary;

      if(!updatePoduct(id,quantity)){
        addProduct(id, name, summary, price, quantity, image);
      }
    }
    var clearProduct = function(){
      setAllProducts([]);
    }
    var removeProduct = function(id){
      var products = getAllProducts();
      products = $.grep(products, function(value, index) {
        return value.id != id;
      });
      setAllProducts(products);
    }
    var getTotalQuantity = function(){
      var total = 0;
      var products = getAllProducts();
      $.each(products, function(index, value){
        total += value.quantity * 1;
      });
      return total;
    }
    var getTotalPrice = function(){
      var products = getAllProducts();
      var total = 0;
      $.each(products, function(index, value){
        total += value.quantity * value.price;
      });
      return total;
    }

    objToReturn.getAllProducts = getAllProducts;
    objToReturn.updatePoduct = updatePoduct;
    objToReturn.setProduct = setProduct;
    objToReturn.clearProduct = clearProduct;
    objToReturn.removeProduct = removeProduct;
    objToReturn.getTotalQuantity = getTotalQuantity;
    objToReturn.getTotalPrice = getTotalPrice;
    objToReturn.updateProductKey = updateProductKey;
    return objToReturn;
  }());


  var loadMyCartEvent = function(userOptions){

    let host = "http://localhost:8080/home";  
    var options = OptionManager.getOptions(userOptions);
    var $cartIcon = $("." + options.classCartIcon);
    var $cartBadge = $("." + options.classCartBadge);
    var classProductQuantity = options.classProductQuantity;
    var classProductRemove = options.classProductRemove;
    var classCheckoutCart = options.classCheckoutCart;

    var idCartModal = 'my-cart-modal';
    var idCartTable = 'my-cart-table';
    var idOrderDetailModal = 'my-order-detail-modal';
    var idOrderDetailTable = 'my-order-detail-table';
    var idGrandTotal = 'my-cart-grand-total';
    var idGrandTotalCheckout = 'my-cart-grand-total-check-out';
    var idEmptyCartMessage = 'my-cart-empty-message';
    var idDiscountPrice = 'my-cart-discount-price';
    var idCheckoutPackage = 'check-out-package';
    var idCheckoutHistory= 'check-out-history';
    var idCheckoutTable = 'check-out-table';
    var classProductTotal = 'my-product-total';
    var classAffixMyCartIcon = 'my-cart-icon-affix';

    $cartBadge.text(ProductManager.getTotalQuantity());

    if(!$("#" + idCartModal).length) {
      $('body').append(
        '<div class="modal fade" id="' + idCartModal + '" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">' +
        '<div class="modal-dialog" role="document">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
        '<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-shopping-cart"></span>Giỏ hàng của tôi</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<table class="table table-hover table-responsive" id="' + idCartTable + '"></table>' +
        '</div>' +
        '<div class="modal-footer">' +
        '<a href="/home/cart" class="btn btn-default">Thanh Toán</a>' +
        '<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>' +

        '</div>' +
        '</div>' +
        '</div>' +
        '</div>'
      );

      
      
    }


    if(!$("#" + idOrderDetailModal).length) {
      $('body').append(
        '<div class="modal fade" id="' + idOrderDetailModal + '" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">' +
        '<div class="modal-dialog" role="document">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
        '<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-shopping-cart"></span>Đơn hàng chi tiết của tôi</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<table class="table table-hover table-responsive" id="' + idOrderDetailTable + '"></table>' +
        '</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>' +

        '</div>' +
        '</div>' +
        '</div>' +
        '</div>'
      );  
      
    }

    var drawOrderDetailModal = async function(id){
      var $orderDetailTable = $("#" + idOrderDetailTable);
      $orderDetailTable.empty();
      var orderDetails = await getOrderDetailAPI(id);
      $.each(orderDetails, async function(){
        var image = await getImagesOneProductAPI(this.sp.maSP);
        $orderDetailTable.append(
          '<tr title="' + this.summary + '" data-id="' + this.maDon + '" data-price="' + this.donGia + '">' +
          '<td class="text-center" style="width: 30px;"><img width="30px" height="30px" src="/images/' + image[0].tenHinh + '"/></td>' +
          '<td>' + this.sp.tenSP + '</td>' +
          '<td title="Unit Price">VNĐ ' + this.donGia + '</td>' +
          '<td title="Quantity"><input  disabled data-id="' + this.maDon + '" style="width: 70px;" class="' + classProductQuantity + '" value="' + this.soLuong + '"/></td>' +
          '<td title="Total" class="' + classProductTotal + '">VNĐ ' + this.donGia * this.soLuong + '</td>' +
          
          '</tr>'
        );


      });  
    }


    

    //Lất tất cả đơn hàng
    var getOrderAPI = async function(username){
      var orders = [];
      await  $.ajax({
        url: `${host}/check-out/${username}/histories`,
        type: 'GET',
        contentType: "application/json",
        success:function(res){
          orders = res;
        },
        error:function(resp){
        }
        
      });
      return orders;
    }

    //Lấy tất cả đơn hàng chi tiết
    var getOrderDetailAPI = async function(id){
      var ordersdetail = [];
      await  $.ajax({
        url: `${host}/check-out/${id}/history-detail`,
        type: 'GET',
        contentType: "application/json",
        success:function(res){
          ordersdetail = res;
        },
        error:function(resp){
        }
        
      });
      return ordersdetail;
    }
    //Lấy hình ảnh 1 sản phẩm
    var getImagesOneProductAPI = async function(id){
      var pics = [];
      await  $.ajax({
        url: `${host}/check-out/${id}/image`,
        type: 'GET',
        contentType: "application/json",
        success:function(res){
          pics = res;
        },
        error:function(resp){
        }
        
      });
      return pics;
    }


    //Lịch sử
    var drawHeaderTableHistory = async function(){
      $('#'+idCheckoutHistory).append(
          '<table class="table" id="check-out-table-history">'+
            '<tr>'+
              '<th class="t-head head-it ">Mã đơn</th>'+
              '<th class="t-head">Ngày tạo</th>'+
              '<th class="t-head">Trạng thái</th>'+
              '<th class="t-head">Tổng</th>'+
              '<th class="t-head">Tài khoản</th>'+
              '<th class="t-head"></th>'+
            '</tr>'+
          '</table>'     
      );

      await drawTableHistory();
    }
    



    var drawTableHistory = async function(){
      var $checkoutHistoryTable = $("#check-out-table-history");
      $checkoutHistoryTable.children().first().children().not(':first').empty();
      var orders = await getOrderAPI(localStorage.username);
      $.each(orders, function(){
        var trangThai = this.trangthai == 2 ? 'Đã thanh toán' : 'Chưa thanh toán';
        
        $checkoutHistoryTable.append(
          '<tr class="cross">'+
              '<td class="t-data">'+
              '<h5 id="name-cart">'+this.maDon+'</h5>'+
              '</td>'+
              '<td class="t-data">'+ this.ngayTao + '</td>'+
              '<td class="t-data">' + trangThai + '</td>' +
              '<td class="t-data" >'+this.tong+' VNĐ</td>'+
              '<td class="t-data" >'+this.tk.taiKhoan+'</td>'+
              '<td class="t-data" ><button class=" btn btn-warning serach-detail" data-id="'+this.maDon+'"><i class="fa fa-search "></i></button></td>'+
            '</tr>'
        );

        
      });
    }
    drawHeaderTableHistory(); 

    $(document).on('click', ".serach-detail", async function(){
      var id = $(this).attr('data-id');
        await drawOrderDetailModal(id);
        $('#'+idOrderDetailModal).modal("show");
    });
    
    //Thanh toán
    var drawHeaderTableCheckout = function(){
      $('#'+idCheckoutPackage).children(':eq(1)').after( ProductManager.getAllProducts().length ?
          '<table class="table" id="check-out-table">'+
            '<tr>'+
              '<th class="t-head head-it ">Sản Phẩm</th>'+
              '<th class="t-head">Đơn giá</th>'+
              '<th class="t-head">Số lượng</th>'+
              '<th class="t-head">Tổng</th>'+
            '</tr>'+
            
          '</table>'
        : '<div class="alert alert-danger" role="alert" id="' + idEmptyCartMessage + '">Giỏ hàng của bạn đang trống</div>'       
      );
    }



    
    var drawTableCheckout = function(){
      var $checkoutTable = $("#" + idCheckoutTable);
      var $checkoutTotal = $("#total-check-out");
      $checkoutTotal.remove();
      $checkoutTable.children().first().children().not(':first').empty();
      var products = ProductManager.getAllProducts();
      $.each(products, function(){
        var total = this.quantity * this.price;
        $checkoutTable.append(
          '<tr class="cross" id="PRCO'+this.id+'">'+
              '<td class="ring-in t-data">'+
                '<a href="single.html" class="at-in">'+
                  '<img width="100px" height="100px" src="'+this.image+'" class="img-responsive" alt="" id="img-cart">'+
                '</a>'+
                '<div class="sed">'+
                  '<h5 id="name-cart">'+this.name+'</h5>'+
                '</div>'+
                '<div class="clearfix"> </div>'+
                '<div class="close1" data-id="'+this.id+'"> <i class="fa fa-times" aria-hidden="true"></i></div>'+
              '</td>'+
              '<td class="t-data" id="price-cart">'+this.price+' VNĐ</td>'+
              '<td class="t-data">'+
                '<div class="quantity">'+ 
                  '<div class="quantity-select">'+            
                    '<div class="entry value-minus value-co-minus">&nbsp;</div>'+
                    '<div class="entry value" data-id="'+this.id+'" data-price="'+this.price+'"><span class="span-1">'+this.quantity+'</span></div>'+									
                    '<div class="entry value-plus value-co-plus active">&nbsp;</div>'+
                  '</div>'+
                '</div>'+
              '</td>'+
              '<td class="t-data '+classProductTotal+'">' + total + ' VNĐ</td>' +
            '</tr>'
        );

        
      });
      $('#'+idCheckoutPackage).children(':eq(2)').after( ProductManager.getAllProducts().length ?
            '<div id="total-check-out" class="text-right"><h3>Tổng tiền: <h3 id="'+idGrandTotalCheckout+'"></h3></h3></div>' : ''       
        );
        showGrandTotalCheckout();
    }
    var showGrandTotalCheckout = function(){
      $("#" + idGrandTotalCheckout).text( ProductManager.getTotalPrice() + " VNĐ");
    }

    drawHeaderTableCheckout();
    drawTableCheckout();

    var drawTable = function(){
      var $cartTable = $("#" + idCartTable);
      $cartTable.empty();
      var products = ProductManager.getAllProducts();
      $.each(products, function(){
        var total = this.quantity * this.price;
        $cartTable.append(
          '<tr title="' + this.summary + '" data-id="' + this.id + '" data-price="' + this.price + '">' +
          '<td class="text-center" style="width: 30px;"><img width="30px" height="30px" src="' + this.image + '"/></td>' +
          '<td>' + this.name + '</td>' +
          '<td title="Unit Price">VNĐ ' + this.price + '</td>' +
          '<td title="Quantity"><input type="number" min="1" data-id="' + this.id + '" style="width: 70px;" class="' + classProductQuantity + '" value="' + this.quantity + '"/></td>' +
          '<td title="Total" class="' + classProductTotal + '">VNĐ ' + total + '</td>' +
          '<td title="Remove from Cart" class="text-center" style="width: 30px;"><a href="javascript:void(0);" class="btn btn-xs btn-danger ' + classProductRemove + '">X</a></td>' +
          '</tr>'
        );


      });

      $cartTable.append(products.length ?
        '<tr>' +
        '<td></td>' +
        '<td><strong>Tổng Tiền</strong></td>' +
        '<td></td>' +
        '<td></td>' +
        '<td><strong id="' + idGrandTotal + '">VNĐ </strong></td>' +
        '<td></td>' +
        '</tr>'
        : '<div class="alert alert-danger" role="alert" id="' + idEmptyCartMessage + '">Giỏ hàng của bạn đang trống</div>'
      );

      var discountPrice = options.getDiscountPrice(products, ProductManager.getTotalPrice(), ProductManager.getTotalQuantity());
      if(products.length && discountPrice !== null) {
        $cartTable.append(
          '<tr style="color: red">' +
          '<td></td>' +
          '<td><strong>Tổng Tiền( bao gồm chiết khấu)</strong></td>' +
          '<td></td>' +
          '<td></td>' +
          '<td><strong id="' + idDiscountPrice + '">VNĐ </strong></td>' +
          '<td></td>' +
          '</tr>'
        );
      }
      showGrandTotal();
      showDiscountPrice();
    }
    var showModal = function(){
      drawTable();
      $("#" + idCartModal).modal('show');
    }
    var updateCart = function(){
      $.each($("." + classProductQuantity), function(){
        var id = $(this).closest("tr").data("id");
        ProductManager.updatePoduct(id, $(this).val());
        
      });
    }
    var showGrandTotal = function(){
      $("#" + idGrandTotal).text(ProductManager.getTotalPrice() + " VNĐ");
    }
    var showDiscountPrice = function(){
      $("#" + idDiscountPrice).text(options.getDiscountPrice(ProductManager.getAllProducts(), ProductManager.getTotalPrice(), ProductManager.getTotalQuantity()) + " VNĐ");
    }

    /*
    EVENT
    */
    if(options.affixCartIcon) {
      var cartIconBottom = $cartIcon.offset().top * 1 + $cartIcon.css("height").match(/\d+/) * 1;
      var cartIconPosition = $cartIcon.css('position');
      $(window).scroll(function () {
        if ($(window).scrollTop() >= cartIconBottom) {
          $cartIcon.css('position', 'fixed').css('z-index', '999').addClass(classAffixMyCartIcon);
        } else {
          $cartIcon.css('position', cartIconPosition).css('background-color', 'inherit').removeClass(classAffixMyCartIcon);
        }
      });
    }

    $cartIcon.click(function(){
      options.showCheckoutModal ? showModal() : options.clickOnCartIcon($cartIcon, ProductManager.getAllProducts(), ProductManager.getTotalPrice(), ProductManager.getTotalQuantity());
    });

    $(document).on("input", "." + classProductQuantity, function () {
      var price = $(this).closest("tr").data("price");
      var id = $(this).closest("tr").data("id");
      var quantity = $(this).val();
      if(quantity === ""){
        quantity = 1;
      }

      $(this).parent("td").next("." + classProductTotal).text(price * quantity + " VNĐ");
      ProductManager.updateProductKey(id,quantity);
      $cartBadge.text(ProductManager.getTotalQuantity());
      drawTableCheckout();
      showGrandTotal();
      showDiscountPrice();
     
    });

    $(document).on('keyup', "." + classProductQuantity, function(evt){
      // var id = $(this).attr("data-id");
      // var productIndex = ProductManager.getIndexOfProduct(id);
      // var products = ProductManager.getAllProducts();
      // products[productIndex].quantity =  parseInt($(this).val());
      // alert(products[productIndex].quantity)
      // ProductManager.setAllProducts(products);
      // $cartBadge.text(ProductManager.getTotalQuantity());
      // showGrandTotal();
      // showDiscountPrice();
      
    });

    $(document).on('click', "." + classProductRemove, function(){
      var $tr = $(this).closest("tr");
      var id = $tr.data("id");
      $tr.hide(500, function(){
        ProductManager.removeProduct(id);
        drawTable();
        drawTableCheckout();
        $cartBadge.text(ProductManager.getTotalQuantity());
      });
    });

    $(document).on('click', ".close1", function(){
      var id = $(this).attr("data-id");
      var $tr = $('#PRCO'+id);
      $tr.hide(500, function(){
        ProductManager.removeProduct(id);
        drawTable();
        drawTableCheckout();
        $cartBadge.text(ProductManager.getTotalQuantity());
      });
    });

    $("." + classCheckoutCart).click(function(){
      var products = ProductManager.getAllProducts();
      if(!products.length) {
        $("#" + idEmptyCartMessage).fadeTo('fast', 0.5).fadeTo('fast', 1.0);
        return ;
      }
      updateCart();
      options.checkoutCart(ProductManager.getAllProducts(), ProductManager.getTotalPrice(), ProductManager.getTotalQuantity());
      ProductManager.clearProduct();
      $cartBadge.text(ProductManager.getTotalQuantity());
      $("#" + idCartModal).modal("hide");
    });

    // quantity check out page

			$('.value-co-plus').on('click', function(){
        
				var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)+1;
				divUpd.text(newVal);
        $($($($(this)).parent()).parent()).parent("td").next("." + classProductTotal).text(parseFloat(divUpd.attr('data-price')) * newVal + " VNĐ");
        ProductManager.updateProductKey(divUpd.attr('data-id'),newVal);
        $cartBadge.text(ProductManager.getTotalQuantity());
        drawTable();
        showGrandTotalCheckout();
			});

			$('.value-co-minus').on('click', function(){
				var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)-1;
				if(newVal <=1) newVal =1;
        divUpd.text(newVal);
        $($($($(this)).parent()).parent()).parent("td").next("." + classProductTotal).text(parseFloat(divUpd.attr('data-price')) * newVal + " VNĐ");
        ProductManager.updateProductKey(divUpd.attr('data-id'),newVal);
        $cartBadge.text(ProductManager.getTotalQuantity());
        drawTable();
        showGrandTotalCheckout();
			});
      
		// quantity check out page
      $('.check-out-btn').on('click', async function(){
           var check = await check_quantity();
           
           if(check === false)  return;         
           var products = ProductManager.getAllProducts();
           
           await $.ajax({
                    url: `${host}/cart/check-out`,
                    type: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(products),
                    success:function(res){
                      ProductManager.clearProduct()
                      $cartBadge.text(ProductManager.getTotalQuantity());
                      drawTable();
                      drawHeaderTableCheckout();
                      showGrandTotalCheckout();
                      $("#" + idCheckoutTable).empty();
                      alert('Thanh toán thành công')
                    },
                    error:function(resp){
                      
                    }
                  
                });
      });
      
      var check_quantity = async function(){
        var prs_api =  await getProductFromAPI();
        var products = ProductManager.getAllProducts();
        var check = true;
        products.forEach(function(item){
          var index = prs_api.findIndex(p => p.maSP == parseInt(item.id));
          if(index != -1 ){
            if(prs_api[index].soLuong -  parseInt(item.quantity) < 0){
              alert('Số lượng hiện tại của '+item.name+' chỉ còn lại ' + prs_api[index].soLuong + ' cái ! \n'+
                    'Vui lòng chọn số lượng nhỏ hơn !');
                    check = false;                  
            }
          }
        });
        return check;
      }

      
      var getProductFromAPI = async function(){
        var prs = [];
        await  $.ajax({
          url: `${host}/cart/get-product`,
          type: 'GET',
          contentType: "application/json",
          // data: JSON.stringify(data),
          success:function(res){
            prs = res;
          },
          error:function(resp){
          }
          
        });
        return prs;
      };
  }


  var MyCart = function (target, userOptions) {
    /*
    PRIVATE
    */
    var $target = $(target);
    var options = OptionManager.getOptions(userOptions);
    var $cartIcon = $("." + options.classCartIcon);
    var $cartBadge = $("." + options.classCartBadge);

    /*
    EVENT
    */
    $target.click(function(){
      options.clickOnAddToCart($target);

      var id = $target.attr("data-id");
      var name = $target.attr("data-name");
      var summary = $target.attr("data-summary");
      var price = $target.attr("data-price");
      var quantity = $target.attr("data-quantity");
      var image = (($($target.parent()).parent())).parent().children().first().attr('data-img');
      if(image == undefined){
        $('.class-check-img').each(function(){
					if($(this).attr('data-img-check') == "true"){
						image = $(this).attr('data-img')
					}
				})
      }

      ProductManager.setProduct(id, name, summary, price, quantity, image);
      $cartBadge.text(ProductManager.getTotalQuantity());
    });

  }


  $.fn.myCart = function (userOptions) {
    loadMyCartEvent(userOptions);
    return $.each(this, function () {
      new MyCart(this, userOptions);
    });
  }

 


})(jQuery);
