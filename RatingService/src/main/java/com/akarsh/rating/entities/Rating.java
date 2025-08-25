package com.akarsh.rating.entities;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("user_ratings")
public class Rating {

    @Id
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;

    // In MySQL we were using @Entity annotation
    // In MongoDB we are using @Document annotation

    //also id's are automatically generated in MongoDB, so we don't need to use @GeneratedValue annotation

}
