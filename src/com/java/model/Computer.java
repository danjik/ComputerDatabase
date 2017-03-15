package com.java.model;

import java.util.Date;

public class Computer {
	private long id;
	private String name;
	private Date discontinued;
	private Date introduced;
	private long companyId;

	public static class Builder {
		private long id;
		private String name;
		private Date discontinued;
		private Date introduced;
		private long companyId;

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder discontinued(Date discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder introduced(Date introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder companyId(long companyId) {
			this.companyId = companyId;
			return this;
		}

		public Computer build() {
			return new Computer(id, name, discontinued, introduced, companyId);
		}
	}

	private Computer(long id, String name, Date discontinued, Date introduced, long companyId) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.discontinued = discontinued;
		this.introduced = introduced;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Computer [companyId=" + companyId + ", name=" + name + ", discontinued=" + discontinued
				+ ", introduced=" + introduced + ", id=" + id + "]";
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ companyId >>> 32);
		result = prime * result + (discontinued == null ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ id >>> 32);
		result = prime * result + (introduced == null ? 0 : introduced.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		long secDis1 = this.getDiscontinued() == null ? 0 : this.getDiscontinued().getTime() / 1000;
		long secDis2 = other.getDiscontinued() == null ? 0 : other.getDiscontinued().getTime() / 1000;
		long secIns1 = this.getIntroduced() == null ? 0 : this.getIntroduced().getTime() / 1000;
		long secIns2 = other.getIntroduced() == null ? 0 : other.getIntroduced().getTime() / 1000;
		if (companyId != other.companyId)
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (secDis1 != secDis2)
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (secIns1 != secIns2)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
