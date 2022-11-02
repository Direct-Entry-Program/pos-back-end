package lk.ijse.dep9.dto;

import java.io.Serializable;

public class OrderDetailsDTO implements Serializable {
    private String orderId;
    private String itemCode;
    private String unitPrice;
    private String Qty;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderId, String itemCode, String unitPrice, String qty) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        Qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
