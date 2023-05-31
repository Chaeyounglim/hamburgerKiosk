package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Order {
    private List<Food> cartFoods = new ArrayList<>();
    private List<Food> orderFoods = new ArrayList<>();
    private int cartFoodCnt = 0;

    // [ 주문 메서드 ]
    // 주문 타입 출력
    public int orderMenuPrint() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int orderCall = 0;
        int orderResult = 0;
        int cancelResult = 0;

        while (orderCall != 5 || orderCall != 6) { // 5번이나 6번이 아닌 경우 돌기
            System.out.println("\n ===== Order ==== ");
            System.out.printf("%d. %-20s | %s\n", 5, "Order", "장바구니를 확인 후 주문합니다.");
            System.out.printf("%d. %-20s | %s\n", 6, "Cancel", "진행중인 주문을 취소합니다.");

            System.out.println("원하는 항목의 번호를 입력하세요: ");
            orderCall = sc.nextInt();
            if (orderCall == 5) { // 장바구니 확인 후 주문
                orderResult = printCart();
                if (orderResult == 1) { // 장바구니 정보 출력후 주문 (1)
                    this.takeOrder();
                    break;
                } else if(orderResult == 2){
                    break;
                }else {
                    System.out.println("장바구니에 상품이 없습니다.");
                    break;
                }
            } else if (orderCall == 6) { // 주문 취소
                cancelResult = orderCancel();
                if (cancelResult > 0) { // 주문 취소한 경우
                    break;
                } else if (cancelResult == 0) { //주문한 내역 없는 경우
                    break;
                }
            } else { // 5,6번 둘다 아닌 상황
                break;
            }
        }
        return orderCall;
    }

    // 주문 하기
    private void takeOrder() throws InterruptedException {
        ;
        Order od = new Order();
        for (Food f : this.cartFoods) {
            od.orderFoods.add(f);
        }
        Main.orderList[Main.waitingOrderNumber] = od;

        System.out.println("=============================================");
        System.out.println(" [ 주문 상세 ] ");

        for (Food f : Main.orderList[Main.waitingOrderNumber].orderFoods) {
            f.printFood(f);
        }
        System.out.println("주문이 완료되었습니다!");
        this.cartFoods.clear();
        System.out.println("대기 번호는 [" + ++Main.waitingOrderNumber + "] 번 입니다.");
        System.out.println("(3초 후 초기화면으로 돌아갑니다.)");
        System.out.println("=============================================");
        TimeUnit.SECONDS.sleep(3);

        /*for(Food f : this.cartFoods){
            this.orderFoods.add(f);
        }
        System.out.println("=============================================");
        System.out.println("[ 주문 상세 ]");
        for(Food f : this.orderFoods){
            System.out.println(f.getFoodName());
        }
        System.out.println("주문이 완료되었습니다!");
        this.cartFoods.clear(); // 장바구니 비우기/
        System.out.println("대기 번호는 [" + ++Main.waitingOrderNumber + "] 번 입니다.");
        System.out.println("(3초 후 초기화면으로 돌아갑니다.)");
        System.out.println("=============================================");
        TimeUnit.SECONDS.sleep(3);
        }*/
    }

    // 주문 취소하기
    private int orderCancel() {
        Scanner sc = new Scanner(System.in);

        int result = 0; // 메뉴판으로 돌아가기: 0 , 주문 취소: 1-n
        if (Main.orderList != null) { // 주문 객체 배열에 주문 내역이 한개 이상이라도 있어야 함.
            for (int i = 0; i < Main.waitingOrderNumber; i++) {
                float sum = 0;
                System.out.println("=============================================");
                System.out.println(" [ " + (i+1) + "번째 Order ]  ");
                for (Food f : Main.orderList[i].orderFoods) {
                    if(f.getName().isEmpty()){
                        System.out.println("취소된 주문");
                    }else {
                        f.printFood(f);
                        sum += f.getFoodPrice();
                    }
                }
                System.out.println(" [ Total ] ");
                System.out.printf(" W %-4.1f\n\n", sum);
            }

            System.out.println("취소할 주문 번호를 입력해주세요");
            System.out.printf("%-10s %-10s\n", "1-n. 주문 취소", "0. 메뉴판");
            result = sc.nextInt();
            Order od = new Order();
            if (result > 0) { // 1번째 이상의 주문번호를 입력받으므로, 그외는 잘못 입력받은 경우임.
                if (Main.orderList[result - 1]!=od) {
                    Main.orderList[result - 1] = od;
                    // 주문이 3개이고 0,1,2 번째 중에서 1번째 주문을 취소할 경우 2번째 주문을 앞으로 땡겨줘야함.
                    //Main.waitingOrderNumber--;
                    System.out.println(result + "번째 주문이 취소되었습니다.");
                }else {
                    System.out.println("해당 번호에 맞는 주문이 없습니다.");
                }
            }
        }else {
            System.out.println("주문한 내역이 없습니다.");
            System.out.println("=============================================\n");
            result =-1;
        }
        /*
        if(this.orderFoods!=null) {
            Scanner sc = new Scanner(System.in);

            float sum = 0;
            System.out.println(" [ Order ]  ");
            for (Food f : this.orderFoods) {
                f.printFood(f);
                sum += f.getFoodPrice();
            }
            System.out.println("\n [ Total ] ");
            System.out.printf(" W %-4.1f\n\n", sum);

            System.out.println("상단과 같이 진행하던 주문을 취소하시겠습니까?");
            System.out.printf("%-10s %-10s", "1. 확인", "2. 취소");
            result = sc.nextInt();

            if (result == 1) {
                this.orderFoods.clear();
                Main.waitingOrderNumber--;
                System.out.println("진행하던 주문이 취소되었습니다.");
            }
        }else {
            System.out.println("진행중인 주문이 없습니다.");
        }*/
        return result;
    }

    // [ 장바구니 메서드 ]
    // 장바구니 상품 출력하기
    private int printCart() {
        Scanner sc = new Scanner(System.in);
        int result = 0;
        if (!this.cartFoods.isEmpty()) {
            System.out.println("=============================================");
            System.out.println("아래와 같이 주문 하시겠습니까?\n");
            float sum = 0;
            System.out.println(" [ Order ]  ");
            for (Food f : cartFoods) {
                f.printFood(f);
                sum += f.getFoodPrice();
            }
            System.out.println("\n [ Total ] ");
            System.out.printf(" W %-4.1f\n\n", sum);

            System.out.printf("%-10s %-10s", "1. 주문", "2. 메뉴판");
            System.out.println("\n원하는 항목 번호 입력 : ");
            result = sc.nextInt();
        }
        return result;
    }

    // 장바구니 추가 확인 메서드
    public boolean checkCart(Food selectedFood) {
        Scanner sc = new Scanner(System.in);
        boolean result = false;

        System.out.print("==================== 장바구니 추가 확인 페이지 =================");
        System.out.printf("\n%-20s | %-4.1f | %-50s \n", selectedFood.getName(), selectedFood.getFoodPrice(), selectedFood.getContent());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.printf("%-10s %-10s\n", "1. 확인", "2. 취소");

        while (true) {
            int call = sc.nextInt();
            if (call == 1) { // 장바구니에 추가
                if (this.addCart(selectedFood)) {
                    result = true;
                    break;
                }
            } else if (call == 2) { // 장바구니 취소
                result = true;
                break;
            } else {
                System.out.println("다시 입력해주세요");
                continue;
            }
        }
        return result;
    }

    // 장바구니에 추가하기
    private boolean addCart(Food food) {
        boolean rs = cartFoods.add(food);
        if (rs) {
            cartFoodCnt++;
            System.out.println(food.getName() + "가 장바구니에 추가되었습니다.");
            System.out.println("장바구니에 총 " + cartFoodCnt + "개의 상품이 담겨있습니다.");
        }
        return rs;
    }
}