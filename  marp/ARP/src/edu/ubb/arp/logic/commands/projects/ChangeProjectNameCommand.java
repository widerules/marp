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
 *changes the name of a project
 */
public class ChangeProjectNameCommand extends BaseCommandOperations implements Command {
	private static final Logger logger = Logger.getLogger(ChangeProjectNameCommand.class);
	private JSONArray request = null;
	private JSONArray response = null;
	private DaoFactory instance = null;
	private ProjectsDao projectDao = null;
	
	/**
	 * constructor 
	 * @param request contains a project id and a new project name
	 */
	public ChangeProjectNameCommand (JSONArray request) {
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
	 *  @return returns the project's id or an error message
	 */
	@Override
	public JSONArray execute() {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		logger.debug(getClass().getName() + methodName + "-> START");
		
		int projectID = 0;
		String newProjectName = null; 
		
		try {
			projectID = getInt(0,"projectid",request);
			newProjectName = getString(0,"newprojectname",request);
			
		} catch (IllegalStateException e) {
			logger.error(getClass().getName() + methodName + e);
			System.out.println("illegal state exception");
			response = setError(-1);
		}
		
		if (!errorCheck(response)) {
			try {
				int projectNameChanged = projectDao.setProjectName(projectID, newProjectName);
				response = addInt("projectnamechanged", projectNameChanged, response);
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
