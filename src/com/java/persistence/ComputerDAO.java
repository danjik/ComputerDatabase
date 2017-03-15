package com.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Computer;
import com.java.util.ComputerDBException;

public enum ComputerDAO implements IComputerDAO {
	INSTANCE;

	private Logger logger = LogManager.getRootLogger();

	/**
	 * The ConnectionDB could be get by using the static method getInstance() of
	 * the ConnectionDB class
	 */

	@Override
	public int getNbComputer() throws ComputerDBException {
		String query = "select count(*) from computer";
		int nbComputer = -1;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				Statement selectPStatement = conn.createStatement();) {
			try (ResultSet rs = selectPStatement.executeQuery(query)) {
				while (rs.next()) {
					nbComputer = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			logger.debug("getNbComputer  : " + e.getMessage());
			throw new ComputerDBException("getNbComputer " + e);
		}

		return nbComputer;
	}

	@Override
	public long createComputer(Computer newComputer) throws ComputerDBException {
		String query = "insert into computer(name,introduced,discontinued,company_id) values (?,?,?,?)";
		long generateId = -1;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement insertPStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			if (newComputer.getName() == null || !newComputer.getName().matches("^[a-zA-Z][a-zA-Z .-][a-zA-Z .-]+$"))
				throw new ComputerDBException("The name must be composed at least by 3 chars");
			insertPStatement.setString(1, newComputer.getName());
			if (newComputer.getIntroduced() != null)
				insertPStatement.setTimestamp(2, new Timestamp(newComputer.getIntroduced().getTime()));
			else
				insertPStatement.setTimestamp(2, null);
			if (newComputer.getDiscontinued() != null)
				insertPStatement.setTimestamp(3, new Timestamp(newComputer.getDiscontinued().getTime()));
			else
				insertPStatement.setTimestamp(3, null);
			insertPStatement.setLong(4, newComputer.getCompanyId());
			insertPStatement.executeUpdate();
			try (ResultSet rs = insertPStatement.getGeneratedKeys()) {
				while (rs.next()) {
					generateId = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			logger.debug("createComputer  : " + e.getMessage());
			throw new ComputerDBException("createComputer " + e);
		}
		return generateId;

	}

	@Override
	public Computer getComputerById(Long idToSelect) throws ComputerDBException {
		String query = "select * from computer where id=?";
		Computer selectComputer = null;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn.prepareStatement(query);) {

			selectPStatement.setLong(1, idToSelect);
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					selectComputer = new Computer.Builder().id(rs.getInt(1)).name(rs.getString(2))
							.introduced(rs.getTimestamp(3)).discontinued(rs.getTimestamp(4)).companyId(rs.getInt(5))
							.build();
				}
			}
		} catch (SQLException e) {
			logger.error("getComputerById  : " + e.getMessage());
			throw new ComputerDBException("getComputerById " + e);
		}

		return selectComputer;
	}

	@Override
	public List<Computer> getAllComputer() throws ComputerDBException {
		List<Computer> listComputer = new ArrayList<>();
		String query = "select * from computer";
		Computer selectComputer = null;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				Statement selectPStatement = conn.createStatement();) {
			try (ResultSet rs = selectPStatement.executeQuery(query)) {
				while (rs.next()) {
					selectComputer = new Computer.Builder().id(rs.getInt(1)).name(rs.getString(2))
							.introduced(rs.getTimestamp(3)).discontinued(rs.getTimestamp(4)).companyId(rs.getInt(5))
							.build();
					listComputer.add(selectComputer);
				}
			}
		} catch (SQLException e) {
			logger.error("getAllComputer  : " + e.getMessage());
			throw new ComputerDBException("getAllComputer " + e);
		}

		logger.debug("getAllComputer : " + listComputer.size() + " computers has been selected");
		return listComputer;
	}

	@Override
	public void deleteComputer(long idToDelete) throws ComputerDBException {
		String query = "delete from computer where id=?";
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement deletePStatement = conn.prepareStatement(query);) {
			deletePStatement.setLong(1, idToDelete);
			deletePStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("deleteComputer  : " + e.getMessage());
			throw new ComputerDBException("deleteComputer " + e);
		}

	}

	@Override
	public void updateComputer(Computer updateComputer) throws ComputerDBException {
		String query = "update computer set name=?,  introduced=?,discontinued=?,company_id=? where id=?";
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement updatePStatement = conn.prepareStatement(query);) {
			if (updateComputer.getName() == null
					|| !updateComputer.getName().matches("^[a-zA-Z][a-zA-Z -.0-9][a-zA-Z -.0-9]+$"))
				throw new ComputerDBException("The name must be composed at least by 3 chars");
			updatePStatement.setString(1, updateComputer.getName());

			if (updateComputer.getIntroduced() != null)
				updatePStatement.setTimestamp(2, new Timestamp(updateComputer.getIntroduced().getTime()));
			else
				updatePStatement.setTimestamp(2, null);
			if (updateComputer.getDiscontinued() != null)
				updatePStatement.setTimestamp(3, new Timestamp(updateComputer.getDiscontinued().getTime()));
			else
				updatePStatement.setTimestamp(3, null);
			updatePStatement.setLong(4, updateComputer.getCompanyId());
			updatePStatement.setLong(5, updateComputer.getId());
			updatePStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("updateComputer  : " + e);
			throw new ComputerDBException("updateComputer " + e);
		}
	}

}
