package blanco.restgeneratorkt;

import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

public class BlancoRestGeneratorKtXmlParser {

    /**
     * An access object to the resource bundle for this product.
     */
    private final BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();


    private boolean fVerbose = false;
    public void setVerbose(boolean argVerbose) {
        this.fVerbose = argVerbose;
    }
    public boolean isVerbose() {
        return fVerbose;
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
     * Parses an XML document in an intermediate XML file to get an array of value object information.
     *
     * @param argMetaXmlSourceFile
     *            An intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoRestGeneratorKtTelegramProcessStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            System.out.println("Fail to unmarshal XML.");
            return null;
        }

        // Gets the root element.
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(documentMeta);
        if (elementRoot == null) {
            // The process is aborted if there is no root element.
            if (this.isVerbose()) {
                System.out.println("praser !!! NO ROOT ELEMENT !!!");
            }
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("[Starts the process of " + argMetaXmlSourceFile.getName() + "]");
        }

        // First, it gets the list of telegrams.
        Map<String, BlancoRestGeneratorKtTelegramStructure> telegramStructureMap =
                parseTelegrams(elementRoot);

        if (telegramStructureMap.isEmpty()) {
            if (this.isVerbose()) {
                System.out.println("praser !!! NO telegramStructureMap !!!");
            }
            return null;
        }

