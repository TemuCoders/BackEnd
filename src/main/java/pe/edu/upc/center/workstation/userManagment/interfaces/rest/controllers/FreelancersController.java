package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/freelancers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Freelancers", description = "Freelancer Management Endpoints")
public class FreelancersController {

    private final FreelancerQueryService freelancerQueryService;
    private final FreelancerCommandService freelancerCommandService;

    public FreelancersController(FreelancerQueryService freelancerQueryService,
                                 FreelancerCommandService freelancerCommandService) {
        this.freelancerQueryService = freelancerQueryService;
        this.freelancerCommandService = freelancerCommandService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> createFreelancer(@Valid @RequestBody CreateFreelancerRequest resource) {
        var cmd = CreateFreelancerCommandFromResourceAssembler.toCommand(resource);
        Long id = freelancerCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        var res = FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FreelancerResponse>> getAllFreelancers() {
        var list = freelancerQueryService.handle(new GetAllFreelancersQuery());
        var res = list.stream()
                .map(FreelancerResourceFromEntityAssembler::toResponseFromEntity)
                .toList();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{freelancerId}")
    public ResponseEntity<FreelancerResponse> getFreelancerById(@PathVariable long freelancerId) {
        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @PutMapping(path = "/{freelancerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> updateFreelancer(@PathVariable long freelancerId,
                                                               @Valid @RequestBody UpdateFreelancerRequest resource) {
        var cmd = UpdateFreelancerCommandFromResourceAssembler.toCommand(freelancerId, resource);
        var opt = freelancerCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @PatchMapping(path = "/{freelancerId}/user-type", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> updateUserType(@PathVariable long freelancerId,
                                                             @Valid @RequestBody UpdateFreelancerUserTypeRequest resource) {
        freelancerCommandService.handle(new UpdateFreelancerUserTypeCommand(freelancerId, resource.userType()));
        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @GetMapping("/{freelancerId}/preferences")
    public ResponseEntity<List<String>> getPreferences(@PathVariable long freelancerId) {
        var prefs = freelancerQueryService.handle(new GetFreelancerPreferencesQuery(freelancerId));
        return ResponseEntity.ok(prefs);
    }

    @DeleteMapping("/{freelancerId}")
    public ResponseEntity<Void> deleteFreelancer(@PathVariable long freelancerId) {
        freelancerCommandService.handle(new DeleteFreelancerCommand(freelancerId));
        return ResponseEntity.noContent().build();
    }
}
