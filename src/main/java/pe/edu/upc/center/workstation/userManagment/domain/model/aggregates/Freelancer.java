package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.*;




@Entity
@Table(name = "freelancers")
public class Freelancer extends AuditableAbstractAggregateRoot<Freelancer> {

    @Getter
    @NotNull
    @NotBlank
    @Size(max = 20)
    @Column(name = "user_type", length = 20, nullable = false)
    private String userType;

    @ElementCollection
    @CollectionTable(name = "freelancer_preferences",
            joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "tag", nullable = false, length = 50)
    private final Set<String> preferences = new LinkedHashSet<>();


    @ElementCollection
    @CollectionTable(name = "freelancer_favorite_spaces",
            joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "space_id", nullable = false)
    private final Set<Long> favoriteSpaces = new LinkedHashSet<>();

    public void addFavoriteSpace(long spaceId) {
        favoriteSpaces.add(spaceId);
    }

    public void removeFavoriteSpace(long spaceId) {
        favoriteSpaces.remove(spaceId);
    }

    public List<Long> getFavoriteSpaceIds() {
        return List.copyOf(favoriteSpaces);
    }

    protected Freelancer() { }

    public Freelancer(String userType) {
        this.userType = normalizeRequired(userType);
    }

    public void addPreference(String tag) {
        String t = normalize(tag);
        if (t.isEmpty()) throw new IllegalArgumentException("Preference required");
        preferences.add(t);
    }

    public void removePreference(String tag) {
        preferences.remove(normalize(tag));
    }



    public List<String> getPreferences() {
        return List.copyOf(preferences);
    }



    public void updateUserType(String newUserType) {
        String value = normalizeRequired(newUserType);
        if (value.length() > 20) throw new IllegalArgumentException("userType max length is 20");
        if (!value.equals(this.userType)) this.userType = value;
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private static String normalizeRequired(String s) {
        String v = s == null ? "" : s.trim();
        if (v.isEmpty()) throw new IllegalArgumentException("userType is required");
        return v;
    }
}
