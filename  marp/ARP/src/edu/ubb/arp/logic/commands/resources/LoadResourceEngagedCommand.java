package edu.ubb.arp.logic.commands.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import edu.ubb.arp.dao.DaoFactory;
import edu.ubb.arp.dao.ResourcesDao;
import edu.ubb.arp.dao.jdbc.JdbcDaoFactory;
import edu.ubb.arp.exceptions.DalException;
import edu.ubb.arp.logic.commands.BaseCommandOperations;
import edu.ubb.arp.logic.commands.Command;
/**
 * 
 * @author VargaAdorjan , TurdeanArnoldRobert
 *loads booking in all projects of a resource
 */
public class LoadResourceEngagedCommand extends BaseCommandOperations implements Command {
	private static final Logger logger = Logger.getLogger(LoadResourceEngagedCommand.class);
	private JSONArray request = null;
	private JSONArray response = null;
	private DaoFactory instance = null;
	List<Integer> resultForAllProjects = null;
	List<Integer> resultForCurrentProject = null;
	private ResourcesDao resourceDao = null;
	
	/**
	 * constructor
	 * @param request contains a projectName an action  a resourceID , a startWeek and an endWeek
	 */
	public LoadResourceEngagedCommand (JSONArray request) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		
		try {
			this.response = new JSONArray();
			this.instance = JdbcDaoFactory.getInstance();
			this.resourceDao = instance.getResourceDao();
			this.request = request;
			this.resultForAllProjects = new ArrayList<Integer>();
			this.resultForCurrentProject = new ArrayList<Integer>();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			response = setError(0);
		}
		
	}
	
	/**
	 * @return returns the booking of the resource from start week to end week in all projects he works in or an error message
	 */
	@Override
	public JSONArray execute() {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		logger.debug(getClass().getName() + methodName + "-> START");
		
		String projectName = null;
		String action = null;
		int resourceID = 0;
		int startWeek = 0;
		int endWeek = 0;
		
		try {
			resourceID = getInt(0,"resourceid", request);
			startWeek = getInt(0,"startweek", request);
			endWeek = getInt(0,"endweek", request);
			projectName = getString(0,"projectname", request);
			action = getString(0, "action", request);
		} catch (IllegalStateException e) {
			logger.error(getClass().getName() + methodName + e);
			System.out.println("illegal state exception");
			response = setError(-1);
		}
		
		if (!errorCheck(response)) {
			try {
				resultForAllProjects = resourceDao.loadResourceTotalEngages(resourceID, startWeek, endWeek, projectName, action);

				if (action.equals("update")) {
					resultForCurrentProject = resourceDao.loadResourceCurrentProjectEngages(resourceID, startWeek, endWeek, projectName);
				}
				
				
				Iterator<Integer> it = resultForAllProjects.iterator();
				Iterator<Integer> it2 = resultForCurrentProject.iterator();
				String keys[] = new String[2];
				int elements[] = new int[2];
				
				while (it.hasNext()) {
					if(action.equals("update")) {
						keys[0] = "ratiototal";
						keys[1] = "ratioincurrentproject";
						elements[0] = it.next();
						elements[1] = it2.next();
						response = addMoreInt(keys, elements, response);
					} else {
						response = addInt("ratiototal", it.next(), response);
					}
				}
			}
			catch (DalException e) {
				logger.error(getClass().getName() + methodName + e.getErrorMessage());
				response = setError(e.getErrorCode());
			}
			catch (SQLException e) {
				logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
				response = setError(0);
			}
		}
		
		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return response;

	}
}
