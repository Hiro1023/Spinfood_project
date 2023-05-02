public class Teilnehmer {
    public enum FOOD_PREFERENCE {
        meat("meat"),
        none("none"),
        vegan("vegan"),
        veggie("veggie");

        private final String text;

        FOOD_PREFERENCE(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }


    }
    public enum SEX {
        male(0),
        female(1),
        other(2);

        private final int value;
        SEX(int value) {
            this.value = value;
        }

    }
    Kitchen kitchen;
    private String ID;
    private String name;
    private FOOD_PREFERENCE foodPreference;
    private int age;
    private SEX sex; //enum
    private int agerange; //enem
    private int count_WG;
    private String ID_2;
    private String name_2;
    private int age_2;
    private SEX sex_2; //enum


    public Teilnehmer(String ID, String name, String foodPreference, String age, String sex, String Kitchen,
                      String Kitchen_Story,String Kitchen_Longitude, String Kitchen_Latitude, String ID_2, String name_2,
                      String age_2, String sex_2) {
        this.ID = ID;
        this.name = name;
        this.foodPreference = FOOD_PREFERENCE.valueOf(foodPreference);
        this.age = Integer.parseInt(age);
        this.sex = (sex.equals(""))? SEX.other: SEX.valueOf(sex);
        Kitchen kitchen = new Kitchen(Kitchen,Double.parseDouble(Kitchen_Story),
                Double.parseDouble(Kitchen_Longitude),Double.parseDouble(Kitchen_Latitude));
        //this.agerange = agerange;
        //this.count_WG = count_WG;
        this.ID_2 = ID_2;
        this.name_2 = name_2;
        this.age_2 = (int) Double.parseDouble(age_2);
        this.sex_2 = (sex_2.equals(""))? SEX.other: SEX.valueOf(sex_2);
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

    public void setAgerange(int agerange) {
        this.agerange = agerange;
    }

    public void setCount_WG(int count_WG) {
        this.count_WG = count_WG;
    }

    public void setID_2(String ID_2) {
        this.ID_2 = ID_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public void setAge_2(int age_2) {
        this.age_2 = age_2;
    }

    public void setSex_2(SEX sex_2) {
        this.sex_2 = sex_2;
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

    public int getAgerange() {
        return agerange;
    }

    public int getCount_WG() {
        return count_WG;
    }

    public String getID_2() {
        return ID_2;
    }

    public String getName_2() {
        return name_2;
    }

    public int getAge_2() {
        return age_2;
    }

    public SEX getSex_2() {
        return sex_2;
    }

    public static void main(String[] args) {

        //Teilnehmer teilnehmer = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen,kitchenStory,kitchenLongitude,kitchenLatitude , ID_2, name_2, age_2, sex_2);

    }
}


