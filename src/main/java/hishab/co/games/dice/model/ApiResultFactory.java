package hishab.co.games.dice.model;

import java.util.List;

public class ApiResultFactory<T> {
    private boolean success = false;
    private ApiResult<T> result = null;

    public ApiResultFactory(boolean succeeded) {
        this.result = new ApiResult<>(succeeded);
    }

    public ApiResultFactory<T> message(String message) {
        this.result.setMessage(message);
        return this;
    }

    public ApiResultFactory<T> entity(T entity) {
        this.result.setEntity(entity);
        return this;
    }

    public ApiResultFactory<T> entities(List<T> entities) {
        this.result.setEntities(entities);
        return this;
    }

    public ApiResultFactory<T> error(Throwable error) {
        this.result.setError(error);
        return this;
    }

    // final creation
    public ApiResult<T> create() {
        return this.result;
    }

}
