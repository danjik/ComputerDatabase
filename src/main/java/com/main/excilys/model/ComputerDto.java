package com.main.excilys.model;

import org.springframework.stereotype.Component;

@Component
public class ComputerDto {
  private long id;
  private String name;
  private String discontinued;
  private String introduced;
  private CompanyDto companyDto;

  public static class Builder {
    private long id;
    private String name;
    private String discontinued;
    private String introduced;
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
     * Add discontinued to the builder.
     *
     * @param discontinued
     *          the discontinued to set
     * @return the updated builder
     */
    public Builder discontinued(String discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    public Builder introduced(String introduced) {
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

  private ComputerDto(long id, String name, String discontinued, String introduced,
      CompanyDto companyDto) {
    super();
    this.companyDto = companyDto;
    this.name = name;
    this.discontinued = discontinued;
    this.introduced = introduced;
    this.id = id;
  }

  public ComputerDto() {

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

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getIntroduced() {
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
