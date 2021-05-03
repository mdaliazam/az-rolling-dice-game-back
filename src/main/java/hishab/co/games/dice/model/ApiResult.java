package hishab.co.games.dice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiResult<T> {

    private boolean success;
    private String message;
    private T entity;
    private List<T> entities;
    private Throwable error;

    public ApiResult(boolean success) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    public ApiResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    public ApiResult(boolean success, String message, Throwable error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }

    public ApiResult<T> entity(T entity) {
        this.entity = entity;
        return this;
    }

    public ApiResult<T> entities(List<T> entities) {
        this.entities = entities;
        return this;
    }


}
