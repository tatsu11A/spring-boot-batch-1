package com.example.demo.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.common.entity.Users;

/** ユーザマッパー */
//MyBatisのMapperインターフェース
//JavaのメソッドとSQLをつなげるためのインターフェース。
@Mapper
// SpringやMyBatisに「これはMapperです」と教えるアノテーション。

public interface UsersMapper {
//ユーザー関連のDB操作をまとめたもの。

    @Select("select * from users")
    //このメソッドが呼ばれたらこのSQLを実行する、という宣言。
    //つまり、selectAll() メソッドは「usersテーブルの全行を取得する」という処理になる。
    Users selectAll();
    //QL結果を Users エンティティのインスタンスとして返すメソッド。
}
