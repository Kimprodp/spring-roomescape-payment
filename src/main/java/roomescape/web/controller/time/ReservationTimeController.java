package roomescape.web.controller.time;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.TimeWithAvailableResponse;
import roomescape.service.time.TimeDeleteService;
import roomescape.service.time.TimeRegisterService;
import roomescape.service.time.TimeSearchService;

@RestController
@RequestMapping("/times")
class ReservationTimeController {

    private final TimeRegisterService registerService;
    private final TimeSearchService searchService;
    private final TimeDeleteService deleteService;

    public ReservationTimeController(TimeRegisterService registerService,
                                     TimeSearchService searchService,
                                     TimeDeleteService deleteService
    ) {
        this.registerService = registerService;
        this.searchService = searchService;
        this.deleteService = deleteService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = registerService.registerTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllReservationTimes() {
        List<ReservationTimeResponse> responses = searchService.findAllTimes();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTimeResponse> getReservationTime(@PathVariable Long id) {
        ReservationTimeResponse response = searchService.findTime(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TimeWithAvailableResponse>> getAvailableTimes(@RequestParam LocalDate date,
                                                                             @RequestParam Long themeId
    ) {
        List<TimeWithAvailableResponse> responses = searchService.findAvailableTimes(date, themeId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        deleteService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
