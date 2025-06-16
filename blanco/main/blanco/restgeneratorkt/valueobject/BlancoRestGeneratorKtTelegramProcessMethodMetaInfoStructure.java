package blanco.restgeneratorkt.valueobject;

import java.util.List;

/**
 * BlancoRestGeneratorKtのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorKtTelegramProcessMethodMetaInfoStructure {
    /**
     * メソッド
     *
     * フィールド: [method]。
     */
    private String fMethod;

    /**
     * アノテーション
     *
     * フィールド: [annotationList]。
     */
    private List<String> fAnnotationList;

    /**
     * 追加パス
     *
     * フィールド: [additionalPath]。
     */
    private String fAdditionalPath;

    /**
     * Pathクエリ書式
     *
     * フィールド: [pathQueryFormat]。
     */
    private String fPathQueryFormat;

    /**
     * フィールド [method] の値を設定します。
     *
     * フィールドの説明: [メソッド]。
     *
     * @param argMethod フィールド[method]に設定する値。
     */
    public void setMethod(final String argMethod) {
        fMethod = argMethod;
    }

    /**
     * フィールド [method] の値を取得します。
     *
     * フィールドの説明: [メソッド]。
     *
     * @return フィールド[method]から取得した値。
     */
    public String getMethod() {
        return fMethod;
    }

    /**
     * フィールド [annotationList] の値を設定します。
     *
     * フィールドの説明: [アノテーション]。
     *
     * @param argAnnotationList フィールド[annotationList]に設定する値。
     */
    public void setAnnotationList(final List<String> argAnnotationList) {
        fAnnotationList = argAnnotationList;
    }

    /**
     * フィールド [annotationList] の値を取得します。
     *
     * フィールドの説明: [アノテーション]。
     *
     * @return フィールド[annotationList]から取得した値。
     */
    public List<String> getAnnotationList() {
        return fAnnotationList;
    }

    /**
     * フィールド [additionalPath] の値を設定します。
     *
     * フィールドの説明: [追加パス]。
     *
     * @param argAdditionalPath フィールド[additionalPath]に設定する値。
     */
    public void setAdditionalPath(final String argAdditionalPath) {
        fAdditionalPath = argAdditionalPath;
    }

    /**
     * フィールド [additionalPath] の値を取得します。
     *
     * フィールドの説明: [追加パス]。
     *
     * @return フィールド[additionalPath]から取得した値。
     */
    public String getAdditionalPath() {
        return fAdditionalPath;
    }

    /**
     * フィールド [pathQueryFormat] の値を設定します。
     *
     * フィールドの説明: [Pathクエリ書式]。
     *
     * @param argPathQueryFormat フィールド[pathQueryFormat]に設定する値。
     */
    public void setPathQueryFormat(final String argPathQueryFormat) {
        fPathQueryFormat = argPathQueryFormat;
    }

    /**
     * フィールド [pathQueryFormat] の値を取得します。
     *
     * フィールドの説明: [Pathクエリ書式]。
     *
     * @return フィールド[pathQueryFormat]から取得した値。
     */
    public String getPathQueryFormat() {
        return fPathQueryFormat;
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
        buf.append("blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessMethodMetaInfoStructure[");
        buf.append("method=" + fMethod);
        buf.append(",annotationList=" + fAnnotationList);
        buf.append(",additionalPath=" + fAdditionalPath);
        buf.append(",pathQueryFormat=" + fPathQueryFormat);
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
    public void copyTo(final BlancoRestGeneratorKtTelegramProcessMethodMetaInfoStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtTelegramProcessMethodMetaInfoStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fMethod
        // Type: java.lang.String
        target.fMethod = this.fMethod;
        // Name: fAnnotationList
        // Type: java.util.List
        // Field[fAnnotationList] is an unsupported type[java.util.Listjava.lang.String].
        // Name: fAdditionalPath
        // Type: java.lang.String
        target.fAdditionalPath = this.fAdditionalPath;
        // Name: fPathQueryFormat
        // Type: java.lang.String
        target.fPathQueryFormat = this.fPathQueryFormat;
    }
}
