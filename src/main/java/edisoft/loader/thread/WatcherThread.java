package edisoft.loader.thread;


import edisoft.loader.db.DataLoader;
import edisoft.loader.parser.InvoiceReader;
import edisoft.loader.consts.PathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import java.io.FileInputStream;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class WatcherThread extends Thread {

    @Autowired
    private DataLoader dataLoader;

    @Override
    public void run() {
        try {
            watcher(PathConstants.WATCHER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void watcher(final String path) throws Exception {
        final Path directoryToWatch = Paths.get(path);
        final WatchService watcherSvc = FileSystems.getDefault().newWatchService();
        WatchKey watchKey = directoryToWatch.register(watcherSvc, ENTRY_MODIFY, ENTRY_CREATE );
        final Transformer transformer = InvoiceReader.newTransformer(new FileInputStream(PathConstants.XSL_FILE));

        while (true) {
            watchKey = watcherSvc.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                if (event.kind() == OVERFLOW) {
                    continue;
                }
                WatchEvent<Path> watchEvent = cast(event);
                String fullPathXML = directoryToWatch.resolve(watchEvent.context().getFileName()).toString();
                if (isXML(fullPathXML)) {
                    dataLoader.setTransformer(transformer);
                    dataLoader.save(fullPathXML);
                }
                if (!watchKey.reset()) {
                    break;
                }
            }
        }
    }

    private boolean isXML(final String fullPath) {
        int dot = fullPath.lastIndexOf(PathConstants.EXTENSION_SEPARATOR);
        return fullPath.substring(dot + 1).equals(PathConstants.XML_EXTENSION);
    }

    @SuppressWarnings(value = "unchecked")
    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

}
