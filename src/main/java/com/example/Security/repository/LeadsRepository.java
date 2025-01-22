package com.example.Security.repository;

import com.example.Security.entity.Leads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeadsRepository extends JpaRepository<Leads,Long> {

//    @Query("SELECT l FROM Leads l " +
//            "LEFT JOIN l.users u " +
//            "WHERE LOWER(l.clientName) LIKE LOWER(CONCAT('%', :name, '%')) " +
//            "OR LOWER(l.phoneNo) LIKE LOWER(CONCAT('%', :name, '%')) " +
//            "OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :name, '%'))")
//    List<Leads> searchFlexibleLeadsByName(@Param("name") String name);



    @Query("SELECT l FROM Leads l " +
            "LEFT JOIN l.users u " +
            "WHERE l.accountId = :accountId " +
            "AND (LOWER(l.clientName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(l.phoneNo) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Leads> searchFlexibleLeadsByName(@Param("accountId") Long accountId, @Param("name") String name, Pageable pageable);






//    @Query("SELECT l FROM Leads l WHERE :assignedTo IS NULL OR :assignedTo IN (SELECT u.userId FROM l.users u)")
//    Page<Leads> findByAssignedTo(@Param("assignedTo") Long assignedTo, Pageable pageable);
//
//    @Query("SELECT l FROM Leads l WHERE :source IS NULL OR l.source = :source")
//    Page<Leads> findBySource(@Param("source") String source, Pageable pageable);
//
//    @Query("SELECT l FROM Leads l WHERE CAST(l.createdAt AS date) = CAST(:createdDate AS date)")
//    Page<Leads> findByCreatedDate(@Param("createdDate") String createdDate, Pageable pageable);


//    @Query("SELECT l FROM Leads l WHERE (:assignedTo IS NULL OR :assignedTo IN (SELECT u.userId FROM l.users u))")
//    Page<Leads> findByAssignedTo(@Param("assignedTo") Long assignedTo, Pageable pageable);
//
//    // Query for source filter (if source is provided)
//    @Query("SELECT l FROM Leads l WHERE (:source IS NULL OR l.source = :source)")
//    Page<Leads> findBySource(@Param("source") String source, Pageable pageable);
//
//    // Query for createdDate and lastDate filter (if both createdDate and lastDate are provided)
//    @Query("""
//        SELECT l FROM Leads l
//        WHERE (:createdDate IS NULL OR CAST(l.createdAt AS date) = CAST(:createdDate AS date))
//        AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= CAST(:lastDate AS date))
//    """)
//    Page<Leads> findByCreatedDateAndLastDate(@Param("createdDate") String createdDate, @Param("lastDate") String lastDate, Pageable pageable);
//
//    // Query to handle all filters (assignedTo, source, createdDate, lastDate)
//    @Query("""
//        SELECT l FROM Leads l
//        WHERE (:assignedTo IS NULL OR :assignedTo IN (SELECT u.userId FROM l.users u))
//        AND (:source IS NULL OR l.source = :source)
//        AND (:createdDate IS NULL OR CAST(l.createdAt AS date) = CAST(:createdDate AS date))
//        AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= CAST(:lastDate AS date))
//    """)
//    Page<Leads> findByFilters(@Param("assignedTo") Long assignedTo, @Param("source") String source,
//                              @Param("createdDate") String createdDate, @Param("lastDate") String lastDate,
//                              Pageable pageable);

    // Query for source filter (if source is provided)

//    @Query("""
//    SELECT l FROM Leads l
//    LEFT JOIN l.users u
//    WHERE (:assignedTo IS NULL OR u.userId = :assignedTo)
//    AND (:source IS NULL OR l.source = :source)
//    AND (
//        :createdDate IS NULL OR
//        l.createdAt BETWEEN
//        CAST(:createdDate AS timestamp) AND COALESCE(CAST(:lastDate AS timestamp), CURRENT_TIMESTAMP)
//    )
//""")
//    Page<Leads> findByFilters(
//            @Param("assignedTo") Long assignedTo,
//            @Param("source") String source,
//            @Param("createdDate") String createdDate,
//            @Param("lastDate") String lastDate,
//            Pageable pageable
//    );



    @Query("SELECT l FROM Leads l WHERE l.accountId = :accountId " +
            "AND (l.phoneNo = :phoneNo OR l.primaryEmail = :primaryEmail)")
    Leads findByAccountIdAndPhoneNoOrEmail(@Param("accountId") Long accountId,
                                           @Param("phoneNo") String phoneNo,
                                           @Param("primaryEmail") String primaryEmail);


//    @Query("SELECT l FROM Leads l JOIN l.users u WHERE (:assignedTo IS NULL OR u.userId = :assignedTo) AND (:source IS NULL OR l.source = :source)")
//    Page<Leads> findByAssignedToAndSource(
//            @Param("assignedTo") Long assignedTo,
//            @Param("source") String source,
//            Pageable pageable
//    );
//
//    @Query("SELECT l FROM Leads l JOIN l.users u WHERE (:assignedTo IS NULL OR u.userId = :assignedTo)")
//    Page<Leads> findByAssignedTo(
//            @Param("assignedTo") Long assignedTo,
//            Pageable pageable
//    );
//
//    @Query("SELECT l FROM Leads l WHERE (:source IS NULL OR l.source = :source)")
//    Page<Leads> findBySource(
//            @Param("source") String source,
//            Pageable pageable
//    );
//
//    @Query("SELECT l FROM Leads l WHERE CAST(l.createdAt AS date) = CAST(:createdDate AS date) AND CAST(l.createdAt AS date) <= CAST(:lastDate AS date)")
//    Page<Leads> findByCreatedDateAndLastDate(
//            @Param("createdDate") String createdDate,
//            @Param("lastDate") String lastDate,
//            Pageable pageable
//    );
//
//    @Query("SELECT l FROM Leads l JOIN l.users u WHERE (:assignedTo IS NULL OR u.userId = :assignedTo) AND (:source IS NULL OR l.source = :source) AND (:createdDate IS NULL OR l.createdAt BETWEEN CAST(:createdDate AS timestamp) AND COALESCE(CAST(:lastDate AS timestamp), CURRENT_TIMESTAMP))")
//    Page<Leads> findByFilters(
//            @Param("assignedTo") Long assignedTo,
//            @Param("source") String source,
//            @Param("createdDate") String createdDate,
//            @Param("lastDate") String lastDate,
//            Pageable pageable
//    );


// Query for filtering by assigned user and source
//@Query("SELECT l FROM Leads l JOIN l.users u WHERE (:assignedTo IS NULL OR u.userId = :assignedTo) " +
//        "AND (:source IS NULL OR l.source = :source)")
//Page<Leads> findByAssignedToAndSource(
//        @Param("accountId" )Long accountId,
//        @Param("assignedTo") Long assignedTo,
//        @Param("source") String source,
//        Pageable pageable
//);

    @Query("SELECT l FROM Leads l JOIN l.users u WHERE l.accountId = :accountId " +
            "AND (:assignedTo IS NULL OR u.userId = :assignedTo) " +
            "AND (:source IS NULL OR l.source = :source)")
    Page<Leads> findByAssignedToAndSource(
            @Param("accountId") Long accountId,
            @Param("assignedTo") Long assignedTo,
            @Param("source") String source,
            Pageable pageable
    );
    // Query for filtering by assigned user only
//    @Query("SELECT l FROM Leads l JOIN l.users u WHERE (:assignedTo IS NULL OR u.userId = :assignedTo)")
//    Page<Leads> findByAssignedTo(
//            @Param("accountId" )Long accountId,
//            @Param("assignedTo") Long assignedTo,
//            Pageable pageable
//    );

    @Query("SELECT l FROM Leads l JOIN l.users u WHERE l.accountId = :accountId " +
            "AND (:assignedTo IS NULL OR u.userId = :assignedTo)")
    Page<Leads> findByAssignedTo(
            @Param("accountId") Long accountId,
            @Param("assignedTo") Long assignedTo,
            Pageable pageable
    );

    // Query for filtering by source only
//    @Query("SELECT l FROM Leads l WHERE (:source IS NULL OR l.source = :source)")
//    Page<Leads> findBySource(
//            @Param("accountId" )Long accountId,
//            @Param("source") String source,
//            Pageable pageable
//    );
    @Query("SELECT l FROM Leads l WHERE l.accountId = :accountId " +
            "AND (:source IS NULL OR l.source = :source)")
    Page<Leads> findBySource(
            @Param("accountId") Long accountId,
            @Param("source") String source,
            Pageable pageable
    );


    // Query for filtering by created date and last date with proper date comparison
//    @Query("SELECT l FROM Leads l WHERE " +
//            "(:createdDate IS NULL OR CAST(l.createdAt AS date) >= CAST(:createdDate AS date)) " +
//            "AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= CAST(:lastDate AS date))")
//    Page<Leads> findByCreatedDateAndLastDate(
//            @Param("accountId" )Long accountId,
//            @Param("createdDate") String createdDate,
//            @Param("lastDate") String lastDate,
//            Pageable pageable
//    );

    @Query("SELECT l FROM Leads l WHERE l.accountId = :accountId " +
            "AND (:createdDate IS NULL OR CAST(l.createdAt AS date) >= CAST(:createdDate AS date)) " +
            "AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= CAST(:lastDate AS date))")
    Page<Leads> findByCreatedDateAndLastDate(
            @Param("accountId") Long accountId,
            @Param("createdDate") String createdDate,
            @Param("lastDate") String lastDate,
            Pageable pageable
    );


    // General query for filtering by assigned user, source, created date, and last date
//    @Query("SELECT l FROM Leads l JOIN l.users u WHERE " +
//            "(:assignedTo IS NULL OR u.userId = :assignedTo) " +
//            "AND (:source IS NULL OR l.source = :source) " +
//            "AND (:createdDate IS NULL OR CAST(l.createdAt AS date) >= CAST(:createdDate AS date)) " +
//            "AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= COALESCE(CAST(:lastDate AS date), CURRENT_DATE))")
//    Page<Leads> findByFilters(
//            @Param("accountId" )Long accountId,
//            @Param("assignedTo") Long assignedTo,
//            @Param("source") String source,
//            @Param("createdDate") String createdDate,
//            @Param("lastDate") String lastDate,
//            Pageable pageable
//    );
    @Query("SELECT l FROM Leads l JOIN l.users u WHERE " +
            "l.accountId = :accountId " +
            "AND (:assignedTo IS NULL OR u.userId = :assignedTo) " +
            "AND (:source IS NULL OR l.source = :source) " +
            "AND (:createdDate IS NULL OR CAST(l.createdAt AS date) >= CAST(:createdDate AS date)) " +
            "AND (:lastDate IS NULL OR CAST(l.createdAt AS date) <= COALESCE(CAST(:lastDate AS date), CURRENT_DATE))")
    Page<Leads> findByFilters(
            @Param("accountId") Long accountId,
            @Param("assignedTo") Long assignedTo,
            @Param("source") String source,
            @Param("createdDate") String createdDate,
            @Param("lastDate") String lastDate,
            Pageable pageable
    );

    boolean existsByAccountIdAndPrimaryEmailOrAccountIdAndPhoneNo(Long accountId1, String primaryEmail, Long accountId2, String phoneNo);

    List<Leads> findByAccountId(@Param("accountId") Long accountId);

//    @Query("SELECT l FROM leads l WHERE l.accountId = :accountId")
//@Query("SELECT l FROM Leads l WHERE l.accountId = :accountId")
//Page<Leads> LeadsByAccountId(@Param("accountId") Long accountId, Pageable pageable);

    @Query("SELECT l FROM Leads l WHERE l.accountId = :accountId ORDER BY l.updatedDate DESC")
    Page<Leads> LeadsByAccountId(@Param("accountId") Long accountId, Pageable pageable);



}
