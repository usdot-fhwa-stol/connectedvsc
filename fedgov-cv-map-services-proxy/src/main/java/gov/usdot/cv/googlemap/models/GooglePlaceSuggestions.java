package gov.usdot.cv.googlemap.models;

import java.util.ArrayList;

public class GooglePlaceSuggestions {
    private ArrayList<GooglePlaceSuggestionItem> suggestions;

    public GooglePlaceSuggestions() {
    }

    public GooglePlaceSuggestions(ArrayList<GooglePlaceSuggestionItem> suggestions) {
        this.suggestions = suggestions;
    }

    public ArrayList<GooglePlaceSuggestionItem> getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(ArrayList<GooglePlaceSuggestionItem> suggestions) {
        this.suggestions = suggestions;
    }

    public GooglePlaceSuggestions suggestions(ArrayList<GooglePlaceSuggestionItem> suggestions) {
        setSuggestions(suggestions);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " suggestions='" + getSuggestions() + "'" +
            "}";
    }
    
}
