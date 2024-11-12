package ar.edu.utn.frc.tup.lc.iv.models;
/**
 * Represents the different
 * periods that can be used for grouping access data.
 * Can be used to specify the time range
 * for grouping (e.g., by day, week, month, or year).
 */
public enum GroupByPeriod {
    /**
     * Group Day.
     * */
    DAY,
    /**
     * Group Week.
     * */
    WEEK,
    /**
     * Group Month.
     * */
    MONTH,
    /**
     * Group Year.
     * */
    YEAR
}
