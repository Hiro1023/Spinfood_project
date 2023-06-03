package models;
import utility.Utility;

/**
 * The Participant class contains all information about a Participant
 */
public class Participant implements Utility {
    private String ID;
    private String name;
    private FOOD_PREFERENCE foodPreference;
    private int age;
    private SEX sex;
    private Kitchen kitchen;
    private AGE_RANGE agerange;

    private int KitchenCount = 0;

    public Participant(String ID, String name, String foodPreference, String age, String sex, String availability,
                       String Kitchen_Story, String Kitchen_Longitude, String Kitchen_Latitude) {
        this.ID = ID;
        this.name = name;
        this.foodPreference = assignFoodPreference(foodPreference);
        this.age = (int) Double.parseDouble(age);
        this.sex = (sex.equals(""))? SEX.other: SEX.valueOf(sex);
        this.kitchen = (availability.equals("no"))?  null:new Kitchen (Double.parseDouble(Kitchen_Story), Double.parseDouble(Kitchen_Longitude),Double.parseDouble(Kitchen_Latitude));
        this.agerange = assignAgeRange(this.age);
    }

    /**
     * This method assigns the corresponding enum for each age group
     * @param age the age of the Participant
     * @return AGE_RANGE
     */
    private AGE_RANGE assignAgeRange(int age) {
        if (age < 18) {
            return AGE_RANGE.LessThan18;
        } else if (age < 24) {
            return AGE_RANGE.LessThan24;
        } else if (age < 28) {
            return AGE_RANGE.LessThan28;
        } else if (age < 31) {
            return AGE_RANGE.LessThan31;
        } else if (age < 36) {
            return AGE_RANGE.LessThan36;
        } else if (age < 42) {
            return AGE_RANGE.LessThan42;
        } else if (age < 47) {
            return AGE_RANGE.LessThan47;
        } else if (age < 57) {
            return AGE_RANGE.LessThan57;
        } else {
            return AGE_RANGE.MoreThan56;
        }
    }


    /**
     * This method assigns FOOD_PREFERENCE for each participant's food preference
     * @param foodPreference the participant's food preference
     * @return FOOD_PREFERENCE the corresponding food preference
     */
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
    }

    @Override
    public boolean equal(Object o) {
        Participant p = (Participant) o;
        return ID.equals(p.ID);
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

    public int getKitchenCount() {
        return KitchenCount;
    }

    public void setKitchenCount(int kitchenCount) {
        KitchenCount = kitchenCount;
    }

}


