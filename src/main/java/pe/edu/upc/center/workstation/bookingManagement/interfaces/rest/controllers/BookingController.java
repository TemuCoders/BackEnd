package pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetAllBookingsQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.queries.GetBookingByCodeQuery;
import pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects.BookingCode;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingCommandService;
import pe.edu.upc.center.workstation.bookingManagement.domain.services.BookingQueryService;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.assemblers.BookingAssembler;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.BookingResponse;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.ChangeStatusBookingRequest;
import pe.edu.upc.center.workstation.bookingManagement.interfaces.rest.resources.CreateBookingRequest;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.ServiceUnavailableResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins ="*", methods = {RequestMethod.POST, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bookings", description = "Booking Management Endpoints")
public class BookingController {
    private final BookingCommandService bookingCommandService;
    private final BookingQueryService bookingQueryService;

    public BookingController(BookingCommandService bookingCommandService, BookingQueryService bookingQueryService) {
        this.bookingCommandService = bookingCommandService;
        this.bookingQueryService = bookingQueryService;
    }

    /**
     * Endpoint to create a new booking.
     *
     * @param request the booking data to be created
     * @return a ResponseEntity containing the created booking resource or a bad request status
     *     if creation fails
     */

    @Operation(summary = "Create a new Booking",
            description = "Creates a new booking with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Booking data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateBookingRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest request
    ){
        //Create Booking
        var createBookingCommand = BookingAssembler.toCommandFromRequest(request);
        var bookingCode = this.bookingCommandService.handle(createBookingCommand);

        //is null or blank?
        if(Objects.isNull(bookingCode)|| bookingCode.code().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        //Fetch booking

        var getBookingByBookingCodeQuery = new GetBookingByCodeQuery(bookingCode);
        var booking = this.bookingQueryService.handle(getBookingByBookingCodeQuery);
        if(booking.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var bookingResource = BookingAssembler.toResponseFromEntity(booking.get());
        return new ResponseEntity<>(bookingResource, HttpStatus.CREATED);
    }

    /**
     * Get all bookings.
     *
     * @return a ResponseEntity containing a list of BookingResource
     */
    @Operation( summary = "Retrieve all bookings",
            description = "Retrieves all bookings in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BookingResponse.class)) ))
    })
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        var getAllBookingsQuery = new GetAllBookingsQuery();
        var bookings = this.bookingQueryService.handle(getAllBookingsQuery);
        var bookingResponses = bookings.stream()
                .map(BookingAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingResponses);
    }

    /**
     * Get a booking by booking code.
     *
     * @param bookingCode the booking code of the booking to be retrieved
     * @return a ResponseEntity containing the BookingResource or a bad request status if not found
     */
    @Operation(summary = "Retrieve a Booking by its ID",
            description = "Retrieves a Booking using its unique ID",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "bookingCode",
                            description = "Booking code of the profile to retrieve",
                            required = true,
                            schema = @Schema(type = "string", format = "uuid"))
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookingResponse.class)))
    })
    @GetMapping("/{bookingCode}")
    public ResponseEntity<BookingResponse> getBookingByBookingCode(@PathVariable String bookingCode){
        var getBookingByBookingCodeQuery = new GetBookingByCodeQuery(
                new BookingCode(bookingCode)
        );
        var optionalBooking = this.bookingQueryService.handle(getBookingByBookingCodeQuery);
        var bookingResponse = BookingAssembler.toResponseFromEntity(optionalBooking.get());
        return ResponseEntity.ok(bookingResponse);
    }

    /**
     * Change booking status with booking code.
     *
     * @param bookingCode the data for changing booking status.
     * @param request the data for updating the booking status
     * @return a ResponseEntity containing the updated BookingResource or a bad request status
     *     if the update fails
     */
    @Operation(summary = "Change booking status",
            description = "Changes the booking status using it's unique code",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "bookingCode",
                            description = "Booking code of the booking to retrieve",
                            required = true,
                            schema = @Schema(type = "string", format = "uuid"))
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status changed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping("/{bookingCode}/change")
    public ResponseEntity<BookingResponse> changeBookingStatus(
            @PathVariable String bookingCode,
            @Valid @RequestParam ChangeStatusBookingRequest request){
        var statusChangeCommand = BookingAssembler.toCommandFromRequest(bookingCode, request);
        var optionalBooking = this.bookingCommandService.handle(statusChangeCommand);

        if(optionalBooking.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var bookingMinimalResponse = BookingAssembler.toResponseFromEntity(optionalBooking.get());
        return ResponseEntity.ok(bookingMinimalResponse);
    }
}
