package blanco.restgeneratorkt;

import java.io.File;
import java.util.List;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.BlancoCgClass;
import blanco.cg.valueobject.BlancoCgField;
import blanco.cg.valueobject.BlancoCgInterface;
import blanco.cg.valueobject.BlancoCgSourceFile;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
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


    /**
     * Generates a telegram class.
     *
     * @param argTelegramStructure
     * @param argDirectoryTarget
     */
    protected void generateTelegram(
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure,
            final File argDirectoryTarget) {

        /*
         * The output directory will be in the format specified by the targetStyle argument of the ant task.
         * To maintain compatibility with the previous version, it will be blanco/main if not specified.
         * by tueda, 2019/08/30
         */
        String strTarget = argDirectoryTarget
                .getAbsolutePath(); // advanced
        if (!this.isTargetStyleAdvanced()) {
            strTarget += "/main"; // legacy
        }
        final File fileBlancoMain = new File(strTarget);

        /* tueda DEBUG */
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegram START with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String myPackage = argTelegramStructure.getPackage();
        if (argTelegramStructure.getPackageSuffix() != null && argTelegramStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argTelegramStructure.getPackageSuffix();
        } else if (argTelegramStructure.getOverridePackage() != null && argTelegramStructure.getOverridePackage().length() > 0) {
            myPackage = argTelegramStructure.getOverridePackage();
        }
        argTelegramStructure.setCalculatedPackage(myPackage);

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(myPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(this.getEncoding());
        fCgSourceFile.setTabs(this.getTabs());
        // Creates a class.
        fCgClass = fCgFactory.createClass(argTelegramStructure.getName(),
                BlancoStringUtil.null2Blank(argTelegramStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // The telegram class is always public, and can be omitted in Kotlin.
        String access = "";
        // Whether or not it is a data class and whether or not it has at least one constructor argument.
        if (argTelegramStructure.getData() && this.hasConstructorArgs(argTelegramStructure)) {
            access += "data";
        }
        fCgClass.setAccess(access);
        // Whether or not it is a Final class. data class is forced to be final.
        if (argTelegramStructure.getData() && !argTelegramStructure.getFinal()) {
            fCgClass.setFinal(true);
        } else {
            fCgClass.setFinal(argTelegramStructure.getFinal());
        }
        // Inheritance
        if (BlancoStringUtil.null2Blank(argTelegramStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argTelegramStructure.getExtends()));
        }
        // Implementation
        for (int index = 0; index < argTelegramStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argTelegramStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }

        // Sets the JavaDoc for the class.
        fCgClass.setDescription(argTelegramStructure.getDescription());

        /* Sets the annotation for the class. */
        List<String> annotationList = argTelegramStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argTelegramStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegram : class annotation = " + argTelegramStructure.getAnnotationList().get(0));
            }
        }

        /* Sets the import for the class. */
        for (int index = 0; index < argTelegramStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argTelegramStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXml2SourceFile: Start create properties : " + argTelegramStructure.getName());
        }

        // TelegramDefinition list.
        for (int indexField = 0; indexField < argTelegramStructure.getListField()
                .size(); indexField++) {
            // Processes each field.
            final BlancoRestGeneratorKtTelegramFieldStructure fieldStructure =
                    argTelegramStructure.getListField().get(indexField);

            // Performs exception processing if a required field is not set.
            if (fieldStructure.getName() == null) {
                throw new IllegalArgumentException(fBundle
                        .getXml2sourceFileErr004(argTelegramStructure.getName()));
            }
            if (fieldStructure.getType() == null) {
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr003(
                        argTelegramStructure.getName(), fieldStructure.getName()));
            }

            if (this.isVerbose()) {
                System.out.println("property : " + fieldStructure.getName());
            }

            // Generates a field.
            buildField(argTelegramStructure, fieldStructure);
        }

        // If permissionKind is set.
        if (BlancoStringUtil.null2Blank(argTelegramStructure.getPermissionKind()).length() > 0) {
            buildPermissionKindField(argTelegramStructure.getPermissionKind());
        }

        // Set StatusCode on error telegram.
        // if (BlancoStringUtil.null2Blank(argTelegramStructure.getTelegramType()))

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

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
     * Generates a field in the class.
     * @param argClassStructure
     *            Class information.
     * @param argFieldStructure
     */
    protected void buildField(
            final BlancoRestGeneratorKtTelegramStructure argClassStructure,
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {

        switch (this.getSheetLang()) {
            case BlancoCgSupportedLang.PHP:
                if (argFieldStructure.getType() == "kotlin.Int") argFieldStructure.setType("kotlin.Long");
                break;
            /* Adds the case here if you want to add more languages. */
        }

        /* Determines the type; if typeKt is set, it will take precedence. */
        boolean isKtPreferred = true;
        String typeRaw = argFieldStructure.getTypeKt();
        if (typeRaw == null || typeRaw.length() == 0) {
            typeRaw = argFieldStructure.getType();
            isKtPreferred = false;
        }

        /*
         * In blancoValueObject, the property name is prefixed with "f", but in Kotlin, it is not because of the implicit getter/setter.
         */
        final BlancoCgField field = fCgFactory.createField(argFieldStructure.getName(),
                typeRaw, null);

        /*
         * Supports Generic. The blancoCg side assumes that "<>" is attached and trims the package part, so if it is not set here, it will no be set correctly.
         * If genericKt is set, it will take precedence.
         */
        String genericRaw = argFieldStructure.getGenericKt();
        if (!isKtPreferred && (genericRaw == null || genericRaw.length() == 0)) {
            genericRaw = argFieldStructure.getGeneric();
        }
        if (genericRaw != null && genericRaw.length() > 0) {
            field.getType().setGenerics(genericRaw);
        }

//        if (this.isVerbose()) {
//            System.out.println("!!! type = " + argFieldStructure.getType());
//            System.out.println("!!! generic = " + field.getType().getGenerics());
//        }

        /*
         * For the time being, it does not support private, getter/setter, and open in blancoValueObjectKt.
         */
        field.setAccess("public");
        field.setFinal(true);

        // Supports nullable.
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // Supports value / variable.
        Boolean isValue = argFieldStructure.getValue();
        if (isValue != null && isValue) {
            field.setConst(true);
        } else {
            field.setConst(false);
        }

        /* Sets the override modifier. */
        field.setOverride(argFieldStructure.getOverride());

        /*
         * Checks if field is a constructor argument.
         */
        Boolean isConstArg = argFieldStructure.getConstArg();
        if (isConstArg != null && isConstArg) {
            fCgClass.getConstructorArgList().add(field);
        } else {
            fCgClass.getFieldList().add(field);
        }

        // Sets the JavaDoc for the field.
        field.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            field.getLangDoc().getDescriptionList().add(line);
        }

        // Sets the default value for the field.
        if (argFieldStructure.getDefault() != null || argFieldStructure.getDefaultKt() != null) {
            final String type = field.getType().getName();

            if (type.equals("java.util.Date")) {
                /*
                 * The java.util.Date type does not allow default values.
                 */
                throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg06());
            }

            /*
             * In Kotlin, the default value of a property is mandatory in principle.
             * However, in the abstract class, it can be omitted if the property has the abstract modifier.
             * Nevertheless, blancoValueObjectKt will not support abstract properties for the time being.
             */

            /*
             * If there is a defaultKt, it will take precedence.
             */
            String defaultRawValue = argFieldStructure.getDefaultKt();
            if (!isKtPreferred && (defaultRawValue == null || defaultRawValue.length() == 0)) {
                defaultRawValue = argFieldStructure.getDefault();
            }
            if (!(isConstArg != null && isConstArg) && (defaultRawValue == null || defaultRawValue.length() <= 0)) {
                System.err.println("/* tueda */ The field does not have a default value. blancoValueObjectKt will not support abstract fields for the time being, so be sure to set the default value.");
                throw new IllegalArgumentException(fBundle
                        .getBlancorestErrorMsg07());
            }

            // Sets the default value for the field.
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.KOTLIN, fBundle.getXml2sourceFileFieldDefault(defaultRawValue)));

            // In the case of blancoRestGenerator, the default value is the value in the definition document.
            field.setDefault(defaultRawValue);
        }

        /* Sets the annotation for the method. If there is an annotation (kt), it will take precedence. */
        List<String> annotationList = argFieldStructure.getAnnotationListKt();
        if (annotationList == null || annotationList.size() == 0) {
            annotationList = argFieldStructure.getAnnotationList();
        }
        if (annotationList != null && annotationList.size() > 0) {
            field.getAnnotationList().addAll(annotationList);
            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }

        /* Set alias as annotation. */
        String aliaString = argFieldStructure.getAlias();
        if (BlancoStringUtil.null2Blank(aliaString).length() > 0) {
            String annotationType = this.getAnnotationType(argClassStructure.getTelegramMethod(), argFieldStructure);
            field.getAnnotationList().add(annotationType + "(\"" + aliaString + "\")");
        }
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

    protected String getAnnotationType(final String argTelegramMethod, final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        String annotatioString = "field:JsonProperty";
        if (BlancoRestGeneratorKtUtil.isTelegramStylePlain &&
            (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.endsWith(argTelegramMethod) ||
            BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.endsWith(argTelegramMethod))
        ) {
            annotatioString = "field:QueryValue";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.QueryValue");
        }
        return annotatioString;
    }
}
