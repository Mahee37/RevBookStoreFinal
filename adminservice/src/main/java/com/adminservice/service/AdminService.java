package com.adminservice.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminservice.dao.ComplaintRepository;
import com.adminservice.dao.OrdersRepository;
import com.adminservice.dao.UserRepository;
import com.adminservice.dto.ComplaintProjection;
import com.adminservice.dto.UserProjection;
import com.adminservice.entity.Complaint;
import com.adminservice.entity.Orders;
import com.adminservice.entity.User;

@Service
@Transactional
public class AdminService implements AdminServiceInterface{
	
	Logger log = Logger.getLogger("adminservice");	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ComplaintRepository comprepo;
	
	@Autowired
	private OrdersRepository orderdao;
	

	@Override
	public List<UserProjection> viewSellers() {
		// TODO Auto-generated method stub
		List<UserProjection> sellers = userRepo.findAllSellers();
		return sellers;
	}

	@Override
	public List<UserProjection> viewBuyers() {
		// TODO Auto-generated method stub
		List<UserProjection> buyers = userRepo.findAllBuyers();
		return buyers;
	}

	@Override
	public List<ComplaintProjection> viewComplaintOnSeller() {
		// TODO Auto-generated method stub
		List<ComplaintProjection> complaint = comprepo.findAllComplaintBySellerId();
		return complaint;
	}

	@Override
	public int login(User user) {
		if("admin".equals(user.getUserType()))
		{
			User admin = userRepo.findByEmailAndPassword(user.getEmail(),user.getPassword());
			if(admin!=null)
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int register(User admin) {
		// TODO Auto-generated method stub
		userRepo.save(admin);
		return 1;
	}

	@Override
	public int activateUser(Long userId) {
		// TODO Auto-generated method stub
		userRepo.activateUserById(userId);
		return 1;
	}

	@Override
	public int blockUser(Long userId) {
		// TODO Auto-generated method stub
		userRepo.blockUserById(userId);
		return 1;
	}

	@Override
	public int deleteUser(Long userId) {
		// TODO Auto-generated method stub
		userRepo.deleteUserById(userId);
		return 1;
	}
	
	@Override
	public List<Complaint> getAllProducts() {
		return comprepo.findAll();
	
	}

	@Override
	public List<Orders> getAllOrderes() {
		// TODO Auto-generated method stub
		return orderdao.findAll();
	}

	@Override
	public List<User> getAllBuyer() {
		// TODO Auto-generated method stub
		List<User> buyer = userRepo.findbuyer();
		System.out.println(buyer);
		return buyer;
	}

	@Override
	public List<User> getAllSeller() {
		String str = "seller";
		List<User> seller = userRepo.findseller(str);
		return seller;
	}


}
