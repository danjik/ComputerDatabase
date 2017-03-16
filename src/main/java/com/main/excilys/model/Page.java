package com.main.excilys.model;

import java.util.List;

public class Page<E> {
	List<E> pageObject;
	private int numPage = 1;
	private static int NB_OBJECT_PER_PAGE = 10;

	public int getNumPage() {
		return numPage;
	}

	public void nextPage() {
		this.numPage++;
	}

	public void previousPage() {
		this.numPage--;
	}

	public static int getNbObjectPerPage() {
		return NB_OBJECT_PER_PAGE;
	}

	public static void setNbObjectPerPage(int nB_OBJECT_PER_PAGE) {
		NB_OBJECT_PER_PAGE = nB_OBJECT_PER_PAGE;
	}
}
