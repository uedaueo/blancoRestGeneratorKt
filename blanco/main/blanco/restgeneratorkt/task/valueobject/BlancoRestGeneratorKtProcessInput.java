package blanco.restgeneratorkt.task.valueobject;

/**
 * An input value object class for the processing class [BlancoRestGeneratorKtProcess].
 */
public class BlancoRestGeneratorKtProcessInput {
    /**
     * Whether to run in verbose mode.
     *
     * フィールド: [verbose]。
     * デフォルト: [false]。
     */
    private boolean fVerbose = false;

    /**
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。
     *
     * フィールド: [metadir]。
     */
    private String fMetadir;

    /**
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。
     *
     * フィールド: [targetdir]。
     * デフォルト: [blanco]。
     */
    private String fTargetdir = "blanco";

    /**
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。
     *
     * フィールド: [tmpdir]。
     * デフォルト: [tmp]。
     */
    private String fTmpdir = "tmp";

    /**
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。
     *
     * フィールド: [searchTmpdir]。
     */
    private String fSearchTmpdir;

    /**
     * フィールド名やメソッド名を名前変形を実施するかどうか。
     *
     * フィールド: [nameAdjust]。
     * デフォルト: [true]。
     */
    private boolean fNameAdjust = true;

    /**
     * 自動生成するソースファイルの文字エンコーディングを指定します。
     *
     * フィールド: [encoding]。
     */
    private String fEncoding;

    /**
     * タブをwhite spaceいくつで置き換えるか、という値です。
     *
     * フィールド: [tabs]。
     * デフォルト: [4]。
     */
    private int fTabs = 4;

    /**
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。
     *
     * フィールド: [xmlrootelement]。
     * デフォルト: [false]。
     */
    private boolean fXmlrootelement = false;

    /**
     * meta定義書が期待しているプログラミング言語を指定します
     *
     * フィールド: [sheetType]。
     * デフォルト: [java]。
     */
    private String fSheetType = "java";

    /**
     * 出力先フォルダの書式を指定します。&amp;lt;br&amp;gt;\nblanco: [targetdir]/main&amp;lt;br&amp;gt;\nmaven: [targetdir]/main/java&amp;lt;br&amp;gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)
     *
     * フィールド: [targetStyle]。
     * デフォルト: [blanco]。
     */
    private String fTargetStyle = "blanco";

    /**
     * trueの場合はサーバ用のメソッドを生成しません。
     *
     * フィールド: [client]。
     * デフォルト: [false]。
     */
    private boolean fClient = false;

    /**
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。
     *
     * フィールド: [clientAnnotation]。
     */
    private String fClientAnnotation;

    /**
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。
     *
     * フィールド: [overrideClientAnnotation]。
     */
    private String fOverrideClientAnnotation;

    /**
     * Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。
     *
     * フィールド: [serverType]。
     * デフォルト: [micronaut]。
     */
    private String fServerType = "micronaut";

    /**
     * blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。
     *
     * フィールド: [basepackage]。
     */
    private String fBasepackage;

    /**
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。
     *
     * フィールド: [runtimepackage]。
     */
    private String fRuntimepackage;

    /**
     * ユーティリティ類の生成を省略する場合はfalseを指定します。
     *
     * フィールド: [genUtils]。
     * デフォルト: [true]。
     */
    private boolean fGenUtils = true;

    /**
     * 電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。
     *
     * フィールド: [telegrampackage]。
     */
    private String fTelegrampackage;

    /**
     * 実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。
     *
     * フィールド: [impledir]。
     */
    private String fImpledir;

    /**
     * controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。
     *
     * フィールド: [genSkeleton]。
     * デフォルト: [false]。
     */
    private boolean fGenSkeleton = false;

    /**
     * 実装クラスが処理を委譲するクラスのCanonical名です。
     *
     * フィールド: [skeletonDelegateClass]。
     */
    private String fSkeletonDelegateClass;

    /**
     * 実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。
     *
     * フィールド: [skeletonDelegateInterface]。
     */
    private String fSkeletonDelegateInterface;

    /**
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。
     *
     * フィールド: [lineSeparator]。
     * デフォルト: [LF]。
     */
    private String fLineSeparator = "LF";

    /**
     * 定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。
     *
     * フィールド: [packageSuffix]。
     */
    private String fPackageSuffix;

    /**
     * 定義書で指定されたパッケージ名を上書きします。
     *
     * フィールド: [overridePackage]。
     */
    private String fOverridePackage;

    /**
     * 定義書で指定されたロケーション名を上書きします。
     *
     * フィールド: [overrideLocation]。
     */
    private String fOverrideLocation;