        // Next, it gets the telegram processing.
        return parseTelegramProcess(elementRoot,telegramStructureMap);
    }

    /**
     * Parses an XML document in the intermediate XML file format to create a list of telegram information that can be searched by telegram name.
     *
     * @param argElementRoot
     * @return
     */
    private Map<String, BlancoRestGeneratorKtTelegramStructure> parseTelegrams(final BlancoXmlElement argElementRoot) {

        Map <String, BlancoRestGeneratorKtTelegramStructure> telegramStructureMap = new HashMap<>();

        // Gets a list of sheets (Excel sheets).
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // Processes each sheet.
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            // Gets detailed information from the sheet.
            final BlancoRestGeneratorKtTelegramStructure telegramStructure = parseTelegramSheet(elementSheet);

            // Stores the telegram information in a map with the telegram ID as the key.
            if (telegramStructure != null && BlancoStringUtil.null2Blank(telegramStructure.getName()).trim().length() > 0) {
                telegramStructureMap.put(telegramStructure.getName(), telegramStructure);
            }
        }
        /**
         *  Checking if Input and Output are paired is done when storing in the TelegramProcessStructure.
         */
        return telegramStructureMap;
    }


    /**
     * Parses an XML document in the intermediate XML file format to obtain telegram information.
     * @param argElementSheet
     * @return
     */
    private BlancoRestGeneratorKtTelegramStructure parseTelegramSheet(final BlancoXmlElement argElementSheet) {

        final BlancoRestGeneratorKtTelegramStructure telegramStructure = new BlancoRestGeneratorKtTelegramStructure();

        // Gets the common information.
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlTelegramCommon());
        if (elementCommon == null) {
            // If there is no common, skips the processing of this sheet.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO COMMON !!!");
            return telegramStructure;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");
        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // If name is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        final String httpMethod = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "telegramMethod");
        if (BlancoStringUtil.null2Blank(httpMethod).trim().isEmpty()) {
            // If httpMethod is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        final String telegramType = BlancoXmlBindingUtil.getTextContent(elementCommon, "type");
        if (BlancoStringUtil.null2Blank(telegramType).trim().length() == 0 || (
            !BlancoRestGeneratorKtUtil.isTelegramStylePlain && BlancoRestGeneratorKtConstants.TELEGRAM_TYPE_ERROR.equals(telegramType)
        )) {
            System.out.println("BlancoRestGeneratorKtXmlParser#parseTelegramSheet !!! Error sheet is skipped !!! " + name);
            return telegramStructure;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXmlParser#parseTelegramSheet name = " + name);
        }

        // create bodyTelegram
        BlancoRestGeneratorKtTelegramStructure bodyTelegram = new BlancoRestGeneratorKtTelegramStructure();

        // First, it sets the Package overwrite options.
        telegramStructure.setPackageSuffix(BlancoRestGeneratorKtUtil.packageSuffix);
        telegramStructure.setOverridePackage(BlancoRestGeneratorKtUtil.overridePackage);
        bodyTelegram.setPackageSuffix(BlancoRestGeneratorKtUtil.packageSuffix);
        bodyTelegram.setOverridePackage(BlancoRestGeneratorKtUtil.overridePackage);
        // There is no location in telegram.

        // TelegramDefinition common
        this.parseTelegramCommon(elementCommon, telegramStructure);
        this.parseTelegramCommon(elementCommon, bodyTelegram);

        /* Check Telegram Method */
        boolean isPostPutRequest = (httpMethod.equalsIgnoreCase("POST") ||
                httpMethod.equalsIgnoreCase("PUT")) && "Input".equals(telegramStructure.getTelegramType());

        // TelegramDefinition inheritance, prefer defined in sheet.
        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlTelegramExtends());
        final BlancoXmlElement elementExtendsRoot = extendsList.isEmpty() ? null : extendsList.get(0);
        if (elementExtendsRoot != null && !BlancoStringUtil.null2Blank(BlancoXmlBindingUtil.getTextContent(elementExtendsRoot, "name")).isEmpty()) {
            parseTelegramExtends(elementExtendsRoot, telegramStructure);
            parseTelegramExtends(elementExtendsRoot, bodyTelegram);
        } else {
            this.parseTelegramExtends(telegramStructure);
            this.parseTelegramExtends(bodyTelegram);
        }

        // TelegramDefinition implementation
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            this.parseTelegramImplements(elementInterfaceRoot, telegramStructure);
            this.parseTelegramImplements(elementInterfaceRoot, bodyTelegram);
        }

        // TelegramDefinition import
        final List<BlancoXmlElement> importList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImport());
        if (importList != null && importList.size() != 0) {
            final BlancoXmlElement elementImportRoot = importList.get(0);
            this.parseTelegramImport(elementImportRoot, telegramStructure);
            this.parseTelegramImport(elementImportRoot, bodyTelegram);
        }

        // Gets the list information.
        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlTeregramList());
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            this.parseTelegramFields(elementListRoot, telegramStructure);
            this.parseTelegramFields(elementListRoot, bodyTelegram);

            if (isPostPutRequest) {
                // remove query params from bodyTelegram
                List<BlancoRestGeneratorKtTelegramFieldStructure> fieldStructureList = bodyTelegram.getListField();
                ArrayList<BlancoRestGeneratorKtTelegramFieldStructure> bodyFields = new ArrayList<>();
                ArrayList<BlancoRestGeneratorKtTelegramFieldStructure> queryPathFields = new ArrayList<>();
                for (BlancoRestGeneratorKtTelegramFieldStructure fieldStructure : fieldStructureList) {
                    if (BlancoStringUtil.null2Blank(fieldStructure.getQueryKind()).trim().isEmpty()) {
                        bodyFields.add(fieldStructure);
                    } else {
                        queryPathFields.add(fieldStructure);
                    }
                }


                boolean hasPrimitivePayload = !BlancoStringUtil.null2Blank(telegramStructure.getPrimitivePayload()).trim().isEmpty();
                boolean arrayPayload = telegramStructure.getArrayPayload();
                boolean optionalPayload = telegramStructure.getOptionalPayload();

                BlancoRestGeneratorKtTelegramFieldStructure bodyFieldStructure = new BlancoRestGeneratorKtTelegramFieldStructure();
                String num = "" + (queryPathFields.size() + 1);
                bodyFieldStructure.setNo(num);
                bodyFieldStructure.setName("argBody");
                if (!bodyFields.isEmpty()) {
                    /* Error on both primitive payload and field list is specified. */
                    if (hasPrimitivePayload) {
                        throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg11(telegramStructure.getName(), telegramStructure.getPrimitivePayload()));
                    }
                    bodyTelegram.setListField(bodyFields);
                    bodyTelegram.setName(bodyTelegram.getName() + "Body");
                    telegramStructure.setBodyTelegram(bodyTelegram);

                    if (arrayPayload || optionalPayload) {
                        String type = bodyTelegram.getName();
                        if (arrayPayload) {
                            bodyFieldStructure.setGeneric(type);
                            type = "List";
                        }
                        if (optionalPayload) {
                            bodyFieldStructure.setNullable(true);
                        }
                        bodyFieldStructure.setType(type);
                        bodyFieldStructure.setDescription(bodyTelegram.getDescription());
                        bodyFieldStructure.setConstArg(true);
                        queryPathFields.add(bodyFieldStructure);
                        telegramStructure.setListField(queryPathFields);
                    }
                } else if (hasPrimitivePayload) {
                    String type = telegramStructure.getPrimitivePayload();
                    String generics = telegramStructure.getPrimitivePayload();

                    if (arrayPayload) {
                        bodyFieldStructure.setGeneric(type);
                        type = "List";
                    }
                    if (optionalPayload) {
                        bodyFieldStructure.setNullable(true);
                    }
                    bodyFieldStructure.setType(type);
                    bodyFieldStructure.setConstArg(true);
                    bodyFieldStructure.setDescription(telegramStructure.getDescription());
                    queryPathFields.add(bodyFieldStructure);
                    telegramStructure.setListField(queryPathFields);
                }
            }
        }
        return telegramStructure;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition common".
     *
     * @param argElementCommon
     * @param argTelegramStructure
     */
    private void parseTelegramCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {

        // Telegram ID
        argTelegramStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // Package
        argTelegramStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));
        // Description
        argTelegramStructure.setDescription(BlancoXmlBindingUtil.getTextContent(argElementCommon, "description"));
        // Telegram type (Input/Output/Error)
        argTelegramStructure.setTelegramType(BlancoXmlBindingUtil.getTextContent(argElementCommon, "type"));
        // HTTP method
        argTelegramStructure.setTelegramMethod(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramMethod"));
        // telegram suffix
        argTelegramStructure.setTelegramSuffix(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramSuffix"));

        // StatusCode
        if (BlancoRestGeneratorKtConstants.TELEGRAM_TYPE_ERROR.equals(argTelegramStructure.getTelegramType())) {
            String statusCode = BlancoXmlBindingUtil.getTextContent(argElementCommon, "statusCode");
            if (BlancoStringUtil.null2Blank(statusCode).trim().length() == 0) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStylePlainStatusCodeRequired(argTelegramStructure.getName()));
            }
            argTelegramStructure.setStatusCode("\"" + statusCode + "\"");
        }

        // 追加パス
        argTelegramStructure.setAdditionalPath(BlancoXmlBindingUtil.getTextContent(argElementCommon, "additionalPath"));
        // パラメータ優先
        argTelegramStructure.setParamPreferred(BlancoXmlBindingUtil.getTextContent(argElementCommon, "paramPreferred"));
        // Pathクエリ書式
        argTelegramStructure.setPathQueryFormat(BlancoXmlBindingUtil.getTextContent(argElementCommon, "pathQueryFormat"));
        // 配列クエリパラメータに[]を付与しない
        argTelegramStructure.setArrayNoBracket("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "arrayNoBracket")));
        // ペイロードに電文の配列が載る
        argTelegramStructure.setArrayPayload("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "arrayPayload")));
        // ペイロードがprimitive
        String phpType = BlancoXmlBindingUtil.getTextContent(argElementCommon, "primitivePayload");
        if (!BlancoStringUtil.null2Blank(phpType).isEmpty()) {
            String kotlinType = parsePhpTypes(phpType, false, argTelegramStructure);
            argTelegramStructure.setPrimitivePayload(kotlinType);
        }
        // ペイロード電文を省略可能
        argTelegramStructure.setOptionalPayload("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "optionalPayload")));

        // basedir
        argTelegramStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(argElementCommon, "basedir"));

        /* Supports class annotation. */
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotationKt");
        if (classAnnotation == null || classAnnotation.length() == 0) {
            classAnnotation = BlancoXmlBindingUtil.getTextContent(
                    argElementCommon, "annotation");
        }
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            argTelegramStructure.setAnnotationList(createAnnotaionList(classAnnotation));
        }
        /* Always adds @Introspected in micronaut. */
        if (BlancoRestGeneratorKtUtil.isServerTypeMicronaut) {
            argTelegramStructure.getAnnotationList().add("Introspected");
            argTelegramStructure.getImportList().add("io.micronaut.core.annotation.Introspected");

            /* Add @Serdeable annotation */
            if (BlancoRestGeneratorKtUtil.isSerdeable) {
                if (this.isVerbose()) {
                    System.out.println("parseTelegramCommon: @Serdeable annotaion is requested for " + argTelegramStructure.getName());
                }
                BlancoRestGeneratorKtUtil.addNewAnnotation(
                        "Serdeable",
                        "Serdeable",
                        "io.micronaut.serde.annotation.Serdeable",
                        argTelegramStructure.getAnnotationList(),
                        argTelegramStructure.getImportList()
                );
            }

            /* Add @JsonIgnoreProperties(ignoreUnknown = true) annotation */
            if (BlancoRestGeneratorKtUtil.isIgnoreUnknown) {
                if (this.isVerbose()) {
                    System.out.println("parseTelegramCommon: ignoreUnknow annotaion is requested for " + argTelegramStructure.getName());
                }
                BlancoRestGeneratorKtUtil.addNewAnnotation(
                        "JsonIgnoreProperties",
                        "JsonIgnoreProperties(ignoreUnknown = true)",
                        "com.fasterxml.jackson.annotation.*",
                        argTelegramStructure.getAnnotationList(),
                        argTelegramStructure.getImportList()
                );
            }
        }
        argTelegramStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));

        argTelegramStructure.setImpleSpoiled("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "impleSpoiled")));

        argTelegramStructure.setPermissionKind(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                "permissionKind"));

        /*
         * The telegram class is always the data class and final class and adjusts the field name.
         */
        argTelegramStructure.setData(true);
        argTelegramStructure.setFinal(true);
        argTelegramStructure.setAdjustFieldName(true);
    }

    private void parseTelegramExtends(
            final BlancoXmlElement argElementExtendsRoot,
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure
    ) {
        String className = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "name");
        if (className != null) {
            String classNameCanon = className;
            String packageName = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "package");
            if (packageName == null) {
                /*
                 * Finds the package name for this class.
                 */
                packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(className);
            }
            if (packageName != null) {
                classNameCanon = packageName + "." + className;
            }
            if (isVerbose()) {
                System.out.println("Telegram Extends : " + classNameCanon);
            }
            argTelegramStructure.setExtends(classNameCanon);
        } else {
            System.out.println("/* Extends Skip */ className is not specified!!!");
        }
    }

    /**
     * In blancoRestGenerator, a telegram always inherits from Api[Get|Post|Put|Delete]Telegram <- ApiTelegram.
     *
     * @param argTelegramStructure
     */
    private void parseTelegramExtends(
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {
        // Null in a method is already checked in common.
        String method = argTelegramStructure.getTelegramMethod().toUpperCase();
        boolean isRequest = "Input".equals(argTelegramStructure.getTelegramType());
        String superClassId = "";
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorKtConstants.DEFAULT_API_GET_REQUESTID :
                    BlancoRestGeneratorKtConstants.DEFAULT_API_GET_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorKtConstants.DEFAULT_API_POST_REQUESTID :
                    BlancoRestGeneratorKtConstants.DEFAULT_API_POST_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorKtConstants.DEFAULT_API_PUT_REQUESTID :
                    BlancoRestGeneratorKtConstants.DEFAULT_API_PUT_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.endsWith(method)) {
            superClassId = isRequest ?
                    BlancoRestGeneratorKtConstants.DEFAULT_API_DELETE_REQUESTID :
                    BlancoRestGeneratorKtConstants.DEFAULT_API_DELETE_RESPONSEID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }
        /*
         * Finds the package name for this class.
         */
        String packageName = null;
        if (this.fTelegramPackage != null && this.fTelegramPackage.length() > 0) {
            packageName = this.fTelegramPackage;
        } else {
            packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(superClassId);
        }
        String superClassIdCanon = superClassId;
        if (packageName != null) {
            superClassIdCanon = packageName + "." + superClassId;
        }
        if (isVerbose()) {
            System.out.println("Extends : " + superClassIdCanon);
        }
        argTelegramStructure.setExtends(superClassIdCanon);
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition implementation".
     *  @param argElementInterfaceRoot
     * @param argTelegramStructure
     */
    private void parseTelegramImplements(
            final BlancoXmlElement argElementInterfaceRoot,
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {

        final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementInterfaceRoot, "interface");
        for (int index = 0;
             listInterfaceChildNodes != null &&
                     index < listInterfaceChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listInterfaceChildNodes
                    .get(index);

            final String interfaceName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (interfaceName == null || interfaceName.trim().length() == 0) {
                continue;
            }
            argTelegramStructure.getImplementsList().add(interfaceName);
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition import".
     * Assumes that the import class name is written in Canonical.
     * @param argElementListRoot
     * @param argTelegramStructure
     */
    private void parseTelegramImport(
            final BlancoXmlElement argElementListRoot,
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {
        final List<BlancoXmlElement> listImportChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementListRoot, "import");
        for (int index = 0;
             listImportChildNodes != null &&
                     index < listImportChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listImportChildNodes
                    .get(index);

            final String importName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (importName == null || importName.trim().length() == 0) {
                continue;
            }
            argTelegramStructure.getImportList().add(
                    BlancoXmlBindingUtil
                            .getTextContent(elementList, "name"));
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition list".
     *  @param argElementListRoot
     * @param argTelegramStructure
     */
    private void parseTelegramFields(
            final BlancoXmlElement argElementListRoot,
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {

        final List<BlancoXmlElement> listChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementListRoot, "field");

        for (int index = 0; index < listChildNodes.size(); index++) {
            final BlancoXmlElement elementList = listChildNodes.get(index);
            final BlancoRestGeneratorKtTelegramFieldStructure fieldStructure = new BlancoRestGeneratorKtTelegramFieldStructure();

            fieldStructure.setNo(BlancoXmlBindingUtil.getTextContent(
                    elementList, "no"));
            fieldStructure.setName(BlancoXmlBindingUtil.getTextContent(
                    elementList, "fieldName"));

//            System.out.println("*** field name = " + fieldStructure.getName());

            if (fieldStructure.getName() == null
                    || fieldStructure.getName().trim().length() == 0) {
//                System.out.println("*** NO NAME SKIP!!! ");
                continue;
            }

            /* if telegramStyle is plain, statusCode is reserved. */
            if (BlancoRestGeneratorKtConstants.TELEGRAM_TYPE_ERROR.equals(argTelegramStructure.getTelegramType()) &&
                BlancoRestGeneratorKtConstants.TELEGRAM_STATUS_CODE.equals(fieldStructure.getName())) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStylePlainStatusCodeReserved(argTelegramStructure.getName()));
            }

            /*
             * Gets the type. Assumes that PHP-like types are defined in the definition document.
             * Changes the type name to Kotlin-style here.
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "fieldType");
            if (BlancoStringUtil.null2Blank(phpType).length() == 0) {
                // Type is required.
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr005(
                        argTelegramStructure.getName(),
                        fieldStructure.getName()
                ));

            }
            String kotlinType = parsePhpTypes(phpType, false, argTelegramStructure);
            fieldStructure.setType(kotlinType);

            /* Supports Generic. */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "fieldGeneric");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String kotlinGeneric = parsePhpTypes(phpGeneric, true, argTelegramStructure);
                fieldStructure.setGeneric(kotlinGeneric);
            }

            /* Supports annnotation. */
            String methodAnnotation = BlancoXmlBindingUtil.getTextContent(elementList, "annotation");
            if (BlancoStringUtil.null2Blank(methodAnnotation).length() != 0) {
                fieldStructure.setAnnotationList(createAnnotaionList(methodAnnotation));
            }

            /*
             * Gets the Kotlin-style type. The type name is assumed to be defined in Kotlin-style.
             */
            String phpTypeKt = BlancoXmlBindingUtil.getTextContent(elementList, "fieldTypeKt");
            String kotlinTypeKt = parsePhpTypes(phpTypeKt, false, argTelegramStructure);
            fieldStructure.setTypeKt(kotlinTypeKt);

            /* Supports Generic in Kotlin. */
            String phpGenericKt = BlancoXmlBindingUtil.getTextContent(elementList, "fieldGenericKt");
            if (BlancoStringUtil.null2Blank(phpGenericKt).length() != 0) {
                String kotlinGenericKt = parsePhpTypes(phpGenericKt, true, argTelegramStructure);
                fieldStructure.setGenericKt(kotlinGenericKt);
            }

            /* Supports annnotation in Kotlin. */
            String methodAnnotationKt = BlancoXmlBindingUtil.getTextContent(elementList, "annotationKt");
            if (BlancoStringUtil.null2Blank(methodAnnotationKt).length() != 0) {
                fieldStructure.setAnnotationList(createAnnotaionList(methodAnnotationKt));
            }

            // Supports required (giving NotNull annotation)
            String requiredKt = BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldRequiredKt");
            String required = BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldRequired");
            if (BlancoStringUtil.null2Blank(requiredKt).length() > 0) {
                if ("true".equals(requiredKt)) {
                    required = requiredKt;
                } else if ("not".equals(requiredKt) &&
                        BlancoStringUtil.null2Blank(required).length() > 0) {
                    required = ""; // If requiredKt is not, ignores required.
                }
            }
            fieldStructure.setRequired("true".equals(required));
            if (fieldStructure.getRequired()) {
                String importNotNull = "javax.validation.constraints.NotNull";
                if (BlancoRestGeneratorKtUtil.isTargetJakartaEE) {
                    importNotNull = "jakarta.validation.constraints.NotNull";
                }
                BlancoRestGeneratorKtUtil.addNewAnnotation(
                        "field:NotNull",
                        "field:NotNull",
                        importNotNull,
                        fieldStructure.getAnnotationList(),
                        argTelegramStructure.getImportList()
                );
            } else if (BlancoRestGeneratorKtUtil.isNullAnnotation) {
                BlancoRestGeneratorKtUtil.addNewAnnotation(
                        "Nullable",
                        "Nullable",
                        "io.micronaut.core.annotation.Nullable",
                        fieldStructure.getAnnotationList(),
                        argTelegramStructure.getImportList()
                );
            }

            // Supports Nullable.
            String nullableKt = BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullableKt");
            String nullable = BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable");
            if (BlancoStringUtil.null2Blank(nullableKt).length() > 0) {
                if ("true".equals(nullableKt)) {
                    nullable = nullableKt;
                } else if ("not".equals(nullableKt) &&
                        BlancoStringUtil.null2Blank(nullable).length() > 0) {
                    nullable = ""; // If nullableKt  is not, ignores nullable.
                }
            }
            fieldStructure.setNullable("true".equals(nullable));

            // Description
            fieldStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldDescription"));
            final String[] lines = BlancoNameUtil.splitString(
                    fieldStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    fieldStructure.setDescription(lines[indexLine]);
                } else {
                    // For a multi-line description, it will be split and stored.
                    // From the second line, assumes that character reference encoding has been properly implemented.
                    fieldStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            // Default
            fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));
            fieldStructure.setDefaultKt(BlancoXmlBindingUtil.getTextContent(
                    elementList, "defaultKt"));
            // Length
            String strMinLength = BlancoXmlBindingUtil
                    .getTextContent(elementList, "minLength");
            if (strMinLength != null) {
                try {
                    int minLength = Integer.parseInt(strMinLength);
                    fieldStructure.setMinLength(minLength);

                } catch (NumberFormatException e) {
                    System.out.println(fBundle.getXml2sourceFileErr008(argTelegramStructure.getName(), fieldStructure.getName()));
                }
            }
            String strMaxLength = BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxLength");
            if (strMaxLength != null) {
                try {
                    int maxLength = Integer.parseInt(strMaxLength);
                    fieldStructure.setMaxLength(maxLength);
                } catch (NumberFormatException e) {

                }
            }

            // Maximum and minimum
            fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "minInclusive"));
            fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxInclusive"));
            // Regular expression
            fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                    elementList, "pattern"));

            /*
             * In the telegram, all fields are constructor arguments.
             */
            fieldStructure.setConstArg(true);

