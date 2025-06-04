package co.uco.golocal.golocalapi.controllers.location.state;

import co.uco.golocal.golocalapi.domain.location.StateDomain;
import co.uco.golocal.golocalapi.service.location.state.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateDomain>> getAllStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDomain> getStateById(@PathVariable UUID id) {
        return stateService.getStateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}