package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.SearchHistory;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryService {
    List<SearchHistory> findAll();
    SearchHistory save(SearchHistory searchHistory);
    Optional<SearchHistory> findById(Long id);
    Optional<SearchHistory> update(Long id, SearchHistory searchHistory);
    Optional<SearchHistory> delete(Long id);
}
