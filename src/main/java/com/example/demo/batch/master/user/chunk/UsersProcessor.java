package com.example.demo.batch.master.user.chunk;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.common.entity.Users;

/** プロセッサー */
//Spring Batchの ItemProcessor を実装したクラス
//バッチ処理の 「読み込んだデータを加工・変換する部分」 を担当

@Component
//Springの管理する「部品（Bean）」として登録
public class UsersProcessor implements ItemProcessor<Users, Users> {

    @Override
    public Users process(@SuppressWarnings("null") Users users) throws Exception {
        return users;
    }
    //コードでは 「何も加工せずそのまま返す」 処理
}
