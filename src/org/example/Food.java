package org.example;

import java.util.List;
import java.util.Scanner;

public class Food extends Menu{
    public int foodCnt = 1;

    // 생성자 메서드
    public Food() {
    }

    public Food(String name, double price, String content) {
        super(name,content);
        this.name = name;
        this.price = price;
        this.content = content;
    }

    // setters, getters
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getContent() {
        return content;
    }
    public int getFoodCnt() {
        return foodCnt;
    }

    // 메서드
    // 상품 메뉴 출력 기능
    public static void printFood(int selectedMenuNum, String selectedMenuName,Food[] foods) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n [ " + selectedMenuName +" MENU ] ");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요");
        int i=1;
        for ( Food f : foods ) {
            if(f!=null) {
                System.out.print(i++ + ". ");
                System.out.printf("%-20s | W %-4.1f | %-50s\n", f.getName(),f.price,f.content);
            }
        }
    }

    // 장바구니에서 Food 상세 정보 출력
    public void printFood(Food f) {
        System.out.printf("%-20s | W %-4.1f | %2d개 | %-50s\n", f.name,f.price,f.foodCnt,f.content);
    }

    // 총 주문 내역 출력
    public void printFood() {
        System.out.printf("%-20s | W %-4.1f(per) | %2d개 | W %-4.1f(Total)\n", this.name,this.price,this.foodCnt,(this.price*this.foodCnt));
    }

    // 해당 장바구니에 같은 음식이 있는지 Count하는 메서드
    public int setFoodCnt(Food selectedFood,List<Order> orderLists, int orderCnt,int cartFoodCnt) {
        int result = cartFoodCnt; // 같은 음식이 있을 경우에 count를 증가시킬 foodlist의 인덱스 번호 지정할 변수
        List<Food> fList = orderLists.get(orderCnt).cartFoods; // 장바구니

        for(int i=0; i<fList.size();i++){
            if(fList.get(i).getName().equals(selectedFood.getName())){
                result = i;
                selectedFood.foodCnt++;
            }
        }
        return result;
    }

    // 상품 번호 선택
    // 1번째 상품을 선택할 경우 food list에서 0번째 요소를 가져오기 위해 감소하여 반환.
    public static int selectFood() {
        Scanner sc = new Scanner(System.in);
        System.out.println("선택한 상품 번호 : ");
        int i = sc.nextInt();
        System.out.println();
        i--;
        return i;
    }
}
