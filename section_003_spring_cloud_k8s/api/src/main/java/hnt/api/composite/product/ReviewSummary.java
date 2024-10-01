package hnt.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewSummary {
  private int reviewId;
  private String author;
  private String subject;
  private String content;
}
