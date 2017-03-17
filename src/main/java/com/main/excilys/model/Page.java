package com.main.excilys.model;

import java.util.List;

public class Page<E> {
  List<E> pageObject;
  private int numPage = 0;
  private static int nbObjectPerPage = 10;

  public int getNumPage() {
    return numPage;
  }
  /**
   * Switch to newPage.
   */

  public void nextPage() {
    this.numPage++;
  }
  /**
   * Switch to previousPage.
   */

  public void previousPage() {
    this.numPage--;
  }

  public static int getNbObjectPerPage() {
    return nbObjectPerPage;
  }

  public static void setNbObjectPerPage(int nbObjectPerPage) {
    Page.nbObjectPerPage = nbObjectPerPage;
  }
}
