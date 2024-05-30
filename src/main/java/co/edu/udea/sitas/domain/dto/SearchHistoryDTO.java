package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.SearchHistory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing a search history")
public class SearchHistoryDTO {

    @Schema(description = "Search history ID", example = "1")
    private Long searchHistoryId;

    @Schema(description = "Person ID", example = "100")
    private Long personId;

    @Schema(description = "Scale ID", example = "200")
    private Long scaleId;

    @Schema(description = "Search date", example = "2024-06-01")
    private LocalDate searchDate;

    @Schema(description = "Search query", example = "Flights to New York")
    private String searchQuery;

    public static SearchHistoryDTO buildSearchHistoryDTO(SearchHistory searchHistory) {
        log.info("Convert SearchHistory to SearchHistoryDTO");
        return SearchHistoryDTO.builder()
                .searchHistoryId(searchHistory.getSearchHistoryId())
                .personId(searchHistory.getPerson().getPersonId())
                .scaleId(searchHistory.getScale().getScaleId())
                .searchDate(searchHistory.getSearchDate())
                .searchQuery(searchHistory.getSearchQuery())
                .build();
    }
}