    /**
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。
     *
     * フィールド: [voPackageSuffix]。
     */
    private String fVoPackageSuffix;

    /**
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。
     *
     * フィールド: [voOverridePackage]。
     */
    private String fVoOverridePackage;

    /**
     * 電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。
     *
     * フィールド: [telegramStyle]。
     * デフォルト: [blanco]。
     */
    private String fTelegramStyle = "blanco";

    /**
     * Management クラスの package として、Controller クラスの package に application を追加したものを想定し、importします。
     *
     * フィールド: [appendApplicationPackage]。
     * デフォルト: [true]。
     */
    private boolean fAppendApplicationPackage = true;

    /**
     * 電文クラスに@Serdeableアノテーションを付与します。
     *
     * フィールド: [serdeable]。
     * デフォルト: [false]。
     */
    private boolean fSerdeable = false;

    /**
     * 電文クラスに@JsonIgnoreProperties(ignoreunknow = true)アノテーションを付与します。
     *
     * フィールド: [ignoreUnknown]。
     * デフォルト: [false]。
     */
    private boolean fIgnoreUnknown = false;

    /**
     * 「必須」が指定されていないパラメータに@Nullableアノテーションを強制します
     *
     * フィールド: [nullableAnnotation]。
     * デフォルト: [false]。
     */
    private boolean fNullableAnnotation = false;

    /**
     * target が JVM17 以降の開発環境で利用する場合、J2EE の代わりに JakartaEE を使用する。
     *
     * フィールド: [isTargetJaraktaEE]。
     * デフォルト: [false]。
     */
    private boolean fIsTargetJaraktaEE = false;

    /**
     * Micronaut の Controller にManagement クラスの代わりに Interface を依存注入する。true にすると genSkelton と appendApplicationPackage は false を強制されます。
     *
     * フィールド: [injectInterfaceToController]。
     * デフォルト: [false]。
     */
    private boolean fInjectInterfaceToController = false;

    /**
     * 想定する Micronaut の最低バージョンを指定します。バージョンによって若干の動作差異や不具合があるため、それに対応する目的です。
     *
     * フィールド: [micronautVersion]。
     * デフォルト: [3.0]。
     */
    private String fMicronautVersion = "3.0";

    /**
     * フィールド [verbose] の値を設定します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     *
     * @param argVerbose フィールド[verbose]に設定する値。
     */
    public void setVerbose(final boolean argVerbose) {
        fVerbose = argVerbose;
    }

    /**
     * フィールド [verbose] の値を取得します。
     *
     * フィールドの説明: [Whether to run in verbose mode.]。
     * デフォルト: [false]。
     *
     * @return フィールド[verbose]から取得した値。
     */
    public boolean getVerbose() {
        return fVerbose;
    }

    /**
     * フィールド [metadir] の値を設定します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @param argMetadir フィールド[metadir]に設定する値。
     */
    public void setMetadir(final String argMetadir) {
        fMetadir = argMetadir;
    }

    /**
     * フィールド [metadir] の値を取得します。
     *
     * フィールドの説明: [メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]。
     *
     * @return フィールド[metadir]から取得した値。
     */
    public String getMetadir() {
        return fMetadir;
    }

    /**
     * フィールド [targetdir] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     *
     * @param argTargetdir フィールド[targetdir]に設定する値。
     */
    public void setTargetdir(final String argTargetdir) {
        fTargetdir = argTargetdir;
    }

    /**
     * フィールド [targetdir] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetdir]から取得した値。
     */
    public String getTargetdir() {
        return fTargetdir;
    }

    /**
     * フィールド [tmpdir] の値を設定します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     *
     * @param argTmpdir フィールド[tmpdir]に設定する値。
     */
    public void setTmpdir(final String argTmpdir) {
        fTmpdir = argTmpdir;
    }

    /**
     * フィールド [tmpdir] の値を取得します。
     *
     * フィールドの説明: [テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]。
     * デフォルト: [tmp]。
     *
     * @return フィールド[tmpdir]から取得した値。
     */
    public String getTmpdir() {
        return fTmpdir;
    }

    /**
     * フィールド [searchTmpdir] の値を設定します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @param argSearchTmpdir フィールド[searchTmpdir]に設定する値。
     */
    public void setSearchTmpdir(final String argSearchTmpdir) {
        fSearchTmpdir = argSearchTmpdir;
    }

    /**
     * フィールド [searchTmpdir] の値を取得します。
     *
     * フィールドの説明: [import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]。
     *
     * @return フィールド[searchTmpdir]から取得した値。
     */
    public String getSearchTmpdir() {
        return fSearchTmpdir;
    }

