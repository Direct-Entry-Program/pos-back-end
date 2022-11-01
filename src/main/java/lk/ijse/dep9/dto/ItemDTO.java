package lk.ijse.dep9.dto;

public class ItemDTO {

    private String code;
    private int stock;
    private double unit_price;
    private String description;

    public ItemDTO(String code, String stock, String unit_price, String description) {
    }

    public ItemDTO(String code, int stock, double unit_price, String description) {
        this.code = code;
        this.stock = stock;
        this.unit_price = unit_price;
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

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
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
                ", unit_price=" + unit_price +
                ", description='" + description + '\'' +
                '}';
    }
}
