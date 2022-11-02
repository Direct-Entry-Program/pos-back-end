package lk.ijse.dep9.dto;

public class OrderCompleteDTO {
    private String id;
    private String date;
    private String customerId;
    private String itemCode;
    private String unitPrice;
    private String qty;

    public OrderCompleteDTO() {
    }

    public OrderCompleteDTO(String id, String date, String customerId, String itemCode, String unitPrice, String qty) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
