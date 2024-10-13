package com.adminservice.service;

import java.util.List;

import com.adminservice.dto.ComplaintProjection;
import com.adminservice.dto.UserProjection;
import com.adminservice.entity.Complaint;
import com.adminservice.entity.Orders;
import com.adminservice.entity.User;

public interface AdminServiceInterface {

	List<UserProjection> viewSellers();

	List<UserProjection> viewBuyers();

	List<ComplaintProjection> viewComplaintOnSeller();

	int login(User user);

	int register(User user);

	int activateUser(Long userId);

	int blockUser(Long userId);

	int deleteUser(Long userId);

	List<Complaint> getAllProducts();

	List<Orders> getAllOrderes();

	List<User> getAllBuyer();

	List<User> getAllSeller();

}
