package ar.edu.utn.frc.tup.lc.iv.models;

/**
 * enum class representing the visitor types.
 */
public enum VisitorType {
    /** enum for owner. */
    OWNER,
    /** enum for worker. */
    WORKER,
    /** enum for visitor. */
    VISITOR,
    /** enum for employee. */
    EMPLOYEE,
    /** enum for provider. */
    PROVIDER,
    /** enum for provider organization. */
    PROVIDER_ORGANIZATION,
    /** enum for cohabitant. */
    COHABITANT,
    /** enum for emergency. */
    EMERGENCY;
    /**
     * Checks if the specified visitor
     * type is part of the enum.
     * @param type the {@link VisitorType} to check.
     * @return true if the visitor type is found; false otherwise.
     */
    public static boolean contains(VisitorType type) {
        for (VisitorType visitorType : values()) {
            if (visitorType == type) {
                return true;
            }
        }
        return false;
    }
}
