package hnt.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendationSummary {
  private int recommendationId;
  private String author;
  private int rate;
  private String content;
}