    /**
     * フィールド [nameAdjust] の値を設定します。
     *
     * フィールドの説明: [フィールド名やメソッド名を名前変形を実施するかどうか。]。
     *
     * @param argNameAdjust フィールド[nameAdjust]に設定する値。
     */
    public void setNameAdjust(final boolean argNameAdjust) {
        fNameAdjust = argNameAdjust;
    }

    /**
     * フィールド [nameAdjust] の値を取得します。
     *
     * フィールドの説明: [フィールド名やメソッド名を名前変形を実施するかどうか。]。
     * デフォルト: [true]。
     *
     * @return フィールド[nameAdjust]から取得した値。
     */
    public boolean getNameAdjust() {
        return fNameAdjust;
    }

    /**
     * フィールド [encoding] の値を設定します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @param argEncoding フィールド[encoding]に設定する値。
     */
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * フィールド [encoding] の値を取得します。
     *
     * フィールドの説明: [自動生成するソースファイルの文字エンコーディングを指定します。]。
     *
     * @return フィールド[encoding]から取得した値。
     */
    public String getEncoding() {
        return fEncoding;
    }

    /**
     * フィールド [tabs] の値を設定します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     *
     * @param argTabs フィールド[tabs]に設定する値。
     */
    public void setTabs(final int argTabs) {
        fTabs = argTabs;
    }

    /**
     * フィールド [tabs] の値を取得します。
     *
     * フィールドの説明: [タブをwhite spaceいくつで置き換えるか、という値です。]。
     * デフォルト: [4]。
     *
     * @return フィールド[tabs]から取得した値。
     */
    public int getTabs() {
        return fTabs;
    }

    /**
     * フィールド [xmlrootelement] の値を設定します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     *
     * @param argXmlrootelement フィールド[xmlrootelement]に設定する値。
     */
    public void setXmlrootelement(final boolean argXmlrootelement) {
        fXmlrootelement = argXmlrootelement;
    }

    /**
     * フィールド [xmlrootelement] の値を取得します。
     *
     * フィールドの説明: [XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]。
     * デフォルト: [false]。
     *
     * @return フィールド[xmlrootelement]から取得した値。
     */
    public boolean getXmlrootelement() {
        return fXmlrootelement;
    }

    /**
     * フィールド [sheetType] の値を設定します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     *
     * @param argSheetType フィールド[sheetType]に設定する値。
     */
    public void setSheetType(final String argSheetType) {
        fSheetType = argSheetType;
    }

    /**
     * フィールド [sheetType] の値を取得します。
     *
     * フィールドの説明: [meta定義書が期待しているプログラミング言語を指定します]。
     * デフォルト: [java]。
     *
     * @return フィールド[sheetType]から取得した値。
     */
    public String getSheetType() {
        return fSheetType;
    }

    /**
     * フィールド [targetStyle] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     *
     * @param argTargetStyle フィールド[targetStyle]に設定する値。
     */
    public void setTargetStyle(final String argTargetStyle) {
        fTargetStyle = argTargetStyle;
    }

    /**
     * フィールド [targetStyle] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetStyle]から取得した値。
     */
    public String getTargetStyle() {
        return fTargetStyle;
    }

    /**
     * フィールド [client] の値を設定します。
     *
     * フィールドの説明: [trueの場合はサーバ用のメソッドを生成しません。]。
     *
     * @param argClient フィールド[client]に設定する値。
     */
    public void setClient(final boolean argClient) {
        fClient = argClient;
    }

    /**
     * フィールド [client] の値を取得します。
     *
     * フィールドの説明: [trueの場合はサーバ用のメソッドを生成しません。]。
     * デフォルト: [false]。
     *
     * @return フィールド[client]から取得した値。
     */
    public boolean getClient() {
        return fClient;
    }

    /**
     * フィールド [clientAnnotation] の値を設定します。
     *
     * フィールドの説明: [Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。]。
     *
     * @param argClientAnnotation フィールド[clientAnnotation]に設定する値。
     */
    public void setClientAnnotation(final String argClientAnnotation) {
        fClientAnnotation = argClientAnnotation;
    }

    /**
     * フィールド [clientAnnotation] の値を取得します。
     *
     * フィールドの説明: [Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。]。
     *
     * @return フィールド[clientAnnotation]から取得した値。
     */
    public String getClientAnnotation() {
        return fClientAnnotation;
    }

    /**
     * フィールド [overrideClientAnnotation] の値を設定します。
     *
     * フィールドの説明: [Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。]。
     *
     * @param argOverrideClientAnnotation フィールド[overrideClientAnnotation]に設定する値。
     */
    public void setOverrideClientAnnotation(final String argOverrideClientAnnotation) {
        fOverrideClientAnnotation = argOverrideClientAnnotation;
    }

