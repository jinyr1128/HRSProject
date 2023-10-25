class Room {
    private String key;     // 방의 고유 키 (이름)
    private int price;      // 객실 가격

    public Room(String key, int price) {
        this.key = key;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public int getPrice() {
        return price;
    }
}
