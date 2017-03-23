package restFile.app.meta.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import restFile.app.meta.entity.FileMeta;


@Repository
public interface FileMetaDAO extends JpaRepository<FileMeta, Integer>
{
	//Find item with keyword anywhere
    @Query("select distinct m.id from FileMeta m where (m.name like CONCAT('%',:keyword,'%')) or (m.desc like CONCAT('%',:keyword,'%')) or (m.user like CONCAT('%',:keyword,'%'))")
    List<Integer> findIdByKeyword(@Param("keyword") String keyword);
    
    @Query("select distinct m.id from FileMeta m where m.user = :user")
    List<Integer> findIdByUser(@Param("user") String user);
    
    @Query("select distinct m from FileMeta m where m.uploadTime >= :hourago")
    List<FileMeta> findByLastHour(@Param("hourago") Timestamp hourago);
    
    
}
