package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Order {
    public List<Food> cartFoods = new ArrayList<>();
    public List<Food> orderFoods = new ArrayList<>();
    private List<Food> saleFoods = new ArrayList<>();
    public int cartFoodCnt = 0;
    public int orderCnt = 0;

    // [ 주문 메서드 ]
    // 주문 타입 출력
    public int orderMenuPrint(List<Order> orderLists) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int orderCall = 0;
        int orderResult = 0;
        int cancelResult = 0;

        while (orderCall != 5 || orderCall != 6) { // 5번이나 6번이 아닌 경우 돌가
            System.out.println("\n ===== Order ==== ");
            System.out.printf("%d. %-20s | %s\n", 5, "Order", "장바구니를 확인 후 주문합니다.");
            System.out.printf("%d. %-20s | %s\n", 6, "Cancel", "진행중인 주문을 취소합니다.");

            System.out.println("원하는 항목의 번호를 입력하세요: ");
            orderCall = sc.nextInt();
            if (orderCall == 5) { // 장바구니 확인 후 주문
                orderResult = printCart(orderLists);
                if (orderResult == 1) { // 장바구니 정보 출력후 주문 (1)
                    this.takeOrder(orderLists);
                    break;
                } else if (orderResult == 2) {
                    break;
                } else {
                    System.out.println("장바구니에 상품이 없습니다.");
                    break;
                }
            } else if (orderCall == 6) { // 주문 취소
                cancelResult = orderCancel(orderLists);
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
    public void takeOrder(List<Order> orderLists) throws InterruptedException {
        orderLists.add(new Order());

        for (Food f : orderLists.get(this.orderCnt).cartFoods) {
            orderLists.get(this.orderCnt).orderFoods.add(f);
        }

        System.out.println("=============================================");
        System.out.println(" [ 주문 상세 ] ");

        for (Food f : orderLists.get(this.orderCnt).orderFoods) {
            f.printFood(f);
        }

        addSaleFoods(orderLists.get(this.orderCnt).orderFoods);

        System.out.println("주문이 완료되었습니다!");
        orderLists.get(this.orderCnt).cartFoods.clear();
        orderLists.get(this.orderCnt).cartFoodCnt = 0; // 주문 완료로 인해 장바구니에 담긴 상품 갯수 초기화
        this.orderCnt++;
        System.out.println("대기 번호는 [" + this.orderCnt + "] 번 입니다.");

        System.out.println("(3초 후 초기화면으로 돌아갑니다.)");
        System.out.println("=============================================");
        TimeUnit.SECONDS.sleep(3);
    }

    // 주문할 경우 saleFoods에 추가
    private void addSaleFoods(List<Food> cartFoods) {
        for (Food cf : cartFoods) {
            if (!saleFoods.isEmpty()) { // 판매된 상품이 있을 경우
                for (Food sf : this.saleFoods) {
                    if (sf.getName().equals(cf.getName())) {
                        // 판매 상품 목록에 주문 상품이 있을 경우 수행
                        sf.foodCnt += cf.foodCnt;
                    } else if(sf.getFoodCnt()==0){ // 판매 상품 목록에 없을 경우
                        this.saleFoods.add(new Food(cf.getName(), cf.getPrice(), cf.getContent(), cf.getFoodCnt()));
                    } else { // 판매 상품 목록에 없을 경우
                    }
                }// inner for() of the end
            } else { // 판매상품 비어있을 경우 추가.
                this.saleFoods.add(new Food(cf.getName(), cf.getPrice(), cf.getContent(), cf.getFoodCnt()));
            }
        }// outer for() of the end
    }

    // 주문 취소하기
    public int orderCancel(List<Order> orderLists) {
        Scanner sc = new Scanner(System.in);

        int result = 0; // 메뉴판으로 돌아가기: 0 , 주문 취소: 1-n

        // 2
        if (orderLists != null) {
            for (int i = 0; i < this.orderCnt; i++) {
                float sum = 0;
                System.out.println("=============================================");
                System.out.println(" [ " + (i + 1) + "번째 Order ]  ");
                for (Food f : orderLists.get(i).orderFoods) { // i번째 주문의 주문한 음식 출력
                    if (f.getPrice() == 0) { // 가격이 0원일 경우는 주문이 취소된 경우임.
                        System.out.println("취소된 주문");
                    } else {
                        f.printFood(f);
                        sum += (f.getPrice() * f.getFoodCnt());
                    }
                }
                System.out.println(" [ Total ] ");
                System.out.printf(" W %-4.1f\n\n", sum);
            }

            System.out.println("취소할 주문 번호를 입력해주세요");
            System.out.printf("%-10s %-10s\n", "1-10. 주문 취소", "0. 메뉴판");
            result = sc.nextInt();
            Order od = new Order();
            if (result > 0) { // 1번째 이상의 주문번호를 입력받으므로, 그외는 잘못 입력받은 경우임.
                if (orderLists.get(result - 1) != od) {
                    deleteSaleFoods(orderLists.get(result - 1), result - 1);
                    orderLists.set(result - 1, new Order());
                    System.out.println(result + "번째 주문이 취소되었습니다.");
                } else {
                    System.out.println("해당 번호에 맞는 주문이 없습니다.");
                }
            } else {
                // 구현없음
            }
        } else {
            System.out.println("주문한 내역이 없습니다.");
            System.out.println("=============================================\n");
            result = -1;
        }
        return result;
    }

    // 주문 취소할 경우 saleFoods에서 삭제
    private void deleteSaleFoods(Order order, int i) {
        List<Food> removed = new ArrayList<>();
        int resultCnt =0;
        if(!saleFoods.isEmpty()) { // 판매상품이 비어있지 않을 경우
            for (Food sf : saleFoods) {
//            if (sf!=null) {// 판매상품이 비어있지 않을 경우
                for (Food cancelF : order.orderFoods) {
                    System.out.println("취소할 주문 내역: ");
                    cancelF.printFood(cancelF);
                    if (cancelF.getName().equals(sf.getName())) {
                        // 판매 목록에 취소한 주문의 내역이 있을 경우
                        System.out.println("판매 목록에 취소한 주문의 내역이 있을 경우");
                        resultCnt = sf.getFoodCnt();
                        resultCnt = resultCnt - cancelF.getFoodCnt();
                        if (resultCnt == 0) { // 갯수가 0이라는건 판매목록에서 삭제해줘야 함.
                            removed.add(sf);
                            saleFoods.removeAll(removed);
                            saleFoods.add(null);
                            sf.printFood(sf);
                            System.out.println("판매상품에서 제거 완료");
                        } else {
                            sf.foodCnt = resultCnt;
                        }
                    }
                }// inner for() of the end
            }// outer for() of the end
        } else {// if() of the end
            System.out.println("판매한 상품이 없습니다.");
        }
    }// deleteSaleFoods() of the end

    // [ 장바구니 메서드 ]
    // 장바구니 상품 출력하기
    public int printCart(List<Order> orderLists) {
        Scanner sc = new Scanner(System.in);
        int result = 0;

        System.out.println("=============================================");
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        float sum = 0;

        System.out.println(" [ Order ]  ");
        /*
        System.out.println("printCart: ");
        for(Food f : orderLists.get(this.orderCnt).cartFoods){
            System.out.println(f.getName() +", "+ f.getFoodCnt());
        }
        */
        for (Food f : orderLists.get(this.orderCnt).cartFoods) {
            f.printFood(f);
            sum += (f.getPrice() * f.getFoodCnt());
        }
        System.out.println("\n [ Total ] ");
        System.out.printf(" W %-4.1f\n\n", sum);

        System.out.printf("%-10s %-10s", "1. 주문", "2. 메뉴판");
        System.out.println("\n원하는 항목 번호 입력 : ");
        result = sc.nextInt();
        return result;
    }

    // 장바구니 추가 확인 메서드
    public boolean checkCart(Food selectedFood, List<Order> orderLists) {
        Scanner sc = new Scanner(System.in);
        boolean result = false;

        System.out.print("==================== 장바구니 추가 확인 페이지 =================");
        System.out.printf("\n%-20s | %-4.1f | %-50s \n", selectedFood.getName(), selectedFood.getPrice(), selectedFood.getContent());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.printf("%-10s %-10s\n", "1. 확인", "2. 취소");

        while (true) {
            int call = sc.nextInt();
            if (call == 1) { // 장바구니에 추가
                if (this.addCart(selectedFood, orderLists)) { // 갯수를 추가한건지
                    result = true;
                    break;
                } else { // 새로운 상품을 넣은건지
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
    public boolean addCart(Food selectedFood, List<Order> orderLists) {
        orderLists.add(new Order());
        int addcartFoodCnt = selectedFood.setFoodCnt(selectedFood, orderLists, this.orderCnt, this.cartFoodCnt);
        int cnt = selectedFood.foodCnt;
        if (cnt == 1) {
            orderLists.get(this.orderCnt).cartFoods.add(new Food(selectedFood.getName(), selectedFood.getPrice(), selectedFood.getContent()));
        } else if (cnt > 1) { // carFoods에 추가할 필요없이 cnt만 증가시키면 됨.
            orderLists.get(this.orderCnt).cartFoods.get(addcartFoodCnt).foodCnt++;
        }

        orderLists.get(this.orderCnt).cartFoodCnt++;
        System.out.println();
        System.out.println(selectedFood.getName() + "가 장바구니에 추가되었습니다.");
        System.out.println("장바구니에 총 " + orderLists.get(this.orderCnt).cartFoodCnt + "개의 상품이 담겨있습니다.");
        System.out.println("=============================================\n");
        return true;
    }


    // 관리자 페이지 : 총 판매금액 조회 , 총 판매상품 목록
    public void adminPage() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=============================================");
            System.out.println("관리자 페이지\n");
            System.out.println("1. 총 판매 금액 현황 ");
            System.out.println("2. 총 판매상품 목록 현황 ");
            System.out.println("\n원하는 조회 항목 번호 입력 : ");

            int adminIndex = sc.nextInt();
            int goToMenu = 0;
            if (adminIndex == 1) { // 총 판매 금액
                goToMenu = this.printSalePrice();
                if (goToMenu == 1) {
                    break;
                }
            } else if (adminIndex == 2) { // 판매 상품 목록과 가격
                goToMenu = this.printSaleFoods();
                if (goToMenu == 1) {
                    break;
                }
            } else {
                System.out.println("알맞은 항목 번호를 입력해주세요. ");
                System.out.println("=============================================");
                continue;
            }
        }// while() of the End
    }

    // 판매한 상품 목록 출력
    private int printSaleFoods() {
        Scanner sc = new Scanner(System.in);

        int goMenu = 0; //1일 경우 메뉴로

        System.out.println("=============================================");
        System.out.println("\n[ 총 판매상품 목록 현황 ]");
        for (Food sf : this.saleFoods) {
            if(sf!=null) {
                System.out.print("- ");
                sf.printFood();
            }
        }
        System.out.println("\n1. 돌아가기");
        goMenu = sc.nextInt();
        return goMenu;
    }

    // 판매한 상품의 금액 합 출력
    private int printSalePrice() {
        Scanner sc = new Scanner(System.in);
        int goMenu = 0; //1일 경우 메뉴로
        double salesTotal = 0.0; // 총 판매금액

        for (Food sf : this.saleFoods) {
            if(sf!=null) {
                salesTotal += (sf.getFoodCnt() * sf.getPrice());
            }
        }

        System.out.println("=============================================");
        System.out.println("\n[ 총 판매금액 현황 ]");
        System.out.printf("현재까지 총 판매된 금액은 [ W %.1f ] 입니다.\n\n", salesTotal);

        System.out.println("1. 돌아가기");
        goMenu = sc.nextInt();
        return goMenu;
    }
}
