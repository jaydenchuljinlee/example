package com.batch.batchex.job;

import com.batch.batchex.config.TestBatchConfig;
import com.batch.batchex.entity.Pay;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest(classes = {
        MultiThreadPagingConfiguration.class,
        TestBatchConfig.class,
        MultiThreadPagingConfigurationTest.TestDataSourceConfiguration.class
})
@TestPropertySource(properties = {"chunkSize=1", "poolSize=4"})
public class MultiThreadPagingConfigurationTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private JdbcOperations jdbcTemplate;
    private LocalDateTime txDateTime = LocalDateTime.of(2022, 11, 2, 0, 0);

    @BeforeEach
    public void setUp() throws Exception{
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @AfterEach
    public void tearDown() {
        this.jdbcTemplate.update("delete from pay");
    }

    @Test
    public void 페이징_분산처리_된다() throws Exception {
        // given
        for (int i = 1; i <= 100; i++) {
            insert(i, i*10);
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pay");

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        jobExecution.getStatus();

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }

    private void insert(int idx, long amount) {
        jdbcTemplate.update("insert into pay (amount, tx_name, tx_date_time) values (?, ?, ?)", amount, "pay_"+idx, txDateTime);
    }

    @Configuration
    public static class TestDataSourceConfiguration {
        private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS `PAY` (`ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `AMOUNT` BIGINT NOT NULL, `TX_NAME` VARCHAR(255) NOT NULL, `TX_DATE_TIME` DATE)";
        private static final String CREATE_SQL2 = "CREATE TABLE IF NOT EXISTS `PAY2` (`ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `AMOUNT` BIGINT NOT NULL, `TX_NAME` VARCHAR(255) NOT NULL, `TX_DATE_TIME` DATE)";

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseFactory databaseFactory = new EmbeddedDatabaseFactory();
            databaseFactory.setDatabaseType(EmbeddedDatabaseType.H2);
            return databaseFactory.getDatabase();
        }

        @Bean
        public DataSourceInitializer initializer(DataSource dataSource) {
            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);

            Resource create1 = new ByteArrayResource(CREATE_SQL.getBytes());
            Resource create2 = new ByteArrayResource(CREATE_SQL2.getBytes());
            dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create1, create2));

            return dataSourceInitializer;
        }
    }
}
