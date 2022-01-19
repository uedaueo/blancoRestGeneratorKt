package blanco.restgeneratorkt.valueobject;

/**
 * BlancoRestGeneratorKtのなかで利用されるValueObjectです。
 */
public class BlancoRestGeneratorKtGetRequestBindStructure {
    /**
     * 番号
     *
     * フィールド: [number]。
     */
    private Integer fNumber;

    /**
     * 項目名
     *
     * フィールド: [name]。
     */
    private String fName;

    /**
     * 種類
     *
     * フィールド: [kind]。
     */
    private String fKind;

    /**
     * フィールド [number] の値を設定します。
     *
     * フィールドの説明: [番号]。
     *
     * @param argNumber フィールド[number]に設定する値。
     */
    public void setNumber(final Integer argNumber) {
        fNumber = argNumber;
    }

    /**
     * フィールド [number] の値を取得します。
     *
     * フィールドの説明: [番号]。
     *
     * @return フィールド[number]から取得した値。
     */
    public Integer getNumber() {
        return fNumber;
    }

    /**
     * フィールド [name] の値を設定します。
     *
     * フィールドの説明: [項目名]。
     *
     * @param argName フィールド[name]に設定する値。
     */
    public void setName(final String argName) {
        fName = argName;
    }

    /**
     * フィールド [name] の値を取得します。
     *
     * フィールドの説明: [項目名]。
     *
     * @return フィールド[name]から取得した値。
     */
    public String getName() {
        return fName;
    }

    /**
     * フィールド [kind] の値を設定します。
     *
     * フィールドの説明: [種類]。
     *
     * @param argKind フィールド[kind]に設定する値。
     */
    public void setKind(final String argKind) {
        fKind = argKind;
    }

    /**
     * フィールド [kind] の値を取得します。
     *
     * フィールドの説明: [種類]。
     *
     * @return フィールド[kind]から取得した値。
     */
    public String getKind() {
        return fKind;
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
        buf.append("blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure[");
        buf.append("number=" + fNumber);
        buf.append(",name=" + fName);
        buf.append(",kind=" + fKind);
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
    public void copyTo(final BlancoRestGeneratorKtGetRequestBindStructure target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoRestGeneratorKtGetRequestBindStructure#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fNumber
        // Type: java.lang.Integer
        target.fNumber = this.fNumber;
        // Name: fName
        // Type: java.lang.String
        target.fName = this.fName;
        // Name: fKind
        // Type: java.lang.String
        target.fKind = this.fKind;
    }
}
