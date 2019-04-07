package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {

    //test
    @Query("Select a from Artifact a  join Auction b  on a.artifactId=b.artifactId where b.priceNew = :price")
    List<Artifact> testselect(@Param("price") Integer price);

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

    List<Artifact> findAllByBanned(Boolean banned);

    List<Artifact> findAllByApproved(Boolean approved);

    List<Artifact> findAllByName(String name);

    List<Artifact> findAllByCountryId(Integer countryId);

}