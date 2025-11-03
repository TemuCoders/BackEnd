package pe.edu.upc.center.workstation.properties.domain.model.valueobjects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Access(AccessType.FIELD)
public class Location implements Serializable {

    private String address;
    private String district;
    private String city;

    public Location() {}

    public Location(String address, String district, String city) {
        if (address == null || district == null || city == null)
            throw new IllegalArgumentException("All fields in Location are required");
        this.address = address;
        this.district = district;
        this.city = city;
    }

    public String getAddress() { return address; }
    public String getDistrict() { return district; }
    public String getCity() { return city; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location that = (Location) o;
        return Objects.equals(address, that.address)
                && Objects.equals(district, that.district)
                && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, district, city);
    }
}
