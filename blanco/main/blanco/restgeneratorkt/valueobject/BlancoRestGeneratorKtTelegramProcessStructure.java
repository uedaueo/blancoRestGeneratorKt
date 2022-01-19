package blanco.restgeneratorkt.valueobject;

import java.util.HashMap;
import java.util.List;

/**
 * BlancoRestGeneratorKtのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorKtTelegramProcessStructure {
    /**
     * 電文ID
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 説明
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * WebサービスID
     *
     * フィールド: [serviceId]。
     */
    private String fServiceId;

    /**
     * ロケーション
     *
     * フィールド: [location]。
     */
    private String fLocation;

    /**
     * 名前空間
     *
     * フィールド: [namespace]。
     */
    private String fNamespace;

    /**
     * パッケージ
     *
     * フィールド: [package]。
     */
    private String fPackage;

    /**
     * 本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。
     *
     * フィールド: [basedir]。
     */
    private String fBasedir;

    /**
     * APIの実装クラス（Micronautの場合は委譲クラス）配置ディレクトリ
     *
     * フィールド: [impleDirKt]。
     */
    private String fImpleDirKt;

    /**
     * クラスのアノテーションを指定します。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * クライアントクラス（インタフェイス）のアノテーションを指定します。
     *
     * フィールド: [clientAnnotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fClientAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * カンマ区切りで記述された文字列が、配列情報として生成されます
     *
     * フィールド: [metaIdList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fMetaIdList = new java.util.ArrayList<java.lang.String>();

    /**
     * 認証が不要な場合はtrue
     *
     * フィールド: [noAuthentication]。
     * デフォルト: [false]。
     */
    private Boolean fNoAuthentication = false;

    /**
     * 補助的な認証が不要な場合はtrue
     *
     * フィールド: [noAuxiliaryAuthentication]。
     * デフォルト: [false]。
     */
    private Boolean fNoAuxiliaryAuthentication = false;

    /**
     * TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。
     *
     * フィールド: [createImportList]。
     * デフォルト: [true]。
     */
    private Boolean fCreateImportList = true;

    /**
     * 継承するクラスを指定します。
     *
     * フィールド: [extends]。
     */
    private String fExtends;

    /**
     * 実装するインタフェース(java.lang.String)の一覧。
     *
     * フィールド: [implementsList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fImplementsList = new java.util.ArrayList<java.lang.String>();

    /**
     * importを指定します。
     *
     * フィールド: [importList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fImportList = new java.util.ArrayList<java.lang.String>();

    /**
     * source コードの先頭に書かれるコード群です。
     *
     * フィールド: [headerList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fHeaderList = new java.util.ArrayList<java.lang.String>();

    /**
     * Getリクエストパラメータのリスト
     *
     * フィールド: [getRequestBindList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure&gt;()]。
     */
    private List<BlancoRestGeneratorKtGetRequestBindStructure> fGetRequestBindList = new java.util.ArrayList<blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure>();

    /**
     * この電文処理が使用する電文の一覧
     *
     * フィールド: [listTelegrams]。
     * デフォルト: [new java.util.HashMap&lt;&gt;()]。
     */
    private HashMap<String, HashMap<String, BlancoRestGeneratorKtTelegramStructure>> fListTelegrams = new java.util.HashMap<>();

    /**
     * リクエスト電文のヘッダクラス名（Canonical）
     *
     * フィールド: [requestHeaderClass]。
     */
    private String fRequestHeaderClass;

    /**
     * レスポンス電文のヘッダクラス名（Canonical）
     *
     * フィールド: [responseHeaderClass]。
     */
    private String fResponseHeaderClass;

    /**
     * パッケージ名の後ろに付加する文字列をしていします。
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
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [電文ID]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [電文ID]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [説明]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [説明]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [serviceId] の値を設定します。
     *
     * フィールドの説明: [WebサービスID]。
     *
     * @param argServiceId フィールド[serviceId]に設定する値。
     */
    public void setServiceId(final String argServiceId) {
        fServiceId = argServiceId;
    }

    /**
     * フィールド [serviceId] の値を取得します。
     *
     * フィールドの説明: [WebサービスID]。
     *
     * @return フィールド[serviceId]から取得した値。
     */
    public String getServiceId() {
        return fServiceId;
    }

    /**
     * フィールド [location] の値を設定します。
     *
     * フィールドの説明: [ロケーション]。
     *
     * @param argLocation フィールド[location]に設定する値。
     */
    public void setLocation(final String argLocation) {
        fLocation = argLocation;
    }

    /**
     * フィールド [location] の値を取得します。
     *
     * フィールドの説明: [ロケーション]。
     *
     * @return フィールド[location]から取得した値。
     */
    public String getLocation() {
        return fLocation;
    }

    /**
     * フィールド [namespace] の値を設定します。
     *
     * フィールドの説明: [名前空間]。
     *
     * @param argNamespace フィールド[namespace]に設定する値。
     */
    public void setNamespace(final String argNamespace) {
        fNamespace = argNamespace;
    }

    /**
     * フィールド [namespace] の値を取得します。
     *
     * フィールドの説明: [名前空間]。
     *
     * @return フィールド[namespace]から取得した値。
     */
    public String getNamespace() {
        return fNamespace;
    }

    /**
     * フィールド [package] の値を設定します。
     *
     * フィールドの説明: [パッケージ]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ]。
     *
     * @return フィールド[package]から取得した値。
     */
    public String getPackage() {
        return fPackage;
    }

    /**
     * フィールド [basedir] の値を設定します。
     *
     * フィールドの説明: [本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @param argBasedir フィールド[basedir]に設定する値。
     */
    public void setBasedir(final String argBasedir) {
        fBasedir = argBasedir;
    }

    /**
     * フィールド [basedir] の値を取得します。
     *
     * フィールドの説明: [本番時にファイルを配置する歳のベースディレクトリ。主にTypeScriptのimport文生成時に使用する事を想定しています。]。
     *
     * @return フィールド[basedir]から取得した値。
     */
    public String getBasedir() {
        return fBasedir;
    }

    /**
     * フィールド [impleDirKt] の値を設定します。
     *
     * フィールドの説明: [APIの実装クラス（Micronautの場合は委譲クラス）配置ディレクトリ]。
     *
     * @param argImpleDirKt フィールド[impleDirKt]に設定する値。
     */
    public void setImpleDirKt(final String argImpleDirKt) {
        fImpleDirKt = argImpleDirKt;
    }

    /**
     * フィールド [impleDirKt] の値を取得します。
     *
     * フィールドの説明: [APIの実装クラス（Micronautの場合は委譲クラス）配置ディレクトリ]。
     *
     * @return フィールド[impleDirKt]から取得した値。
     */
    public String getImpleDirKt() {
        return fImpleDirKt;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [クラスのアノテーションを指定します。]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [クラスのアノテーションを指定します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [clientAnnotationList] の値を設定します。
     *
     * フィールドの説明: [クライアントクラス（インタフェイス）のアノテーションを指定します。]。
     *
     * @param argClientAnnotationList フィールド[clientAnnotationList]に設定する値。
     */
    public void setClientAnnotationList(final List<String> argClientAnnotationList) {
        fClientAnnotationList = argClientAnnotationList;
    }

    /**
     * フィールド [clientAnnotationList] の値を取得します。
     *
     * フィールドの説明: [クライアントクラス（インタフェイス）のアノテーションを指定します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[clientAnnotationList]から取得した値。
     */
    public List<String> getClientAnnotationList() {
        return fClientAnnotationList;
    }

    /**
     * フィールド [metaIdList] の値を設定します。
     *
     * フィールドの説明: [カンマ区切りで記述された文字列が、配列情報として生成されます]。
     *
     * @param argMetaIdList フィールド[metaIdList]に設定する値。
     */
    public void setMetaIdList(final List<String> argMetaIdList) {
        fMetaIdList = argMetaIdList;
    }

    /**
     * フィールド [metaIdList] の値を取得します。
     *
     * フィールドの説明: [カンマ区切りで記述された文字列が、配列情報として生成されます]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[metaIdList]から取得した値。
     */
    public List<String> getMetaIdList() {
        return fMetaIdList;
    }

    /**
     * フィールド [noAuthentication] の値を設定します。
     *
     * フィールドの説明: [認証が不要な場合はtrue]。
     *
     * @param argNoAuthentication フィールド[noAuthentication]に設定する値。
     */
    public void setNoAuthentication(final Boolean argNoAuthentication) {
        fNoAuthentication = argNoAuthentication;
    }

    /**
     * フィールド [noAuthentication] の値を取得します。
     *
     * フィールドの説明: [認証が不要な場合はtrue]。
     * デフォルト: [false]。
     *
     * @return フィールド[noAuthentication]から取得した値。
     */
    public Boolean getNoAuthentication() {
        return fNoAuthentication;
    }

    /**
     * フィールド [noAuxiliaryAuthentication] の値を設定します。
     *
     * フィールドの説明: [補助的な認証が不要な場合はtrue]。
     *
     * @param argNoAuxiliaryAuthentication フィールド[noAuxiliaryAuthentication]に設定する値。
     */
    public void setNoAuxiliaryAuthentication(final Boolean argNoAuxiliaryAuthentication) {
        fNoAuxiliaryAuthentication = argNoAuxiliaryAuthentication;
    }

    /**
     * フィールド [noAuxiliaryAuthentication] の値を取得します。
     *
     * フィールドの説明: [補助的な認証が不要な場合はtrue]。
     * デフォルト: [false]。
     *
     * @return フィールド[noAuxiliaryAuthentication]から取得した値。
     */
    public Boolean getNoAuxiliaryAuthentication() {
        return fNoAuxiliaryAuthentication;
    }

    /**
     * フィールド [createImportList] の値を設定します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     *
     * @param argCreateImportList フィールド[createImportList]に設定する値。
     */
    public void setCreateImportList(final Boolean argCreateImportList) {
        fCreateImportList = argCreateImportList;
    }

    /**
     * フィールド [createImportList] の値を取得します。
     *
     * フィールドの説明: [TypeScript 独自。blancoで一括生成されたクラスについて、import文を自動生成します。]。
     * デフォルト: [true]。
     *
     * @return フィールド[createImportList]から取得した値。
     */
    public Boolean getCreateImportList() {
        return fCreateImportList;
    }

    /**
     * フィールド [extends] の値を設定します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @param argExtends フィールド[extends]に設定する値。
     */
    public void setExtends(final String argExtends) {
        fExtends = argExtends;
    }

    /**
     * フィールド [extends] の値を取得します。
     *
     * フィールドの説明: [継承するクラスを指定します。]。
     *
     * @return フィールド[extends]から取得した値。
     */
    public String getExtends() {
        return fExtends;
    }

    /**
     * フィールド [implementsList] の値を設定します。
     *
     * フィールドの説明: [実装するインタフェース(java.lang.String)の一覧。]。
     *
     * @param argImplementsList フィールド[implementsList]に設定する値。
     */
    public void setImplementsList(final List<String> argImplementsList) {
        fImplementsList = argImplementsList;
    }

    /**
     * フィールド [implementsList] の値を取得します。
     *
     * フィールドの説明: [実装するインタフェース(java.lang.String)の一覧。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[implementsList]から取得した値。
     */
    public List<String> getImplementsList() {
        return fImplementsList;
    }

    /**
     * フィールド [importList] の値を設定します。
     *
     * フィールドの説明: [importを指定します。]。
     *
     * @param argImportList フィールド[importList]に設定する値。
     */
    public void setImportList(final List<String> argImportList) {
        fImportList = argImportList;
    }

    /**
     * フィールド [importList] の値を取得します。
     *
     * フィールドの説明: [importを指定します。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[importList]から取得した値。
     */
    public List<String> getImportList() {
        return fImportList;
    }

    /**
     * フィールド [headerList] の値を設定します。
     *
     * フィールドの説明: [source コードの先頭に書かれるコード群です。]。
     *
     * @param argHeaderList フィールド[headerList]に設定する値。
     */
    public void setHeaderList(final List<String> argHeaderList) {
        fHeaderList = argHeaderList;
    }

    /**
     * フィールド [headerList] の値を取得します。
     *
     * フィールドの説明: [source コードの先頭に書かれるコード群です。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[headerList]から取得した値。
     */
    public List<String> getHeaderList() {
        return fHeaderList;
    }

    /**
     * フィールド [getRequestBindList] の値を設定します。
     *
     * フィールドの説明: [Getリクエストパラメータのリスト]。
     *
     * @param argGetRequestBindList フィールド[getRequestBindList]に設定する値。
     */
    public void setGetRequestBindList(final List<BlancoRestGeneratorKtGetRequestBindStructure> argGetRequestBindList) {
        fGetRequestBindList = argGetRequestBindList;
    }

    /**
     * フィールド [getRequestBindList] の値を取得します。
     *
     * フィールドの説明: [Getリクエストパラメータのリスト]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure&gt;()]。
     *
     * @return フィールド[getRequestBindList]から取得した値。
     */
    public List<BlancoRestGeneratorKtGetRequestBindStructure> getGetRequestBindList() {
        return fGetRequestBindList;
    }

    /**
     * フィールド [listTelegrams] の値を設定します。
     *
     * フィールドの説明: [この電文処理が使用する電文の一覧]。
     *
     * @param argListTelegrams フィールド[listTelegrams]に設定する値。
     */
    public void setListTelegrams(final HashMap<String, HashMap<String, BlancoRestGeneratorKtTelegramStructure>> argListTelegrams) {
        fListTelegrams = argListTelegrams;
    }

    /**
     * フィールド [listTelegrams] の値を取得します。
     *
     * フィールドの説明: [この電文処理が使用する電文の一覧]。
     * デフォルト: [new java.util.HashMap&lt;&gt;()]。
     *
     * @return フィールド[listTelegrams]から取得した値。
     */
    public HashMap<String, HashMap<String, BlancoRestGeneratorKtTelegramStructure>> getListTelegrams() {
        return fListTelegrams;
    }

    /**
     * フィールド [requestHeaderClass] の値を設定します。
     *
     * フィールドの説明: [リクエスト電文のヘッダクラス名（Canonical）]。
     *
     * @param argRequestHeaderClass フィールド[requestHeaderClass]に設定する値。
     */
    public void setRequestHeaderClass(final String argRequestHeaderClass) {
        fRequestHeaderClass = argRequestHeaderClass;
    }

    /**
     * フィールド [requestHeaderClass] の値を取得します。
     *
     * フィールドの説明: [リクエスト電文のヘッダクラス名（Canonical）]。
     *
     * @return フィールド[requestHeaderClass]から取得した値。
     */
    public String getRequestHeaderClass() {
        return fRequestHeaderClass;
    }

    /**
     * フィールド [responseHeaderClass] の値を設定します。
     *
     * フィールドの説明: [レスポンス電文のヘッダクラス名（Canonical）]。
     *
     * @param argResponseHeaderClass フィールド[responseHeaderClass]に設定する値。
     */
    public void setResponseHeaderClass(final String argResponseHeaderClass) {
        fResponseHeaderClass = argResponseHeaderClass;
    }

    /**
     * フィールド [responseHeaderClass] の値を取得します。
     *
     * フィールドの説明: [レスポンス電文のヘッダクラス名（Canonical）]。
     *
     * @return フィールド[responseHeaderClass]から取得した値。
     */
    public String getResponseHeaderClass() {
        return fResponseHeaderClass;
    }

    /**
     * フィールド [packageSuffix] の値を設定します。
     *
     * フィールドの説明: [パッケージ名の後ろに付加する文字列をしていします。]。
     *
     * @param argPackageSuffix フィールド[packageSuffix]に設定する値。
     */
    public void setPackageSuffix(final String argPackageSuffix) {
        fPackageSuffix = argPackageSuffix;
    }

    /**
     * フィールド [packageSuffix] の値を取得します。
     *
     * フィールドの説明: [パッケージ名の後ろに付加する文字列をしていします。]。
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
        buf.append("blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",serviceId=" + fServiceId);
        buf.append(",location=" + fLocation);
        buf.append(",namespace=" + fNamespace);
        buf.append(",package=" + fPackage);
        buf.append(",basedir=" + fBasedir);
        buf.append(",impleDirKt=" + fImpleDirKt);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",clientAnnotationList=" + fClientAnnotationList);
        buf.append(",metaIdList=" + fMetaIdList);
        buf.append(",noAuthentication=" + fNoAuthentication);
        buf.append(",noAuxiliaryAuthentication=" + fNoAuxiliaryAuthentication);
        buf.append(",createImportList=" + fCreateImportList);
        buf.append(",extends=" + fExtends);
        buf.append(",implementsList=" + fImplementsList);
        buf.append(",importList=" + fImportList);
        buf.append(",headerList=" + fHeaderList);
        buf.append(",getRequestBindList=" + fGetRequestBindList);
        buf.append(",listTelegrams=" + fListTelegrams);
        buf.append(",requestHeaderClass=" + fRequestHeaderClass);
        buf.append(",responseHeaderClass=" + fResponseHeaderClass);
        buf.append(",packageSuffix=" + fPackageSuffix);
        buf.append(",overridePackage=" + fOverridePackage);
        buf.append(",overrideLocation=" + fOverrideLocation);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoRestGeneratorKtTelegramProcessStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtTelegramProcessStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fServiceId
        // Type: java.lang.String
        target.fServiceId = this.fServiceId;
        // Name: fLocation
        // Type: java.lang.String
        target.fLocation = this.fLocation;
        // Name: fNamespace
        // Type: java.lang.String
        target.fNamespace = this.fNamespace;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fBasedir
        // Type: java.lang.String
        target.fBasedir = this.fBasedir;
        // Name: fImpleDirKt
        // Type: java.lang.String
        target.fImpleDirKt = this.fImpleDirKt;
        // Name: fAnnotationList
        // Type: java.util.List
        // フィールド[fAnnotationList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fClientAnnotationList
        // Type: java.util.List
        // フィールド[fClientAnnotationList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fMetaIdList
        // Type: java.util.List
        // フィールド[fMetaIdList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fNoAuthentication
        // Type: java.lang.Boolean
        target.fNoAuthentication = this.fNoAuthentication;
        // Name: fNoAuxiliaryAuthentication
        // Type: java.lang.Boolean
        target.fNoAuxiliaryAuthentication = this.fNoAuxiliaryAuthentication;
        // Name: fCreateImportList
        // Type: java.lang.Boolean
        target.fCreateImportList = this.fCreateImportList;
        // Name: fExtends
        // Type: java.lang.String
        target.fExtends = this.fExtends;
        // Name: fImplementsList
        // Type: java.util.List
        // フィールド[fImplementsList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fImportList
        // Type: java.util.List
        // フィールド[fImportList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fHeaderList
        // Type: java.util.List
        // フィールド[fHeaderList]はサポート外の型[java.util.Listjava.lang.String]です。
        // Name: fGetRequestBindList
        // Type: java.util.List
        // フィールド[fGetRequestBindList]はサポート外の型[java.util.Listblanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure]です。
        // Name: fListTelegrams
        // Type: java.util.HashMap
        // フィールド[fListTelegrams]はサポート外の型[java.util.HashMapjava.lang.String, java.util.HashMap<java.lang.String, blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure>]です。
        // Name: fRequestHeaderClass
        // Type: java.lang.String
        target.fRequestHeaderClass = this.fRequestHeaderClass;
        // Name: fResponseHeaderClass
        // Type: java.lang.String
        target.fResponseHeaderClass = this.fResponseHeaderClass;
        // Name: fPackageSuffix
        // Type: java.lang.String
        target.fPackageSuffix = this.fPackageSuffix;
        // Name: fOverridePackage
        // Type: java.lang.String
        target.fOverridePackage = this.fOverridePackage;
        // Name: fOverrideLocation
        // Type: java.lang.String
        target.fOverrideLocation = this.fOverrideLocation;
    }
}
