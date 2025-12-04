package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

public interface FreelancerContextFacade {

    Long createFreelancer(Long userId,String userType);

    void updateFreelancerUserType(Long freelancerId, String userType);

    void addPreference(Long freelancerId, String tag);

    void removePreference(Long freelancerId, String tag);

    void addFavoriteSpace(Long freelancerId, Long spaceId);

    void removeFavoriteSpace(Long freelancerId, Long spaceId);

    boolean existsFreelancerById(Long freelancerId);
}
