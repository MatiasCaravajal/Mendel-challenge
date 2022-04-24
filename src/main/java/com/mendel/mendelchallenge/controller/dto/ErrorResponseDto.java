package com.mendel.mendelchallenge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * The error response dto to map a error to unexpected behavior.
 */
public class ErrorResponseDto {
  /**
   * The error, cannot be null or empty.
   */
  private String error;
  /**
   * The http status code, cannot be null.
   */
  private Integer status;
}

