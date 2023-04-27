public class Teilnehmer {
    Kitchen kitchen;
    private int ID;
    private String name;
    private int foodPreference;
    private int age;
    private int sex; //enum
    private int agerange; //enem
    private int count_WG;
    private int ID_2;
    private String name_2;
    private int age_2;
    private int sex_2; //enum

    public Teilnehmer(int ID, String name, int foodPreference, int age, int sex,boolean Kitchen, int Kitchen_Story,double Kitchen_Longitude, double Kitchen_Latitude, int ID_2, String name_2, int age_2, int sex_2) {
        this.ID = ID;
        this.name = name;
        this.foodPreference = foodPreference;
        this.age = age;
        this.sex = sex;
        Kitchen kitchen = new Kitchen(Kitchen,Kitchen_Story,Kitchen_Longitude,Kitchen_Longitude);
        this.agerange = agerange;
        this.count_WG = count_WG;
        this.ID_2 = ID_2;
        this.name_2 = name_2;
        this.age_2 = age_2;
        this.sex_2 = sex_2;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodPreference(int foodPreference) {
        this.foodPreference = foodPreference;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(int sex) {
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

    public void setID_2(int ID_2) {
        this.ID_2 = ID_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public void setAge_2(int age_2) {
        this.age_2 = age_2;
    }

    public void setSex_2(int sex_2) {
        this.sex_2 = sex_2;
    }
}
