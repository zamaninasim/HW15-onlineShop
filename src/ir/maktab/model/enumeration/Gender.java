package ir.maktab.model.enumeration;

public enum Gender {
    FEMALE(1,"Female"),
    MALE(2,"Male"),
    NOT_SET(3,"NOT_SET");
    private Integer id;
    private String name;

    Gender(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Gender getVal(String name){
        for (Gender gender:values()){
            if (gender.name.equalsIgnoreCase(name.trim())){
                return gender;
            }
        }
        return NOT_SET;
    }
}
