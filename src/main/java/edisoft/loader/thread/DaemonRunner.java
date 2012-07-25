package edisoft.loader.thread;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

public class DaemonRunner {
    private TaskExecutor taskExecutor;

    @Autowired
    public DaemonRunner(final TaskExecutor taskExecutor, final WatcherThread watcherThread) {
        this.taskExecutor = taskExecutor;
        taskExecutor.execute(watcherThread);
    }
}
