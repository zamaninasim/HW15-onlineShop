package ir.maktab.model.enumeration;

public enum OrderStatus {
    RESERVED(1,"reserved"),
    CANCELED(2,"canceled"),
    PAID(3,"paid");

    private Integer id;
    private String name;

    OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static OrderStatus getVal(String name) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.name.equalsIgnoreCase(name.trim())) {
                return orderStatus;
            }
        }
        return CANCELED;
    }
}
