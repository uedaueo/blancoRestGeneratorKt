package blanco.restgeneratorkt;

/**
 * BlancoRestGeneratorKt が利用する定数を蓄えます。
 */
public class BlancoRestGeneratorKtConstants {
    /**
     * 項目番号:1<br>
     * プロダクト名。英字で指定します。
     */
    public static final String PRODUCT_NAME = "blancoRestGeneratorKt";

    /**
     * 項目番号:2<br>
     * プロダクト名の小文字版。英字で指定します。
     */
    public static final String PRODUCT_NAME_LOWER = "blancorestgeneratorkt";

    /**
     * 項目番号:3<br>
     * バージョン番号。
     */
    public static final String VERSION = "0.0.1";

    /**
     * 項目番号:4<br>
     * 処理の過程で利用されるサブディレクトリ。
     */
    public static final String TARGET_SUBDIRECTORY = "/restgeneratorkt";

    /**
     * 項目番号:5<br>
     * valueobjectが格納されているサブディレクトリ
     */
    public static final String OBJECT_SUBDIRECTORY = "/valueobjectkt";

    /**
     * 項目番号:6<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_BLANCO = "blanco";

    /**
     * 項目番号:7<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_MAVEN = "maven";

    /**
     * 項目番号:8<br>
     * targetdirに設定される文字列
     */
    public static final String TARGET_STYLE_FREE = "free";

    /**
     * 項目番号:9<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_BLANCO = "main";

    /**
     * 項目番号:10<br>
     * 生成したソースコードを保管するディレクトリのsuffix
     */
    public static final String TARGET_DIR_SUFFIX_MAVEN = "main/kotlin";

    /**
     * 項目番号:11<br>
     * 生成されるAbstractクラスのプレフィックス
     */
    public static final String PREFIX_ABSTRACT = "Abstract";

    /**
     * 項目番号:11<br>
     * 生成されるコントローラクラスのサフィックス
     */
    public static final String SUFFIX_CONTROLLER = "Controller";

    /**
     * 項目番号:12<br>
     * コントローラから呼び出される実装処理クラスのサフィックス
     */
    public static final String SUFFIX_MANAGER = "Management";

    /**
     * 項目番号:13<br>
     * APIが継承するベースクラス名
     */
    public static final String BASE_CLASS = "blanco.rest.common.ApiBase";

    /**
     * 項目番号:1<br>
     * ベースクラスから呼ばれる実行メソッド名
     */
    public static final String BASE_EXECUTOR_METHOD = "execute";

    /**
     * 項目番号:2<br>
     * API実装クラスの初期化メソッド名
     */
    public static final String API_INITIALIZER_METHOD = "initialize";

    /**
     * 項目番号:3<br>
     * API実装クラスの処理メソッド名
     */
    public static final String API_PROCESS_METHOD = "process";

    /**
     * 項目番号:4<br>
     * APIが認証を必要とする場合のフラグ変数名
     */
    public static final String API_AUTHENTICATION_REQUIRED = "isAuthenticationRequired";

    /**
     * 項目番号:5<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_GET_REQUESTID_METHOD = "getGetRequestId";

    /**
     * 項目番号:6<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_POST_REQUESTID_METHOD = "getPostRequestId";

    /**
     * 項目番号:7<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_PUT_REQUESTID_METHOD = "getPutRequestId";

    /**
     * 項目番号:8<br>
     * APIのリクエストID名を取得するメソッド名
     */
    public static final String API_DELETE_REQUESTID_METHOD = "getDeleteRequestId";

    /**
     * 項目番号:9<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_GET_RESPONSEID_METHOD = "getGetResponseId";

    /**
     * 項目番号:10<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_POST_RESPONSEID_METHOD = "getPostResponseId";

    /**
     * 項目番号:11<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_PUT_RESPONSEID_METHOD = "getPutResponseId";

    /**
     * 項目番号:12<br>
     * APIのレスポンスID名を取得するメソッド名
     */
    public static final String API_DELETE_RESPONSEID_METHOD = "getDeleteResponseId";

    /**
     * 項目番号:13<br>
     * プロダクトのデフォルトパッケージ名です．
     */
    public static final String DEFAULT_PACKAGE = "blanco.restgeneratorkt";

