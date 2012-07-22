package edisoft.model;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

public class DatabaseLoader {
    @Autowired
    public DatabaseLoader(final DataSource dataSource, final String script) throws IOException {
        final JdbcTemplate database = new JdbcTemplate(dataSource);
        final String[] commands = IOUtils.toString(getClass().getResourceAsStream(script)).split(";\\s*");
        for (final String command : commands) {
            if (command.startsWith("--")) {
                continue;
            }

            database.execute(command);
        }
    }
}
