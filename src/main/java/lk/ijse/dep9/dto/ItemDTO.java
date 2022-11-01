package lk.ijse.dep9.dto;

public class ItemDTO {

    private String code;
    private int stock;
    private double unitPrice;
    private String description;

    public ItemDTO() {
    }

    public ItemDTO(String code, int stock, double unitPrice, String description) {
        this.code = code;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", stock=" + stock +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                '}';
    }
}
