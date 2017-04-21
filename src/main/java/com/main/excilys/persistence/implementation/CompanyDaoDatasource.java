package com.main.excilys.persistence.implementation;

import com.main.excilys.model.Company;
import com.main.excilys.persistence.ICompanyDao;
import com.main.excilys.util.ComputerDbException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("companyDao")
public class CompanyDaoDatasource implements ICompanyDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @Override
  public List<Company> getAllCompany() {
    String query = "select * from company";
    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Company.class));
  }

  @Override
  public int getNbCompany() throws ComputerDbException {
    String query = "select count(*) from company";
    return jdbcTemplate.queryForObject(query, Integer.class);
  }

  @Override
  public Company getCompanyById(long idToTest) throws ComputerDbException {
    String query = "select * from company where id=?";
    return jdbcTemplate.queryForObject(query, new Object[] { idToTest },
        new BeanPropertyRowMapper<>(Company.class));
  }

  @Override
  public List<Company> getCompanyInRange(long idBegin, long idEnd) {

    String query = "select * from company limit ?,?";
    List<Company> listCompany = jdbcTemplate.query(query, new Object[] { idBegin, idEnd },
        new BeanPropertyRowMapper<>(Company.class));
    return listCompany;

  }

  @Override
  public void deleteCompany(long idToDelete) {
    final String queryDeleteCompany = "delete from company where id = ?";
    jdbcTemplate.update(queryDeleteCompany, new Object[] { idToDelete });

  }

}
