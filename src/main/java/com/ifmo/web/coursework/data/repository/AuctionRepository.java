package com.ifmo.web.coursework.data.repository;

import com.ifmo.web.coursework.data.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    @Query("SELECT a from Auction a  join SubscriptionAuction b  on a.auctionId=b.auctionId where b.humanId = :human")
    List<Auction> findAllSubscriptionByHumanId(@Param("human") Integer human_id);

    @Query("SELECT a from Auction a  where a.auctionId = :art_id")
    List<Auction> findAllSubscriptionByHumanIdAnAndAuctionId(@Param("art_id") Integer art_id);

    @Query("SELECT a from Auction a where a.priceNew between :price1 and :price2")
    List<Auction> pricebetween(@Param("price1") Integer price1, @Param("price2") Integer price2);

    @Query("SELECT a from Auction a JOIN Artifact b on a.artifactId = b.artifactId where b.name like :name% ")
    List<Auction> namelike(@Param("name") String name);

    @Query("SELECT a from Auction a JOIN Artifact b  on a.artifactId=b.artifactId order by a.priceNew")
    List<Auction> pricesort();

    @Query("SELECT a from Auction a JOIN Artifact b on a.artifactId = b.artifactId order by b.name")
    List<Auction> namesort();
}
