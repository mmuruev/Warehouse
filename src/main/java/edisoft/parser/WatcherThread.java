package edisoft.parser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import java.io.FileInputStream;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class WatcherThread extends Thread {

    private static final String WATCHER_PATH = "/home/mf/workspace/IdeaProjects/Warehouse/xml/";
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String XML_EXTENSION = "xml";
    private static final String XSL_FILE = "/home/mf/workspace/IdeaProjects/Warehouse/xslt/INVOIC_edisoft2initial_view_1c.xsl";

    @Autowired
    private DataLoader dataLoader;

    @Override
    public void run() {
        try {
            watcher(WATCHER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void watcher(final String path) throws Exception {
        final Path directoryToWatch = Paths.get(path);
        final WatchService watcherSvc = FileSystems.getDefault().newWatchService();
        WatchKey watchKey = directoryToWatch.register(watcherSvc, ENTRY_MODIFY, ENTRY_CREATE );
        final Transformer transformer = InvoiceReader.newTransformer(new FileInputStream(XSL_FILE));

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
        int dot = fullPath.lastIndexOf(EXTENSION_SEPARATOR);
        return fullPath.substring(dot + 1).equals(XML_EXTENSION);
    }

    @SuppressWarnings(value = "unchecked")
    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

}
