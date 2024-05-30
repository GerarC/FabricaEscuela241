package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.SearchHistoryDTO;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.domain.model.SearchHistory;
import co.edu.udea.sitas.services.PersonService;
import co.edu.udea.sitas.services.ScaleService;
import co.edu.udea.sitas.services.SearchHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/search-histories")
@Tag(name = "Search History", description = "The Search History API")
public class SearchHistoryController {
    private final PersonService personService;
    private final ScaleService scaleService;
    private final SearchHistoryService searchHistoryService;

    @Autowired
    public SearchHistoryController(PersonService personService, ScaleService scaleService, SearchHistoryService searchHistoryService) {
        this.personService = personService;
        this.scaleService = scaleService;
        this.searchHistoryService = searchHistoryService;
    }

    @Operation(summary = "Get all search histories", description = "Returns a list of all search histories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the search histories", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SearchHistoryDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<SearchHistoryDTO>> getAllSearchHistories() {
        List<SearchHistoryDTO> searchHistoryDTOs = searchHistoryService.findAll().stream()
                .map(SearchHistoryDTO::buildSearchHistoryDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(searchHistoryDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get search history by ID", description = "Returns a single search history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the search history", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SearchHistoryDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Search history not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> getSearchHistoryById(@Parameter(description = "ID of the search history") @PathVariable Long id) {
        Optional<SearchHistory> searchHistoryOptional = searchHistoryService.findById(id);
        return searchHistoryOptional.map(searchHistory -> new ResponseEntity<>(SearchHistoryDTO.buildSearchHistoryDTO(searchHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new search history", description = "Creates a new search history.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Search history created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SearchHistoryDTO.class))
            })
    })
    @PostMapping
    public ResponseEntity<SearchHistoryDTO> createSearchHistory(@RequestBody SearchHistoryDTO searchHistoryDTO) {
        Scale scale = scaleService.findById(searchHistoryDTO.getScaleId()).orElseThrow();
        Person person = personService.findById(searchHistoryDTO.getPersonId()).orElseThrow();
        SearchHistory searchHistory = SearchHistory.builder()
                .person(person)
                .scale(scale)
                .searchDate(searchHistoryDTO.getSearchDate())
                .build();
        SearchHistory savedSearchHistory = searchHistoryService.save(searchHistory);
        return new ResponseEntity<>(SearchHistoryDTO.buildSearchHistoryDTO(savedSearchHistory), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing search history", description = "Updates an existing search history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search history updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SearchHistoryDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Search history not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> updateSearchHistory(
            @Parameter(description = "ID of the search history") @PathVariable Long id,
            @RequestBody SearchHistoryDTO searchHistoryDTO) {
        Scale scale = scaleService.findById(searchHistoryDTO.getScaleId()).orElseThrow();
        Person person = personService.findById(searchHistoryDTO.getPersonId()).orElseThrow();
        Optional<SearchHistory> searchHistoryOptional = searchHistoryService.update(id, SearchHistory.builder()
                .person(person)
                .scale(scale)
                .searchDate(searchHistoryDTO.getSearchDate())
                .build());
        return searchHistoryOptional.map(searchHistory -> new ResponseEntity<>(SearchHistoryDTO.buildSearchHistoryDTO(searchHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

