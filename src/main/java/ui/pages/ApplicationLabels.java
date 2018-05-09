package ui.pages;

/**
 * Created by vevinmoza on 5/5/18.
 */
public enum ApplicationLabels {
    COMMUNITY("Community"),
    EXPLOREPATH("Explore path"),
    HOME("Home");


    private String label;

    ApplicationLabels(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
