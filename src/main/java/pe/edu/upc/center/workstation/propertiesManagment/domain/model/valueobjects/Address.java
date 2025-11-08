package pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import pe.edu.upc.center.workstation.shared.utils.Util;

/**
 * Value object representing a street address.
 */
@Embeddable
public record Address(String street, String number, String city, String postalCode) {

    /**
     * Constructs an Address instance with the specified details.
     *
     * @param street      the street name
     * @param number      the street number
     * @param city        the city name
     * @param postalCode  the postal code
     */
    public Address {
        // Street validation
        if (Objects.isNull(street) || street.isBlank()) {
            throw new IllegalArgumentException("[StreetAddress] Street cannot be null or blank");
        }

        // Number validation
        if (Objects.isNull(number) || number.isBlank()) {
            throw new IllegalArgumentException("[StreetAddress] Street Number cannot be null or blank");
        }
        if (number.length() > Util.STREET_NUMBER_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("[StreetAddress] Street Number cannot have more than %d digits",
                            Util.STREET_NUMBER_MAX_LENGTH)
            );
        }

        // City validation
        if (Objects.isNull(city) || city.isBlank()) {
            throw new IllegalArgumentException("[StreetAddress] City cannot be null or blank");
        }

        // Postal code validation
        if (Objects.isNull(postalCode) || postalCode.isBlank()) {
            throw new IllegalArgumentException("[StreetAddress] Postal code cannot be null or blank");
        }
        if (postalCode.length() != Util.POSTAL_CODE_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("[StreetAddress] Postal code must be %d digits long",
                            Util.POSTAL_CODE_LENGTH)
            );
        }
    }

    /**
     * Default constructor for JPA.
     */
    public Address() {
        this(null, null, null, null);
    }

    /**
     * Returns the full address as a formatted string.
     *
     * @return a concatenation of all address components
     */
    public String getFullAddress() {
        return String.format("%s %s, %s, %s", street, number, city, postalCode);
    }
}
