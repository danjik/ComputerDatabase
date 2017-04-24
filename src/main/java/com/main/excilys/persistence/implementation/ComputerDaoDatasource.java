package com.main.excilys.persistence.implementation;

import com.main.excilys.mapper.LocalDateToSqlTimestampMapper;
import com.main.excilys.model.Company;
import com.main.excilys.model.Computer;
import com.main.excilys.persistence.IComputerDao;
import com.main.excilys.util.ComputerDbException;
import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("computerDao")
public class ComputerDaoDatasource implements IComputerDao {

  private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int getNbComputer(Map<String, String> options) throws ComputerDbException {
    String query = "select count(co.id) from computer co "
        + "left join company c on co.company_id = c.id where co.name like ? or c.name like ?";
    String optionSearch = options.get("search") != null ? options.get("search") + "%" : "%";

    return jdbcTemplate.queryForObject(query, new Object[] { optionSearch, optionSearch },
        Integer.class);
  }

  @Override
  public long createComputer(Computer newComputer) throws ComputerDbException {
    logger.debug("createComputer : " + newComputer + " computerToCreate ");

    KeyHolder holder = new GeneratedKeyHolder();

    PreparedStatementCreator psc = con -> {
      String query = "insert into computer(id,name,introduced,"
          + "discontinued,company_id) values (?,?,?,?,?)";

      Object[] params = this.getComputerParam(newComputer);

      PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      new ArgumentPreparedStatementSetter(params).setValues(ps);
      return ps;
    };

    jdbcTemplate.update(psc, holder);
    return holder.getKey().longValue();
  }

  @Override
  public Computer getComputerById(Long idToSelect) throws ComputerDbException {
    logger.debug("updateComputer : " + idToSelect + " idToSelect ");
    String query = "select c.id,c.name,introduced, "
        + "discontinued,co.id as company_id,co.name as company_name from computer c "
        + "left join company co on c.company_id = co.id where c.id=?";

    RowMapper<Computer> beanPropertyRowMapper = (rs, rowNum) -> {
      Computer computer = new Computer();
      computer.setId(rs.getLong("c.id"));
      computer.setName(rs.getString("c.name"));
      LocalDate getDiscontinued = rs.getTimestamp("c.introduced") != null
          ? rs.getTimestamp("c.introduced").toLocalDateTime().toLocalDate()
          : null;
      computer.setIntroduced(getDiscontinued);
      LocalDate getIntroduced = rs.getTimestamp("c.discontinued") != null
          ? rs.getTimestamp("c.discontinued").toLocalDateTime().toLocalDate()
          : null;
      computer.setDiscontinued(getIntroduced);
      computer.setCompany(new Company(rs.getLong("company_id"), rs.getString("company_name")));
      return computer;
    };

    return jdbcTemplate.queryForObject(query, new Object[] { idToSelect }, beanPropertyRowMapper);
  }

  @Override
  public void deleteComputer(long idToDelete) {
    final String queryDeleteComputer = "delete from company where id = ?";
    jdbcTemplate.update(queryDeleteComputer, new Object[] { idToDelete });

  }

  @Override
  public List<Computer> getAllComputer() {
    String query = "select c.id,c.name,introduced, "
        + "discontinued,co.id as company_id,co.name as company_name from computer c "
        + "left join company co on c.company_id = co.id  ";

    RowMapper<Computer> beanPropertyRowMapper = (rs, rowNum) -> {
      Computer computer = new Computer();
      computer.setId(rs.getLong("c.id"));
      computer.setName(rs.getString("c.name"));
      LocalDate getDiscontinued = rs.getTimestamp("c.introduced") != null
          ? rs.getTimestamp("c.introduced").toLocalDateTime().toLocalDate()
          : null;
      computer.setIntroduced(getDiscontinued);
      LocalDate getIntroduced = rs.getTimestamp("c.discontinued") != null
          ? rs.getTimestamp("c.discontinued").toLocalDateTime().toLocalDate()
          : null;
      computer.setDiscontinued(getIntroduced);
      computer.setCompany(new Company(rs.getLong("company_id"), rs.getString("company_name")));
      return computer;
    };

    List<Computer> listComputer = jdbcTemplate.query(query, beanPropertyRowMapper);
    return listComputer;
  }

  @Override
  public List<Computer> getComputerInRange(long idBegin, long nbObjectToGet,
      Map<String, String> options) {
    String query = "select c.id,c.name,introduced, "
        + "discontinued,co.id as company_id,co.name as company_name from computer c "
        + "left join company co on c.company_id = co.id  "
        + "where c.name like ? or co.name like ? %s limit ?,?";
    String optionColumn = options.get("column") != null && !options.get("column").isEmpty()
        ? "order by " + options.get("column") + " asc"
        : "";
    query = String.format(query, optionColumn);
    String optionSearch = options.get("search") != null ? options.get("search") + "%" : "%";

    Object[] params = new Object[] { optionSearch, optionSearch, idBegin, nbObjectToGet };
    RowMapper<Computer> beanPropertyRowMapper = (rs, rowNum) -> {
      Computer computer = new Computer();
      computer.setId(rs.getLong("c.id"));
      computer.setName(rs.getString("c.name"));
      LocalDate getDiscontinued = LocalDateToSqlTimestampMapper
          .sqlTimestampToLocalDate(rs.getTimestamp("c.discontinued"));
      computer.setDiscontinued(getDiscontinued);
      LocalDate getIntroduced = LocalDateToSqlTimestampMapper
          .sqlTimestampToLocalDate(rs.getTimestamp("c.introduced"));
      computer.setIntroduced(getIntroduced);
      computer.setCompany(new Company(rs.getLong("company_id"), rs.getString("company_name")));
      return computer;
    };

    List<Computer> listComputer = jdbcTemplate.query(query, params, beanPropertyRowMapper);
    return listComputer;
  }

  @Override
  public void updateComputer(Computer updateComputer) {
    logger.debug("updateComputer : " + updateComputer + " computerToUpdate ");
    String query = "update computer set id=?, name=?, "
        + "introduced=?,discontinued=?,company_id=? where id=?";
    Object[] params = this.getComputerParam(updateComputer);
    jdbcTemplate.update(query, params);
  }

  private Object[] getComputerParam(Computer computer) {
    Timestamp timestampIntroduced = LocalDateToSqlTimestampMapper
        .localDateToSqlTimestamp(computer.getIntroduced());
    Timestamp timestampDiscontinued = LocalDateToSqlTimestampMapper
        .localDateToSqlTimestamp(computer.getDiscontinued());
    return new Object[] { computer.getId(), computer.getName(), timestampIntroduced,
        timestampDiscontinued, computer.getCompany().getId(), computer.getId() };
  }
}
