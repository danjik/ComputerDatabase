package com.main.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.main.excilys.model.Company;
import com.main.excilys.model.Computer;
import com.main.excilys.util.ComputerDBException;

public enum ComputerDAO implements IComputerDAO {
	INSTANCE;

	private Logger logger = LogManager.getRootLogger();
	private static final int COMPUTER_ID_COLUMN = 1;
	private static final int COMPUTER_NAME_COLUMN = 2;
	private static final int COMPUTER_INTRODUCED_COLUMN = 3;
	private static final int COMPUTER_DISCONTINUED_COLUMN = 4;
	private static final int COMPUTER_COMPANY_ID_COLUMN = 5;
	private static final int COMPANY_ID_COLUMN = 6;
	private static final int COMPANY_NAME_COLUMN = 7;

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
			logger.error("getNbComputer  : " + e.getMessage());
			throw new ComputerDBException("getNbComputer " + e);
		}

		return nbComputer;
	}

	@Override
	public long createComputer(Computer newComputer)
			throws ComputerDBException {
		String query = "insert into computer(id,name,introduced,discontinued,company_id) values (?,?,?,?,?)";
		long generateId = -1;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement insertPStatement = conn.prepareStatement(
						query, Statement.RETURN_GENERATED_KEYS);) {
			insertPStatement.setLong(COMPUTER_ID_COLUMN, 0);
			if (newComputer.getName() == null || !newComputer.getName()
					.matches("^[a-zA-Z][a-zA-Z .-][a-zA-Z .-]+$")) {
				throw new ComputerDBException(
						"The name must be composed at least by 3 chars");
			}
			insertPStatement.setString(COMPUTER_NAME_COLUMN,
					newComputer.getName());
			if (newComputer.getIntroduced() != null) {
				insertPStatement.setTimestamp(COMPUTER_INTRODUCED_COLUMN,
						Timestamp.valueOf(
								newComputer.getIntroduced().atStartOfDay()));
			} else {
				insertPStatement.setTimestamp(COMPUTER_INTRODUCED_COLUMN, null);
			}
			if (newComputer.getDiscontinued() != null) {
				insertPStatement.setTimestamp(COMPUTER_DISCONTINUED_COLUMN,
						Timestamp.valueOf(
								newComputer.getDiscontinued().atStartOfDay()));
			} else {
				insertPStatement.setTimestamp(COMPUTER_DISCONTINUED_COLUMN,
						null);
			}
			insertPStatement.setLong(COMPUTER_COMPANY_ID_COLUMN,
					newComputer.getCompany().getId());
			insertPStatement.executeUpdate();
			try (ResultSet rs = insertPStatement.getGeneratedKeys()) {
				while (rs.next()) {
					generateId = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			logger.error("createComputer  : " + e.getMessage());
			e.printStackTrace();
			throw new ComputerDBException("createComputer " + e);
		}
		return generateId;

	}

	@Override
	public Computer getComputerById(Long idToSelect)
			throws ComputerDBException {
		String query = "select * from computer co left join company c on co.company_id = c.id where co.id=?";
		Computer selectComputer = null;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn
						.prepareStatement(query);) {

			selectPStatement.setLong(1, idToSelect);
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					LocalDate getIntroduced = rs
							.getTimestamp(COMPUTER_INTRODUCED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_INTRODUCED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					LocalDate getDiscontinued = rs
							.getTimestamp(COMPUTER_DISCONTINUED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_DISCONTINUED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					selectComputer = new Computer.Builder()
							.id(rs.getInt(COMPUTER_ID_COLUMN))
							.name(rs.getString(COMPUTER_NAME_COLUMN))
							.introduced(getIntroduced)
							.discontinued(getDiscontinued)
							.company(new Company.Builder()
									.id(rs.getLong(COMPANY_ID_COLUMN))
									.name(rs.getString(COMPANY_NAME_COLUMN))
									.build())
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
		String query = "select * from computer co left join company c on co.company_id = c.id";
		Computer selectComputer = null;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				Statement selectPStatement = conn.createStatement();) {
			try (ResultSet rs = selectPStatement.executeQuery(query)) {
				while (rs.next()) {
					LocalDate getIntroduced = rs
							.getTimestamp(COMPUTER_INTRODUCED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_INTRODUCED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					LocalDate getDiscontinued = rs
							.getTimestamp(COMPUTER_DISCONTINUED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_DISCONTINUED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					selectComputer = new Computer.Builder()
							.id(rs.getInt(COMPUTER_ID_COLUMN))
							.name(rs.getString(COMPUTER_NAME_COLUMN))
							.introduced(getIntroduced)
							.discontinued(getDiscontinued)
							.company(new Company.Builder()
									.id(rs.getLong(COMPANY_ID_COLUMN))
									.name(rs.getString(COMPANY_NAME_COLUMN))
									.build())
							.build();
					listComputer.add(selectComputer);
				}
			}
		} catch (SQLException e) {
			logger.error("getAllComputer  : " + e.getMessage());
			e.printStackTrace();
			throw new ComputerDBException("getAllComputer " + e);
		}
		logger.debug("getAllComputer : " + listComputer.size()
				+ " computers has been selected");
		return listComputer;
	}

	@Override
	public void deleteComputer(long idToDelete) throws ComputerDBException {
		String query = "delete from computer where id=?";
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement deletePStatement = conn
						.prepareStatement(query);) {
			deletePStatement.setLong(1, idToDelete);
			deletePStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("deleteComputer  : " + e.getMessage());
			throw new ComputerDBException("deleteComputer " + e);
		}

	}

	@Override
	public void updateComputer(Computer updateComputer)
			throws ComputerDBException {
		String query = "update computer set id=?, name=?,  introduced=?,discontinued=?,company_id=? where id=?";
		final int idWhereColumn = 6;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement updatePStatement = conn
						.prepareStatement(query);) {
			if (updateComputer.getName() == null || !updateComputer.getName()
					.matches("^[a-zA-Z][a-zA-Z -.0-9][a-zA-Z -.0-9]+$")) {
				throw new ComputerDBException(
						"The name must be composed at least by 3 chars");
			}
			updatePStatement.setLong(COMPUTER_ID_COLUMN,
					updateComputer.getId());
			updatePStatement.setString(COMPUTER_NAME_COLUMN,
					updateComputer.getName());

			if (updateComputer.getIntroduced() != null) {
				updatePStatement.setTimestamp(COMPUTER_INTRODUCED_COLUMN,
						Timestamp.valueOf(
								updateComputer.getIntroduced().atStartOfDay()));
			} else {
				updatePStatement.setTimestamp(COMPUTER_INTRODUCED_COLUMN, null);
			}
			if (updateComputer.getDiscontinued() != null) {
				updatePStatement.setTimestamp(COMPUTER_DISCONTINUED_COLUMN,
						Timestamp.valueOf(updateComputer.getDiscontinued()
								.atStartOfDay()));
			} else {
				updatePStatement.setTimestamp(COMPUTER_DISCONTINUED_COLUMN,
						null);
			}
			updatePStatement.setLong(COMPUTER_COMPANY_ID_COLUMN,
					updateComputer.getCompany().getId());
			updatePStatement.setLong(idWhereColumn, updateComputer.getId());
			updatePStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("updateComputer  : " + e);
			throw new ComputerDBException("updateComputer " + e);
		}
	}

	@Override
	public List<Computer> getComputerInRange(long idBegin, long nbObjectToGet) {
		String query = "select * from computer co left join company c on co.company_id = c.id limit ?,?";
		List<Computer> listComputer = new ArrayList<>();
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn
						.prepareStatement(query);) {

			selectPStatement.setLong(1, idBegin);
			selectPStatement.setLong(2, nbObjectToGet);
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					LocalDate getIntroduced = rs
							.getTimestamp(COMPUTER_INTRODUCED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_INTRODUCED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					LocalDate getDiscontinued = rs
							.getTimestamp(COMPUTER_DISCONTINUED_COLUMN) != null
									? rs.getTimestamp(
											COMPUTER_DISCONTINUED_COLUMN)
											.toLocalDateTime().toLocalDate()
									: null;
					listComputer.add(new Computer.Builder()
							.id(rs.getInt(COMPUTER_ID_COLUMN))
							.name(rs.getString(COMPUTER_NAME_COLUMN))
							.introduced(getIntroduced)
							.discontinued(getDiscontinued)
							.company(new Company.Builder()
									.id(rs.getLong(COMPANY_ID_COLUMN))
									.name(rs.getString(COMPANY_NAME_COLUMN))
									.build())
							.build());
				}
			}
		} catch (SQLException e) {
			logger.error("getComputerById  : " + e.getMessage());
			throw new ComputerDBException("getComputerById " + e);
		}

		return listComputer;
	}

}
