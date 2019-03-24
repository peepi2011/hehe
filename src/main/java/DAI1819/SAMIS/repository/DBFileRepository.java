package DAI1819.SAMIS.repository;

import DAI1819.SAMIS.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
    DBFile findById(String id);
}
