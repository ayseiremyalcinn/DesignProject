package com.irem.gcs.repository;

import com.irem.gcs.entity.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {
    List<Position> findBySatid(int satid);

}
