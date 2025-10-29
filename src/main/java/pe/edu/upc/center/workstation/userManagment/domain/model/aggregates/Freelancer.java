package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.util.*;
import java.util.stream.Collectors;

import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.workstation.bookings.domain.model.valueobjects.BookingId;
import pe.edu.upc.center.workstation.properties.domain.model.valueobjects.SpaceId;

@Entity
@Table(name = "freelancers")
public class Freelancer extends AuditableAbstractAggregateRoot<Freelancer>{

    @Getter
    @NotNull
    @Column(name = "user_type", length = 20, nullable = false)
    private String userType;

    @ElementCollection
    @CollectionTable(name = "freelancer_preferences", joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "tag", length = 40, nullable = false)
    private Set<String> preferences = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_bookings", joinColumns = @JoinColumn(name = "freelancer_id"))
    @AttributeOverride(name = "value", column = @Column(name = "booking_id", nullable = false))
    private List<BookingId> activeBookings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_favorite_spaces", joinColumns = @JoinColumn(name = "freelancer_id"))
    @AttributeOverride(name = "value", column = @Column(name = "space_id", nullable = false))
    private Set<SpaceId> favoriteSpaces = new LinkedHashSet<>();

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

    public void addFavoriteSpace(Long spaceId) { favoriteSpaces.add(new SpaceId(spaceId)); }
    public void removeFavoriteSpace(Long spaceId) { favoriteSpaces.remove(new SpaceId(spaceId)); }

    public Set<String> getPreferences() { return Collections.unmodifiableSet(preferences); }
    public List<Long> getActiveBookingIds() { return activeBookings.stream().map(BookingId::value).toList(); }
    public Set<Long> getFavoriteSpaceIds() { return favoriteSpaces.stream().map(SpaceId::value).collect(Collectors.toUnmodifiableSet()); }
}
