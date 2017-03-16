package com.java.model;

import java.util.List;

public class Page<E> {
	List<E> pageObject;
	private int numPage = 0;
	public static int NB_OBJECT_PER_PAGE = 10;

	public Page(List<E> newList) {
		pageObject = newList;
	}

	public int getNumPage() {
		return numPage;
	}

	public void nextPage() {
		this.numPage++;
	}

	public void previousPage() {
		this.numPage++;
	}
}