    /**
     * 項目番号:14<br>
     * ValueObjectのパッケージ名です．
     */
    public static final String VALUEOBJECT_PACKAGE = "blanco.restgeneratorkt.valueobject";

    /**
     * 項目番号:15<br>
     * BlancoRestの設定ファイル名です．
     */
    public static final String CONFIG_FILE = "/etc/blancorest/config.xml";

    /**
     * 項目番号:16<br>
     * BlancoRestの標準例外クラス名です．
     */
    public static final String DEFAULT_EXCEPTION = "Error";

    /**
     * 項目番号:17<br>
     * APIの戻りステータス（Accepted）
     */
    public static final String API_STATUS_ACCEPTED = "accept";

    /**
     * 項目番号:18<br>
     * APIの戻りステータス（Dismiss）
     */
    public static final String API_STATUS_DISMISS = "dismiss";

    /**
     * 項目番号:19<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_GET_REQUESTID = "ApiGetTelegram";

    /**
     * 項目番号:20<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_POST_REQUESTID = "ApiPostTelegram";

    /**
     * 項目番号:21<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_PUT_REQUESTID = "ApiPutTelegram";

    /**
     * 項目番号:22<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_DELETE_REQUESTID = "ApiDeleteTelegram";

    /**
     * 項目番号:23<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_GET_RESPONSEID = "ApiGetTelegram";

    /**
     * 項目番号:24<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_POST_RESPONSEID = "ApiPostTelegram";

    /**
     * 項目番号:25<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_PUT_RESPONSEID = "ApiPutTelegram";

    /**
     * 項目番号:26<br>
     * デフォルトのAPIのリクエストID
     */
    public static final String DEFAULT_API_DELETE_RESPONSEID = "ApiDeleteTelegram";

    /**
     * 項目番号:27<br>
     * HTTPメソッドGETを表す文字列
     */
    public static final String HTTP_METHOD_GET = "HTTP_METHOD_GET";

    /**
     * 項目番号:28<br>
     * HTTPメソッドPOSTを表す文字列
     */
    public static final String HTTP_METHOD_POST = "HTTP_METHOD_POST";

    /**
     * 項目番号:29<br>
     * HTTPメソッドPUTを表す文字列
     */
    public static final String HTTP_METHOD_PUT = "HTTP_METHOD_PUT";

    /**
     * 項目番号:30<br>
     * HTTPメソッドDELETEを表す文字列
     */
    public static final String HTTP_METHOD_DELETE = "HTTP_METHOD_DELETE";

    /**
     * 項目番号:31<br>
     * 電文種別INPUTを表す文字列
     */
    public static final String TELEGRAM_INPUT = "TELEGRAM_INPUT";

    /**
     * 項目番号:32<br>
     * 電文種別OUTPUTを表す文字列
     */
    public static final String TELEGRAM_OUTPUT = "TELEGRAM_OUTPUT";

    /**
     * 項目番号:14<br>
     * POSTメソッド時に呼ばれるmicronaut controller用メソッド
     */
    public static final String POST_CONTROLLER_METHOD = "doPost";

    /**
     * 項目番号:15<br>
     * PUTメソッド時に呼ばれるmicronaut controller用メソッド
     */
    public static final String PUT_CONTROLLER_METHOD = "doPut";

    /**
     * 項目番号:16<br>
     * GETメソッド時に呼ばれるmicronaut controller用メソッド
     */
    public static final String GET_CONTROLLER_METHOD = "doGet";

    /**
     * 項目番号:17<br>
     * DELETEメソッド時に呼ばれるmicronaut controller用メソッド
     */
    public static final String DELETE_CONTROLLER_METHOD = "doDelete";

    /**
     * 項目番号:18<br>
     * コントローラから呼び出す実装クラスのサブパッケージ名
     */
    public static final String MANAGER_PACKAGE = "application";

    /**
     * 項目番号:19<br>
     * コントローラから呼び出す実装クラスが実装するインタフェイスのサブパッケージ名
     */
    public static final String INTERFACE_PACKAGE = "interface";
}
