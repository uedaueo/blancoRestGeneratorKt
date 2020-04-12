
はじめに
==============

このファイルは、
blancoRestGenerator
のREADMEファイルです。

blancoRestGenerator はJava によるRestful API の電文と電文処理を生成するためのツールです。

1.電文処理定義書・電文定義書から Reqeust, Response, AbstractApi コードを自動生成します。

2.Apache Antタスクの形式で配布されています。

3.blacoRest から派生しています。blancoRestとの差異は、電文定義やAPI処理の基底クラスと、Serveletが含まれないことです。

 > - ant -f task.xml clean で一時ファイルは全てクリーンされます
 > - ant -f task.xml meta で最低限必要な Java ソースを生成します（一回目はエラーになりますが気にしないで下さい）
 > - ant -f task.xml compile で必要な Java ソースをコンパイルします
 > - ant meta
 > - ant compile
 > - ant jar : ここで作成されたjarファイルを各プロジェクトで使用します．

## 電文ID, 電文処理IDに関するルール

電文IDは、以下のルールで電文処理IDから一意に決定されます。

```
電文ID = 電文処理ID + HTTPメソッド名 + Request|Response
```

### 例

|項目|値|備考|
|:--|:--|:--|
|電文処理ID|BlancoApi|API名|
|要求電文ID (GETメソッド)|BlancoApiGetRequest|要求電文ID|
|応答電文ID (GETメソッド)|BlancoApiGetResponse|応答電文ID|

##開発者
うえだうえお(tueda)

##ライセンス
 blancoRest は ライセンス として GNU Lesser General Public License を採用しています。

##依存するライブラリ
blancoRestは下記のライブラリを利用しています。
※各オープンソース・プロダクトの提供者に感謝します。

 1.JExcelApi - Java Excel API - A Java API to read, write and modify Excel spreadsheets
     http://jexcelapi.sourceforge.net/
     http://sourceforge.net/projects/jexcelapi/
     http://www.andykhan.com/jexcelapi/
   概要: JavaからExcelブック形式を読み書きするためのライブラリです。
   ライセンス: GNU Lesser General Public License

 2.blancoCodeGenerator
   概要: ソースコード生成ライブラリ
   ライセンス: GNU Lesser General Public License

 3.blancoCommons
   概要: blanco Framework共通ライブラリ
         メタ情報ファイルを読み込む際に利用しています。
   ライセンス: GNU Lesser General Public License

==============
設定ファイル
==============

/etc/blancorest/config.xml を配置します。

```config.xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
   <properties>
    <comment>Runtime Properties for blancoRest</comment>
    <entry key="ApiUrl">http://localhost/dapanda/</entry>
    <entry key="AuthId"></entry>
    <entry key="AuthPass"></entry>
    <entry key="UserAgent">BlancoRestAPI test client</entry>
    <entry key="SocketTimeout">3</entry>
    <entry key="ConnectionTimeout">3</entry>
    <entry key="Loglevel">LOG_DEBUG</entry>
   </properties>
```

