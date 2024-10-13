package com.buyerservice.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyerservice.dao.ComplaintRepsoitory;
import com.buyerservice.entity.Complaint;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BuyerComplaintServiceImpl implements BuyerComplaintService{

	@Autowired
	private ComplaintRepsoitory compRepo;
	@Override
	public int submitcomplaint(Complaint complaint) {
		// TODO Auto-generated method stub
		int i=0;
		compRepo.save(complaint);
		i=1;
		return i;
	}

}
