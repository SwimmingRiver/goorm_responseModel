package com.example.response.controller;

import com.example.response.entity.ApiResponse;
import com.example.response.entity.ErrorCode;
import com.example.response.exception.CustomException;
import com.example.response.exception.InputRestriction;
import com.example.response.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/student")
    public ApiResponse add(
            @RequestParam("name") String name,
            @RequestParam("grade") int grade
    ){
        if(grade >= 6){
            throw new CustomException(ErrorCode.BAD_REQUEST,"graade는 6이상 입력할 수 없습니다.",new InputRestriction(6));
        }
        return makeResponse(studentService.addStudent(name, grade));
    }
    @GetMapping("/students")
    public ApiResponse getAll(){return makeResponse(studentService.getAll());}

    @GetMapping("/students/{grade}")
    public ApiResponse getGradeStudent(
            @PathVariable("grade") int grade
    ){
        return makeResponse(studentService.getGradeStudent(grade));
    }

    public <T> ApiResponse<T> makeResponse(List<T> result){return new ApiResponse<>(result);}
    public <T> ApiResponse<T> makeResponse(T result){
        return makeResponse(Collections.singletonList(result));
    }
    @ExceptionHandler(CustomException.class)
    public ApiResponse customExceptionHandler(HttpServletResponse response,CustomException customException){
        return new ApiResponse(customException.getErrorCode().getCode(),
                customException.getErrorCode().getMessage(),
                customException.getData()
                );
    }
}