    /**
     * フィールド [overrideClientAnnotation] の値を取得します。
     *
     * フィールドの説明: [Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。]。
     *
     * @return フィールド[overrideClientAnnotation]から取得した値。
     */
    public String getOverrideClientAnnotation() {
        return fOverrideClientAnnotation;
    }

    /**
     * フィールド [serverType] の値を設定します。
     *
     * フィールドの説明: [Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。]。
     *
     * @param argServerType フィールド[serverType]に設定する値。
     */
    public void setServerType(final String argServerType) {
        fServerType = argServerType;
    }

    /**
     * フィールド [serverType] の値を取得します。
     *
     * フィールドの説明: [Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。]。
     * デフォルト: [micronaut]。
     *
     * @return フィールド[serverType]から取得した値。
     */
    public String getServerType() {
        return fServerType;
    }

    /**
     * フィールド [basepackage] の値を設定します。
     *
     * フィールドの説明: [blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。]。
     *
     * @param argBasepackage フィールド[basepackage]に設定する値。
     */
    public void setBasepackage(final String argBasepackage) {
        fBasepackage = argBasepackage;
    }

    /**
     * フィールド [basepackage] の値を取得します。
     *
     * フィールドの説明: [blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。]。
     *
     * @return フィールド[basepackage]から取得した値。
     */
    public String getBasepackage() {
        return fBasepackage;
    }

    /**
     * フィールド [runtimepackage] の値を設定します。
     *
     * フィールドの説明: [ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。]。
     *
     * @param argRuntimepackage フィールド[runtimepackage]に設定する値。
     */
    public void setRuntimepackage(final String argRuntimepackage) {
        fRuntimepackage = argRuntimepackage;
    }

    /**
     * フィールド [runtimepackage] の値を取得します。
     *
     * フィールドの説明: [ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。]。
     *
     * @return フィールド[runtimepackage]から取得した値。
     */
    public String getRuntimepackage() {
        return fRuntimepackage;
    }

    /**
     * フィールド [genUtils] の値を設定します。
     *
     * フィールドの説明: [ユーティリティ類の生成を省略する場合はfalseを指定します。]。
     *
     * @param argGenUtils フィールド[genUtils]に設定する値。
     */
    public void setGenUtils(final boolean argGenUtils) {
        fGenUtils = argGenUtils;
    }

    /**
     * フィールド [genUtils] の値を取得します。
     *
     * フィールドの説明: [ユーティリティ類の生成を省略する場合はfalseを指定します。]。
     * デフォルト: [true]。
     *
     * @return フィールド[genUtils]から取得した値。
     */
    public boolean getGenUtils() {
        return fGenUtils;
    }

    /**
     * フィールド [telegrampackage] の値を設定します。
     *
     * フィールドの説明: [電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。]。
     *
     * @param argTelegrampackage フィールド[telegrampackage]に設定する値。
     */
    public void setTelegrampackage(final String argTelegrampackage) {
        fTelegrampackage = argTelegrampackage;
    }

    /**
     * フィールド [telegrampackage] の値を取得します。
     *
     * フィールドの説明: [電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。]。
     *
     * @return フィールド[telegrampackage]から取得した値。
     */
    public String getTelegrampackage() {
        return fTelegrampackage;
    }

    /**
     * フィールド [impledir] の値を設定します。
     *
     * フィールドの説明: [実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。]。
     *
     * @param argImpledir フィールド[impledir]に設定する値。
     */
    public void setImpledir(final String argImpledir) {
        fImpledir = argImpledir;
    }

    /**
     * フィールド [impledir] の値を取得します。
     *
     * フィールドの説明: [実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。]。
     *
     * @return フィールド[impledir]から取得した値。
     */
    public String getImpledir() {
        return fImpledir;
    }

    /**
     * フィールド [genSkeleton] の値を設定します。
     *
     * フィールドの説明: [controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。]。
     *
     * @param argGenSkeleton フィールド[genSkeleton]に設定する値。
     */
    public void setGenSkeleton(final boolean argGenSkeleton) {
        fGenSkeleton = argGenSkeleton;
    }

    /**
     * フィールド [genSkeleton] の値を取得します。
     *
     * フィールドの説明: [controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。]。
     * デフォルト: [false]。
     *
     * @return フィールド[genSkeleton]から取得した値。
     */
    public boolean getGenSkeleton() {
        return fGenSkeleton;
    }

    /**
     * フィールド [skeletonDelegateClass] の値を設定します。
     *
     * フィールドの説明: [実装クラスが処理を委譲するクラスのCanonical名です。]。
     *
     * @param argSkeletonDelegateClass フィールド[skeletonDelegateClass]に設定する値。
     */
    public void setSkeletonDelegateClass(final String argSkeletonDelegateClass) {
        fSkeletonDelegateClass = argSkeletonDelegateClass;
    }

