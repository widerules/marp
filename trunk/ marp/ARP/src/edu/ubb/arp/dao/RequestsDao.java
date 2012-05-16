package edu.ubb.arp.dao;

import java.sql.SQLException;
import java.util.List;

import edu.ubb.arp.exceptions.DalException;

public interface RequestsDao {

	public int createNewRequestForUser(List<Integer> week, List<Integer> ratio, String senderUserName, String targetUserName,
			String projectName) throws SQLException, DalException;
	
	public int createNewRequestForResource(List<Integer> week, List<Integer> ratio, String senderUserName,
			String targetResourceName, String projectName) throws SQLException, DalException;
	
	public int updateRequestRatioOfUser(List<Integer> week, List<Integer> ratio, String senderUserName,
			String targetUserName, String projectName) throws SQLException, DalException;
	
	public int updateRequestRatioOfResource(List<Integer> week, List<Integer> ratio, String senderUserName,
			String targetResourceName, String projectName) throws SQLException, DalException;
	
	public int removeRequestFromSomebody(int resourceID, int requestID, int projectID) throws SQLException, DalException;
	
	public int removeExpiredRequests(int currentWeek) throws SQLException, DalException;
	
}
