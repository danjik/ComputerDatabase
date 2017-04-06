package com.main.excilys.taglib;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PaginationTag extends SimpleTagSupport {
  private int numPage;
  private int maxPage;
  private String column;
  private String search;

  @Override
  public void doTag() throws JspException, IOException {

    int[] listToPrint = new int[5];
    for (int j = -2; j < 3; j++) {
      if (numPage + j < 0) {
        listToPrint[j + 2] = -(j - 2) + 1;
      } else if (numPage + j > maxPage) {
        listToPrint[j + 2] = maxPage - j - 1;
      } else {
        listToPrint[j + 2] = numPage + j + 1;
      }
    }
    Arrays.sort(listToPrint);

    for (int element : listToPrint) {
      JspWriter out = this.getJspContext().getOut();
      out.println("<li");
      if (element - 1 == numPage) {
        out.println("class=active");

      }
      out.println("><a href='dashboard?action=switchPage&page=" + element + "&column=+" + column
          + "&search=" + search + "'>" + element + "</a></li>");
    }

  }

  public void setColumn(String column) {
    this.column = column;
  }

  public void setNumPage(int numPage) {
    this.numPage = numPage;
  }

  public void setMaxPage(int maxPage) {
    this.maxPage = maxPage;
  }

  public void setSearch(String search) {
    this.search = search;
  }
}
