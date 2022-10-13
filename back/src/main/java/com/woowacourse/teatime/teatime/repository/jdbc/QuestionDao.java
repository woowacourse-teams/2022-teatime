package com.woowacourse.teatime.teatime.repository.jdbc;

import com.woowacourse.teatime.teatime.domain.Question;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QuestionDao {
    private static final int BATCH_SIZE = 1000;

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<Question> questions) {
        int batchCount = 0;
        List<Question> subItems = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            subItems.add(questions.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                batchCount = batchInsert(batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchCount, subItems);
        }
    }

    private int batchInsert(int batchCount, List<Question> subItems) {
        jdbcTemplate.batchUpdate("INSERT INTO question (coach_id, number, content, is_required) VALUES (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, subItems.get(i).getCoach().getId());
                        ps.setInt(2, subItems.get(i).getNumber());
                        ps.setString(3, subItems.get(i).getContent());
                        ps.setBoolean(4, subItems.get(i).getIsRequired());
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
