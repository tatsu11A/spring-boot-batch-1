package main.java.com.example.demo.batch.master.user.chunk;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.common.entity.Users;

import lombok.RequiredArgsConstructor;

/** ライター */
//Spring Batchの ItemWriter インターフェースを実装したクラス
//バッチ処理で 「加工済みデータを出力・書き込みする役割」 

@Component
//@Component はSpringがこのクラスを管理し、自動的にバッチ処理の中で使います。
@RequiredArgsConstructor
//@RequiredArgsConstructor は今回特に使われていませんが、必要なコンストラクタを自動生成します。
public class UsersWriter implements ItemWriter<Users> {
    
    @Override
    public void write(@NonNull Chunk<? extends Users> list) throws Exception {
// write メソッドは、Chunk（データの塊）を受け取り、その中のデータを処理するためにオーバーライドされています。
        System.out.println("********* start ************");

        for (Users users: list) {
            System.out.println(users);
        }

        System.out.println("********* end **************");
    }
}
