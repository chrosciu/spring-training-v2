package pl.wojtyna.trainings.spring.crowdsorcery.borrower;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrower-module/api/v0/borrowers")
public class BorrowerRestApi {

    public static final List<Borrower> BORROWERS = List.of(new Borrower("123", "George Borrower"),
            new Borrower("456", "Henry Borrower"),
            new Borrower("789", "Martin Borrower"));

    @GetMapping
    public List<Borrower> fetchAllBorrowers() {
        return BORROWERS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrower> fetchBorrowerById(@PathVariable String id) {
        //TODO
        //Find borrower with given id or throw exception and handle it in similar way as with investors
        //(message / translation etc.)
        return null;
    }

    @GetMapping("/confidential")
    public List<Borrower> fetchConfidentialBorrowers() {
        return List.of(new Borrower("111", "[Confidential] George Borrower"),
                       new Borrower("222", "[Confidential] Henry Borrower"));
    }
}
