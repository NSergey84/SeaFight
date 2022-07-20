import java.util.HashSet;

public class Ship {
    private int size;
    private String name;
    private HashSet<String> shipPosition;
    private HashSet<String> shipArea;

    public HashSet<String> getShipArea() {
        return shipArea;
    }

    public void setShipArea(HashSet<String> shipArea) {
        this.shipArea = shipArea;
    }

    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public HashSet<String> getShipPosition() {
        return shipPosition;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setShipPosition(HashSet<String> shipPosition) {
        this.shipPosition = shipPosition;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void forShipHits(Ship ship, String hit) {
        if (ship.getShipPosition().contains(hit)) {
            ship.getShipPosition().remove(hit);
            if (ship.getShipPosition().size() == 0) {
                System.out.printf("ТЫ потопил %s\nАх, ты - морской дьявол!!!\nПродолжай отбстрел дальше.\n", ship.getName());
            } else {
                System.out.println("Попадание!!!\nТак держать адмирал, ТЫ попал во вражиский корабль!");
            }
        }
    }
}