    /**
     * フィールド [skeletonDelegateClass] の値を取得します。
     *
     * フィールドの説明: [実装クラスが処理を委譲するクラスのCanonical名です。]。
     *
     * @return フィールド[skeletonDelegateClass]から取得した値。
     */
    public String getSkeletonDelegateClass() {
        return fSkeletonDelegateClass;
    }

    /**
     * フィールド [skeletonDelegateInterface] の値を設定します。
     *
     * フィールドの説明: [実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。]。
     *
     * @param argSkeletonDelegateInterface フィールド[skeletonDelegateInterface]に設定する値。
     */
    public void setSkeletonDelegateInterface(final String argSkeletonDelegateInterface) {
        fSkeletonDelegateInterface = argSkeletonDelegateInterface;
    }

    /**
     * フィールド [skeletonDelegateInterface] の値を取得します。
     *
     * フィールドの説明: [実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。]。
     *
     * @return フィールド[skeletonDelegateInterface]から取得した値。
     */
    public String getSkeletonDelegateInterface() {
        return fSkeletonDelegateInterface;
    }

    /**
     * フィールド [lineSeparator] の値を設定します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     *
     * @param argLineSeparator フィールド[lineSeparator]に設定する値。
     */
    public void setLineSeparator(final String argLineSeparator) {
        fLineSeparator = argLineSeparator;
    }

    /**
     * フィールド [lineSeparator] の値を取得します。
     *
     * フィールドの説明: [行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]。
     * デフォルト: [LF]。
     *
     * @return フィールド[lineSeparator]から取得した値。
     */
    public String getLineSeparator() {
        return fLineSeparator;
    }

    /**
     * フィールド [packageSuffix] の値を設定します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]。
     *
     * @param argPackageSuffix フィールド[packageSuffix]に設定する値。
     */
    public void setPackageSuffix(final String argPackageSuffix) {
        fPackageSuffix = argPackageSuffix;
    }

    /**
     * フィールド [packageSuffix] の値を取得します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]。
     *
     * @return フィールド[packageSuffix]から取得した値。
     */
    public String getPackageSuffix() {
        return fPackageSuffix;
    }

    /**
     * フィールド [overridePackage] の値を設定します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名を上書きします。]。
     *
     * @param argOverridePackage フィールド[overridePackage]に設定する値。
     */
    public void setOverridePackage(final String argOverridePackage) {
        fOverridePackage = argOverridePackage;
    }

    /**
     * フィールド [overridePackage] の値を取得します。
     *
     * フィールドの説明: [定義書で指定されたパッケージ名を上書きします。]。
     *
     * @return フィールド[overridePackage]から取得した値。
     */
    public String getOverridePackage() {
        return fOverridePackage;
    }

    /**
     * フィールド [overrideLocation] の値を設定します。
     *
     * フィールドの説明: [定義書で指定されたロケーション名を上書きします。]。
     *
     * @param argOverrideLocation フィールド[overrideLocation]に設定する値。
     */
    public void setOverrideLocation(final String argOverrideLocation) {
        fOverrideLocation = argOverrideLocation;
    }

    /**
     * フィールド [overrideLocation] の値を取得します。
     *
     * フィールドの説明: [定義書で指定されたロケーション名を上書きします。]。
     *
     * @return フィールド[overrideLocation]から取得した値。
     */
    public String getOverrideLocation() {
        return fOverrideLocation;
    }

    /**
     * フィールド [voPackageSuffix] の値を設定します。
     *
     * フィールドの説明: [packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。]。
     *
     * @param argVoPackageSuffix フィールド[voPackageSuffix]に設定する値。
     */
    public void setVoPackageSuffix(final String argVoPackageSuffix) {
        fVoPackageSuffix = argVoPackageSuffix;
    }

    /**
     * フィールド [voPackageSuffix] の値を取得します。
     *
     * フィールドの説明: [packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。]。
     *
     * @return フィールド[voPackageSuffix]から取得した値。
     */
    public String getVoPackageSuffix() {
        return fVoPackageSuffix;
    }

    /**
     * フィールド [voOverridePackage] の値を設定します。
     *
     * フィールドの説明: [packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。]。
     *
     * @param argVoOverridePackage フィールド[voOverridePackage]に設定する値。
     */
    public void setVoOverridePackage(final String argVoOverridePackage) {
        fVoOverridePackage = argVoOverridePackage;
    }

    /**
     * フィールド [voOverridePackage] の値を取得します。
     *
     * フィールドの説明: [packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。]。
     *
     * @return フィールド[voOverridePackage]から取得した値。
     */
    public String getVoOverridePackage() {
        return fVoOverridePackage;
    }

