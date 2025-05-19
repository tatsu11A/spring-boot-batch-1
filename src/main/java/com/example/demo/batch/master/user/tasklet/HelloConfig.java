package com.example.demo.batch.master.user.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
//中核設定ファイル

/** コンフィグ ついてる設定クラス*/
@Configuration
@RequiredArgsConstructor
public class HelloConfig {

    private final JobRepository jobRepository;
    /** ジョブの実行履歴や状態を管理する仕組み。 */
    private final PlatformTransactionManager platformTransactionManager;
    /**データベースのトランザクション管理を行う仕組み。 */
    /** 「必要なオブジェクトを外から渡す」仕組みのこと */
    /** タスクレット 
     * Tasklet は Spring Batch の処理の最小単位。
    helloTasklet() メソッドで Tasklet オブジェクトを作り、それを Spring の管理下に置くために @Bean をつけている。
    こうすると、この Tasklet が名前（helloTasklet）で Spring のコンテナに登録される。
    ジョブの中でこの Tasklet を呼び出して「実行する処理」として使う。* */

    @Bean
    public Tasklet helloTasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("************ hello world! ************");
            return null;
        };
    }

    /** ジョブ */
    @Bean
    public Job helloJob() {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep())
                .build();
    }

    /** ステップ
     * Step はバッチ処理の「1つの段階」や「処理単位」のこと
     */

    @Bean
    public Step helloStep() {
        return new StepBuilder("helloStep", jobRepository)
                .tasklet(helloTasklet(), platformTransactionManager)
                .allowStartIfComplete(true)// 成功済みジョブでも何度でも実行可能にする設定。false にして「二重実行を防止」することがある。

                .build();
    }
}