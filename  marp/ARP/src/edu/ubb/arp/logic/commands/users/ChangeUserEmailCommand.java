package edu.ubb.arp.logic.commands.users;

import java.sql.SQLException;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import edu.ubb.arp.dao.DaoFactory;
import edu.ubb.arp.dao.UsersDao;
import edu.ubb.arp.dao.jdbc.JdbcDaoFactory;
import edu.ubb.arp.exceptions.DalException;
import edu.ubb.arp.logic.commands.BaseCommandOperations;
import edu.ubb.arp.logic.commands.Command;
/**
 * 
 * @author VargaAdorjan , TurdeanArnoldRobert
 *changes a user's email 
 */
public class ChangeUserEmailCommand extends BaseCommandOperations implements Command {
	private static final Logger logger = Logger.getLogger(ChangeUserEmailCommand.class);
	private JSONArray request = null;
	private JSONArray response = null;
	private DaoFactory instance = null;
	private UsersDao userDao = null;
	
	/**
	 * constructor
	 * @param request contains a userNmae and an email
	 */
	public ChangeUserEmailCommand(JSONArray request) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		
		try {
			this.response = new JSONArray();
			this.instance = JdbcDaoFactory.getInstance();
			this.userDao = instance.getUsersDao();
			this.request = request;
			
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			response = setError(0);
		}
		
	}
	
	/**
	 * @return returns the id of the user if there was no error , otherwise an error message
	 */
	@Override
	public JSONArray execute() {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		logger.debug(getClass().getName() + methodName + "-> START");
		String userName = null;
		String newEmail = null;
		
		try {
			userName = getString(0,"username",request);
			newEmail = getString(0,"newemail",request);
			
		} catch (IllegalStateException e) {
			logger.error(getClass().getName() + methodName + e);
			response = setError(-1);
		}
		
		if (!errorCheck(response)) {
			try {
				int userEmailChanged = userDao.changeEmail(userName, newEmail);
				response = addInt("useremailchanged", userEmailChanged, response);
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