    /**
     * フィールド [telegramStyle] の値を設定します。
     *
     * フィールドの説明: [電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。]。
     *
     * @param argTelegramStyle フィールド[telegramStyle]に設定する値。
     */
    public void setTelegramStyle(final String argTelegramStyle) {
        fTelegramStyle = argTelegramStyle;
    }

    /**
     * フィールド [telegramStyle] の値を取得します。
     *
     * フィールドの説明: [電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[telegramStyle]から取得した値。
     */
    public String getTelegramStyle() {
        return fTelegramStyle;
    }

    /**
     * フィールド [appendApplicationPackage] の値を設定します。
     *
     * フィールドの説明: [Management クラスの package として、Controller クラスの package に application を追加したものを想定し、importします。]。
     *
     * @param argAppendApplicationPackage フィールド[appendApplicationPackage]に設定する値。
     */
    public void setAppendApplicationPackage(final boolean argAppendApplicationPackage) {
        fAppendApplicationPackage = argAppendApplicationPackage;
    }

    /**
     * フィールド [appendApplicationPackage] の値を取得します。
     *
     * フィールドの説明: [Management クラスの package として、Controller クラスの package に application を追加したものを想定し、importします。]。
     * デフォルト: [true]。
     *
     * @return フィールド[appendApplicationPackage]から取得した値。
     */
    public boolean getAppendApplicationPackage() {
        return fAppendApplicationPackage;
    }

    /**
     * フィールド [serdeable] の値を設定します。
     *
     * フィールドの説明: [電文クラスに@Serdeableアノテーションを付与します。]。
     *
     * @param argSerdeable フィールド[serdeable]に設定する値。
     */
    public void setSerdeable(final boolean argSerdeable) {
        fSerdeable = argSerdeable;
    }

    /**
     * フィールド [serdeable] の値を取得します。
     *
     * フィールドの説明: [電文クラスに@Serdeableアノテーションを付与します。]。
     * デフォルト: [false]。
     *
     * @return フィールド[serdeable]から取得した値。
     */
    public boolean getSerdeable() {
        return fSerdeable;
    }

    /**
     * フィールド [ignoreUnknown] の値を設定します。
     *
     * フィールドの説明: [電文クラスに@JsonIgnoreProperties(ignoreunknow = true)アノテーションを付与します。]。
     *
     * @param argIgnoreUnknown フィールド[ignoreUnknown]に設定する値。
     */
    public void setIgnoreUnknown(final boolean argIgnoreUnknown) {
        fIgnoreUnknown = argIgnoreUnknown;
    }

    /**
     * フィールド [ignoreUnknown] の値を取得します。
     *
     * フィールドの説明: [電文クラスに@JsonIgnoreProperties(ignoreunknow = true)アノテーションを付与します。]。
     * デフォルト: [false]。
     *
     * @return フィールド[ignoreUnknown]から取得した値。
     */
    public boolean getIgnoreUnknown() {
        return fIgnoreUnknown;
    }

    /**
     * フィールド [nullableAnnotation] の値を設定します。
     *
     * フィールドの説明: [「必須」が指定されていないパラメータに@Nullableアノテーションを強制します]。
     *
     * @param argNullableAnnotation フィールド[nullableAnnotation]に設定する値。
     */
    public void setNullableAnnotation(final boolean argNullableAnnotation) {
        fNullableAnnotation = argNullableAnnotation;
    }

    /**
     * フィールド [nullableAnnotation] の値を取得します。
     *
     * フィールドの説明: [「必須」が指定されていないパラメータに@Nullableアノテーションを強制します]。
     * デフォルト: [false]。
     *
     * @return フィールド[nullableAnnotation]から取得した値。
     */
    public boolean getNullableAnnotation() {
        return fNullableAnnotation;
    }

    /**
     * フィールド [isTargetJaraktaEE] の値を設定します。
     *
     * フィールドの説明: [target が JVM17 以降の開発環境で利用する場合、J2EE の代わりに JakartaEE を使用する。]。
     *
     * @param argIsTargetJaraktaEE フィールド[isTargetJaraktaEE]に設定する値。
     */
    public void setIsTargetJaraktaEE(final boolean argIsTargetJaraktaEE) {
        fIsTargetJaraktaEE = argIsTargetJaraktaEE;
    }

    /**
     * フィールド [isTargetJaraktaEE] の値を取得します。
     *
     * フィールドの説明: [target が JVM17 以降の開発環境で利用する場合、J2EE の代わりに JakartaEE を使用する。]。
     * デフォルト: [false]。
     *
     * @return フィールド[isTargetJaraktaEE]から取得した値。
     */
    public boolean getIsTargetJaraktaEE() {
        return fIsTargetJaraktaEE;
    }

