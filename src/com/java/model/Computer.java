package com.java.model;

import java.util.Date;

public class Computer {
	private long companyId;
	private String name;
	private Date discontinued;
	private Date introduced;
	private long id;

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

	public boolean compareTo(Computer o) {
		long secDis1 = this.getDiscontinued() == null ? 0 : this.getDiscontinued().getTime() / 1000;
		long secDis2 = o.getDiscontinued() == null ? 0 : o.getDiscontinued().getTime() / 1000;
		long secIns1 = this.getIntroduced() == null ? 0 : this.getIntroduced().getTime() / 1000;
		long secIns2 = o.getIntroduced() == null ? 0 : o.getIntroduced().getTime() / 1000;
		if (this.getName().equals(o.getName()))
			if (secDis1 == secDis2)
				if (secIns1 == secIns2)
					if (this.getCompanyId() == o.getCompanyId())
						return true;
		return false;
	}

}
