package com.main.excilys.model;

import com.main.excilys.util.ComputerDbException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ComputerDto {
  private long id;
  private String name;
  private LocalDate discontinued;
  private LocalDate introduced;
  private CompanyDto companyDto;

  public static class Builder {
    private long id;
    private String name;
    private LocalDate discontinued;
    private LocalDate introduced;
    private CompanyDto companyDto;

    /**
     * Add id to the builder.
     *
     * @param id
     *          the id to set
     * @return the updated builder
     */
    public Builder id(long id) {
      this.id = id;
      return this;
    }

    /**
     * Add name to the builder.
     *
     * @param name
     *          the name to set
     * @return the updated builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;

    }

    /**
     * Setters for the LocalDate discontinued using a string.
     *
     * @param discontinued
     *          the string to parse in local date, must match dd-MM-yyyy
     * @return the builder
     */

    public Builder discontinued(String discontinued) {
      try {
        this.discontinued = discontinued != null && !discontinued.isEmpty()
            ? LocalDate.parse(discontinued)
            : null;
        return this;
      } catch (DateTimeParseException e) {
        throw new ComputerDbException("The date : " + discontinued + " is invalid");

      }
    }

    /**
     * Add discontinued to the builder.
     *
     * @param discontinued
     *          the discontinued to set
     * @return the updated builder
     */
    public Builder discontinued(LocalDate discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    /**
     * Setters for the LocalDate introduced using a string.
     *
     * @param introduced
     *          the string to parse in local date, must match dd-MM-yyyy
     * @return the builder
     */

    public Builder introduced(String introduced) {
      try {
        this.introduced = introduced != null && !introduced.isEmpty()
            ? LocalDate.parse(introduced)
            : null;
        return this;
      } catch (DateTimeParseException e) {
        throw new ComputerDbException("The date : " + introduced + " is invalid");
      }
    }

    /**
     * Add introduced to the builder.
     *
     * @param introduced
     *          the introduced to set
     * @return the updated builder
     */
    public Builder introduced(LocalDate introduced) {
      this.introduced = introduced;
      return this;
    }

    /**
     * Add companyDto to the builder.
     *
     * @param companyDto
     *          the companyDto to set
     * @return the updated builder
     */
    public Builder companyDto(CompanyDto companyDto) {
      this.companyDto = companyDto;
      return this;
    }

    /**
     * Return the corresponding ComputerDTO of the builder.
     *
     * @return the computerDto
     */
    public ComputerDto build() {
      return new ComputerDto(id, name, discontinued, introduced, companyDto);
    }
  }

  /**
   * Full constructor used by the builder.
   *
   * @param id
   *          id
   * @param name
   *          name
   * @param discontinued
   *          discontinued
   * @param introduced
   *          introduced
   * @param companyDto
   *          companyDto
   */

  private ComputerDto(long id, String name, LocalDate discontinued, LocalDate introduced,
      CompanyDto companyDto) {
    super();
    this.companyDto = companyDto;
    this.name = name;
    this.discontinued = discontinued;
    this.introduced = introduced;
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  /**
   * Add discontinued to the builder.
   *
   * @param discontinued
   *          the discontinued to set
   */
  public void setDiscontinued(String discontinued) {
    try {
      this.discontinued = discontinued != null && !discontinued.isEmpty()
          ? LocalDate.parse(discontinued)
          : null;
    } catch (DateTimeParseException e) {
      throw new ComputerDbException("The date : " + discontinued + " is invalid");

    }
  }

  /**
   * Setters for the LocalDate introduced using a string.
   *
   * @param introduced
   *          the string to parse in local date, must match dd-MM-yyyy
   */

  public void setIntroduced(String introduced) {
    try {
      this.introduced = introduced != null && !introduced.isEmpty()
          ? LocalDate.parse(introduced)
          : null;
    } catch (DateTimeParseException e) {
      throw new ComputerDbException("The date : " + introduced + " is invalid");
    }
  }

  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  public CompanyDto getCompanyDto() {
    return companyDto;
  }

  public void setCompanyDto(CompanyDto companyDto) {
    this.companyDto = companyDto;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (companyDto == null ? 0 : companyDto.hashCode());
    result = prime * result + (discontinued == null ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ id >>> 32);
    result = prime * result + (introduced == null ? 0 : introduced.hashCode());
    result = prime * result + (name == null ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }

    ComputerDto other = (ComputerDto) obj;
    if (!companyDto.equals(other.companyDto)) {
      return false;
    }

    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ComputerDTO [id=" + id + ", name=" + name + ", discontinued=" + discontinued
        + ", introduced=" + introduced + ", companyDto=" + companyDto + "]";
  }

}