    /**
     * フィールド [injectInterfaceToController] の値を設定します。
     *
     * フィールドの説明: [Micronaut の Controller にManagement クラスの代わりに Interface を依存注入する。true にすると genSkelton と appendApplicationPackage は false を強制されます。]。
     *
     * @param argInjectInterfaceToController フィールド[injectInterfaceToController]に設定する値。
     */
    public void setInjectInterfaceToController(final boolean argInjectInterfaceToController) {
        fInjectInterfaceToController = argInjectInterfaceToController;
    }

    /**
     * フィールド [injectInterfaceToController] の値を取得します。
     *
     * フィールドの説明: [Micronaut の Controller にManagement クラスの代わりに Interface を依存注入する。true にすると genSkelton と appendApplicationPackage は false を強制されます。]。
     * デフォルト: [false]。
     *
     * @return フィールド[injectInterfaceToController]から取得した値。
     */
    public boolean getInjectInterfaceToController() {
        return fInjectInterfaceToController;
    }

    /**
     * フィールド [micronautVersion] の値を設定します。
     *
     * フィールドの説明: [想定する Micronaut の最低バージョンを指定します。バージョンによって若干の動作差異や不具合があるため、それに対応する目的です。]。
     *
     * @param argMicronautVersion フィールド[micronautVersion]に設定する値。
     */
    public void setMicronautVersion(final String argMicronautVersion) {
        fMicronautVersion = argMicronautVersion;
    }

    /**
     * フィールド [micronautVersion] の値を取得します。
     *
     * フィールドの説明: [想定する Micronaut の最低バージョンを指定します。バージョンによって若干の動作差異や不具合があるため、それに対応する目的です。]。
     * デフォルト: [3.0]。
     *
     * @return フィールド[micronautVersion]から取得した値。
     */
    public String getMicronautVersion() {
        return fMicronautVersion;
    }

