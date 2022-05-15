package blanco.restgeneratorkt.valueobject;

import java.util.ArrayList;
import java.util.List;

import blanco.cg.valueobject.BlancoCgField;

/**
 * BlancoRestGeneratorKtのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorKtTelegramStructure {
    /**
     * 電文ID
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * メッセージ定義の説明を記載します。
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * 電文種類
     *
     * フィールド: [telegramType]。
     */
    private String fTelegramType;

    /**
     * 電文のMETHOD
     *
     * フィールド: [telegramMethod]。
     */
    private String fTelegramMethod;

    /**
     * 電文の親クラス名
     *
     * フィールド: [telegramSuperClass]。
     */
    private String fTelegramSuperClass;

    /**
     * 名前空間
     *
     * フィールド: [namespace]。
     */
    private String fNamespace;

    /**
     * パッケージ名を指定します。必須項目です。
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
     * クラスのアノテーションを指定します。
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

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
     * フィールドを保持するリスト
     *
     * フィールド: [listField]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     */
    private ArrayList<BlancoRestGeneratorKtTelegramFieldStructure> fListField = new java.util.ArrayList<>();

    /**
     * クラスのアクセス。通常は public。
     *
     * フィールド: [access]。
     * デフォルト: [&quot;public&quot;]。
     */
    private String fAccess = "public";

    /**
     * dataクラスかどうか。
     *
     * フィールド: [data]。
     * デフォルト: [true]。
     */
    private Boolean fData = true;

    /**
     * クラスが拡張可能かどうか。kotlin では通常は true。
     *
     * フィールド: [final]。
     * デフォルト: [true]。
     */
    private Boolean fFinal = true;

    /**
     * フィールド名の名前変形をおこなうかどうか。
     *
     * フィールド: [adjustFieldName]。
     * デフォルト: [true]。
     */
    private Boolean fAdjustFieldName = true;

    /**
     * クラス定義に含めるコンストラクタの引数マップです。&lt;引数名, 型&gt; となります。
     *
     * フィールド: [constructorArgList]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     */
    private List<BlancoCgField> fConstructorArgList = new java.util.ArrayList<blanco.cg.valueobject.BlancoCgField>();

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
     * APIの権限認証で使用。[write]の場合のみ利用可能、または、[read/write]で利用可能
     *
     * フィールド: [permissionKind]。
     */
    private String fPermissionKind;

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
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [メッセージ定義の説明を記載します。]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [telegramType] の値を設定します。
     *
     * フィールドの説明: [電文種類]。
     *
     * @param argTelegramType フィールド[telegramType]に設定する値。
     */
    public void setTelegramType(final String argTelegramType) {
        fTelegramType = argTelegramType;
    }

    /**
     * フィールド [telegramType] の値を取得します。
     *
     * フィールドの説明: [電文種類]。
     *
     * @return フィールド[telegramType]から取得した値。
     */
    public String getTelegramType() {
        return fTelegramType;
    }

    /**
     * フィールド [telegramMethod] の値を設定します。
     *
     * フィールドの説明: [電文のMETHOD]。
     *
     * @param argTelegramMethod フィールド[telegramMethod]に設定する値。
     */
    public void setTelegramMethod(final String argTelegramMethod) {
        fTelegramMethod = argTelegramMethod;
    }

    /**
     * フィールド [telegramMethod] の値を取得します。
     *
     * フィールドの説明: [電文のMETHOD]。
     *
     * @return フィールド[telegramMethod]から取得した値。
     */
    public String getTelegramMethod() {
        return fTelegramMethod;
    }

    /**
     * フィールド [telegramSuperClass] の値を設定します。
     *
     * フィールドの説明: [電文の親クラス名]。
     *
     * @param argTelegramSuperClass フィールド[telegramSuperClass]に設定する値。
     */
    public void setTelegramSuperClass(final String argTelegramSuperClass) {
        fTelegramSuperClass = argTelegramSuperClass;
    }

    /**
     * フィールド [telegramSuperClass] の値を取得します。
     *
     * フィールドの説明: [電文の親クラス名]。
     *
     * @return フィールド[telegramSuperClass]から取得した値。
     */
    public String getTelegramSuperClass() {
        return fTelegramSuperClass;
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
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
     *
     * @param argPackage フィールド[package]に設定する値。
     */
    public void setPackage(final String argPackage) {
        fPackage = argPackage;
    }

    /**
     * フィールド [package] の値を取得します。
     *
     * フィールドの説明: [パッケージ名を指定します。必須項目です。]。
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
     * フィールド [listField] の値を設定します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     *
     * @param argListField フィールド[listField]に設定する値。
     */
    public void setListField(final ArrayList<BlancoRestGeneratorKtTelegramFieldStructure> argListField) {
        fListField = argListField;
    }

    /**
     * フィールド [listField] の値を取得します。
     *
     * フィールドの説明: [フィールドを保持するリスト]。
     * デフォルト: [new java.util.ArrayList&lt;&gt;()]。
     *
     * @return フィールド[listField]から取得した値。
     */
    public ArrayList<BlancoRestGeneratorKtTelegramFieldStructure> getListField() {
        return fListField;
    }

    /**
     * フィールド [access] の値を設定します。
     *
     * フィールドの説明: [クラスのアクセス。通常は public。]。
     *
     * @param argAccess フィールド[access]に設定する値。
     */
    public void setAccess(final String argAccess) {
        fAccess = argAccess;
    }

    /**
     * フィールド [access] の値を取得します。
     *
     * フィールドの説明: [クラスのアクセス。通常は public。]。
     * デフォルト: [&quot;public&quot;]。
     *
     * @return フィールド[access]から取得した値。
     */
    public String getAccess() {
        return fAccess;
    }

    /**
     * フィールド [data] の値を設定します。
     *
     * フィールドの説明: [dataクラスかどうか。]。
     *
     * @param argData フィールド[data]に設定する値。
     */
    public void setData(final Boolean argData) {
        fData = argData;
    }

    /**
     * フィールド [data] の値を取得します。
     *
     * フィールドの説明: [dataクラスかどうか。]。
     * デフォルト: [true]。
     *
     * @return フィールド[data]から取得した値。
     */
    public Boolean getData() {
        return fData;
    }

    /**
     * フィールド [final] の値を設定します。
     *
     * フィールドの説明: [クラスが拡張可能かどうか。kotlin では通常は true。]。
     *
     * @param argFinal フィールド[final]に設定する値。
     */
    public void setFinal(final Boolean argFinal) {
        fFinal = argFinal;
    }

    /**
     * フィールド [final] の値を取得します。
     *
     * フィールドの説明: [クラスが拡張可能かどうか。kotlin では通常は true。]。
     * デフォルト: [true]。
     *
     * @return フィールド[final]から取得した値。
     */
    public Boolean getFinal() {
        return fFinal;
    }

    /**
     * フィールド [adjustFieldName] の値を設定します。
     *
     * フィールドの説明: [フィールド名の名前変形をおこなうかどうか。]。
     *
     * @param argAdjustFieldName フィールド[adjustFieldName]に設定する値。
     */
    public void setAdjustFieldName(final Boolean argAdjustFieldName) {
        fAdjustFieldName = argAdjustFieldName;
    }

    /**
     * フィールド [adjustFieldName] の値を取得します。
     *
     * フィールドの説明: [フィールド名の名前変形をおこなうかどうか。]。
     * デフォルト: [true]。
     *
     * @return フィールド[adjustFieldName]から取得した値。
     */
    public Boolean getAdjustFieldName() {
        return fAdjustFieldName;
    }

    /**
     * フィールド [constructorArgList] の値を設定します。
     *
     * フィールドの説明: [クラス定義に含めるコンストラクタの引数マップです。<引数名, 型> となります。]。
     *
     * @param argConstructorArgList フィールド[constructorArgList]に設定する値。
     */
    public void setConstructorArgList(final List<BlancoCgField> argConstructorArgList) {
        fConstructorArgList = argConstructorArgList;
    }

    /**
     * フィールド [constructorArgList] の値を取得します。
     *
     * フィールドの説明: [クラス定義に含めるコンストラクタの引数マップです。<引数名, 型> となります。]。
     * デフォルト: [new java.util.ArrayList&lt;blanco.cg.valueobject.BlancoCgField&gt;()]。
     *
     * @return フィールド[constructorArgList]から取得した値。
     */
    public List<BlancoCgField> getConstructorArgList() {
        return fConstructorArgList;
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
     * フィールド [permissionKind] の値を設定します。
     *
     * フィールドの説明: [APIの権限認証で使用。[write]の場合のみ利用可能、または、[read/write]で利用可能]。
     *
     * @param argPermissionKind フィールド[permissionKind]に設定する値。
     */
    public void setPermissionKind(final String argPermissionKind) {
        fPermissionKind = argPermissionKind;
    }

    /**
     * フィールド [permissionKind] の値を取得します。
     *
     * フィールドの説明: [APIの権限認証で使用。[write]の場合のみ利用可能、または、[read/write]で利用可能]。
     *
     * @return フィールド[permissionKind]から取得した値。
     */
    public String getPermissionKind() {
        return fPermissionKind;
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
        buf.append("blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure[");
        buf.append("name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",telegramType=" + fTelegramType);
        buf.append(",telegramMethod=" + fTelegramMethod);
        buf.append(",telegramSuperClass=" + fTelegramSuperClass);
        buf.append(",namespace=" + fNamespace);
        buf.append(",package=" + fPackage);
        buf.append(",basedir=" + fBasedir);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",createImportList=" + fCreateImportList);
        buf.append(",extends=" + fExtends);
        buf.append(",implementsList=" + fImplementsList);
        buf.append(",importList=" + fImportList);
        buf.append(",headerList=" + fHeaderList);
        buf.append(",listField=" + fListField);
        buf.append(",access=" + fAccess);
        buf.append(",data=" + fData);
        buf.append(",final=" + fFinal);
        buf.append(",adjustFieldName=" + fAdjustFieldName);
        buf.append(",constructorArgList=" + fConstructorArgList);
        buf.append(",packageSuffix=" + fPackageSuffix);
        buf.append(",overridePackage=" + fOverridePackage);
        buf.append(",permissionKind=" + fPermissionKind);
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
    public void copyTo(final BlancoRestGeneratorKtTelegramStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtTelegramStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fTelegramType
        // Type: java.lang.String
        target.fTelegramType = this.fTelegramType;
        // Name: fTelegramMethod
        // Type: java.lang.String
        target.fTelegramMethod = this.fTelegramMethod;
        // Name: fTelegramSuperClass
        // Type: java.lang.String
        target.fTelegramSuperClass = this.fTelegramSuperClass;
        // Name: fNamespace
        // Type: java.lang.String
        target.fNamespace = this.fNamespace;
        // Name: fPackage
        // Type: java.lang.String
        target.fPackage = this.fPackage;
        // Name: fBasedir
        // Type: java.lang.String
        target.fBasedir = this.fBasedir;
        // Name: fAnnotationList
        // Type: java.util.List
        // Field[fAnnotationList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fCreateImportList
        // Type: java.lang.Boolean
        target.fCreateImportList = this.fCreateImportList;
        // Name: fExtends
        // Type: java.lang.String
        target.fExtends = this.fExtends;
        // Name: fImplementsList
        // Type: java.util.List
        // Field[fImplementsList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fImportList
        // Type: java.util.List
        // Field[fImportList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fHeaderList
        // Type: java.util.List
        // Field[fHeaderList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fListField
        // Type: java.util.ArrayList
        // Field[fListField] is an unsupported type[java.util.ArrayListblanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure].
        // Name: fAccess
        // Type: java.lang.String
        target.fAccess = this.fAccess;
        // Name: fData
        // Type: java.lang.Boolean
        target.fData = this.fData;
        // Name: fFinal
        // Type: java.lang.Boolean
        target.fFinal = this.fFinal;
        // Name: fAdjustFieldName
        // Type: java.lang.Boolean
        target.fAdjustFieldName = this.fAdjustFieldName;
        // Name: fConstructorArgList
        // Type: java.util.List
        // Field[fConstructorArgList] is an unsupported type[java.util.Listblanco.cg.valueobject.BlancoCgField].
        // Name: fPackageSuffix
        // Type: java.lang.String
        target.fPackageSuffix = this.fPackageSuffix;
        // Name: fOverridePackage
        // Type: java.lang.String
        target.fOverridePackage = this.fOverridePackage;
        // Name: fPermissionKind
        // Type: java.lang.String
        target.fPermissionKind = this.fPermissionKind;
    }
}
