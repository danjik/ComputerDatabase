package com.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.java.model.Company;
import com.java.util.ComputerDBException;

public enum CompanyDAO implements ICompanyDAO {
	INSTANCE;
	private Logger logger = LogManager.getRootLogger();

	@Override
	public List<Company> getAllCompany() throws ComputerDBException {
		List<Company> listCompany = new ArrayList<>();
		String query = "select * from company";
		Company selectCompany = null;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn.prepareStatement(query);) {
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					selectCompany = new Company();
					selectCompany.setId(rs.getInt(1));
					selectCompany.setName(rs.getString(2));
					listCompany.add(selectCompany);
				}
			}
		} catch (SQLException e) {
			logger.error("getAllCompany" + e);
			throw new ComputerDBException("getAllCompany " + e);
		} finally {
		}
		logger.debug("getAllCompany : " + listCompany.size() + " companies has been selected");

		return listCompany;
	}

	@Override
	public int getNbCompany() throws ComputerDBException {
		String query = "select count(*) from company";
		int nbComputer = -1;
		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn.prepareStatement(query);) {
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					nbComputer = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			logger.error("getNbCompany" + e.getMessage());
			throw new ComputerDBException("getNbCompany " + e);
		}

		return nbComputer;
	}

	@Override
	public Company getCompanyById(long idToTest) throws ComputerDBException {
		logger.debug("getCompanyById idToTest : " + idToTest);
		String query = "select * from company where id=?";
		Company selectCompany = null;

		try (Connection conn = ConnectionDB.CONNECTION.getConnection();
				PreparedStatement selectPStatement = conn.prepareStatement(query);) {
			selectPStatement.setLong(1, idToTest);
			try (ResultSet rs = selectPStatement.executeQuery()) {
				while (rs.next()) {
					selectCompany = new Company();
					selectCompany.setId(rs.getInt(1));
					selectCompany.setName(rs.getString(2));
				}
			}

		} catch (SQLException e) {
			logger.error("getCompanyById" + e.getMessage());
			throw new ComputerDBException("getCompanyById " + e);
		} finally {

		}
		logger.debug("getCompanyById selectedCompany : " + selectCompany);

		return selectCompany;
	}
}
