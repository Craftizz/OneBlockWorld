package io.github.craftizz.oneblockworld.tasks.tasktypes;

public interface Task {

    void compute();

    default boolean shouldReschedule() {
        return false;
    }

}
