import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class SeaFightGame {

    static int forDeckSize = 10;
    static String[][] array = new String[forDeckSize][forDeckSize];
    static String[][] userHits;
    static String s = "ABCDEFGHIJ";
    static int gameScore;
    static HashSet<String> allShips = new HashSet<>();
    static HashSet<String> copyOfTarget;
    static Ship fourShip = new Ship(4, "Линкор (4палубы)");
    static Ship threeShip1 = new Ship(3, "Фрегат (3палубы)");
    static Ship threeShip2 = new Ship(3, "Фрегат (3палубы)");
    static Ship twoShip1 = new Ship(2, "Эсминец (2 палубы)");
    static Ship twoShip2 = new Ship(2, "Эсминец (2 палубы)");
    static Ship twoShip3 = new Ship(2, "Эсминец (2 палубы)");
    static Ship oneShip1 = new Ship(1, "Крейсер (1 палуба)");
    static Ship oneShip2 = new Ship(1, "Крейсер (1 палуба)");
    static Ship oneShip3 = new Ship(1, "Крейсер (1 палуба)");
    static Ship oneShip4 = new Ship(1, "Крейсер (1 палуба)");

    public void playGame() {
        getAllShips();
        forMissCheck();
        doUserMap();
        firstShowUserMap();
        checkMyHit();
    }

    private String getPosition() {
        String b;
        Scanner sc = new Scanner(System.in);
        do {
            b = sc.nextLine();
            if (b.length() > 2 || b.isEmpty()) {
                System.out.println("Адмирал, определись с координатами и отдай четкое расспоряжение.\nЗадай координаты отбстрела в поле от 0 до 9 по вертикали и от A до J по горизонтали.");
            }
            } while (b.length() > 2 || b.isEmpty());
            String letterCoord = b.replaceAll("[0-9]", "");
            String numberCoord = b.replaceAll("[^0-9]", "");
            String hit = letterCoord.concat(numberCoord);
        return hit.toUpperCase();
    }

    private void getAllShips() {
        fourShip.setShipPosition(shipBuilder(array, s, fourShip));
        threeShip1.setShipPosition(shipBuilder(array, s, threeShip1));
        threeShip2.setShipPosition(shipBuilder(array, s, threeShip2));
        twoShip1.setShipPosition(shipBuilder(array, s, twoShip1));
        twoShip2.setShipPosition(shipBuilder(array, s, twoShip2));
        twoShip3.setShipPosition(shipBuilder(array, s, twoShip3));
        oneShip1.setShipPosition(shipBuilder(array, s, oneShip1));
        oneShip2.setShipPosition(shipBuilder(array, s, oneShip2));
        oneShip3.setShipPosition(shipBuilder(array, s, oneShip3));
        oneShip4.setShipPosition(shipBuilder(array, s, oneShip4));
    }

    private void checkMyHit() {
        System.out.println("Тривога первого уровня, в акватории замечена вражеская флотилия!!!\nТолько такой отважный адмирал как " +
                "ТЫ сможет дать им отпор.\nВперед не мешкая заставь их понять кто покоритель семи морей и настоящий морской дьявол.");
        System.out.println("Отдай команду своим матросам. Задай координаты отбстрела в поле от 0 до 9 по вертикали и от A до J по горизонтали.\nПример ввода: A1, 4F или b3, 7g и т.д...:");
        while (true) {
            gameScore++;
            String hit = getPosition();
            if (!allShips.contains(hit)) {
                System.out.println("Мимо!!!!\nПроклятье, но ничего, у нас ещё есть заряды");
            } else {
                allShips.remove(hit);
                fourShip.forShipHits(fourShip, hit);
                threeShip1.forShipHits(threeShip1, hit);
                threeShip2.forShipHits(threeShip2, hit);
                twoShip1.forShipHits(twoShip1, hit);
                twoShip2.forShipHits(twoShip2, hit);
                twoShip3.forShipHits(twoShip3, hit);
                oneShip1.forShipHits(oneShip1, hit);
                oneShip2.forShipHits(oneShip2, hit);
                oneShip3.forShipHits(oneShip3, hit);
                oneShip4.forShipHits(oneShip4, hit);
            }
            allShipsAreaCheck();
            showUserMap(hit);
            if (allShips.size() == 0) {
                System.out.println();
                System.out.println("-----------------------------------------------------");
                System.out.println("Игра окончена - ТЫ потопил все корабли!!!");
                System.out.println("Тебе удалось потопить все корабли с " + gameScore + "-й попытки");
                System.out.println("Такой адмирал как ТЫ заслуживает своего звания!");
                System.out.println("-----------------------------------------------------");
                break;
            }
        }
    }

    private static HashSet<String> shipBuilder(String[][] array, String s, Ship ship) {
        HashSet<String> builder = new HashSet<>();
        HashSet<String> missHit = new HashSet<>();
        while (true) {
            int rotation = (int) (Math.random() * 2);
            if (rotation == 0) {
                int firstPlace = (int) (Math.random() * forDeckSize);
                int firstPlace2ndItem = (int) (Math.random() * (forDeckSize - 1 - ship.getSize()));
                String check = null;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < ship.getSize() + 1; j++) {
                        try {
                            if (check != (array[firstPlace + i][firstPlace2ndItem + j])) {
                                check = array[firstPlace + i][firstPlace2ndItem + j];
                                break;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored){
                        }
                    }
                    if (check != null) { break;}
                }
                if (check == null) {
                    for (int i = 0; i < ship.getSize(); i++) {
                        String putPlace = s.charAt(firstPlace) + "" + (firstPlace2ndItem + i);
                        array[firstPlace][firstPlace2ndItem + i] = putPlace;
                        builder.add(putPlace);
                    }
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < ship.getSize() + 1; j++) {
                            if (firstPlace + i > -1 && firstPlace + i < 10 && firstPlace2ndItem + j > -1 && firstPlace2ndItem + j < 10) {
                                missHit.add(s.charAt(firstPlace + i) + "" + (firstPlace2ndItem + j));
                            }
                            if (i == 0) {
                                j = j + ship.getSize();
                            }
                        }
                    }
                    ship.setShipArea(missHit);
                    break;
                }
            } else {
                int firstPlace = (int) (Math.random() * (forDeckSize - 1 - ship.getSize()));
                int firstPlace2ndItem = (int) (Math.random() * forDeckSize);
                String check = null;
                for (int i = -1; i < ship.getSize() + 1; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            if (check != (array[firstPlace + i][firstPlace2ndItem + j])) {
                                check = array[firstPlace + i][firstPlace2ndItem + j];
                                break;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ignored){
                        }
                    }
                    if (check != null) { break;}
                }
                if (check == null) {
                    for (int i = 0; i < ship.getSize(); i++) {
                        String putPlace = s.charAt(firstPlace + i) + "" + (firstPlace2ndItem);
                        array[firstPlace + i][firstPlace2ndItem] = putPlace;
                        builder.add(putPlace);
                    }
                    for (int i = -1; i < ship.getSize() + 1; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (firstPlace + i > -1 && firstPlace + i < 10 && firstPlace2ndItem + j > -1 && firstPlace2ndItem + j < 10) {
                                missHit.add(s.charAt(firstPlace + i) + "" + (firstPlace2ndItem + j));
                            }
                            if (i > -1 && i < ship.getSize()) {
                                j++;
                            }
                        }
                    }
                    ship.setShipArea(missHit);
                    break;
                }
            }
        }
        return builder;
    }

    private void forMissCheck() {
        for (String[] s1 : array
        ) {
            allShips.addAll(Arrays.asList(s1));
        }
        allShips.remove(null);
   /*       Раскоменти, что бы увидить расположение кораблей
        for (String i : allShips
        ) {
            System.out.print(i + " ");
        } */
    }

    public void doUserMap() {
        copyOfTarget = new HashSet<>(allShips);
        userHits = new String[forDeckSize][forDeckSize];
         for (int i = 0; i < userHits.length; i++) {
            for (int j = 0; j < userHits.length; j++) {
                userHits[i][j] = "   ";
            }
        }
    }

    private void allShipsAreaCheck() {
        afterKillShip(fourShip);
        afterKillShip(threeShip1);
        afterKillShip(threeShip2);
        afterKillShip(twoShip1);
        afterKillShip(twoShip2);
        afterKillShip(twoShip3);
        afterKillShip(oneShip1);
        afterKillShip(oneShip2);
        afterKillShip(oneShip3);
        afterKillShip(oneShip4);
    }

    private void afterKillShip(Ship ship) {
        if (ship.getShipPosition().size() == 0) {
            for (String areaShip: ship.getShipArea()
                 ) {
                int forLatter = s.indexOf(areaShip.charAt(0));
                int forNumber = Integer.parseInt(areaShip.replaceAll("[^0-9]", ""));
                userHits[forLatter][forNumber] = (char) 664 + "  ";
            }
        }
    }

    private void firstShowUserMap() {
        System.out.println("    0  1  2  3  4  5  6  7  8  9");
        for (int i = 0; i < userHits.length; i++) {
            System.out.print(s.charAt(i) + "|  ");
            for (int j = 0; j < userHits.length; j++) {
                System.out.print(userHits[i][j]);
            }
            System.out.print("|" + s.charAt(i));
            System.out.println();
        }
    }

    private void showUserMap(String hit) {
        int forLatter = s.indexOf(hit.charAt(0));
        int forNumber;
        try {
            forNumber = Integer.parseInt(hit.replaceAll("[^0-9]", ""));
        }
        catch (NumberFormatException e) {
            forNumber = 100;
        }
        if (forLatter != -1 && forNumber < 10) {
             if (copyOfTarget.contains(hit)) {
                userHits[forLatter][forNumber] = (char) 1133 + "  ";
            } else {
                userHits[forLatter][forNumber] = (char) 664 + "  ";
            }
            System.out.println("    0  1  2  3  4  5  6  7  8  9");
            for (int i = 0; i < userHits.length; i++) {
                System.out.print(s.charAt(i) + "|  ");
                for (int j = 0; j < userHits.length; j++) {
                    System.out.print(userHits[i][j]);
                }
                System.out.print("|" + s.charAt(i));
                System.out.println();
            }
        }
        else {
            System.out.println("Адмирал, у нас наводка идет согласно установленной системы координат, без импровизаций!!!\nЗадай координаты" +
                    " отбстрела в поле от 0 до 9 по вертикали и от A до J по горизонтали.");
        }
    }
}

