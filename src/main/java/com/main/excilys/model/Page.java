package com.main.excilys.model;

import com.main.excilys.service.ComputerService;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Page {
  private int numPage = 0;

  public static int nbObject;
  private int nbObjectPerPage = 10;
  private int maxPage;

  @Autowired
  private ComputerService computerService;

  /**
   * Simple constructor with the number of project.
   */
  @PostConstruct
  public void contruct() {
    CompletableFuture
        .supplyAsync(() -> Page.nbObject = computerService.getNbComputer(new HashMap<>()))
        .thenRun(() -> this.setMaxPage(Page.nbObject / nbObjectPerPage));
  }

  /**
   * Get the n° of the actual page.
   *
   * @return the n° of the actual page
   */
  public int getNumPage() {
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

  public int getNbObjectPerPage() {
    return nbObjectPerPage;
  }

  /**
   * Set the number of object per page.
   *
   * @param nbObjectPerPage
   *          the number of object
   */
  public void setNbObjectPerPage(int nbObjectPerPage) {
    this.nbObjectPerPage = nbObjectPerPage;
    this.setMaxPage(Page.nbObject / nbObjectPerPage);
  }

  public int getMaxPage() {
    return maxPage;
  }

  /**
   * Setter for the maxPage.
   *
   * @param maxPage
   *          the maxPage to set
   */
  public void setMaxPage(int maxPage) {
    if (nbObject != 0 && nbObject % nbObjectPerPage > 0) {
      this.maxPage = maxPage;
    } else {
      this.maxPage = maxPage - 1;
    }

  }

  public static void decrementNbObject() {
    nbObject--;

  }

  public static void incrementNbObject() {
    nbObject++;

  }
}
