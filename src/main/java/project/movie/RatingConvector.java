package project.movie;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConvector implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String s) {
        Rating[] ratings = Rating.values();
        for(Rating rating : ratings){
            if(rating.getValue().equals(s)){
                return rating;
            }
        }
        return null;
    }
}
