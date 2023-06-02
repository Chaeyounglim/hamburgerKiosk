package org.example;

import java.util.List;

public class Sale extends Food {

    private double totalSalesPrice; // 총 판매 금액
    private List<Food> salesFoodList; // 총 판매 상품


    // setter, getter 메서드
    public double getTotalSalesPrice() {
        return totalSalesPrice;
    }

    public List<Food> getSalesFoodList() {
        return salesFoodList;
    }

    public void setTotalSalesPrice(double totalSalesPrice) {
        this.totalSalesPrice = totalSalesPrice;
    }

    public void setSalesFoodList(List<Food> salesFoodList) {
        this.salesFoodList = salesFoodList;
    }


    // 총 판매 금액
    public void setSalesPriceSum() {
        

    }
}
