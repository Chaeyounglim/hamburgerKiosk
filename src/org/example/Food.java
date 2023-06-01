package org.example;

import java.util.Scanner;

public class Food extends Menu{
    private Menu menu;

    // 생성자 메서드
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

    public Menu getMenu() {
        return menu;
    }

    // 메서드
    // 상품 메뉴 출력 기능
    public static void printFoodlist(int selectedMenuNum, String selectedMenuName,Food[] foods) {
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
        System.out.printf("%-20s | W %-4.1f | %-50s\n", f.getName(),f.price,f.content);
    }

    // 상품 번호 선택
    public static int selectFood() {
        Scanner sc = new Scanner(System.in);
        System.out.println("선택한 상품 번호 : ");
        int i = sc.nextInt();
        System.out.println();
        i--;
        return i;
    }
}
