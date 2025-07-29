package com.shoppit.exceptions;

public class ProductException extends RuntimeException {


  public ProductException(String message) {
    super(message);
  }


  public ProductException(Throwable cause) {
    super(cause);
  }


  public ProductException(String message, Throwable cause) {
    super(message, cause);
  }
}
