package blanco.restgeneratorkt;

import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;

public abstract class BlancoRestGeneratorKtExpander implements BlancoRestGeneratorKtExpanderInterface {
    
    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
    }

    private int fTabs = 4;
    public int getTabs() {
        return fTabs;
    }
    public void setTabs(int fTabs) {
        this.fTabs = fTabs;
    }

    private boolean fCreateServiceMethod = false;
    public void setCreateServiceMethod(boolean argCreateServiceMethod) {
        this.fCreateServiceMethod = argCreateServiceMethod;
    }
    public boolean isCreateServiceMethod() {
        return fCreateServiceMethod;
    }

    private String fServerType = "micronaut";
    public String getServerType() {
        return this.fServerType;
    }
    public void setServerType(String serverType) {
        this.fServerType = serverType;
    }

    private String fTelegramPackage = "";
    public String getTelegramPackage() {
        return this.fTelegramPackage;
    }
    public void setTelegramPackage(final String argTelegramPackage) {
        this.fTelegramPackage = argTelegramPackage;
    }

    /**
     * An access object to the resource bundle for this product.
     */
    protected final BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();

    /**
     * Programming language expected for the input sheet.
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }
    public int getSheetLang() {
        return this.fSheetLang;
    }

    /**
     * Style of the destination directory.
     */
    private boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }


    /**
     * Whether to adjust field names and method names.
     *
     */
    private boolean fNameAdjust = true;
    public void setNameAdjust(boolean argNameAdjust) {
        this.fNameAdjust = argNameAdjust;
    }
    public boolean isNameAdjust() {
        return this.fNameAdjust;
    }

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;
    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }
    public String getEncoding() {
        return this.fEncoding;
    }

    /**
     * A factory for blancoCg to be used internally.
     */
    protected BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    protected BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    protected BlancoCgClass fCgClass = null;

    /**
     * Interface information for blancoCg to be used internally.
     */
    protected BlancoCgInterface fCgInterface = null;

    protected void buildPermissionKindField(String value) {
        /*
         * In blancoValueObject, the property name is prefixed with "f", but in Kotlin, it is not prefixed because of the implicit getter/setter.
         */
        final BlancoCgField field = fCgFactory.createField(BlancoRestGeneratorKtConstants.PERMISSION_KIND_PROPERTY,
                "String", null);

        field.setDescription(fBundle.getBlancorestPermissionFieldDescription());
        field.setDefault("\"" + value + "\"");
        field.setAccess("public");
        field.setOverride(true);
        field.setFinal(true);
        field.setConst(true);
        field.setNotnull(true);

        fCgClass.getFieldList().add(field);
    }

    /**
     * Gets the adjusted field name.
     *
     * @param argFieldStructure
     *            Field information.
     * @return Adjusted field name.
     */
    protected String getFieldNameAdjustered(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        return BlancoNameAdjuster.toClassName(argFieldStructure.getName());
    }

    protected boolean hasConstructorArgs(final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {
        boolean found = false;

        for (BlancoRestGeneratorKtTelegramFieldStructure fieldStructure : argTelegramStructure.getListField()) {
            if (fieldStructure.getConstArg()) {
                found = true;
                break;
            }
        }
        return  found;
    }

    protected boolean isArgGetRequestBind(
            String argMethod,
            List<BlancoRestGeneratorKtGetRequestBindStructure> argGetRequestBindList
    ) {
        boolean result = false;
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod) && !argGetRequestBindList.isEmpty()) {
            result = true;
        }

        return result;
    }


    protected String getExecuteMethodId(final String argMethod) {
        String executeMethodId = "";
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.GET_CONTROLLER_METHOD;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.POST_CONTROLLER_METHOD;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.PUT_CONTROLLER_METHOD;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.DELETE_CONTROLLER_METHOD;
        }
        return executeMethodId;
    }

}
