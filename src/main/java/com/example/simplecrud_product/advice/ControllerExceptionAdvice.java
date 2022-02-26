package com.example.simplecrud_product.advice;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//#warning 이거 모든 RestController에 적용하는 것 같음.. 특정 컨트롤러만 지정해주고 싶은데 어떻게 하지
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 모델 유효성 예외 핸들링
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {InvalidRequestModelException.class})
    public ServiceResponse handleInvalidRequestModelException(InvalidRequestModelException ex) {
        return new ServiceResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * 나머지 예외 핸들링
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ServiceResponse handleAllException(Exception ex) {
        return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
