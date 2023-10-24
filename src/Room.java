class Room {
    private String key;// 방의 고유 키
    private int price;// 객실 가격
    private String type;// 객실 타입 (small, big, veryBig)

    public Room(String key, int price, String type) {
        this.key = key;
        this.price = price;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
