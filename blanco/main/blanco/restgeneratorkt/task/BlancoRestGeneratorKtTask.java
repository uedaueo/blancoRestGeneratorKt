package blanco.restgeneratorkt.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;

/**
 * Apache Antタスク [BlancoRestGeneratorKt]のクラス。
 *
 * API定義書からAPIスタブクラスを自動生成するBlancoRestGeneratorKtのためのAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.restgeneratorkt.task]にBlancoRestGeneratorKtBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancorestgeneratorkt&quot; classname=&quot;blanco.restgeneratorkt.task.BlancoRestGeneratorKtTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoRestGeneratorKtTask extends Task {
    /**
     * API定義書からAPIスタブクラスを自動生成するBlancoRestGeneratorKtのためのAntTaskです。
     */
    protected BlancoRestGeneratorKtProcessInput fInput = new BlancoRestGeneratorKtProcessInput();

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [tmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTmpdirProcessed = false;

    /**
     * フィールド [searchTmpdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSearchTmpdirProcessed = false;

    /**
     * フィールド [nameAdjust] に値がセットされたかどうか。
     */
    protected boolean fIsFieldNameAdjustProcessed = false;

    /**
     * フィールド [encoding] に値がセットされたかどうか。
     */
    protected boolean fIsFieldEncodingProcessed = false;

    /**
     * フィールド [tabs] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTabsProcessed = false;

    /**
     * フィールド [xmlrootelement] に値がセットされたかどうか。
     */
    protected boolean fIsFieldXmlrootelementProcessed = false;

    /**
     * フィールド [sheetType] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSheetTypeProcessed = false;

    /**
     * フィールド [targetStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetStyleProcessed = false;

    /**
     * フィールド [client] に値がセットされたかどうか。
     */
    protected boolean fIsFieldClientProcessed = false;

    /**
     * フィールド [clientAnnotation] に値がセットされたかどうか。
     */
    protected boolean fIsFieldClientAnnotationProcessed = false;

    /**
     * フィールド [overrideClientAnnotation] に値がセットされたかどうか。
     */
    protected boolean fIsFieldOverrideClientAnnotationProcessed = false;

    /**
     * フィールド [serverType] に値がセットされたかどうか。
     */
    protected boolean fIsFieldServerTypeProcessed = false;

    /**
     * フィールド [basepackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldBasepackageProcessed = false;

    /**
     * フィールド [runtimepackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldRuntimepackageProcessed = false;

    /**
     * フィールド [genUtils] に値がセットされたかどうか。
     */
    protected boolean fIsFieldGenUtilsProcessed = false;

    /**
     * フィールド [telegrampackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTelegrampackageProcessed = false;

    /**
     * フィールド [impledir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldImpledirProcessed = false;

    /**
     * フィールド [genSkeleton] に値がセットされたかどうか。
     */
    protected boolean fIsFieldGenSkeletonProcessed = false;

    /**
     * フィールド [skeletonDelegateClass] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSkeletonDelegateClassProcessed = false;

    /**
     * フィールド [skeletonDelegateInterface] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSkeletonDelegateInterfaceProcessed = false;

    /**
     * フィールド [lineSeparator] に値がセットされたかどうか。
     */
    protected boolean fIsFieldLineSeparatorProcessed = false;

    /**
     * フィールド [packageSuffix] に値がセットされたかどうか。
     */
    protected boolean fIsFieldPackageSuffixProcessed = false;

    /**
     * フィールド [overridePackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldOverridePackageProcessed = false;

    /**
     * フィールド [overrideLocation] に値がセットされたかどうか。
     */
    protected boolean fIsFieldOverrideLocationProcessed = false;

    /**
     * フィールド [voPackageSuffix] に値がセットされたかどうか。
     */
    protected boolean fIsFieldVoPackageSuffixProcessed = false;

    /**
     * フィールド [voOverridePackage] に値がセットされたかどうか。
     */
    protected boolean fIsFieldVoOverridePackageProcessed = false;

    /**
     * フィールド [telegramStyle] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTelegramStyleProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTmpdir(final String arg) {
        fInput.setTmpdir(arg);
        fIsFieldTmpdirProcessed = true;
    }

    /**
     * Antタスクの[tmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。<br>
     * デフォルト値[tmp]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTmpdir() {
        return fInput.getTmpdir();
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @param arg セットしたい値
     */
    public void setSearchTmpdir(final String arg) {
        fInput.setSearchTmpdir(arg);
        fIsFieldSearchTmpdirProcessed = true;
    }

    /**
     * Antタスクの[searchTmpdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。<br>
     *
     * @return このフィールドの値
     */
    public String getSearchTmpdir() {
        return fInput.getSearchTmpdir();
    }

    /**
     * Antタスクの[nameAdjust]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * フィールド名やメソッド名を名前変形を実施するかどうか。<br>
     *
     * @param arg セットしたい値
     */
    public void setNameAdjust(final boolean arg) {
        fInput.setNameAdjust(arg);
        fIsFieldNameAdjustProcessed = true;
    }

    /**
     * Antタスクの[nameAdjust]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * フィールド名やメソッド名を名前変形を実施するかどうか。<br>
     * デフォルト値[true]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getNameAdjust() {
        return fInput.getNameAdjust();
    }

    /**
     * Antタスクの[encoding]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setEncoding(final String arg) {
        fInput.setEncoding(arg);
        fIsFieldEncodingProcessed = true;
    }

    /**
     * Antタスクの[encoding]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * 自動生成するソースファイルの文字エンコーディングを指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getEncoding() {
        return fInput.getEncoding();
    }

    /**
     * Antタスクの[tabs]アトリビュートのセッターメソッド。
     *
     * 項目番号: 7<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     *
     * @param arg セットしたい値
     */
    public void setTabs(final String arg) {
        try {
            fInput.setTabs(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Antタスクの[tabs]アトリビュートに与えられた値の数値解析に失敗しました。" + e.toString());
        }
        fIsFieldTabsProcessed = true;
    }

    /**
     * Antタスクの[tabs]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 7<br>
     * タブをwhite spaceいくつで置き換えるか、という値です。<br>
     * デフォルト値[4]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTabs() {
        return String.valueOf(fInput.getTabs());
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのセッターメソッド。
     *
     * 項目番号: 8<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     *
     * @param arg セットしたい値
     */
    public void setXmlrootelement(final boolean arg) {
        fInput.setXmlrootelement(arg);
        fIsFieldXmlrootelementProcessed = true;
    }

    /**
     * Antタスクの[xmlrootelement]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 8<br>
     * XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getXmlrootelement() {
        return fInput.getXmlrootelement();
    }

    /**
     * Antタスクの[sheetType]アトリビュートのセッターメソッド。
     *
     * 項目番号: 9<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     *
     * @param arg セットしたい値
     */
    public void setSheetType(final String arg) {
        fInput.setSheetType(arg);
        fIsFieldSheetTypeProcessed = true;
    }

    /**
     * Antタスクの[sheetType]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 9<br>
     * meta定義書が期待しているプログラミング言語を指定します<br>
     * デフォルト値[java]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSheetType() {
        return fInput.getSheetType();
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 10<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetStyle(final String arg) {
        fInput.setTargetStyle(arg);
        fIsFieldTargetStyleProcessed = true;
    }

    /**
     * Antタスクの[targetStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 10<br>
     * 出力先フォルダの書式を指定します。&lt;br&gt;\nblanco: [targetdir]/main&lt;br&gt;\nmaven: [targetdir]/main/java&lt;br&gt;\nfree: [targetdir](targetdirが無指定の場合はblanco/main)<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetStyle() {
        return fInput.getTargetStyle();
    }

    /**
     * Antタスクの[client]アトリビュートのセッターメソッド。
     *
     * 項目番号: 11<br>
     * trueの場合はサーバ用のメソッドを生成しません。<br>
     *
     * @param arg セットしたい値
     */
    public void setClient(final boolean arg) {
        fInput.setClient(arg);
        fIsFieldClientProcessed = true;
    }

    /**
     * Antタスクの[client]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 11<br>
     * trueの場合はサーバ用のメソッドを生成しません。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getClient() {
        return fInput.getClient();
    }

    /**
     * Antタスクの[clientAnnotation]アトリビュートのセッターメソッド。
     *
     * 項目番号: 12<br>
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。<br>
     *
     * @param arg セットしたい値
     */
    public void setClientAnnotation(final String arg) {
        fInput.setClientAnnotation(arg);
        fIsFieldClientAnnotationProcessed = true;
    }

    /**
     * Antタスクの[clientAnnotation]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 12<br>
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。<br>
     *
     * @return このフィールドの値
     */
    public String getClientAnnotation() {
        return fInput.getClientAnnotation();
    }

    /**
     * Antタスクの[overrideClientAnnotation]アトリビュートのセッターメソッド。
     *
     * 項目番号: 13<br>
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。<br>
     *
     * @param arg セットしたい値
     */
    public void setOverrideClientAnnotation(final String arg) {
        fInput.setOverrideClientAnnotation(arg);
        fIsFieldOverrideClientAnnotationProcessed = true;
    }

    /**
     * Antタスクの[overrideClientAnnotation]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 13<br>
     * Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。<br>
     *
     * @return このフィールドの値
     */
    public String getOverrideClientAnnotation() {
        return fInput.getOverrideClientAnnotation();
    }

    /**
     * Antタスクの[serverType]アトリビュートのセッターメソッド。
     *
     * 項目番号: 14<br>
     * Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。<br>
     *
     * @param arg セットしたい値
     */
    public void setServerType(final String arg) {
        fInput.setServerType(arg);
        fIsFieldServerTypeProcessed = true;
    }

    /**
     * Antタスクの[serverType]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 14<br>
     * Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。<br>
     * デフォルト値[micronaut]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getServerType() {
        return fInput.getServerType();
    }

    /**
     * Antタスクの[basepackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 15<br>
     * blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setBasepackage(final String arg) {
        fInput.setBasepackage(arg);
        fIsFieldBasepackageProcessed = true;
    }

    /**
     * Antタスクの[basepackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 15<br>
     * blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getBasepackage() {
        return fInput.getBasepackage();
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 16<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setRuntimepackage(final String arg) {
        fInput.setRuntimepackage(arg);
        fIsFieldRuntimepackageProcessed = true;
    }

    /**
     * Antタスクの[runtimepackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 16<br>
     * ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。<br>
     *
     * @return このフィールドの値
     */
    public String getRuntimepackage() {
        return fInput.getRuntimepackage();
    }

    /**
     * Antタスクの[genUtils]アトリビュートのセッターメソッド。
     *
     * 項目番号: 17<br>
     * ユーティリティ類の生成を省略する場合はfalseを指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setGenUtils(final boolean arg) {
        fInput.setGenUtils(arg);
        fIsFieldGenUtilsProcessed = true;
    }

    /**
     * Antタスクの[genUtils]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 17<br>
     * ユーティリティ類の生成を省略する場合はfalseを指定します。<br>
     * デフォルト値[true]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getGenUtils() {
        return fInput.getGenUtils();
    }

    /**
     * Antタスクの[telegrampackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 18<br>
     * 電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。<br>
     *
     * @param arg セットしたい値
     */
    public void setTelegrampackage(final String arg) {
        fInput.setTelegrampackage(arg);
        fIsFieldTelegrampackageProcessed = true;
    }

    /**
     * Antタスクの[telegrampackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 18<br>
     * 電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。<br>
     *
     * @return このフィールドの値
     */
    public String getTelegrampackage() {
        return fInput.getTelegrampackage();
    }

    /**
     * Antタスクの[impledir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 19<br>
     * 実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setImpledir(final String arg) {
        fInput.setImpledir(arg);
        fIsFieldImpledirProcessed = true;
    }

    /**
     * Antタスクの[impledir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 19<br>
     * 実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。<br>
     *
     * @return このフィールドの値
     */
    public String getImpledir() {
        return fInput.getImpledir();
    }

    /**
     * Antタスクの[genSkeleton]アトリビュートのセッターメソッド。
     *
     * 項目番号: 20<br>
     * controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。<br>
     *
     * @param arg セットしたい値
     */
    public void setGenSkeleton(final boolean arg) {
        fInput.setGenSkeleton(arg);
        fIsFieldGenSkeletonProcessed = true;
    }

    /**
     * Antタスクの[genSkeleton]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 20<br>
     * controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。<br>
     * デフォルト値[false]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public boolean getGenSkeleton() {
        return fInput.getGenSkeleton();
    }

    /**
     * Antタスクの[skeletonDelegateClass]アトリビュートのセッターメソッド。
     *
     * 項目番号: 21<br>
     * 実装クラスが処理を委譲するクラスのCanonical名です。<br>
     *
     * @param arg セットしたい値
     */
    public void setSkeletonDelegateClass(final String arg) {
        fInput.setSkeletonDelegateClass(arg);
        fIsFieldSkeletonDelegateClassProcessed = true;
    }

    /**
     * Antタスクの[skeletonDelegateClass]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 21<br>
     * 実装クラスが処理を委譲するクラスのCanonical名です。<br>
     *
     * @return このフィールドの値
     */
    public String getSkeletonDelegateClass() {
        return fInput.getSkeletonDelegateClass();
    }

    /**
     * Antタスクの[skeletonDelegateInterface]アトリビュートのセッターメソッド。
     *
     * 項目番号: 22<br>
     * 実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。<br>
     *
     * @param arg セットしたい値
     */
    public void setSkeletonDelegateInterface(final String arg) {
        fInput.setSkeletonDelegateInterface(arg);
        fIsFieldSkeletonDelegateInterfaceProcessed = true;
    }

    /**
     * Antタスクの[skeletonDelegateInterface]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 22<br>
     * 実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。<br>
     *
     * @return このフィールドの値
     */
    public String getSkeletonDelegateInterface() {
        return fInput.getSkeletonDelegateInterface();
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのセッターメソッド。
     *
     * 項目番号: 23<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     *
     * @param arg セットしたい値
     */
    public void setLineSeparator(final String arg) {
        fInput.setLineSeparator(arg);
        fIsFieldLineSeparatorProcessed = true;
    }

    /**
     * Antタスクの[lineSeparator]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 23<br>
     * 行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。<br>
     * デフォルト値[LF]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getLineSeparator() {
        return fInput.getLineSeparator();
    }

    /**
     * Antタスクの[packageSuffix]アトリビュートのセッターメソッド。
     *
     * 項目番号: 24<br>
     * 定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setPackageSuffix(final String arg) {
        fInput.setPackageSuffix(arg);
        fIsFieldPackageSuffixProcessed = true;
    }

    /**
     * Antタスクの[packageSuffix]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 24<br>
     * 定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getPackageSuffix() {
        return fInput.getPackageSuffix();
    }

    /**
     * Antタスクの[overridePackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 25<br>
     * 定義書で指定されたパッケージ名を上書きします。<br>
     *
     * @param arg セットしたい値
     */
    public void setOverridePackage(final String arg) {
        fInput.setOverridePackage(arg);
        fIsFieldOverridePackageProcessed = true;
    }

    /**
     * Antタスクの[overridePackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 25<br>
     * 定義書で指定されたパッケージ名を上書きします。<br>
     *
     * @return このフィールドの値
     */
    public String getOverridePackage() {
        return fInput.getOverridePackage();
    }

    /**
     * Antタスクの[overrideLocation]アトリビュートのセッターメソッド。
     *
     * 項目番号: 26<br>
     * 定義書で指定されたロケーション名を上書きします。<br>
     *
     * @param arg セットしたい値
     */
    public void setOverrideLocation(final String arg) {
        fInput.setOverrideLocation(arg);
        fIsFieldOverrideLocationProcessed = true;
    }

    /**
     * Antタスクの[overrideLocation]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 26<br>
     * 定義書で指定されたロケーション名を上書きします。<br>
     *
     * @return このフィールドの値
     */
    public String getOverrideLocation() {
        return fInput.getOverrideLocation();
    }

    /**
     * Antタスクの[voPackageSuffix]アトリビュートのセッターメソッド。
     *
     * 項目番号: 27<br>
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setVoPackageSuffix(final String arg) {
        fInput.setVoPackageSuffix(arg);
        fIsFieldVoPackageSuffixProcessed = true;
    }

    /**
     * Antタスクの[voPackageSuffix]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 27<br>
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getVoPackageSuffix() {
        return fInput.getVoPackageSuffix();
    }

    /**
     * Antタスクの[voOverridePackage]アトリビュートのセッターメソッド。
     *
     * 項目番号: 28<br>
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。<br>
     *
     * @param arg セットしたい値
     */
    public void setVoOverridePackage(final String arg) {
        fInput.setVoOverridePackage(arg);
        fIsFieldVoOverridePackageProcessed = true;
    }

    /**
     * Antタスクの[voOverridePackage]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 28<br>
     * packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。<br>
     *
     * @return このフィールドの値
     */
    public String getVoOverridePackage() {
        return fInput.getVoOverridePackage();
    }

    /**
     * Antタスクの[telegramStyle]アトリビュートのセッターメソッド。
     *
     * 項目番号: 29<br>
     * 電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。<br>
     *
     * @param arg セットしたい値
     */
    public void setTelegramStyle(final String arg) {
        fInput.setTelegramStyle(arg);
        fIsFieldTelegramStyleProcessed = true;
    }

    /**
     * Antタスクの[telegramStyle]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 29<br>
     * 電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTelegramStyle() {
        return fInput.getTelegramStyle();
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("BlancoRestGeneratorKtTask begin.");

        // 項目番号[1], アトリビュート[metadir]は必須入力です。入力チェックを行います。
        if (fIsFieldMetadirProcessed == false) {
            throw new BuildException("必須アトリビュート[metadir]が設定されていません。処理を中断します。");
        }
        // 項目番号[15], アトリビュート[basepackage]は必須入力です。入力チェックを行います。
        if (fIsFieldBasepackageProcessed == false) {
            throw new BuildException("必須アトリビュート[basepackage]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- tmpdir:[" + getTmpdir() + "]");
            System.out.println("- searchTmpdir:[" + getSearchTmpdir() + "]");
            System.out.println("- nameAdjust:[" + getNameAdjust() + "]");
            System.out.println("- encoding:[" + getEncoding() + "]");
            System.out.println("- tabs:[" + getTabs() + "]");
            System.out.println("- xmlrootelement:[" + getXmlrootelement() + "]");
            System.out.println("- sheetType:[" + getSheetType() + "]");
            System.out.println("- targetStyle:[" + getTargetStyle() + "]");
            System.out.println("- client:[" + getClient() + "]");
            System.out.println("- clientAnnotation:[" + getClientAnnotation() + "]");
            System.out.println("- overrideClientAnnotation:[" + getOverrideClientAnnotation() + "]");
            System.out.println("- serverType:[" + getServerType() + "]");
            System.out.println("- basepackage:[" + getBasepackage() + "]");
            System.out.println("- runtimepackage:[" + getRuntimepackage() + "]");
            System.out.println("- genUtils:[" + getGenUtils() + "]");
            System.out.println("- telegrampackage:[" + getTelegrampackage() + "]");
            System.out.println("- impledir:[" + getImpledir() + "]");
            System.out.println("- genSkeleton:[" + getGenSkeleton() + "]");
            System.out.println("- skeletonDelegateClass:[" + getSkeletonDelegateClass() + "]");
            System.out.println("- skeletonDelegateInterface:[" + getSkeletonDelegateInterface() + "]");
            System.out.println("- lineSeparator:[" + getLineSeparator() + "]");
            System.out.println("- packageSuffix:[" + getPackageSuffix() + "]");
            System.out.println("- overridePackage:[" + getOverridePackage() + "]");
            System.out.println("- overrideLocation:[" + getOverrideLocation() + "]");
            System.out.println("- voPackageSuffix:[" + getVoPackageSuffix() + "]");
            System.out.println("- voOverridePackage:[" + getVoOverridePackage() + "]");
            System.out.println("- telegramStyle:[" + getTelegramStyle() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // If you get a compile error at this point, You may be able to solve it by implementing a BlancoRestGeneratorKtProcess interface and creating an BlancoRestGeneratorKtProcessImpl class in package blanco.restgeneratorkt.task.
            final BlancoRestGeneratorKtProcess proc = new BlancoRestGeneratorKtProcessImpl();
            if (proc.execute(fInput) != BlancoRestGeneratorKtBatchProcess.END_SUCCESS) {
                throw new BuildException("The task has terminated abnormally.");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
