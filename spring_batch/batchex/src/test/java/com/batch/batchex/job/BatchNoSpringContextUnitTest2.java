package com.batch.batchex.job;

import com.batch.batchex.entity.Pay;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.batch.batchex.job.BatchOnlyJdbcReaderTestConfiguration.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableBatchProcessing
@SpringBatchTest
@ContextConfiguration(classes = {
        BatchOnlyJdbcReaderTestConfiguration.class,
        BatchNoSpringContextUnitTest2.TestDataSourceConfiguration.class
})
public class BatchNoSpringContextUnitTest2 {
    @Autowired
    private JdbcPagingItemReader<Pay> reader;
    @Autowired
    private DataSource dataSource;

    private JdbcOperations jdbcTemplate;
    private LocalDateTime txDateTime = LocalDateTime.of(2022, 11, 2, 0, 0);

    public StepExecution getStepExecution() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("txDateTime", this.txDateTime.format(FORMATTER))
                .toJobParameters();

        return MetaDataInstanceFactory.createStepExecution(jobParameters);
    }

    @BeforeEach
    public void setUp() throws Exception{
        this.reader.setDataSource(this.dataSource);
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @AfterEach
    public void tearDown() {
        this.jdbcTemplate.update("delete from pay");
    }

    @Test
    public void 기간내_Pay가_집계되어_조회된다() throws Exception {
        // given
        long amount1 = 1000;
        long amount2 = 100;
        long amount3 = 10;
        jdbcTemplate.update("insert into pay (id, amount, tx_name, tx_date_time) values (?, ?, ?, ?)", 1, amount1, "pay_1", txDateTime);
        jdbcTemplate.update("insert into pay (id, amount, tx_name, tx_date_time) values (?, ?, ?, ?)", 2, amount2, "pay_2", txDateTime);
        jdbcTemplate.update("insert into pay (id, amount, tx_name, tx_date_time) values (?, ?, ?, ?)", 3, amount3, "pay_3", txDateTime);

        // when & then
        assertThat(reader.read().getAmount()).isEqualTo(1110);
    }

    @Configuration
    public static class TestDataSourceConfiguration {
        private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS `PAY` (`ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, `AMOUNT` BIGINT NOT NULL, `TX_NAME` VARCHAR(255) NOT NULL, `TX_DATE_TIME` DATE)";

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

            Resource create = new ByteArrayResource(CREATE_SQL.getBytes());
            dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create));

            return dataSourceInitializer;
        }
    }
}
