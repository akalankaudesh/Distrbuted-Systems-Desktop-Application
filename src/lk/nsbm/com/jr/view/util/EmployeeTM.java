package lk.nsbm.com.jr.view.util;

public class EmployeeTM {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */


    /**
     * @author ranjith-suranga
     */

    private String id;
    private String name;
    private String address;
    private String contact;

    public EmployeeTM() {
    }

    public EmployeeTM(String id, String name, String address, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public EmployeeTM(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }


}














