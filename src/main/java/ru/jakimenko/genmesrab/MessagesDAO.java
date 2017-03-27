package ru.jakimenko.genmesrab;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String SQL_SELECT =
                    "select distinct value \n" +
                    "from CUST.EntityPropertyValue \n" +
                    "where entityPropertyKindId = 1 \n" +
                    "and timestamp(now()) > DateFrom \n" +
                    "and timestamp(now()) < DateTo \n" +
                    "limit 300";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Long> getTerminalDevices() throws Exception {
        return jdbcTemplate.query(SQL_SELECT, (ResultSet rs, int i) -> {
            return rs.getLong(1);
        });
    }
}
