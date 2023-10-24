class Room {
    private String key;
    private double price;
    private String type;

    public Room(String key, double price, String type) {
        this.key = key;
        this.price = price;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
