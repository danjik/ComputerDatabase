package com.java.model;

import java.util.List;

public class Page<E> {
	List<E> pageObject;
	private static int NB_OBJECT_PER_PAGE = 10;

	public Page(List<E> newList) {
		pageObject = newList;
	}

	public void updatePage(List<E> updateList) {
		pageObject = updateList;

	}

	public int getNbPage() {
		return pageObject.size() / NB_OBJECT_PER_PAGE;
	}

	public List<E> getPageInRange(int idPage) {
		int indexBegin = idPage * NB_OBJECT_PER_PAGE;
		int indexEnd = idPage * NB_OBJECT_PER_PAGE + NB_OBJECT_PER_PAGE;
		if (indexEnd > pageObject.size())
			indexEnd = pageObject.size();
		System.out.println("Item from " + indexBegin + " to " + indexEnd);
		return pageObject.subList(indexBegin, indexEnd);
	}
}
