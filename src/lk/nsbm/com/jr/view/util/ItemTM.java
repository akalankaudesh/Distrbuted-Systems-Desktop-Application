/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.nsbm.com.jr.view.util;

/**
 *
 * @author ranjith-suranga
 */
public class ItemTM {
    
    private String code;
    private String description;
    private double unitPrice;
    private int qty;

    public ItemTM() {
    }

    public ItemTM(String code, String description, double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qtyOnHand;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Item{" + "code=" + code + ", description=" + description + ", unitPrice=" + unitPrice + ", qty=" + qty + '}';
    }
        
}