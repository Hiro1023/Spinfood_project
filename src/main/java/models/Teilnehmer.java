package models;

public class Teilnehmer {



     public enum Geschlecht {
        male(0),
        female(1),
        other(2);

        private final int value;
        Geschlecht(int value) {
            this.value = value;
        }
    }

    
    private String id;
    private String name;
    private int foodPrefrence;
    private int age;
    private Geschlecht sex;
    Kitchen kitchen;
    private int ageRange;
    private int wgCount;
    private String id2;
    private String name2;
    private int age2;
    private Geschlecht sex2;

    //constructor
    public Teilnehmer(String id, String name, int foodPrefrence, int age, Geschlecht sex, String kitchenName, double kitchenStory, double kitchenLongitude, double kitchenLatitude, boolean avaliablity, int ageRange, int wgCount, String id2, String name2,int age2,Geschlecht sex2) {
        this.id = id;
        this.name = name;
        this.foodPrefrence = foodPrefrence;
        this.age = age;
        this.sex = sex;
        this.kitchen = new Kitchen(kitchenName, kitchenStory, kitchenLongitude, kitchenLatitude, avaliablity);
        this.ageRange = ageRange;
        this.wgCount = wgCount;
        this.id2 = id2;
        this.name2 = name2;
        this.age2 = age2;
        this.sex2= sex2;
    }



    //getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodPrefrence() {
        return foodPrefrence;
    }

    public void setFoodPrefrence(int foodPrefrence) {
        this.foodPrefrence = foodPrefrence;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Geschlecht getSex() {
        return sex;
    }

    public void setSex(Geschlecht sex) {
        this.sex = sex;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public int getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public int getWgCount() {
        return wgCount;
    }

    public void setWgCount(int wgCount) {
        this.wgCount = wgCount;
    }

    public int getAge2() {
        return age2;
    }
    public String getId2() {
        return id2;
    }
    public String getName2() {
        return name2;
    }
    public Geschlecht getSex2() {
        return sex2;
    }







}
