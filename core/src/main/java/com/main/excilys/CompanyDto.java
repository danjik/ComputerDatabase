package com.main.excilys;

import org.springframework.stereotype.Component;

@Component
public class CompanyDto {
  private long id;
  private String name;

  public static class Builder {
    private long id;
    private String name;

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
     * Return the corresponding CompanyDto of the builder.
     *
     * @return the CompanyDto
     */

    public CompanyDto build() {
      return new CompanyDto(id, name);
    }
  }
  /**
   * Full constructor used by the builder.
   *
   * @param id
   *          id
   * @param name
   *          name
   */

  public CompanyDto(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public CompanyDto() {
    // TODO Auto-generated constructor stub
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

  @Override
  public String toString() {
    return "CompanyDTO [id=" + id + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ id >>> 32);
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
    CompanyDto other = (CompanyDto) obj;
    if (id != other.id) {
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
}
