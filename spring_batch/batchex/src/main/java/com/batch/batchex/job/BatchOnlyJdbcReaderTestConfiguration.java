package com.batch.batchex.job;

import com.batch.batchex.entity.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchOnlyJdbcReaderTestConfiguration {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String JOB_NAME = "batchOnlyJdbcReaderTestJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize;

    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job batchJdbcUnitTestJob() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .start(batchJdbcUnitTestStep())
                .build();
    }

    @Bean
    public Step batchJdbcUnitTestStep() throws Exception {
        return stepBuilderFactory.get("batchJdbcUnitTestStep")
                .<Pay, Pay>chunk(chunkSize)
                .reader(batchOnlyJdbcReaderTestJobReader(null))
                .writer(batchJdbcUnitTestJobWriter())
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Pay> batchOnlyJdbcReaderTestJobReader(
            @Value("#{jobParameters[txDateTime]}") String txDateTime) throws Exception{
        Map<String, Object> params = new HashMap<>();

        params.put("txDateTime", LocalDate.parse(txDateTime, FORMATTER));

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("tx_date_time, sum(amount) as amount");
        queryProvider.setFromClause("from pay");
        queryProvider.setWhereClause("where tx_date_time =:txDateTime");
        queryProvider.setGroupClause("group by tx_date_time");
        queryProvider.setSortKey("tx_date_time");

        return new JdbcPagingItemReaderBuilder<Pay>()
                .name("batchOnlyJdbcReaderTestJobReader")
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .queryProvider(queryProvider.getObject())
                .parameterValues(params)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Pay> batchJdbcUnitTestJobWriter() {
        return new JdbcBatchItemWriterBuilder<Pay>()
                .dataSource(dataSource)
                .sql("insert into pay(id, amount, tx_name, tx_date_time) values (:id, :amount, :txName, :txDateTime)")
                .beanMapped()
                .build();
    }
}
