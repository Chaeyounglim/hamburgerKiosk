package org.example;

import java.util.Scanner;
import java.util.Scanner;

public class Main {
    static Order[] orderList = new Order[10];// 주문 10개 할 수 있도록 Order 타입의 변수 선언
    static int waitingOrderNumber = 0; // 주문 대기 번호
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int start = 1; // 1:종료, 0:실행

        Menu[] menus = new Menu[4];
        Food[][] foods = new Food[4][5];
        Order order = new Order();

        // 메뉴 카테고리 설정
        menus[0] = new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거");
        menus[1] = new Menu("Frozen Custard", "매장에서 신선하게 만드는 아이스크림");
        menus[2] = new Menu("Drinks", "매장에서 직접 만드는 음료");
        menus[3] = new Menu("Beer", "뉴욕 브루클린 브루어리에서 양조한 맥주");
        Menu menuList = new Menu(menus);

        // 메뉴별 카테고리에 따른 음식 메뉴 설정
        // menus[0]
        foods[0][0] = new Food("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거");
        foods[0][1] = new Food("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거");
        foods[0][2] = new Food("Shroom Burger", 9.4, "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거");
        foods[0][3] = new Food("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거");
        foods[0][4] = new Food("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거");
        // menus[1]
        foods[1][0] = new Food("Vanilla Icecream", 1.0, "바닐라 아이스크림");
        foods[1][1] = new Food("Choco Icecream", 1.0, "초코 아이스크림");
        // menus[2]
        foods[2][0] = new Food("Lemonade", 1.5, "상큼한 레모네이드");
        foods[2][1] = new Food("Coke Cola", 1.5, "코카 콜라");
        foods[2][2] = new Food("Zero Soda", 1.5, "제로 코카 콜라");
        // menus[3]
        foods[3][0] = new Food("Cass", 1.5, "카쓰");
        foods[3][1] = new Food("Qingtao", 1.5, "칭따오");
        foods[3][2] = new Food("Draft Beer", 1.5, "생맥주");

        // 1. 메뉴 카테고리별 설명을 출력한다.
        // 2. 주문 타입을 출력
        // 3. 원하는 카테고리 입력받기
        // 4. 카테고리별 음식 출력하기
        // 5. 음식 선택하기
        // 6. 음식 장바구니에 추가하기
        // 7. 메뉴로 다시 돌아가기
        while(start==1) {
            System.out.println("\n=============================================");
            System.out.println("계속 실행하시겠습니까? (1 : 실행 , 0 : 종료) ");
            start =sc.nextInt();
            if(start==0){
                break;
            }

            int selectedMenuNum = -1; // 선택된 메뉴 카테고리 번호
            int orderOrMenuNum = -1; // 주문,주문 취소(5,6)인지 혹은 메뉴판인지(1,2,3,4)
            String selectedMenuName = ""; // 카테고리에 맞는 음식 출력할 때 매개변수로 넘겨줄 예정

            menuList.printMenu(); // 첫 메뉴판 출력
            orderOrMenuNum = order.orderMenuPrint(); // 주문 혹은 주문 취소
            while (orderOrMenuNum <= 4 && orderOrMenuNum > 0) { // 메뉴 선택 시 실행

                selectedMenuNum = menuList.setSelectMenu(orderOrMenuNum);

                // selectedMenu에 해당하는 메뉴판 출력
                selectedMenuName = menuList.getMenus()[selectedMenuNum].getName();
                Food.printFoodlist(selectedMenuNum, selectedMenuName, foods[selectedMenuNum]);

                // 음식 선택하기
                int selectedFoodNum = Food.selectFood();
                Food selectedFood = foods[selectedMenuNum][selectedFoodNum];

                // 장바구니 추가 확인
                if (order.checkCart(selectedFood)) {
                    break;
                }
            } // inner whlie() of the end

        } // outer whlie() of the end


    }// main() of the end
}
