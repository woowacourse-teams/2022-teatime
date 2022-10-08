package com.woowacourse.teatime.teatime.repository.jdbc;

import com.woowacourse.teatime.teatime.domain.Schedule;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ScheduleDao {

    private static final int BATCH_SIZE = 1000;

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<Schedule> schedules) {
        int batchCount = 0;
        List<Schedule> subItems = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            subItems.add(schedules.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                batchCount = batchInsert(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchCount, subItems);
        }
    }

    private int batchInsert(int batchCount, List<Schedule> subItems) {
        jdbcTemplate.batchUpdate("INSERT INTO schedule (coach_id, local_date_time, is_possible) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, subItems.get(i).getCoach().getId());
                        ps.setTimestamp(2, Timestamp.valueOf(subItems.get(i).getLocalDateTime()));
                        ps.setBoolean(3, subItems.get(i).getIsPossible());
                    }

                    @Override
                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
        subItems.clear();
        batchCount++;
        return batchCount;
    }
}
