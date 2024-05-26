package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.SearchHistoryDTO;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.domain.model.SearchHistory;
import co.edu.udea.sitas.services.PersonService;
import co.edu.udea.sitas.services.ScaleService;
import co.edu.udea.sitas.services.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/search-histories")
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

    @GetMapping
    public ResponseEntity<List<SearchHistoryDTO>> getAllSearchHistories() {
        List<SearchHistoryDTO> searchHistoryDTOs = searchHistoryService.findAll().stream()
                .map(SearchHistoryDTO::buildSearchHistoryDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(searchHistoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> getSearchHistoryById(@PathVariable Long id) {
        Optional<SearchHistory> searchHistoryOptional = searchHistoryService.findById(id);
        return searchHistoryOptional.map(searchHistory -> new ResponseEntity<>(SearchHistoryDTO.buildSearchHistoryDTO(searchHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> updateSearchHistory(@PathVariable Long id, @RequestBody SearchHistoryDTO searchHistoryDTO) {
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
