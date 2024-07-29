package Filipino;

public class DatePriceModifier {
    private double modifier;
    private int startDate;
    private int endDate;

    public DatePriceModifier(double modifier, int startDate, int endDate) {
        this.modifier = modifier/100;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getModifier() {
        return modifier;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }
}
