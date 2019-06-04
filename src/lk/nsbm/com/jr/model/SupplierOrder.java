package lk.nsbm.com.jr.model;

public class SupplierOrder {

    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private String supplierName;
    private String orderDate;

    public SupplierOrder(String code,String description, int qty, double unitPrice, double total, String supplierName, String orderDate) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.supplierName = supplierName;
        this.orderDate = orderDate;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }




}
