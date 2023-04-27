public class Kitchen {
    private boolean kitchen;
    private int Kitchen_Story;
    private double Kitchen_Longitude;
    private double Kichen_Latitude;

    public Kitchen(boolean kitchen, int Kitchen_Story, double Kitchen_Longitude, double Kichen_Latitude) {
        this.kitchen = kitchen;
        this.Kitchen_Story = Kitchen_Story;
        this.Kitchen_Longitude = Kitchen_Longitude;
        this.Kichen_Latitude = Kichen_Latitude;
    }

    public boolean getKitchen() {
        return kitchen;
    }

    public int getKitchen_Story() {
        return Kitchen_Story;
    }

    public double getKitchen_Longitude() {
        return Kitchen_Longitude;
    }

    public double getKichen_Latitude() {
        return Kichen_Latitude;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public void setKitchen_Story(int kitchen_Story) {
        this.Kitchen_Story = kitchen_Story;
    }

    public void setKitchen_Longitude(double kitchen_Longitude) {
        this.Kitchen_Longitude = kitchen_Longitude;
    }

    public void setKichen_Latitude(double kichen_Latitude) {
        this.Kichen_Latitude = kichen_Latitude;
    }
}
