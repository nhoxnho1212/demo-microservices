package com.tung.productservice.health;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MySqlHealthCheck implements HealthIndicator {

    private DataSource dataSource;

    private void assertConnectionIsOpen(DataSource dataSource) throws SQLException{
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isClosed()) {
                throw new SQLException("Connection is closed!");
            }
        }
    }

    private boolean checkDatabase(DataSource dataSource, Map<String, Object> details) {
        boolean isHealthy = false;

        try {
            assertConnectionIsOpen(dataSource);

            details.put("URL", dataSource.getConnection().getMetaData().getURL());
            details.put("Username", dataSource.getConnection().getMetaData().getUserName());

            isHealthy = true;
        } catch (SQLException exception) {
            details.put("ERROR: ", exception);
        }

        return isHealthy;
    }

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        boolean isHealthy = checkDatabase(dataSource, details);
        return (isHealthy ? Health.up() : Health.down()).withDetails(details).build();
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
