package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Artifact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {

    Optional<Artifact> findFirstByOrderByArtifactIdDesc();

    Optional<Artifact> findByNameAndAgeByAgeId_DescriptionAndCategoryByCategoryId_Name(String name, String age, String type);

    //по цене
    @Query("Select a from Artifact a  join Auction b  on a.artifactId=b.artifactId where b.priceNew between :price1 and :price2")
    List<Artifact> pricebetween(@Param("price1") Integer price1, @Param("price2") Integer price2);


    //по назв
    @Query("Select a from Artifact a  where a.name like :name% ")
    List<Artifact> namelike(@Param("name") String name);


    //по цене
    @Query("Select a from Artifact a left join Auction b  on a.artifactId=b.artifactId order by priceNew")
    List<Artifact> pricesort();


    //по назв
    @Query("Select a from Artifact a  order by a.name")
    List<Artifact> namesort();


    //по artifactid
    @Query("Select a from Artifact a order by a.artifactId")
    List<Artifact> artifact_idsort();


    List<Artifact> findAllByAgeId(Integer ageId);

    List<Artifact> findAllByApprover(Integer approver);

    List<Artifact> findAllByOwner(Integer owner);

    List<Artifact> findAllByBannedIsFalse(Pageable pageable);

    List<Artifact> findAllAuctionByArtifactIdIsNullAndBannedIsFalse(Pageable pageable);

    List<Artifact> findAllAuctionByArtifactIdNotNullAndBannedIsFalse(Pageable pageable);

    List<Artifact> findAllByApproved(Boolean approved);

    List<Artifact> findAllByName(String name);

    List<Artifact> findAllByCountryId(Integer countryId);

}