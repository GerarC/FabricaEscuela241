package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.SearchHistory;
import co.edu.udea.sitas.persistence.SearchHistoryRepository;
import co.edu.udea.sitas.services.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryServiceJPA implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public SearchHistoryServiceJPA(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public List<SearchHistory> findAll() {
        return searchHistoryRepository.findAll();
    }

    @Override
    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryRepository.save(searchHistory);
    }

    @Override
    public Optional<SearchHistory> findById(Long id) {
        return searchHistoryRepository.findById(id);
    }

    @Override
    public Optional<SearchHistory> update(Long id, SearchHistory searchHistory) {
        Optional<SearchHistory> searchHistoryOptional = searchHistoryRepository.findById(id);
        if (searchHistoryOptional.isPresent()) {
            searchHistory.setSearchHistoryId(id);
            return Optional.of(searchHistoryRepository.save(searchHistory));
        }
        return Optional.empty();
    }

    @Override
    public Optional<SearchHistory> delete(Long id) {
        Optional<SearchHistory> searchHistoryOptional = searchHistoryRepository.findById(id);
        if (searchHistoryOptional.isPresent()) {
            searchHistoryRepository.deleteById(id);
        }
        return searchHistoryOptional;
    }
}

