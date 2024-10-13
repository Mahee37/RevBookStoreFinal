package com.buyerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyerservice.entity.Complaint;

@Repository
public interface ComplaintRepsoitory extends JpaRepository<Complaint,Long>{

}
