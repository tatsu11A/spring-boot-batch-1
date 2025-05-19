package main.java.com.example.demo.batch.master.user.chunk;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.common.entity.Users;
import com.example.demo.common.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;

/** コンフィグ */
@Configuration
@RequiredArgsConstructor
public class UsersConfig {
    
    private final JobRepository jobRepository;
    //ジョブ管理を行うSpring Batchの部品
    private final SqlSessionFactory sqlSessionFactory;
    //MyBatisのDB接続を管理する部品
    private final PlatformTransactionManager platformTransactionManager;
    //トランザクション管理（処理の安全な実行）

    private final UsersProcessor usersProcessor;
    //読み込んだデータを加工する処理
    private final UsersWriter usersWriter;
    //加工後のデータを書き出す処理

    /** データリーダー */
    @Bean
    public ItemReader<? extends Users> userReader() {
        MyBatisCursorItemReaderBuilder<Users> reader = new MyBatisCursorItemReaderBuilder<Users>();
        return reader
                .sqlSessionFactory(sqlSessionFactory)
                .queryId(UsersMapper.class.getName() + ".selectAll")
                .build();
    }
    //MyBatisのCursor方式で、DBから「Users」データを1件ずつ読み込む
    //queryId でSQLの場所を指定しています（MyBatisのMapperのselectAllメソッド）。


    /** ジョブ */
    //usersJob という名前のジョブを作成
    @Bean
    public Job usersJob() {
        return new JobBuilder("usersJob", jobRepository)
                .start(usersStep1())
                .build();
    }

    /** ステップ */
    //ステップ usersStep1 はチャンク処理（まとめて10件ずつ処理）を行います。
    //Reader → DBからデータを読み込み
    //Processor → 読み込んだデータを加工（usersProcessor
    //Writer → 加工したデータを書き出し（usersWriter）
    //allowStartIfComplete(true) はジョブが完了していても何度でも再実行できる設定。
    @Bean
    public Step usersStep1() {
        return new StepBuilder("usersStep1", jobRepository)
                .<Users, Users>chunk(10, platformTransactionManager)
                .reader(userReader())
                .processor(usersProcessor)
                .writer(usersWriter)
                .allowStartIfComplete(true) // true:実行履歴があっても何度でも再実行可能。false:一度だけ実行可能
                .build();
    }
}