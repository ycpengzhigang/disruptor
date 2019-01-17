package com.lmax.disruptor.study.multiproducer;

public class Order {
    /**
     * id 
     */
    private String id;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 价格
     */
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
