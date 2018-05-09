package ui.pages;

/**
 * Created by vevinmoza on 5/5/18.
 */
public enum SortLabels {
    POPULAR("Popular questions"),
    RECENT("Recent questions");

    private String label;

    SortLabels(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
