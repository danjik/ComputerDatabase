package com.main.excilys.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LinkTag extends SimpleTagSupport {

  private int numPage;
  private String label;
  private String type;

  @Override
  public void doTag() throws JspException, IOException {
    JspWriter out = this.getJspContext().getOut();
    out.println("<li><a href='dashboard?action=switchPage&page=" + (numPage + 1) + "'aria-label='"
        + type + "' id='" + type + "'><span aria-hidden='true'>" + label + "</span></a></li>");
  }

  public void setNumPage(int numPage) {
    this.numPage = numPage;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setType(String type) {
    this.type = type;
  }

}
