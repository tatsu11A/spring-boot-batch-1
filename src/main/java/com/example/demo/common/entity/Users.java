package main.java.com.example.demo.common.entity;

import java.time.LocalDate;

import lombok.Data;

/** ユーザエンティティ */
//ーザーデータを表すエンティティクラス 
//データベースの users テーブルの1行（1レコード）に対応
//Javaオブジェクトとしてユーザーデータを扱うためのクラス

@Data
//便利なgetter/setter等を自動生成
//getter（値を取得するメソッド）
//setter（値を設定するメソッド）
//toString()（オブジェクトの文字列表現）
//equals() / hashCode()（オブジェクト比較）
public class Users {
    private Integer id;
    //ユーザーの識別ID（主キー）
    private String name;
    //ユーザーの名前
    private String department;
    //ユーザーが所属する部署
    private LocalDate createdAt;
    //登録日や作成日（年月日だけ）
}
