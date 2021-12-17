package ir.maktab.model.enumeration;

public enum ProductType {
    ELECTRONICS(1, "electronics"),
    SHOE(2, "shoe"),
    READABLE(3, "readable"),
    UNKNOWN(4, "unknown");

    private Integer id;
    private String name;

    ProductType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductType getVal(String name) {
        for (ProductType productType : values()) {
            if (productType.name.equalsIgnoreCase(name.trim())) {
                return productType;
            }
        }
        return UNKNOWN;
    }
}
