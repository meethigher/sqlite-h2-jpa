package top.meethigher.sqliteh2jpa.entity.sqlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.meethigher.sqliteh2jpa.entity.sqlite.SQLite;

public interface SQLiteRepository extends JpaRepository<SQLite, Integer> {
}
