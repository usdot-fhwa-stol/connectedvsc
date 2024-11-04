package gov.usdot.cv.googlemap.models;

public class GooglePlaceSuggestionItem {
    private GooglePlacePrediction placePrediction;


    public GooglePlaceSuggestionItem() {
    }

    public GooglePlaceSuggestionItem(GooglePlacePrediction suggestions) {
        this.placePrediction = suggestions;
    }

    public GooglePlacePrediction getPlacePrediction() {
        return this.placePrediction;
    }

    public void setPlacePrediction(GooglePlacePrediction suggestions) {
        this.placePrediction = suggestions;
    }

    public GooglePlaceSuggestionItem suggestions(GooglePlacePrediction suggestions) {
        setPlacePrediction(suggestions);
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " suggestions='" + getPlacePrediction() + "'" +
            "}";
    }

}
