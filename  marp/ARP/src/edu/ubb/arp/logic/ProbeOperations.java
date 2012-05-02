package edu.ubb.arp.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.ubb.arp.dao.DaoFactory;
import edu.ubb.arp.dao.GroupsDao;
import edu.ubb.arp.dao.ProjectsDao;
import edu.ubb.arp.dao.RequestsDao;
import edu.ubb.arp.dao.ResourcesDao;
import edu.ubb.arp.dao.UsersDao;
import edu.ubb.arp.dao.jdbc.JdbcDaoFactory;

public class ProbeOperations {
	DaoFactory instance;
	private Thread threads[];
	public TimerThread objects[];

	public void ProbeOperation() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println(methodName + "()");
		
		DaoFactory instance;
		
		try {
			objects = new TimerThread[ 1 ]; 
		    objects[ 0 ] = new TimerThread(); 
		    threads = new Thread[ 1 ]; 
		    threads[ 0 ] = new Thread(objects[ 0 ]); 
		    threads[ 0 ].start( ); 
		   
			
		    
		    
			instance = JdbcDaoFactory.getInstance();
			GroupsDao g = instance.getGroupsDao();
			ResourcesDao r = instance.getResourceDao();
			UsersDao u = instance.getUsersDao();
			ProjectsDao p = instance.getProjectsDao();
			RequestsDao req = instance.getRequestsDao();
			
			List<Integer> w = new ArrayList<Integer>();
			//w.add(5);
			//w.add(6);
			w.add(8);
			w.add(9);
			w.add(10);
			
			List<Integer> rat = new ArrayList<Integer>();
			rat.add(77);
			rat.add(77);
			rat.add(55);
			
			
			//int errmsg = req.createNewRequestForResource(w, rat, "Juuuh333", "Laptop1", "Project1");
			int errmsg = req.removeExpiredRequests(9);
			System.out.println(errmsg);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 7));
		 }
}
