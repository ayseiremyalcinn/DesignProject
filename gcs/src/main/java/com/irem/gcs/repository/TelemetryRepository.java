package com.irem.gcs.repository;

import com.irem.gcs.entity.Telemetry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends MongoRepository<Telemetry, String> {

}
