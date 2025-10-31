package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.util.*;

import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.workstation.bookings.domain.model.valueobjects.BookingId;

@Entity
@Table(name = "freelancers")
public class Freelancer extends AuditableAbstractAggregateRoot<Freelancer>{

    @Getter
    @NotNull
    @Column(name = "user_type", length = 20, nullable = false)
    private String userType;

    @ElementCollection
    @CollectionTable(name = "freelancer_preferences", joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "tag", nullable = false, length = 50)
    private final Set<String> preferences = new java.util.LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_bookings", joinColumns = @JoinColumn(name = "freelancer_id"))
    private final List<BookingId> activeBookings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_favorite_spaces", joinColumns = @JoinColumn(name = "freelancer_id"))
    private final Set<Long> favoriteSpaces = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_booked_spaces",
            joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "space_id", nullable = false)
    private final List<Long> bookedSpaceIds = new ArrayList<>();

    protected Freelancer() { }

    public Freelancer(String userType) {
        this.userType = Objects.requireNonNull(userType);
    }

    public void addPreference(String tag) {
        String t = normalize(tag);
        if (t.isEmpty()) throw new IllegalArgumentException("Preference required");
        preferences.add(t);
    }
    public void removePreference(String tag) {
        preferences.remove(normalize(tag));
    }
    private static String normalize(String s) { return s == null ? "" : s.trim().toLowerCase(); }

    public void registerBookingRef(Long bookingId) {
        var id = new BookingId(bookingId);
        boolean exists = activeBookings.stream().anyMatch(b -> b.value().equals(bookingId));
        if (exists) throw new IllegalArgumentException("Booking already registered");
        activeBookings.add(id);
    }
    public void cancelBookingRef(Long bookingId) {
        activeBookings.removeIf(b -> b.value().equals(bookingId));
    }


    public void addFavoriteSpace(Long spaceId) { favoriteSpaces.add(spaceId); }
    public void removeFavoriteSpace(Long spaceId) { favoriteSpaces.remove(spaceId); }

    public List<String> getPreferences() { return  List.copyOf(preferences); }
    public List<Long> getActiveBookingIds() { return activeBookings.stream().map(BookingId::value).toList(); }
    public List<Long> getFavoriteSpaceIds() { return List.copyOf(favoriteSpaces); }
    public List<Long> getBookedSpaceIds() {return List.copyOf(bookedSpaceIds);}

    public void updateUserType(String newUserType) {
        if (newUserType == null) throw new IllegalArgumentException("userType is required");
        String value = newUserType.trim();
        if (value.isEmpty()) throw new IllegalArgumentException("userType is required");
        if (value.length() > 50) throw new IllegalArgumentException("userType max length is 50");
        if (value.equals(this.userType)) return;
        this.userType = value;
    }
}
