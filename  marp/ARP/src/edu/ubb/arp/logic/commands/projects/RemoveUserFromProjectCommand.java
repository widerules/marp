package edu.ubb.arp.logic.commands.projects;

import java.sql.SQLException;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import edu.ubb.arp.dao.DaoFactory;
import edu.ubb.arp.dao.ProjectsDao;
import edu.ubb.arp.dao.jdbc.JdbcDaoFactory;
import edu.ubb.arp.exceptions.DalException;
import edu.ubb.arp.logic.commands.BaseCommandOperations;
import edu.ubb.arp.logic.commands.Command;
/**
 * 
 * @author VargaAdorjan , TurdeanArnoldRobert
 *removes a user from a project
 */
public class RemoveUserFromProjectCommand extends BaseCommandOperations implements Command {
	private static final Logger logger = Logger.getLogger(RemoveUserFromProjectCommand.class);
	private JSONArray request = null;
	private JSONArray response = null;
	private DaoFactory instance = null;
	private ProjectsDao projectDao = null;
	
	/**
	 * constructor
	 * @param request contains a project name a user name and a week
	 */
	public RemoveUserFromProjectCommand (JSONArray request) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		
		try {
			this.response = new JSONArray();
			this.instance = JdbcDaoFactory.getInstance();
			this.projectDao = instance.getProjectsDao();
			this.request = request;
			
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			response = setError(0);
		}
		
	}
	/**
	 * @return returns the removed user's id or an error message 
	 */
	@Override
	public JSONArray execute() {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		logger.debug(getClass().getName() + methodName + "-> START");
		
		String projectName = null;
		String targetUserName = null;
		int currentWeek = 0;
		
		try {
			projectName = getString(0,"projectname", request);
			targetUserName = getString(0,"targetusername",request);
			currentWeek = getInt(0,"currentweek", request);
			
		} catch (IllegalStateException e) {
			logger.error(getClass().getName() + methodName + e);
			System.out.println("illegal state exception");
			response = setError(-1);
		}
		
		if (!errorCheck(response)) {
			try {
				int userRemovedFromProject = projectDao.removeUserFromProject(projectName, targetUserName, currentWeek);
				response = addInt("userremovedfromproject", userRemovedFromProject, response);
			} catch (DalException e) {
				logger.error(getClass().getName() + methodName + e.getErrorMessage());
				response = setError(e.getErrorCode());
			} catch (SQLException e) {
				logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
				response = setError(0);
			}
		}
		
		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return response;

	}
}
