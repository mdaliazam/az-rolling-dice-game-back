package hishab.co.games.dice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class Cached<T> {

    private T t;
    private LocalDateTime lastActive;

    public Cached(T t) {
        this.t = t;
        this.lastActive = LocalDateTime.now();
    }

    public T get() {
        return this.t;
    }

    public boolean expires(int seconds) {
        long secs = this.lastActive.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        return secs > seconds;

    }
}