    /**
     * Gets the string representation of this value object.
     *
     * <P>Precautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the stringification process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @return String representation of a value object.
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput[");
        buf.append("verbose=" + fVerbose);
        buf.append(",metadir=" + fMetadir);
        buf.append(",targetdir=" + fTargetdir);
        buf.append(",tmpdir=" + fTmpdir);
        buf.append(",searchTmpdir=" + fSearchTmpdir);
        buf.append(",nameAdjust=" + fNameAdjust);
        buf.append(",encoding=" + fEncoding);
        buf.append(",tabs=" + fTabs);
        buf.append(",xmlrootelement=" + fXmlrootelement);
        buf.append(",sheetType=" + fSheetType);
        buf.append(",targetStyle=" + fTargetStyle);
        buf.append(",client=" + fClient);
        buf.append(",clientAnnotation=" + fClientAnnotation);
        buf.append(",overrideClientAnnotation=" + fOverrideClientAnnotation);
        buf.append(",serverType=" + fServerType);
        buf.append(",basepackage=" + fBasepackage);
        buf.append(",runtimepackage=" + fRuntimepackage);
        buf.append(",genUtils=" + fGenUtils);
        buf.append(",telegrampackage=" + fTelegrampackage);
        buf.append(",impledir=" + fImpledir);
        buf.append(",genSkeleton=" + fGenSkeleton);
        buf.append(",skeletonDelegateClass=" + fSkeletonDelegateClass);
        buf.append(",skeletonDelegateInterface=" + fSkeletonDelegateInterface);
        buf.append(",lineSeparator=" + fLineSeparator);
        buf.append(",packageSuffix=" + fPackageSuffix);
        buf.append(",overridePackage=" + fOverridePackage);
        buf.append(",overrideLocation=" + fOverrideLocation);
        buf.append(",voPackageSuffix=" + fVoPackageSuffix);
        buf.append(",voOverridePackage=" + fVoOverridePackage);
        buf.append(",telegramStyle=" + fTelegramStyle);
        buf.append(",appendApplicationPackage=" + fAppendApplicationPackage);
        buf.append(",serdeable=" + fSerdeable);
        buf.append(",ignoreUnknown=" + fIgnoreUnknown);
        buf.append(",nullableAnnotation=" + fNullableAnnotation);
        buf.append(",isTargetJaraktaEE=" + fIsTargetJaraktaEE);
        buf.append(",injectInterfaceToController=" + fInjectInterfaceToController);
        buf.append(",micronautVersion=" + fMicronautVersion);
        buf.append("]");
        return buf.toString();
    }

    /**
     * Copies this value object to the specified target.
     *
     * <P>Cautions for use</P>
     * <UL>
     * <LI>Only the shallow range of the object will be subject to the copying process.
     * <LI>Do not use this method if the object has a circular reference.
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoRestGeneratorKtProcessInput target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtProcessInput#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fVerbose
        // Type: boolean
        target.fVerbose = this.fVerbose;
        // Name: fMetadir
        // Type: java.lang.String
        target.fMetadir = this.fMetadir;
        // Name: fTargetdir
        // Type: java.lang.String
        target.fTargetdir = this.fTargetdir;
        // Name: fTmpdir
        // Type: java.lang.String
        target.fTmpdir = this.fTmpdir;
        // Name: fSearchTmpdir
        // Type: java.lang.String
        target.fSearchTmpdir = this.fSearchTmpdir;
        // Name: fNameAdjust
        // Type: boolean
        target.fNameAdjust = this.fNameAdjust;
        // Name: fEncoding
        // Type: java.lang.String
        target.fEncoding = this.fEncoding;
        // Name: fTabs
        // Type: int
        target.fTabs = this.fTabs;
        // Name: fXmlrootelement
        // Type: boolean
        target.fXmlrootelement = this.fXmlrootelement;
        // Name: fSheetType
        // Type: java.lang.String
        target.fSheetType = this.fSheetType;
        // Name: fTargetStyle
        // Type: java.lang.String
        target.fTargetStyle = this.fTargetStyle;
        // Name: fClient
        // Type: boolean
        target.fClient = this.fClient;
        // Name: fClientAnnotation
        // Type: java.lang.String
        target.fClientAnnotation = this.fClientAnnotation;
        // Name: fOverrideClientAnnotation
        // Type: java.lang.String
        target.fOverrideClientAnnotation = this.fOverrideClientAnnotation;
        // Name: fServerType
        // Type: java.lang.String
        target.fServerType = this.fServerType;
        // Name: fBasepackage
        // Type: java.lang.String
        target.fBasepackage = this.fBasepackage;
        // Name: fRuntimepackage
        // Type: java.lang.String
        target.fRuntimepackage = this.fRuntimepackage;
        // Name: fGenUtils
        // Type: boolean
        target.fGenUtils = this.fGenUtils;
        // Name: fTelegrampackage
        // Type: java.lang.String
        target.fTelegrampackage = this.fTelegrampackage;
        // Name: fImpledir
        // Type: java.lang.String
        target.fImpledir = this.fImpledir;
        // Name: fGenSkeleton
        // Type: boolean
        target.fGenSkeleton = this.fGenSkeleton;
        // Name: fSkeletonDelegateClass
        // Type: java.lang.String
        target.fSkeletonDelegateClass = this.fSkeletonDelegateClass;
        // Name: fSkeletonDelegateInterface
        // Type: java.lang.String
        target.fSkeletonDelegateInterface = this.fSkeletonDelegateInterface;
        // Name: fLineSeparator
        // Type: java.lang.String
        target.fLineSeparator = this.fLineSeparator;
        // Name: fPackageSuffix
        // Type: java.lang.String
        target.fPackageSuffix = this.fPackageSuffix;
        // Name: fOverridePackage
        // Type: java.lang.String
        target.fOverridePackage = this.fOverridePackage;
        // Name: fOverrideLocation
        // Type: java.lang.String
        target.fOverrideLocation = this.fOverrideLocation;
        // Name: fVoPackageSuffix
        // Type: java.lang.String
        target.fVoPackageSuffix = this.fVoPackageSuffix;
        // Name: fVoOverridePackage
        // Type: java.lang.String
        target.fVoOverridePackage = this.fVoOverridePackage;
        // Name: fTelegramStyle
        // Type: java.lang.String
        target.fTelegramStyle = this.fTelegramStyle;
        // Name: fAppendApplicationPackage
        // Type: boolean
        target.fAppendApplicationPackage = this.fAppendApplicationPackage;
        // Name: fSerdeable
        // Type: boolean
        target.fSerdeable = this.fSerdeable;
        // Name: fIgnoreUnknown
        // Type: boolean
        target.fIgnoreUnknown = this.fIgnoreUnknown;
        // Name: fNullableAnnotation
        // Type: boolean
        target.fNullableAnnotation = this.fNullableAnnotation;
        // Name: fIsTargetJaraktaEE
        // Type: boolean
        target.fIsTargetJaraktaEE = this.fIsTargetJaraktaEE;
        // Name: fInjectInterfaceToController
        // Type: boolean
        target.fInjectInterfaceToController = this.fInjectInterfaceToController;
        // Name: fMicronautVersion
        // Type: java.lang.String
        target.fMicronautVersion = this.fMicronautVersion;
    }
}
