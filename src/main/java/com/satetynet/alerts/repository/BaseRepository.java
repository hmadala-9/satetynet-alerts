package com.satetynet.alerts.repository;

import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findByKey(String... keys);
    T save(T t);
    void deleteByKey(String... keys);
}
