package models;

import controller.Utility;

public class Participant implements Utility {

    private String ID;
    private String name;
    private FOOD_PREFERENCE foodPreference;
    private int age;
    private SEX sex; //enum
    private Kitchen kitchen;
    private AGE_RANGE agerange; //enem
    private int KitchenCount = 0;

    public int getKitchenCount() {
        return KitchenCount;
    }
    public void setKitchenCount(int kitchenCount) {
        KitchenCount = kitchenCount;
    }


    public Participant(String ID, String name, String foodPreference, String age, String sex, String availability,
                       String Kitchen_Story, String Kitchen_Longitude, String Kitchen_Latitude) {
        this.ID = ID;
        this.name = name;
        this.foodPreference = assignFoodPreference(foodPreference);
        this.age = (int) Double.parseDouble(age);
        this.sex = (sex.equals(""))? SEX.other: SEX.valueOf(sex);
        this.kitchen = (availability.equals("no"))?  null:new Kitchen (Double.parseDouble(Kitchen_Story), Double.parseDouble(Kitchen_Longitude),Double.parseDouble(Kitchen_Latitude));
    }

    private FOOD_PREFERENCE assignFoodPreference(String foodPreference) {
        switch (foodPreference) {
            case "meat":
                return FOOD_PREFERENCE.meat;
            case "none":
                return FOOD_PREFERENCE.none;
            case "veggie":
                return FOOD_PREFERENCE.veggie;
            default:
                return FOOD_PREFERENCE.vegan;
        }
    }


    @Override
    public void show(){
        System.out.println("Participant Name: " + name);
        System.out.println("Infos: ");
        System.out.println("ID: " + this.ID);
        System.out.println("Name: " + this.name);
        System.out.println("Food Preference: " + this.foodPreference);
        System.out.println("Age: " + this.age);
        System.out.println("Sex: " + this.sex);
        System.out.println("Kitchen: " + ((this.kitchen==null)?   "null":this.kitchen.showKitchen()) );
        System.out.println("Age Range: " + this.agerange);
    }

    @Override
    public boolean equal(Object o) {
        return false;
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


    public static void main(String[] args) {
        Participant par = new Participant("01be5c1f-4aa1-458d-a530-b1c109ffbb55","Person3","vegan","22","male","yes","1.0","8.681372017093311","50.5820794170933");
        par.show();
    }

}


