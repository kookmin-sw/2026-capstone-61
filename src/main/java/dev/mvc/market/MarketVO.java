package dev.mvc.market;

import java.util.Date;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @ToString
public class MarketVO {
    
    private int productId;         // PRODUCT_ID
    private String sellerId;       // SELLER_ID
    private String className;      // CLASS_NAME
    private String productName;    // PRODUCT_NAME
    private int price;             // PRICE
    private int deliveryFee;       // DELIVERY_FEE
    private int stock;             // STOCK
    private String productDesc;    // PRODUCT_DESC
    private String productStatus;  // PRODUCT_STATUS
    private Date regDate;          // REG_DATE
    private String thumbnailName;
    

   
}