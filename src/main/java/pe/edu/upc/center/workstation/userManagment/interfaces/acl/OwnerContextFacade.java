package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

public interface OwnerContextFacade {

    Long createOwner(String company, String ruc);

    Long fetchOwnerIdByRuc(String ruc);

    void registerSpaceForOwner(Long ownerId, Long spaceId);

    void removeSpaceForOwner(Long ownerId, Long spaceId);

    boolean existsOwnerById(Long ownerId);
}
