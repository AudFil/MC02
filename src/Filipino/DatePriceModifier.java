package Filipino;

public class DatePriceModifier {
    private double modifier;
    private int startDate;
    private int endDate;

    public DatePriceModifier(double modifier, int startDate, int endDate) {
        this.modifier = modifier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }
}
