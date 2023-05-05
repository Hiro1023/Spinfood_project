package models;

public class Participant {

    private Kitchen kitchen;
    private String ID;
    private String name;
    private FOOD_PREFERENCE foodPreference;
    private int age;
    private SEX sex; //enum
    private AGE_RANGE agerange; //enem
    private int count_WG;


    public Participant(String ID, String name, String foodPreference, String age, String sex, String Kitchen,
                       String Kitchen_Story, String Kitchen_Longitude, String Kitchen_Latitude) {
        this.ID = ID;
        this.name = name;
        this.foodPreference = FOOD_PREFERENCE.valueOf(foodPreference);
        this.age = (int) Double.parseDouble(age);
        this.sex = (sex.equals(""))? SEX.other: SEX.valueOf(sex);
        Kitchen kitchen = new Kitchen(Double.parseDouble(Kitchen_Story),
                Double.parseDouble(Kitchen_Longitude),Double.parseDouble(Kitchen_Latitude));
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodPreference(FOOD_PREFERENCE foodPreference) {
        this.foodPreference = foodPreference;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public void setAgerange(AGE_RANGE agerange) {
        this.agerange = agerange;
    }

    public void setCount_WG(int count_WG) {
        this.count_WG = count_WG;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public FOOD_PREFERENCE getFoodPreference() {
        return foodPreference;
    }

    public int getAge() {
        return age;
    }

    public SEX getSex() {
        return sex;
    }

    public AGE_RANGE getAgerange() {
        return agerange;
    }

    public int getCount_WG() {
        return count_WG;
    }
}


