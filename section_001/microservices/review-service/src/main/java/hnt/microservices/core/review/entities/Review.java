package hnt.microservices.core.review.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews", indexes = { @Index(name = "reviews_unique_idx", unique = true, columnList = "productId,reviewId") })
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private int id;
    @Version
    private int version;
    private int productId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;

    public Review(int productId, int reviewId, String author, String subject, String content) {
        this.productId = productId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }
}
