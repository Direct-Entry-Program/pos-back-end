package lk.ijse.dep9.dto;

import java.io.Serializable;

public class OrderDTO implements Serializable {
    private String id;
    private String date;
    private String customerId;

    public OrderDTO() {
    }

    public OrderDTO(String id, String date, String customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
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
}
