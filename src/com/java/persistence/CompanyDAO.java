package com.java.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Company;
import com.java.model.ConnectionDB;
import com.java.util.ComputerDBException;

public class CompanyDAO {
	private ConnectionDB connectionDB;
	private Logger logger;

	/**
	 * The ConnectionDB could be get by using the static method getInstance() of
	 * the ConnectionDB class
	 *
	 * @param connectionDB
	 *            could be get by using the static method getInstance() of the
	 *            ConnectionDB class
	 */
	public CompanyDAO(ConnectionDB connectionDB) {
		this.connectionDB = connectionDB;
		logger = LogManager.getRootLogger();
	}

	public List<Company> getAllCompany() throws ComputerDBException {
		List<Company> listCompany = new ArrayList<>();
		String query = "select * from company";
		PreparedStatement selectPStatement;
		ResultSet rs;
		Company selectCompany = null;
		try {
			selectPStatement = this.connectionDB.getConnection().prepareStatement(query);
			rs = selectPStatement.executeQuery();
			while (rs.next()) {
				selectCompany = new Company();
				selectCompany.setId(rs.getInt(1));
				selectCompany.setName(rs.getString(2));
				listCompany.add(selectCompany);
			}
		} catch (SQLException e) {
			logger.error("getAllCompany" + e);
			throw new ComputerDBException("getAllCompany " + e.getMessage());
		} finally {
		}
		logger.debug("getAllCompany : " + listCompany.size() + " companies has been selected");

		return listCompany;
	}

	public int getNbCompany() throws ComputerDBException {
		String query = "select count(*) from company";
		int nbComputer = -1;
		try {
			PreparedStatement selectPStatement = this.connectionDB.getConnection().prepareStatement(query);
			ResultSet rs = selectPStatement.executeQuery();
			while (rs.next()) {
				nbComputer = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("getNbCompany" + e.getMessage());
			throw new ComputerDBException("getNbCompany " + e);
		}

		return nbComputer;
	}

	public Company getCompanyById(long idToTest) throws ComputerDBException {
		logger.debug("getCompanyById idToTest : " + idToTest);
		String query = "select * from company where id=?";
		Company selectCompany = null;
		try {
			PreparedStatement selectPStatement = this.connectionDB.getConnection().prepareStatement(query);
			selectPStatement.setLong(1, idToTest);
			ResultSet rs = selectPStatement.executeQuery();
			while (rs.next()) {
				selectCompany = new Company();
				selectCompany.setId(rs.getInt(1));
				selectCompany.setName(rs.getString(2));
			}
		} catch (SQLException e) {
			logger.error("getCompanyById" + e.getMessage());
			throw new ComputerDBException("getCompanyById " + e);
		}
		logger.debug("getCompanyById selectedCompany : " + selectCompany);

		return selectCompany;
	}
}
