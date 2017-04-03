package com.main.excilys.model;

public class Page<E> {
  private int numPage = 0;

  private static int nbObject;
  private static int nbObjectPerPage = 10;
  private static int maxPage;

  /**
   * Simple constructor with the number of project.
   *
   * @param nbObject
   *          the number of project
   */
  public Page(int nbObject) {
    Page.nbObject = nbObject;
    Page.setMaxPage(Page.nbObject / Page.nbObjectPerPage);
  }

  /**
   * Get the n° of the actual page.
   *
   * @return the n° of the actual page
   */
  public int getNumPage() {
    if (nbObject != 0 && nbObject % nbObjectPerPage == 0) {
      numPage = maxPage - 1;
    }
    return numPage;
  }

  public void setNumPage(int numPage) {
    this.numPage = numPage;
  }

  /**
   * Switch to newPage.
   */

  public void nextPage() {
    this.numPage++;
  }

  /**
   * Try if the page has a next page.
   *
   * @return true if page has a next
   */
  public boolean hasNext() {
    if (this.numPage + 1 <= maxPage) {
      return true;
    }
    return false;
  }

  /**
   * Try if the page has a previous page.
   *
   * @return true if page has a previous
   */
  public boolean hasPrevious() {
    if (this.numPage - 1 >= 0) {
      return true;
    }
    return false;
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

  /**
   * Set the number of object per page.
   *
   * @param nbObjectPerPage
   *          the number of object
   */
  public static void setNbObjectPerPage(int nbObjectPerPage) {
    Page.nbObjectPerPage = nbObjectPerPage;
    Page.setMaxPage(Page.nbObject / Page.nbObjectPerPage);
  }

  public int getMaxPage() {
    return Page.maxPage;
  }

  /**
   * Setter for the maxPage.
   *
   * @param maxPage
   *          the maxPage to set
   */
  public static void setMaxPage(int maxPage) {
    if (nbObject != 0 && nbObject % nbObjectPerPage > 0) {
      Page.maxPage = maxPage;
    } else {
      Page.maxPage = maxPage - 1;
    }

  }

  public static void setNbObject(int nbObject) {
    Page.nbObject = nbObject;
    Page.setMaxPage(Page.nbObject / Page.nbObjectPerPage);
  }
}
