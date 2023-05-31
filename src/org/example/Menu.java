package org.example;

public class Menu {
    private String name;
    private String content;

    public Menu[] menus;

    // 생성자 메서드
    public Menu(String name, String content) {
        this.name = name;
        this.content = content;
    }
    public Menu(Menu[] menus){
        this.menus = menus;
    }


    // getter() , setter() 메서드
    public String getName() {
        return name;
    }
    public String getContent() {
        return content;
    }
    public Menu[] getMenus() {
        return menus;
    }

    // 메뉴 출력
    public  void printMenu(){
        System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("\n [ SHAKESHACK MENU ] ");
        for (int j = 0; j < 4; j++) {
            System.out.print(j + 1 + ". ");
            System.out.printf("%-20s | %-50s",menus[j].getName(),menus[j].getContent());
            System.out.println();
        }
    }

    // 메뉴 카테고리 선택하기
    public int setSelectMenu(int menuNum) {
        menuNum--;
        return menuNum;
    }
}
