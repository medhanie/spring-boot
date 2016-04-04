package com.github.medhanie.spboot.web;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Medhanie on 4/3/2016.
 */
public interface ExceptionAttributes {

    Map<String, Object> getExceptionAttributes(Exception exception, HttpServletRequest httpRequest, HttpStatus httpStatus);
}
