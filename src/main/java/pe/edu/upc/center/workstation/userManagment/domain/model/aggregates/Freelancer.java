package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "freelancers")
public class Freelancer extends AuditableAbstractAggregateRoot<Freelancer> {

    @NotBlank
    @Column(name = "user_type", length = 40, nullable = false)
    private String userType;

    @ElementCollection
    @CollectionTable(name = "freelancer_preferences", joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "tag", length = 40, nullable = false)
    private Set<String> preferences = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "freelancer_favorite_spaces", joinColumns = @JoinColumn(name = "freelancer_id"))
    @Column(name = "space_id", nullable = false)
    private Set<Long> favoriteSpaceIds = new LinkedHashSet<>();

    protected Freelancer() { }

    public Freelancer(String userType) {
        setUserType(userType);
    }

    public void updateUserType(String userType) {
        setUserType(userType);
    }

    public void addPreference(String tag) {
        preferences.add(normalizeTag(tag));
    }

    public void removePreference(String tag) {
        preferences.remove(normalizeTag(tag));
    }

    public void addFavoriteSpace(Long spaceId) {
        validateSpaceId(spaceId);
        favoriteSpaceIds.add(spaceId);
    }

    public void removeFavoriteSpace(Long spaceId) {
        validateSpaceId(spaceId);
        favoriteSpaceIds.remove(spaceId);
    }


    public String getUserType() {
        return userType;
    }

    public List<String> getPreferences() {
        return Collections.unmodifiableList(new ArrayList<>(preferences));
    }

    public List<Long> getFavoriteSpaceIds() {
        return Collections.unmodifiableList(new ArrayList<>(favoriteSpaceIds));
    }


    private void setUserType(String userType) {
        if (userType == null || userType.isBlank())
            throw new IllegalArgumentException("userType must not be blank");
        this.userType = userType.trim();
    }

    private String normalizeTag(String tag) {
        if (tag == null || tag.isBlank())
            throw new IllegalArgumentException("tag must not be blank");
        return tag.trim().toLowerCase();
    }

    private void validateSpaceId(Long spaceId) {
        if (spaceId == null || spaceId <= 0)
            throw new IllegalArgumentException("spaceId must be > 0");
    }
}
