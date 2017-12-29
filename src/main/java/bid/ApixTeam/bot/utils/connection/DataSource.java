package bid.ApixTeam.bot.utils.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * TSC-Bot was created by ApixTeam (C) 2017
 * in association with TheSourceCode (C) 2017
 */
public class DataSource {
    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();

        Yaml yaml = new Yaml();
        try {
            Map<String, Map<String, String>> values = yaml.load(new FileInputStream(new File(System.getProperty("user.dir") + "/config.yml")));

            boolean b = false;
            String host = "localhost", database = "discord", username = "root", password = "", port = "3306", ssl = "false";

            for (String key : values.keySet()) {
                Map<String, String> subValues = values.get(key);

                for (String subValueKey : subValues.keySet()) {
                    if (subValueKey.equalsIgnoreCase("host"))
                        host = subValues.get(subValueKey);
                    else if (subValueKey.equalsIgnoreCase("port"))
                        port = subValues.get(subValueKey);
                    else if (subValueKey.equalsIgnoreCase("database"))
                        database = subValues.get(subValueKey);
                    else if (subValueKey.equalsIgnoreCase("username"))
                        username = subValues.get(subValueKey);
                    else if (subValueKey.equalsIgnoreCase("password"))
                        password = subValues.get(subValueKey);
                    else if (subValueKey.equalsIgnoreCase("use-ssl"))
                        ssl = subValues.get(subValueKey);
                }
                b = true;
            }

            if (b) {
                config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?useSSL=%s", host, port, database, ssl));
                config.setUsername(username);
                config.setPassword(password);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] An error occurred while trying to retrieve database credentials, forcing dev-local database!");
            config.setJdbcUrl("jdbc:mysql://uae.sytes.net:3306/tsc_discord");
            config.setUsername("tscroot");
            config.setPassword("fvIyCvRohwjCjuqe");
        }

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}