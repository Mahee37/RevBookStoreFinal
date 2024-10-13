package com.adminservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adminservice.dto.ComplaintProjection;
import com.adminservice.entity.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long>{

	@Query(nativeQuery = true, value="select * from complaint c")
	List<ComplaintProjection> findAllComplaintBySellerId();
	
	

}