//            /*
//             * Request telegram parameters cannot be cnahged (tentative)
//             */
//            if ("Input".equalsIgnoreCase(argTelegramStructure.getTelegramType())) {
//                fieldStructure.setValue(true);
//            }
            // Supports fixedValue.
            String valueKt = BlancoXmlBindingUtil
                    .getTextContent(elementList, "fixedValueKt");
            String value = BlancoXmlBindingUtil
                    .getTextContent(elementList, "fixedValue");
            if (BlancoStringUtil.null2Blank(valueKt).length() > 0) {
                if ("true".equals(valueKt)) {
                    value = valueKt;
                } else if ("not".equals(valueKt) &&
                        BlancoStringUtil.null2Blank(value).length() > 0) {
                    value = ""; // If valueKt is not, ignores value.
                }
            }
            fieldStructure.setValue("true".equals(value));

            /* Supports override modifiers. */
            fieldStructure.setOverride("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "override")));

            /* alias */
            fieldStructure.setAlias(BlancoXmlBindingUtil.getTextContent(elementList, "alias"));
            /* queryKind */
            fieldStructure.setQueryKind(BlancoXmlBindingUtil.getTextContent(elementList, "queryKind"));
            if (BlancoStringUtil.null2Blank(fieldStructure.getQueryKind()).trim().length() > 0) {
                argTelegramStructure.setHasQueryParams(true);
            }

            argTelegramStructure.getListField().add(fieldStructure);
        }
        /* Set StatusCode for ErrorTelegram if telegram style is plain. */
        if (BlancoRestGeneratorKtConstants.TELEGRAM_TYPE_ERROR.equals(argTelegramStructure.getTelegramType())) {
            final BlancoRestGeneratorKtTelegramFieldStructure fieldStructure = new BlancoRestGeneratorKtTelegramFieldStructure();

            fieldStructure.setNo("0");
            fieldStructure.setName(BlancoRestGeneratorKtConstants.TELEGRAM_STATUS_CODE);
            fieldStructure.setType("kotlin.String");
            fieldStructure.setNullable(false);
            fieldStructure.setConstArg(false);
            fieldStructure.setValue(true);
            String statusCode = argTelegramStructure.getStatusCode();
            if (BlancoStringUtil.null2Blank(statusCode).trim().length() == 0) {
                throw new IllegalArgumentException(fBundle.getBlancorestTelegramStylePlainStatusCodeRequired(argTelegramStructure.getName()));
            }
            fieldStructure.setDefault(statusCode);
            fieldStructure.setDefaultKt(statusCode);
            fieldStructure.setDescription(fBundle.getBlancorestTelegramStylePlainStatusCodeLangdoc());
            argTelegramStructure.getListField().add(fieldStructure);
        }
    }

    /**
     *
     *
     * @param argHeaderElementList
     * @param argImportHeaderList
     * @return
     */
    private List<String> parseHeaderList(final List<BlancoXmlElement> argHeaderElementList, final Map<String, List<String>> argImportHeaderList) {
        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXmlParser#parseHeaderList: Start parseHeaderList.");
        }

        List<String> headerList = new ArrayList<>();

        /*
         * Creates a list of header
         * First, outputs what is written in the definition as it is.
         */
        if (argHeaderElementList != null && argHeaderElementList.size() > 0) {
            final BlancoXmlElement elementHeaderRoot = argHeaderElementList.get(0);
            final List<BlancoXmlElement> listHeaderChildNodes = BlancoXmlBindingUtil
                    .getElementsByTagName(elementHeaderRoot, "header");
            for (int index = 0; index < listHeaderChildNodes.size(); index++) {
                final BlancoXmlElement elementList = listHeaderChildNodes
                        .get(index);

                final String headerName = BlancoXmlBindingUtil
                        .getTextContent(elementList, "name");
                if (this.isVerbose()) {
                    System.out.println("BlancoRestGeneratorKtXmlParser#parseHeaderList header = " + headerName);
                }
                if (headerName == null || headerName.trim().length() == 0) {
                    continue;
                }
                headerList.add(
                        BlancoXmlBindingUtil
                                .getTextContent(elementList, "name"));
            }
        }

        /*
         * Next, outputs the auto-generated one.
         * The current method requires the following assumptions.
         *  * One class definition per file
         *  * Represents directories with Java/kotlin style package notation in the definition sheet
         * TODO: Should it be possible to define the directory where the files are located on the definition sheet?
         */
        if (argImportHeaderList != null && argImportHeaderList.size() > 0) {
            Set<String> fromList = argImportHeaderList.keySet();
            for (String strFrom : fromList) {
                StringBuffer sb = new StringBuffer();
                sb.append("import { ");
                List<String> classNameList = argImportHeaderList.get(strFrom);
                int count = 0;
                for (String className : classNameList) {
                    if (count > 0) {
                        sb.append(", ");
                    }
                    sb.append(className);
                    count++;
                }
                if (count > 0) {
                    sb.append(" } from \"" + strFrom + "\"");
                    if (this.isVerbose()) {
                        System.out.println("BlancoRestGeneratorKtXmlParser#parseHeaderList import = " + sb.toString());
                    }
                    headerList.add(sb.toString());
                }
            }
        }

        return headerList;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get an array of value object information.
     *
     * @param argElementRoot
     *            XML document in the intermediate XML file.
     * @return An array of value object information obtained as a result of parsing.
     */
    public BlancoRestGeneratorKtTelegramProcessStructure[] parseTelegramProcess(
            final BlancoXmlElement argElementRoot,
            final Map <String, BlancoRestGeneratorKtTelegramStructure> argTelegramStructureMap) {

        final List<BlancoRestGeneratorKtTelegramProcessStructure> processStructures = new ArrayList<>();

        // Gets a list of sheets (Excel sheets).
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // Processes each sheet.
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            // Gets detailed information from the sheet.
            final BlancoRestGeneratorKtTelegramProcessStructure structure =
                    parseProcessSheet(elementSheet, argTelegramStructureMap);

            if (structure != null) {
                processStructures.add(structure);
            }
        }

        final BlancoRestGeneratorKtTelegramProcessStructure[] result = new BlancoRestGeneratorKtTelegramProcessStructure[processStructures
                .size()];
        processStructures.toArray(result);
        return result;
    }

    /**
     *
     * @param argElementSheet
     * @param argTelegramStructureMap
     * @return
     */
    private BlancoRestGeneratorKtTelegramProcessStructure parseProcessSheet(
            final BlancoXmlElement argElementSheet,
            final Map <String, BlancoRestGeneratorKtTelegramStructure> argTelegramStructureMap) {
        BlancoRestGeneratorKtTelegramProcessStructure processStructure = new BlancoRestGeneratorKtTelegramProcessStructure();

        // Gets the common information.
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlProcessCommon());
        if (elementCommon == null) {
            // If there is no common, skips the processing of this sheet.
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO COMMON !!!");
            return null;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");

        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // If name is empty, skips the process.
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO NAME !!!");
            return null;
        }

        if (argTelegramStructureMap == null || argTelegramStructureMap.isEmpty()) {
            System.out.println("parseProcessSheet !!! NO TelegramStructureMap for " + name + " !!!");
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXmlParser#parseProcessSheet name = " + name);
        }

        // Sets the Package overwrite options.
        processStructure.setPackageSuffix(BlancoRestGeneratorKtUtil.packageSuffix);
        processStructure.setOverridePackage(BlancoRestGeneratorKtUtil.overridePackage);
        processStructure.setOverrideLocation(BlancoRestGeneratorKtUtil.overrideLocation);

        // TelegramProcessDefinition common
        parseProcessCommon(elementCommon, processStructure);

        // TelegramProcessDefinition inheritance
        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessExtends());
        if (!this.fServerType.equalsIgnoreCase("micronaut") && extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            parseProcessExtends(elementExtendsRoot, processStructure);
        }

        // TelegramProcessDefinition implementation
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            parseProcessImplements(elementInterfaceRoot, processStructure);
        }

        // TelegramProcessDefinition commonInterface
        final List<BlancoXmlElement> commonInterfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessCommointerface());
        if (commonInterfaceList != null && commonInterfaceList.size() != 0) {
            final BlancoXmlElement elementCommonInterfaceRoot = commonInterfaceList.get(0);
            parseProcessCommonInterface(elementCommonInterfaceRoot, processStructure);
        }

        // TelegramProcessDefinition import
        final List<BlancoXmlElement> importList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImport());
        if (importList != null && importList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = importList.get(0);
            parseProcessImport(elementInterfaceRoot, processStructure);
        }

        final List<BlancoXmlElement> getRequestBindList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessGetRequestBind());
        if (getRequestBindList != null && getRequestBindList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = getRequestBindList.get(0);
            parseProcessGetRequestBind(elementInterfaceRoot, processStructure);
        }

        /*
         * Determines the telegram ID from telegram process ID, and sets only the defined one to processStructure.
         * The telegram ID is determined by the following rule.
         * <Telegram process ID> + <Method> + <Request|Response>
         */
        if (!this.linkTelegramToProcess(processStructure.getName(), argTelegramStructureMap, processStructure)) {
            /* The telegram is undefined or In and Out are not aligned. */
            System.out.println("!!! Invalid Telegram !!! for " + processStructure.getName());
            return null;
        }

        return processStructure;
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition common".
     *
     * @param argElementCommon
     * @param argProcessStructure
     */
    private void parseProcessCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {


        // Telegram process ID
        argProcessStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // Description
        argProcessStructure.setDescription(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "description"));
        if (BlancoStringUtil.null2Blank(argProcessStructure.getDescription())
                .length() > 0) {
            final String[] lines = BlancoNameUtil.splitString(argProcessStructure
                    .getDescription(), '\n');
            for (int index = 0; index < lines.length; index++) {
                if (index == 0) {
                    argProcessStructure.setDescription(lines[index]);
                }
            }
        }

        // Web service ID
        argProcessStructure.setServiceId(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "webServiceId"));

        // Location
        argProcessStructure.setLocation(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "location"));

        // Package
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));

        // Base directory
        argProcessStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "basedir"));

        // Implementation directory
        argProcessStructure.setImpleDirKt(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "impledirKt"));

        // Annotation
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotationKt");
        if (classAnnotation == null || classAnnotation.length() == 0) {
            classAnnotation = BlancoXmlBindingUtil.getTextContent(
                    argElementCommon, "annotation");
        }
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            argProcessStructure.setAnnotationList(createAnnotaionList(classAnnotation));
        }

        // Request header class information
        String requestHeaderClass = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "requestHeaderClass");
        argProcessStructure.setRequestHeaderClass(BlancoStringUtil.null2Blank(requestHeaderClass));
        // Response header class information
        String responseHeaderClass = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "responseHeaderClass");
        argProcessStructure.setResponseHeaderClass(BlancoStringUtil.null2Blank(responseHeaderClass));

        // Meta ID list
        String metaIds = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "metaIdList");
        if (BlancoStringUtil.null2Blank(metaIds).length() > 0) {
            String[] metaIdArray = metaIds.split(",");
            List<String> metaIdList = new ArrayList<>(Arrays.asList(metaIdArray));
            for (String metaId : metaIdList) {
                argProcessStructure.getMetaIdList().add(metaId.trim());
            }
        }

        // clieantAnnotation
        String clientAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "clientAnnotation");
        if (BlancoStringUtil.null2Blank(BlancoRestGeneratorKtUtil.overrideClientAnnotation).length() > 0) {
            clientAnnotation = BlancoRestGeneratorKtUtil.overrideClientAnnotation;
        } else if (BlancoStringUtil.null2Blank(clientAnnotation).length() == 0 &&
                BlancoStringUtil.null2Blank(BlancoRestGeneratorKtUtil.clientAnnotation).length() > 0) {
            clientAnnotation = BlancoRestGeneratorKtUtil.clientAnnotation;
        }
        if (BlancoStringUtil.null2Blank(clientAnnotation).length() > 0) {
            argProcessStructure.setClientAnnotationList(createAnnotaionList(clientAnnotation));
        }

        // API that do not require authentication
        argProcessStructure.setNoAuthentication("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "noAuthentication")));
        // API that do not require supplementary authentication.
        argProcessStructure.setNoAuxiliaryAuthentication("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "noAuxiliaryAuthentication")));

        // Auto-generation of import statements
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition inheritance".
     * @param argElementExtendsRoot
     * @param argProcessStructure
     */
    private void parseProcessExtends(
            final BlancoXmlElement argElementExtendsRoot,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {

        String className = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "name");
        if (className != null) {
            String classNameCanon = className;
            String packageName = BlancoXmlBindingUtil.getTextContent(argElementExtendsRoot, "package");
            if (packageName == null) {
                /*
                 * Finds the package name for this class.
                 */
                packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(className);
            }
            if (packageName != null) {
                classNameCanon = packageName + "." + className;
            }
            if (isVerbose()) {
                System.out.println("Extends : " + classNameCanon);
            }
            argProcessStructure.setExtends(classNameCanon);
        } else {
            System.out.println("/* Extends Skip */ className is not specified!!!");
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition implementation".
     * @param argElementInterfaceRoot
     * @param argProcessStructure
     */
    private void parseProcessImplements(
            final BlancoXmlElement argElementInterfaceRoot,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {

        final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementInterfaceRoot, "interface");
        for (int index = 0;
             listInterfaceChildNodes != null &&
                     index < listInterfaceChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listInterfaceChildNodes
                    .get(index);

            final String interfaceName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (interfaceName == null || interfaceName.trim().length() == 0) {
                continue;
            }

            /*
             * Creates import information
             */
            String fqInterface = interfaceName;
            if (argProcessStructure.getCreateImportList()) {
                String packageName = BlancoRestGeneratorKtUtil.getPackageName(interfaceName);
                String className = BlancoRestGeneratorKtUtil.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * Finds the package name for this class.
                     */
                    packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(className);
                    if (packageName != null & packageName.length() > 0) {
                        fqInterface = packageName + "." + className;
                    }
                }
            }
            argProcessStructure.getImplementsList().add(fqInterface);
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition commonInterface".
     * @param argElementCommonInterfaceRoot
     * @param argProcessStructure
     */
    private void parseProcessCommonInterface(
            final BlancoXmlElement argElementCommonInterfaceRoot,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {

        final List<BlancoXmlElement> listInterfaceChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementCommonInterfaceRoot, "interface");
        for (int index = 0;
             listInterfaceChildNodes != null &&
                     index < listInterfaceChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listInterfaceChildNodes
                    .get(index);

            final String interfaceName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (interfaceName == null || interfaceName.trim().length() == 0) {
                continue;
            }

            /*
             * Creates commonInterface information
             */
            String fqInterface = interfaceName;
            String packageName = BlancoRestGeneratorKtUtil.getPackageName(interfaceName);
            String className = BlancoRestGeneratorKtUtil.getSimpleClassName(interfaceName);
            if (packageName.length() == 0) {
                /*
                 * Finds the package name for this class.
                 */
                packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(className);
                if (packageName != null & packageName.length() > 0) {
                    fqInterface = packageName + "." + className;
                }
            }
            System.out.println("@@@ CommonInterface @@@ : " + fqInterface);
            argProcessStructure.getCommonInterfaceList().add(fqInterface);
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition import".
     * Assumes that the import class name is written in Canonical.
     * @param argElementListRoot
     * @param argProcessStructure
     */
    private void parseProcessImport(
            final BlancoXmlElement argElementListRoot,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {
        final List<BlancoXmlElement> listImportChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementListRoot, "import");
        for (int index = 0;
             listImportChildNodes != null &&
                     index < listImportChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listImportChildNodes
                    .get(index);

            final String importName = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (importName == null || importName.trim().length() == 0) {
                continue;
            }
            argProcessStructure.getImportList().add(
                    BlancoXmlBindingUtil
                            .getTextContent(elementList, "name"));
        }
    }

    /**
     * Parses an XML document in the form of an  intermediate XML file to get "TelegramDefinition get request".
     * @param argElementListRoot
     * @param argProcessStructure
     */
    private void parseProcessGetRequestBind(
            final BlancoXmlElement argElementListRoot,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {
        final List<BlancoXmlElement> listGetRequestBindChildNodes = BlancoXmlBindingUtil
                .getElementsByTagName(argElementListRoot, "get-request-bind");
        for (int index = 0;
             listGetRequestBindChildNodes != null &&
                     index < listGetRequestBindChildNodes.size();
             index++) {
            final BlancoXmlElement elementList = listGetRequestBindChildNodes
                    .get(index);

            final String no = BlancoXmlBindingUtil
                    .getTextContent(elementList, "no");

            final String name = BlancoXmlBindingUtil
                    .getTextContent(elementList, "name");
            if (name == null || name.trim().length() == 0) {
                continue;
            }

            final String kind = BlancoXmlBindingUtil
                    .getTextContent(elementList, "kind");
            if (kind == null || kind.trim().length() == 0) {
                continue;
            }

            BlancoRestGeneratorKtGetRequestBindStructure structure = new BlancoRestGeneratorKtGetRequestBindStructure();
            structure.setNumber(Integer.parseInt(no));
            structure.setName(name);
            structure.setKind(kind);

            argProcessStructure.getGetRequestBindList().add(structure);
        }
    }

    /**
     * Determines the telegram ID from telegram process ID, and sets only the defined one to processStructure.
     * The telegram ID is determined by the following rule.
     * <Telegram process ID> + <Method> + <Request|Response>
     *
     * @param argProcessId
     * @param argTelegramStructureMap
     * @param argProcessStructure
     * @return
     */
    private boolean linkTelegramToProcess(
            final String argProcessId,
            final Map<String, BlancoRestGeneratorKtTelegramStructure> argTelegramStructureMap,
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure
    ) {
        boolean found = false;

        Map<String, String> httpMethods = new HashMap<>();
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_GET, "Get");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_POST, "Post");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT, "Put");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE, "Delete");
        Map<String, String> telegramKind = new HashMap<>();
        telegramKind.put(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT, "Request");
        telegramKind.put(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT, "Response");

        Set<String> methodKeys = httpMethods.keySet();
        for (String methodKey : methodKeys) {
            String method = httpMethods.get(methodKey);
            Set<String> kindKeys = telegramKind.keySet();
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> telegrams = new HashMap<>();
            for (String kindKey : kindKeys) {
                String kind = telegramKind.get(kindKey);
                String telegramId = argProcessId + method + kind;

                BlancoRestGeneratorKtTelegramStructure telegramStructure =
                        argTelegramStructureMap.get(telegramId);
                if (telegramStructure != null) {
                    telegrams.put(kindKey, telegramStructure);
                    /* Check primitive body request exists or not */
                    if (!telegramStructure.getPrimitivePayload().isEmpty()) {
                        argProcessStructure.setHasPrimitiveRequest(true);
                    }
                    /* Check array body request exists or not */
                    if (!telegramStructure.getArrayPayload()) {
                        argProcessStructure.setHasArrayRequest(true);
                    }
                }
            }

            if (argProcessStructure.getCreateImportList() && this.isCreateServiceMethod()) {
                /*
                 * Creates import information for the default telegram class.
                 */
                // obsolete?
//                // Request
//                String defaultTelegramId = BlancoRestGeneratorKtUtil.getDefaultRequestTelegramId(method);
//                String defaultTelegramPackage = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(defaultTelegramId);
//                // Response
//                defaultTelegramId = BlancoRestGeneratorKtUtil.getDefaultResponseTelegramId(method);
//                defaultTelegramPackage = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(defaultTelegramId);
            }

            if (telegrams.size() == 0) {
                continue;
            }
            if (telegrams.size() != 2) {
                /* In and Out are not aligned. */
                return false;
            }
            argProcessStructure.getListTelegrams().put(methodKey, telegrams);
            found = true;

            /* Search Error telegrams on telegramType is plain. */
            if (BlancoRestGeneratorKtUtil.isTelegramStylePlain) {
                String telegramIdPrefix = argProcessId + method + "Error";
                List<BlancoRestGeneratorKtTelegramStructure> listErrors = BlancoRestGeneratorKtUtil.searchTelegramsStartWith(telegramIdPrefix, argTelegramStructureMap);
                if (listErrors.size() > 0) {
                    argProcessStructure.getErrorTelegrams().put(methodKey, listErrors);
                }
            }
        }

        return found;
    }

    /**
     * Creates an Annotation list.
     * @param annotations
     * @return
     */
    private List<String> createAnnotaionList(String annotations) {
        List<String> annotationList = new ArrayList<>();
        final String[] lines = BlancoNameUtil.splitString(annotations, '\n');
        StringBuffer sb = new StringBuffer();
        for (String line : lines) {
            if (line.startsWith("@")) {
                if (sb.length() > 0) {
                    annotationList.add(sb.toString());
                    sb = new StringBuffer();
                }
                line = line.substring(1);
            }
            sb.append(line + System.getProperty("line.separator", "\n"));
        }
        if (sb.length() > 0) {
            annotationList.add(sb.toString());
        }
        if (this.isVerbose()) {
            for (String ann : annotationList) {
                System.out.println("Ann: " + ann);
            }
        }
        return annotationList;
    }

    /**
     * Replaces PHP-style type definitions with Kotlin-style ones.
     * @param phpType
     * @param isGeneric
     * @param argTelegramStructure
     * @return
     */
    private String parsePhpTypes(String phpType, boolean isGeneric, BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {
        String kotlinType = phpType;
        if (BlancoStringUtil.null2Blank(phpType).length() != 0) {
            if ("boolean".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Boolean";
            } else
            if ("integer".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Long";
            } else
            if ("double".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Double";
            } else
            if ("float".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Double";
            } else
            if ("string".equalsIgnoreCase(phpType) ||
                    "java.lang.string".equalsIgnoreCase(phpType)
            ) {
                kotlinType = "kotlin.String";
            } else
            if ("datetime".equalsIgnoreCase(phpType)) {
                kotlinType = "java.util.Date";
            } else
            if ("array".equalsIgnoreCase(phpType) ||
                    "arraylist".equalsIgnoreCase(phpType) ||
                    "java.util.arraylist".equalsIgnoreCase(phpType)
            ) {
                if (isGeneric) {
                    throw new IllegalArgumentException("Cannot use array for Generics.");
                } else {
                    kotlinType = "kotlin.collections.ArrayList";
                }
            } else
            if ("object".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Any";
            } else {
                /* Finds a package with this name. */
                String packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(phpType);
                if (packageName != null) {
                    kotlinType = packageName + "." + phpType;
                    argTelegramStructure.getImportList().add(kotlinType);
                }

                /* Others are written as is. */
                if (this.isVerbose()) {
                    System.out.println("blancoRestGeneratorKt : Unknown PHP type: " + kotlinType);
                }
            }
        }
        return kotlinType;
    }
}
