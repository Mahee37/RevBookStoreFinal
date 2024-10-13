package com.revbookstoreclientapp.dto;

public class CartProductDTO {
    private Long cartId;
    private long UserId;
    private Long productId; // Include this field
    private long quantity;
    private Double totalPrice;
    private String productDescription;
    private String productName;
    
 // Default constructor
    public CartProductDTO() {
    }
    
    // Constructor
    public CartProductDTO(Long cartId, Long productId, long quantity, Double totalPrice, String productDescription, String productName) {
        this.cartId = cartId;
        this.productId = productId; // Ensure this is initialized
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.productDescription = productDescription;
        this.productName = productName;
    }
    
    
    public CartProductDTO(Long cartId, String productName, long quantity, Double totalPrice) {
        this.cartId = cartId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    
    
	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}




}
