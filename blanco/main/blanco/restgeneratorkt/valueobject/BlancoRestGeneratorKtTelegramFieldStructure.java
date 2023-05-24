package blanco.restgeneratorkt.valueobject;

import java.util.List;

/**
 * BlancoRestGeneratorKtのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorKtTelegramFieldStructure {
    /**
     * 項目番号。省略可能です。
     *
     * フィールド: [no]。
     */
    private String fNo;

    /**
     * フィールド名を指定します。必須項目です。
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 項目の説明，javadocで利用されます．
     *
     * フィールド: [description]。
     */
    private String fDescription;

    /**
     * フィールドの補助説明です。文字参照エンコード済みの値を格納してください。
     *
     * フィールド: [descriptionList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fDescriptionList = new java.util.ArrayList<java.lang.String>();

    /**
     * 型名をパッケージ名のフル修飾付で指定します。必須項目です。
     *
     * フィールド: [type]。
     */
    private String fType;

    /**
     * 型が期待する総称型の具体的な型名を指定します．
     *
     * フィールド: [generic]。
     */
    private String fGeneric;

    /**
     * アノテーション文字列です
     *
     * フィールド: [annotationList]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationList = new java.util.ArrayList<java.lang.String>();

    /**
     * kotlin用の型名をパッケージ名のフル修飾付で指定します。
     *
     * フィールド: [typeKt]。
     */
    private String fTypeKt;

    /**
     * kotlin用の型が期待する総称型の具体的な型名を指定します．
     *
     * フィールド: [genericKt]。
     */
    private String fGenericKt;

    /**
     * kotlin用のアノテーション文字列です
     *
     * フィールド: [annotationListKt]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     */
    private List<String> fAnnotationListKt = new java.util.ArrayList<java.lang.String>();

    /**
     * 必須項目の場合はtrue
     *
     * フィールド: [required]。
     */
    private Boolean fRequired;

    /**
     * デフォルト値
     *
     * フィールド: [default]。
     */
    private String fDefault;

    /**
     * kotlin向けデフォルト値
     *
     * フィールド: [defaultKt]。
     */
    private String fDefaultKt;

    /**
     * JSON/クエリのプロパティ名として用いる文字列
     *
     * フィールド: [alias]。
     */
    private String fAlias;

    /**
     * プロパティ名の取得元種別。path/query が明示されていない場合は、GET/DELETE では query。POST/PUTでは JSON プロパティを期待する。
     *
     * フィールド: [queryKind]。
     */
    private String fQueryKind;

    /**
     * 必須項目の場合はtrue
     *
     * フィールド: [nullable]。
     * デフォルト: [false]。
     */
    private Boolean fNullable = false;

    /**
     * 変更不可変数かどうか
     *
     * フィールド: [value]。
     * デフォルト: [false]。
     */
    private Boolean fValue = false;

    /**
     * コンストラクタ引数かどう
     *
     * フィールド: [constArg]。
     * デフォルト: [false]。
     */
    private Boolean fConstArg = false;

    /**
     * 長さmin
     *
     * フィールド: [minLength]。
     */
    private Integer fMinLength;

    /**
     * 長さmax
     *
     * フィールド: [maxLength]。
     */
    private Integer fMaxLength;

    /**
     * 値範囲min
     *
     * フィールド: [minInclusive]。
     */
    private String fMinInclusive;

    /**
     * 値範囲max
     *
     * フィールド: [maxInclusive]。
     */
    private String fMaxInclusive;

    /**
     * 形式Check正規表現
     *
     * フィールド: [pattern]。
     */
    private String fPattern;

    /**
     * 備考
     *
     * フィールド: [fieldBiko]。
     */
    private String fFieldBiko;

    /**
     * override 修飾子を付与します
     *
     * フィールド: [override]。
     * デフォルト: [false]。
     */
    private Boolean fOverride = false;

    /**
     * フィールド [no] の値を設定します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @param argNo フィールド[no]に設定する値。
     */
    public void setNo(final String argNo) {
        fNo = argNo;
    }

    /**
     * フィールド [no] の値を取得します。
     *
     * フィールドの説明: [項目番号。省略可能です。]。
     *
     * @return フィールド[no]から取得した値。
     */
    public String getNo() {
        return fNo;
    }

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [フィールド名を指定します。必須項目です。]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [description] の値を設定します。
     *
     * フィールドの説明: [項目の説明，javadocで利用されます．]。
     *
     * @param argDescription フィールド[description]に設定する値。
     */
    public void setDescription(final String argDescription) {
        fDescription = argDescription;
    }

    /**
     * フィールド [description] の値を取得します。
     *
     * フィールドの説明: [項目の説明，javadocで利用されます．]。
     *
     * @return フィールド[description]から取得した値。
     */
    public String getDescription() {
        return fDescription;
    }

    /**
     * フィールド [descriptionList] の値を設定します。
     *
     * フィールドの説明: [フィールドの補助説明です。文字参照エンコード済みの値を格納してください。]。
     *
     * @param argDescriptionList フィールド[descriptionList]に設定する値。
     */
    public void setDescriptionList(final List<String> argDescriptionList) {
        fDescriptionList = argDescriptionList;
    }

    /**
     * フィールド [descriptionList] の値を取得します。
     *
     * フィールドの説明: [フィールドの補助説明です。文字参照エンコード済みの値を格納してください。]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[descriptionList]から取得した値。
     */
    public List<String> getDescriptionList() {
        return fDescriptionList;
    }

    /**
     * フィールド [type] の値を設定します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @param argType フィールド[type]に設定する値。
     */
    public void setType(final String argType) {
        fType = argType;
    }

    /**
     * フィールド [type] の値を取得します。
     *
     * フィールドの説明: [型名をパッケージ名のフル修飾付で指定します。必須項目です。]。
     *
     * @return フィールド[type]から取得した値。
     */
    public String getType() {
        return fType;
    }

    /**
     * フィールド [generic] の値を設定します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @param argGeneric フィールド[generic]に設定する値。
     */
    public void setGeneric(final String argGeneric) {
        fGeneric = argGeneric;
    }

    /**
     * フィールド [generic] の値を取得します。
     *
     * フィールドの説明: [型が期待する総称型の具体的な型名を指定します．]。
     *
     * @return フィールド[generic]から取得した値。
     */
    public String getGeneric() {
        return fGeneric;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [アノテーション文字列です]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [アノテーション文字列です]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [typeKt] の値を設定します。
     *
     * フィールドの説明: [kotlin用の型名をパッケージ名のフル修飾付で指定します。]。
     *
     * @param argTypeKt フィールド[typeKt]に設定する値。
     */
    public void setTypeKt(final String argTypeKt) {
        fTypeKt = argTypeKt;
    }

    /**
     * フィールド [typeKt] の値を取得します。
     *
     * フィールドの説明: [kotlin用の型名をパッケージ名のフル修飾付で指定します。]。
     *
     * @return フィールド[typeKt]から取得した値。
     */
    public String getTypeKt() {
        return fTypeKt;
    }

    /**
     * フィールド [genericKt] の値を設定します。
     *
     * フィールドの説明: [kotlin用の型が期待する総称型の具体的な型名を指定します．]。
     *
     * @param argGenericKt フィールド[genericKt]に設定する値。
     */
    public void setGenericKt(final String argGenericKt) {
        fGenericKt = argGenericKt;
    }

    /**
     * フィールド [genericKt] の値を取得します。
     *
     * フィールドの説明: [kotlin用の型が期待する総称型の具体的な型名を指定します．]。
     *
     * @return フィールド[genericKt]から取得した値。
     */
    public String getGenericKt() {
        return fGenericKt;
    }

    /**
     * フィールド [annotationListKt] の値を設定します。
     *
     * フィールドの説明: [kotlin用のアノテーション文字列です]。
     *
     * @param argAnnotationListKt フィールド[annotationListKt]に設定する値。
     */
    public void setAnnotationListKt(final List<String> argAnnotationListKt) {
        fAnnotationListKt = argAnnotationListKt;
    }

    /**
     * フィールド [annotationListKt] の値を取得します。
     *
     * フィールドの説明: [kotlin用のアノテーション文字列です]。
     * デフォルト: [new java.util.ArrayList&lt;java.lang.String&gt;()]。
     *
     * @return フィールド[annotationListKt]から取得した値。
     */
    public List<String> getAnnotationListKt() {
        return fAnnotationListKt;
    }

    /**
     * フィールド [required] の値を設定します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     *
     * @param argRequired フィールド[required]に設定する値。
     */
    public void setRequired(final Boolean argRequired) {
        fRequired = argRequired;
    }

    /**
     * フィールド [required] の値を取得します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     *
     * @return フィールド[required]から取得した値。
     */
    public Boolean getRequired() {
        return fRequired;
    }

    /**
     * フィールド [default] の値を設定します。
     *
     * フィールドの説明: [デフォルト値]。
     *
     * @param argDefault フィールド[default]に設定する値。
     */
    public void setDefault(final String argDefault) {
        fDefault = argDefault;
    }

    /**
     * フィールド [default] の値を取得します。
     *
     * フィールドの説明: [デフォルト値]。
     *
     * @return フィールド[default]から取得した値。
     */
    public String getDefault() {
        return fDefault;
    }

    /**
     * フィールド [defaultKt] の値を設定します。
     *
     * フィールドの説明: [kotlin向けデフォルト値]。
     *
     * @param argDefaultKt フィールド[defaultKt]に設定する値。
     */
    public void setDefaultKt(final String argDefaultKt) {
        fDefaultKt = argDefaultKt;
    }

    /**
     * フィールド [defaultKt] の値を取得します。
     *
     * フィールドの説明: [kotlin向けデフォルト値]。
     *
     * @return フィールド[defaultKt]から取得した値。
     */
    public String getDefaultKt() {
        return fDefaultKt;
    }

    /**
     * フィールド [alias] の値を設定します。
     *
     * フィールドの説明: [JSON/クエリのプロパティ名として用いる文字列]。
     *
     * @param argAlias フィールド[alias]に設定する値。
     */
    public void setAlias(final String argAlias) {
        fAlias = argAlias;
    }

    /**
     * フィールド [alias] の値を取得します。
     *
     * フィールドの説明: [JSON/クエリのプロパティ名として用いる文字列]。
     *
     * @return フィールド[alias]から取得した値。
     */
    public String getAlias() {
        return fAlias;
    }

    /**
     * フィールド [queryKind] の値を設定します。
     *
     * フィールドの説明: [プロパティ名の取得元種別。path/query が明示されていない場合は、GET/DELETE では query。POST/PUTでは JSON プロパティを期待する。]。
     *
     * @param argQueryKind フィールド[queryKind]に設定する値。
     */
    public void setQueryKind(final String argQueryKind) {
        fQueryKind = argQueryKind;
    }

    /**
     * フィールド [queryKind] の値を取得します。
     *
     * フィールドの説明: [プロパティ名の取得元種別。path/query が明示されていない場合は、GET/DELETE では query。POST/PUTでは JSON プロパティを期待する。]。
     *
     * @return フィールド[queryKind]から取得した値。
     */
    public String getQueryKind() {
        return fQueryKind;
    }

    /**
     * フィールド [nullable] の値を設定します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     *
     * @param argNullable フィールド[nullable]に設定する値。
     */
    public void setNullable(final Boolean argNullable) {
        fNullable = argNullable;
    }

    /**
     * フィールド [nullable] の値を取得します。
     *
     * フィールドの説明: [必須項目の場合はtrue]。
     * デフォルト: [false]。
     *
     * @return フィールド[nullable]から取得した値。
     */
    public Boolean getNullable() {
        return fNullable;
    }

    /**
     * フィールド [value] の値を設定します。
     *
     * フィールドの説明: [変更不可変数かどうか]。
     *
     * @param argValue フィールド[value]に設定する値。
     */
    public void setValue(final Boolean argValue) {
        fValue = argValue;
    }

    /**
     * フィールド [value] の値を取得します。
     *
     * フィールドの説明: [変更不可変数かどうか]。
     * デフォルト: [false]。
     *
     * @return フィールド[value]から取得した値。
     */
    public Boolean getValue() {
        return fValue;
    }

    /**
     * フィールド [constArg] の値を設定します。
     *
     * フィールドの説明: [コンストラクタ引数かどう]。
     *
     * @param argConstArg フィールド[constArg]に設定する値。
     */
    public void setConstArg(final Boolean argConstArg) {
        fConstArg = argConstArg;
    }

    /**
     * フィールド [constArg] の値を取得します。
     *
     * フィールドの説明: [コンストラクタ引数かどう]。
     * デフォルト: [false]。
     *
     * @return フィールド[constArg]から取得した値。
     */
    public Boolean getConstArg() {
        return fConstArg;
    }

    /**
     * フィールド [minLength] の値を設定します。
     *
     * フィールドの説明: [長さmin]。
     *
     * @param argMinLength フィールド[minLength]に設定する値。
     */
    public void setMinLength(final Integer argMinLength) {
        fMinLength = argMinLength;
    }

    /**
     * フィールド [minLength] の値を取得します。
     *
     * フィールドの説明: [長さmin]。
     *
     * @return フィールド[minLength]から取得した値。
     */
    public Integer getMinLength() {
        return fMinLength;
    }

    /**
     * フィールド [maxLength] の値を設定します。
     *
     * フィールドの説明: [長さmax]。
     *
     * @param argMaxLength フィールド[maxLength]に設定する値。
     */
    public void setMaxLength(final Integer argMaxLength) {
        fMaxLength = argMaxLength;
    }

    /**
     * フィールド [maxLength] の値を取得します。
     *
     * フィールドの説明: [長さmax]。
     *
     * @return フィールド[maxLength]から取得した値。
     */
    public Integer getMaxLength() {
        return fMaxLength;
    }

    /**
     * フィールド [minInclusive] の値を設定します。
     *
     * フィールドの説明: [値範囲min]。
     *
     * @param argMinInclusive フィールド[minInclusive]に設定する値。
     */
    public void setMinInclusive(final String argMinInclusive) {
        fMinInclusive = argMinInclusive;
    }

    /**
     * フィールド [minInclusive] の値を取得します。
     *
     * フィールドの説明: [値範囲min]。
     *
     * @return フィールド[minInclusive]から取得した値。
     */
    public String getMinInclusive() {
        return fMinInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を設定します。
     *
     * フィールドの説明: [値範囲max]。
     *
     * @param argMaxInclusive フィールド[maxInclusive]に設定する値。
     */
    public void setMaxInclusive(final String argMaxInclusive) {
        fMaxInclusive = argMaxInclusive;
    }

    /**
     * フィールド [maxInclusive] の値を取得します。
     *
     * フィールドの説明: [値範囲max]。
     *
     * @return フィールド[maxInclusive]から取得した値。
     */
    public String getMaxInclusive() {
        return fMaxInclusive;
    }

    /**
     * フィールド [pattern] の値を設定します。
     *
     * フィールドの説明: [形式Check正規表現]。
     *
     * @param argPattern フィールド[pattern]に設定する値。
     */
    public void setPattern(final String argPattern) {
        fPattern = argPattern;
    }

    /**
     * フィールド [pattern] の値を取得します。
     *
     * フィールドの説明: [形式Check正規表現]。
     *
     * @return フィールド[pattern]から取得した値。
     */
    public String getPattern() {
        return fPattern;
    }

    /**
     * フィールド [fieldBiko] の値を設定します。
     *
     * フィールドの説明: [備考]。
     *
     * @param argFieldBiko フィールド[fieldBiko]に設定する値。
     */
    public void setFieldBiko(final String argFieldBiko) {
        fFieldBiko = argFieldBiko;
    }

    /**
     * フィールド [fieldBiko] の値を取得します。
     *
     * フィールドの説明: [備考]。
     *
     * @return フィールド[fieldBiko]から取得した値。
     */
    public String getFieldBiko() {
        return fFieldBiko;
    }

    /**
     * フィールド [override] の値を設定します。
     *
     * フィールドの説明: [override 修飾子を付与します]。
     *
     * @param argOverride フィールド[override]に設定する値。
     */
    public void setOverride(final Boolean argOverride) {
        fOverride = argOverride;
    }

    /**
     * フィールド [override] の値を取得します。
     *
     * フィールドの説明: [override 修飾子を付与します]。
     * デフォルト: [false]。
     *
     * @return フィールド[override]から取得した値。
     */
    public Boolean getOverride() {
        return fOverride;
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
        buf.append("blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure[");
        buf.append("no=" + fNo);
        buf.append(",name=" + fName);
        buf.append(",description=" + fDescription);
        buf.append(",descriptionList=" + fDescriptionList);
        buf.append(",type=" + fType);
        buf.append(",generic=" + fGeneric);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",typeKt=" + fTypeKt);
        buf.append(",genericKt=" + fGenericKt);
        buf.append(",annotationListKt=" + fAnnotationListKt);
        buf.append(",required=" + fRequired);
        buf.append(",default=" + fDefault);
        buf.append(",defaultKt=" + fDefaultKt);
        buf.append(",alias=" + fAlias);
        buf.append(",queryKind=" + fQueryKind);
        buf.append(",nullable=" + fNullable);
        buf.append(",value=" + fValue);
        buf.append(",constArg=" + fConstArg);
        buf.append(",minLength=" + fMinLength);
        buf.append(",maxLength=" + fMaxLength);
        buf.append(",minInclusive=" + fMinInclusive);
        buf.append(",maxInclusive=" + fMaxInclusive);
        buf.append(",pattern=" + fPattern);
        buf.append(",fieldBiko=" + fFieldBiko);
        buf.append(",override=" + fOverride);
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
    public void copyTo(final BlancoRestGeneratorKtTelegramFieldStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtTelegramFieldStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fNo
        // Type: java.lang.String
        target.fNo = this.fNo;
        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fDescription
        // Type: java.lang.String
        target.fDescription = this.fDescription;
        // Name: fDescriptionList
        // Type: java.util.List
        // Field[fDescriptionList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fType
        // Type: java.lang.String
        target.fType = this.fType;
        // Name: fGeneric
        // Type: java.lang.String
        target.fGeneric = this.fGeneric;
        // Name: fAnnotationList
        // Type: java.util.List
        // Field[fAnnotationList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fTypeKt
        // Type: java.lang.String
        target.fTypeKt = this.fTypeKt;
        // Name: fGenericKt
        // Type: java.lang.String
        target.fGenericKt = this.fGenericKt;
        // Name: fAnnotationListKt
        // Type: java.util.List
        // Field[fAnnotationListKt] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fRequired
        // Type: java.lang.Boolean
        target.fRequired = this.fRequired;
        // Name: fDefault
        // Type: java.lang.String
        target.fDefault = this.fDefault;
        // Name: fDefaultKt
        // Type: java.lang.String
        target.fDefaultKt = this.fDefaultKt;
        // Name: fAlias
        // Type: java.lang.String
        target.fAlias = this.fAlias;
        // Name: fQueryKind
        // Type: java.lang.String
        target.fQueryKind = this.fQueryKind;
        // Name: fNullable
        // Type: java.lang.Boolean
        target.fNullable = this.fNullable;
        // Name: fValue
        // Type: java.lang.Boolean
        target.fValue = this.fValue;
        // Name: fConstArg
        // Type: java.lang.Boolean
        target.fConstArg = this.fConstArg;
        // Name: fMinLength
        // Type: java.lang.Integer
        target.fMinLength = this.fMinLength;
        // Name: fMaxLength
        // Type: java.lang.Integer
        target.fMaxLength = this.fMaxLength;
        // Name: fMinInclusive
        // Type: java.lang.String
        target.fMinInclusive = this.fMinInclusive;
        // Name: fMaxInclusive
        // Type: java.lang.String
        target.fMaxInclusive = this.fMaxInclusive;
        // Name: fPattern
        // Type: java.lang.String
        target.fPattern = this.fPattern;
        // Name: fFieldBiko
        // Type: java.lang.String
        target.fFieldBiko = this.fFieldBiko;
        // Name: fOverride
        // Type: java.lang.Boolean
        target.fOverride = this.fOverride;
    }
}
