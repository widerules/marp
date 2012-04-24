package edu.ubb.arp.dao.impls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import edu.ubb.arp.dao.ResourcesDao;
import edu.ubb.arp.dao.model.Groups;
import edu.ubb.arp.dao.model.ResourceTypes;
import edu.ubb.arp.dao.model.Resources;
import edu.ubb.arp.exceptions.DalException;

public class ResourcesJdbcDao extends BaseDao implements ResourcesDao {

	public ResourcesJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	public ResourcesJdbcDao(DataSource dataSource, int maxResultSize) {
		super(dataSource, maxResultSize);
	}

	@Override
	public int createResource(String resourceName, boolean active, String resourceTypeName, String resourceGroupName)
			throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		Connection connection = null;
		java.sql.CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stmt = createProcedure(connection, "new_resource", 5);

			int paramIndex = 1;
			setString(stmt, paramIndex++, resourceName);
			setBoolean(stmt, paramIndex++, active);
			setString(stmt, paramIndex++, resourceTypeName);
			setString(stmt, paramIndex++, resourceGroupName);
			stmt.registerOutParameter(paramIndex++, java.sql.Types.INTEGER);

			stmt.executeUpdate();
			errmsg = stmt.getInt("Oerrmsg");
			if (errmsg < 0) {
				logger.error(getClass().getName() + methodName + DalException.errCodeToMessage(errmsg));
				return errmsg;
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			throw new SQLException(getClass().getName() + methodName + "SQL Exception: ", e);
		} finally {
			closeSQLObjects(connection, rs, stmt);
			logger.debug(getClass().getName() + methodName + "-> EXIT");
		}
		return errmsg;
	}

	public int createResource(Resources resource) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		int errmsg2 = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		if (resource.getGroups().size() != 0) {
			errmsg = createResource(resource.getResourceName(), resource.isActive(), resource.getResourceTypes()
					.getResourceTypeName(), resource.getGroups().get(0).getGroupName());
			if (errmsg > 0) { // createResource successfully
				resource.getGroups().remove(0);
				errmsg2 = addResourceToGroups(resource, resource.getGroups());
			}
			if (errmsg2 < 0) { // if is an error while add resources into groups the returned errmsg get that error code too.
				errmsg = errmsg2;
			}
		} else
			throw new NullPointerException(getClass().getName() + methodName + "Null Pointer: resource.groups = NULL");

		return errmsg;
	}

	@Override
	public int updateResource(String oldResourceName, boolean oldActive, String oldResourceTypeName, String oldResourceGroupName,
			String newResourceName, boolean newActive, String newResourceTypeName, String newResourceGroupName)
					throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		Connection connection = null;
		java.sql.CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stmt = createProcedure(connection, "update_resource", 9);

			int paramIndex = 1;
			setString(stmt, paramIndex++, oldResourceName);
			setBoolean(stmt, paramIndex++, oldActive);
			setString(stmt, paramIndex++, oldResourceTypeName);
			setString(stmt, paramIndex++, oldResourceGroupName);
			setString(stmt, paramIndex++, newResourceName);
			setBoolean(stmt, paramIndex++, newActive);
			setString(stmt, paramIndex++, newResourceTypeName);
			setString(stmt, paramIndex++, newResourceGroupName);
			stmt.registerOutParameter(paramIndex++, java.sql.Types.INTEGER);

			stmt.executeUpdate();
			errmsg = stmt.getInt("Oerrmsg");
			if (errmsg < 0) {
				logger.error(getClass().getName() + methodName + DalException.errCodeToMessage(errmsg));
				return errmsg;
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			throw new SQLException(getClass().getName() + methodName + "SQL Exception: ", e);
		} finally {
			closeSQLObjects(connection, rs, stmt);
			logger.debug(getClass().getName() + methodName + "-> EXIT");
		}
		return errmsg;
	}

	public int setActive(String resourceName, boolean active) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		Connection connection = null;
		java.sql.CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stmt = createProcedure(connection, "resource_set_active", 3);

			int paramIndex = 1;
			setString(stmt, paramIndex++, resourceName);
			setBoolean(stmt, paramIndex++, active);
			stmt.registerOutParameter(paramIndex++, java.sql.Types.INTEGER);

			stmt.executeUpdate();
			errmsg = stmt.getInt("Oerrmsg");
			if (errmsg < 0) {
				logger.error(getClass().getName() + methodName + DalException.errCodeToMessage(errmsg));
				return errmsg;
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			throw new SQLException(getClass().getName() + methodName + "SQL Exception: ", e);
		} finally {
			closeSQLObjects(connection, rs, stmt);
			logger.debug(getClass().getName() + methodName + "-> EXIT");
		}
		return errmsg;
	}

	public int addResourceToGroup(String resourceName, String groupName) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");
		Connection connection = null;
		java.sql.CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stmt = createProcedure(connection, "add_resource_to_group", 3);

			int paramIndex = 1;
			setString(stmt, paramIndex++, resourceName);
			setString(stmt, paramIndex++, groupName);
			stmt.registerOutParameter(paramIndex++, java.sql.Types.INTEGER);

			stmt.executeUpdate();
			errmsg = stmt.getInt("Oerrmsg");
			if (errmsg < 0) {
				logger.error(getClass().getName() + methodName + DalException.errCodeToMessage(errmsg));
				return errmsg;
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			throw new SQLException(getClass().getName() + methodName + "SQL Exception: ", e);
		} finally {
			closeSQLObjects(connection, rs, stmt);
			logger.debug(getClass().getName() + methodName + "-> EXIT");
		}
		return errmsg;
	}
	
	public int addResourceToGroup(Resources resource, Groups group) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");
		errmsg = addResourceToGroup(resource.getResourceName(), group.getGroupName());
		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return errmsg;
	}
	
	public int addResourceToGroups(String resourceName, String[] groupName) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		int i = 0;
		while (errmsg >= 0 && i < groupName.length) {
			errmsg = addResourceToGroup(resourceName, groupName[i++]);
		}

		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return errmsg;
	}
	
	public int addResourceToGroups(Resources resource, List<Groups> groups) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");

		int i = 0;
		while (errmsg >= 0 && i < groups.size()) {
			errmsg = addResourceToGroup(resource, groups.get(i++));
		}

		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return errmsg;
	}
	
	public int removeResourceFromGroup(String resourceName, String groupName) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");
		Connection connection = null;
		java.sql.CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stmt = createProcedure(connection, "remove_resource_from_group", 3);

			int paramIndex = 1;
			setString(stmt, paramIndex++, resourceName);
			setString(stmt, paramIndex++, groupName);
			stmt.registerOutParameter(paramIndex++, java.sql.Types.INTEGER);

			stmt.executeUpdate();
			errmsg = stmt.getInt("Oerrmsg");
			if (errmsg < 0) {
				logger.error(getClass().getName() + methodName + DalException.errCodeToMessage(errmsg));
				return errmsg;
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(getClass().getName() + methodName + "SQL Exception: " + e);
			throw new SQLException(getClass().getName() + methodName + "SQL Exception: ", e);
		} finally {
			closeSQLObjects(connection, rs, stmt);
			logger.debug(getClass().getName() + methodName + "-> EXIT");
		}
		return errmsg;
	}

	public int removeResourceFromGroup(Resources resource, Groups group) throws SQLException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int errmsg = 0;
		logger.debug(getClass().getName() + methodName + "-> START");
		errmsg = removeResourceFromGroup(resource.getResourceName(), group.getGroupName());
		logger.debug(getClass().getName() + methodName + "-> EXIT");
		return errmsg;
	}
	
	/*
	 * Kell ra tarolt eljaras
	 * 
	 * @Override public Resources LoadResourceByID(int id) throws DalException { logger.debug(getClass().getName() +
	 * ".LoadResourceByID() -> START");
	 * 
	 * Resources retvalue = new Resources(); Connection connection = null; java.sql.CallableStatement stmt = null; ResultSet rs = null;
	 * try { connection = getConnection(); stmt = createProcedure(connection, "load_resource_by_id", 1);
	 * 
	 * int paramIndex = 1; setInteger(stmt, paramIndex++, id);
	 * 
	 * rs = stmt.executeQuery(); while (rs.next()) { retvalue = (Resources) fillObject(rs); } } catch (SQLException e) {
	 * logger.error(getClass().getName() + ".LoadResourceByID() -> NO_CONNECTION"); throw new DalException(DalException.NO_CONNECTION,
	 * e); } finally { closeSQLObjects(connection, rs, stmt); logger.debug(getClass().getName() + ".LoadResourceByID() -> EXIT"); }
	 * 
	 * return retvalue; }
	 */
	/*
	 * Kell ra tarolt eljaras!
	 * 
	 * @Override public List<Resources> loadResourcesByGroup(Integer groupId) throws DalException { logger.debug(getClass().getName() +
	 * ".loadResourcesByGroup() -> START"); List<Resources> retvalue = new ArrayList<Resources>(); Connection connection = null;
	 * java.sql.CallableStatement stmt = null; ResultSet rs = null; try { connection = getConnection(); stmt =
	 * createProcedure(connection, "load_resource_by_group_id", 1);
	 * 
	 * int paramIndex = 1; setInteger(stmt, paramIndex++, groupId);
	 * 
	 * rs = stmt.executeQuery(); while (rs.next()) { retvalue.add((Resources) fillObject(rs)); } } catch (SQLException e) {
	 * logger.error(getClass().getName() + ".loadResourcesByGroup() -> NO_CONNECTION"); throw new
	 * DalException(DalException.NO_CONNECTION, e); } finally { closeSQLObjects(connection, rs, stmt);
	 * logger.debug(getClass().getName() + ".loadResourcesByGroup() -> EXIT"); }
	 * 
	 * return retvalue; }
	 */

	@Override
	protected Object fillObject(ResultSet rs) throws SQLException {
		Resources resource = new Resources();

		resource.setResourceID(getLong(rs, "ResourceID").intValue());
		resource.setResourceName(getString(rs, "ResourceName"));
		resource.setActive(getBool(rs, "Active"));
		ResourceTypes rt = new ResourceTypes();

		rt.setResourceTypesID(getLong(rs, "ResourceTypeID").intValue());
		rt.setResourceTypeName(getString(rs, "ResourceTypeName"));
		resource.setResourceTypes(rt);

		return resource;
	}

}
