package com.main.excilys.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { SpringRootConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] { SpringWebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    System.out.println("test");
    return new String[] { "/", "/dashboard" };
  }

}