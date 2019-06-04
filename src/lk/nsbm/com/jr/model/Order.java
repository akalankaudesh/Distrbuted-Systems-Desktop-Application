package lk.nsbm.com.jr.model;

import java.time.LocalDate;

public class Order {

    private String id;
    private LocalDate date;
    private String customerId;
    private String customerName;
        private Double total;

    public Order() {
    }

    public Order(String id, LocalDate date, String customerId, Double total) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
