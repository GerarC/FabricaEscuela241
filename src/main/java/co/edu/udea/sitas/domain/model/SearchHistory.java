package co.edu.udea.sitas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SearchHistory")
public class SearchHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_history_id")
    private Long searchHistoryId;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    private Scale scale;

    @Column(name = "search_date")
    private LocalDate searchDate;

    @Column(name="query")
    private String searchQuery;

    @Override
    public String toString() {
        return "SearchHistory{" +
                "searchHistoryId=" + searchHistoryId +
                ", person=" + person +
                ", scale=" + scale +
                ", searchDate=" + searchDate +
                ", searchQuery='" + searchQuery + '\'' +
                '}';
    }
}
