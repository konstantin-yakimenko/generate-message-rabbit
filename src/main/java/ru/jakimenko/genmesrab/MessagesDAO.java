package ru.jakimenko.genmesrab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by kyyakime on 24.03.17.
 */
@Component
public class MessagesDAO {

    @Value("${select.query}")
    private String sql_select;

    @Autowired
    private DataSource dataSource;

    public List<Long> getTerminalDevices() throws Exception {
        return new JdbcTemplate(dataSource).query(sql_select, (ResultSet rs, int i) -> {
            return rs.getLong(1);
        });
    }
}
