package cm.socialfitnesshub.Profile;

public enum Gender {
    MALE, FEMALE, OTHER;

    public String toString() {
        switch (this) {

            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            default:
            case OTHER:
                return "Other";

        }
    }
}
