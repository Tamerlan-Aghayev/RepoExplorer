package com.company.repoexplorer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public record ApiResponse<T>(int status, String message, @JsonInclude(JsonInclude.Include.NON_NULL) T data) {
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ApiResponse<T> exception(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
