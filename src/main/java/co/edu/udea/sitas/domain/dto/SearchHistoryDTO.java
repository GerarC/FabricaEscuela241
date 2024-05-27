package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.SearchHistory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryDTO {
    private Long searchHistoryId;
    private Long personId;
    private Long scaleId;
    private LocalDate searchDate;
    private String searchQuery;

    public static SearchHistoryDTO buildSearchHistoryDTO(SearchHistory searchHistory) {
        log.info("convert SearchHistory to SearchHistoryDTO");
        return SearchHistoryDTO.builder()
                .searchHistoryId(searchHistory.getSearchHistoryId())
                .personId(searchHistory.getPerson().getPersonId())
                .scaleId(searchHistory.getScale().getScaleId())
                .searchDate(searchHistory.getSearchDate())
                .searchQuery(searchHistory.getSearchQuery())
                .build();
    }
}
