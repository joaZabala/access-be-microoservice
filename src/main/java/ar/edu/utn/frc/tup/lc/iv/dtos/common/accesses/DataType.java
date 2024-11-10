package ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses;
/**
 * Represents the different types
 * of access data that can be queried.
 * This enum is used to filter access
 * data based on specific categories.
 */
public enum DataType {
    /**
     * Retrieve all access data
     * without filtering by type.
     */
    ALL,

    /**
     * Retrieve only access data
     * related to inconsistencies.
     */
    INCONSISTENCIES,

    /**
     * Retrieve only late access data.
     */
    LATE
}
