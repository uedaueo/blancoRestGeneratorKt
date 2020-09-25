package blanco.restgeneratorkt;

import blanco.commons.util.BlancoNameUtil;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtClassStructure;
import blanco.xml.bind.BlancoXmlBindingUtil;
import blanco.xml.bind.BlancoXmlUnmarshaller;
import blanco.xml.bind.valueobject.BlancoXmlDocument;
import blanco.xml.bind.valueobject.BlancoXmlElement;

import java.io.File;
import java.util.*;

public class BlancoRestGeneratorKtXmlParser {

    /**
     * このプロダクトのリソースバンドルへのアクセスオブジェクト。
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
     * 中間XMLファイルのXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argMetaXmlSourceFile
     *            中間XMLファイル。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoRestGeneratorKtTelegramProcessStructure[] parse(
            final File argMetaXmlSourceFile) {
        final BlancoXmlDocument documentMeta = new BlancoXmlUnmarshaller()
                .unmarshal(argMetaXmlSourceFile);
        if (documentMeta == null) {
            System.out.println("Fail to unmarshal XML.");
            return null;
        }

        // ルートエレメントを取得します。
        final BlancoXmlElement elementRoot = BlancoXmlBindingUtil
                .getDocumentElement(documentMeta);
        if (elementRoot == null) {
            // ルートエレメントが無い場合には処理中断します。
            if (this.isVerbose()) {
                System.out.println("praser !!! NO ROOT ELEMENT !!!");
            }
            return null;
        }

        if (this.isVerbose()) {
            System.out.println("[" + argMetaXmlSourceFile.getName() + "の処理を開始します]");
        }

        // まず電文の一覧を取得します。
        Map<String, BlancoRestGeneratorKtTelegramStructure> telegramStructureMap =
                parseTelegrams(elementRoot);

        if (telegramStructureMap.isEmpty()) {
            if (this.isVerbose()) {
                System.out.println("praser !!! NO telegramStructureMap !!!");
            }
            return null;
        }

        // 次に電文処理を取得します。
        return parseTelegramProcess(elementRoot,telegramStructureMap);
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、電文名で検索可能な電文情報の一覧を作成します。
     *
     * @param argElementRoot
     * @return
     */
    private Map<String, BlancoRestGeneratorKtTelegramStructure> parseTelegrams(final BlancoXmlElement argElementRoot) {

        Map <String, BlancoRestGeneratorKtTelegramStructure> telegramStructureMap = new HashMap<>();

        // sheet(Excelシート)のリストを取得します。
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // おのおののシートを処理します。
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            // シートから詳細な情報を取得します。
            final BlancoRestGeneratorKtTelegramStructure telegramStructure = parseTelegramSheet(elementSheet);

            // 電文情報を電文IDをキーにMapに格納する
            if (telegramStructure != null) {
                telegramStructureMap.put(telegramStructure.getName(), telegramStructure);
            }
        }
        /**
         *  InputとOutputが対になっているかのチェックは
         *  TelegramProcessStructureへの格納時にを行う
         */
        return telegramStructureMap;
    }


    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、電文情報を取得します。
     * @param argElementSheet
     * @return
     */
    private BlancoRestGeneratorKtTelegramStructure parseTelegramSheet(final BlancoXmlElement argElementSheet) {

        final BlancoRestGeneratorKtTelegramStructure telegramStructure = new BlancoRestGeneratorKtTelegramStructure();

        // 共通情報を取得します。
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlTelegramCommon());
        if (elementCommon == null) {
            // commonが無い場合には、このシートの処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO COMMON !!!");
            return telegramStructure;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");
        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // nameが空の場合には処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        final String httpMethod = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "telegramMethod");
        if (BlancoStringUtil.null2Blank(httpMethod).trim().length() == 0) {
            // httpMethodが空の場合には処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#process !!! NO NAME !!!");
            return telegramStructure;
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXmlParser#parseTelegramSheet name = " + name);
        }

        // はじめにPackage上書き系オプションを設定します。
        telegramStructure.setPackageSuffix(BlancoRestGeneratorKtUtil.packageSuffix);
        telegramStructure.setOverridePackage(BlancoRestGeneratorKtUtil.overridePackage);
        // telegram には location はありません。

        // 電文定義・共通
        this.parseTelegramCommon(elementCommon, telegramStructure);

        // 電文定義・継承
        this.parseTelegramExtends(telegramStructure);

        // 電文定義・実装
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            this.parseTelegramImplements(elementInterfaceRoot, telegramStructure);
        }

        // 電文定義・インポート
        final List<BlancoXmlElement> importList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet,
                        fBundle.getMeta2xmlTelegramImport());
        if (importList != null && importList.size() != 0) {
            final BlancoXmlElement elementImportRoot = importList.get(0);
            this.parseTelegramImport(elementImportRoot, telegramStructure);
        }

        // 一覧情報を取得します。
        final List<BlancoXmlElement> listList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlTeregramList());
        if (listList != null && listList.size() != 0) {
            final BlancoXmlElement elementListRoot = listList.get(0);
            this.parseTelegramFields(elementListRoot, telegramStructure);
        }

        return telegramStructure;
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・共通」を取得します。
     *
     * @param argElementCommon
     * @param argTelegramStructure
     */
    private void parseTelegramCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {

        // 電文ID
        argTelegramStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // パッケージ
        argTelegramStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));
        // 説明
        argTelegramStructure.setDescription(BlancoXmlBindingUtil.getTextContent(argElementCommon, "description"));
        // 電文種類 Input/Output
        argTelegramStructure.setTelegramType(BlancoXmlBindingUtil.getTextContent(argElementCommon, "type"));
        // HTTP メソッド
        argTelegramStructure.setTelegramMethod(BlancoXmlBindingUtil.getTextContent(argElementCommon, "telegramMethod"));
        argTelegramStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(argElementCommon, "basedir"));

        /* クラスの annotation に対応 */
        String classAnnotation = "";
        BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            argTelegramStructure.setAnnotationList(createAnnotaionList(classAnnotation));
        }
        /* micronaut では必ず @Introspected をつける */
        if ("micronaut".equalsIgnoreCase(this.getServerType())) {
            argTelegramStructure.getAnnotationList().add("Introspected");
            argTelegramStructure.getImportList().add("io.micronaut.core.annotation.Introspected");
        }

        argTelegramStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));

        /*
         * 電文クラスは常に
         *  dataクラス
         *  finalクラス
         *  フィールド名の変形
         * を行う
         */
        argTelegramStructure.setData(true);
        argTelegramStructure.setFinal(true);
        argTelegramStructure.setAdjustFieldName(true);
    }

    /**
     * blancoRestGenerator では、電文は常に
     * Api[Get|Post|Put|Delete]Telegram <- ApiTelegram
     * を継承します。
     *
     * @param argTelegramStructure
     */
    private void parseTelegramExtends(
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {

        // method の NULL check は common で済み
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
         * このクラスのパッケージ名を探す
         */
        String packageName = null;
        if (this.fTelegramPackage != null && this.fTelegramPackage.length() > 0) {
            packageName = this.fTelegramPackage;
        } else {
            BlancoValueObjectKtClassStructure voStructure = BlancoRestGeneratorKtUtil.objects.get(superClassId);
            if (voStructure != null) {
                packageName = voStructure.getPackage();
            }
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
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・実装」を取得します。
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
            /*
             * import 情報の作成
             */
            String fqInterface = interfaceName;
            if (argTelegramStructure.getCreateImportList()) {
                String packageName = BlancoRestGeneratorKtUtil.getPackageName(interfaceName);
                String className = BlancoRestGeneratorKtUtil.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * このクラスのパッケージ名を探す
                     */
                    BlancoValueObjectKtClassStructure voStructure = BlancoRestGeneratorKtUtil.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                        if (packageName != null & packageName.length() > 0) {
                            fqInterface = packageName + "." + className;
                        }
                    }
                }

                argTelegramStructure.getImplementsList().add(fqInterface);
            }
        }
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・インポート」を取得します。
     * インポートクラス名はCanonicalで記述されている前提です。
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
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・一覧」を取得します。
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

            /*
             * 型の取得。定義書にはphp風な型が定義されている前提。
             * ここで Kotlin 風の型名に変えておく
             */
            String phpType = BlancoXmlBindingUtil.getTextContent(elementList, "fieldType");
            if (BlancoStringUtil.null2Blank(phpType).length() == 0) {
                // 型は必須
                throw new IllegalArgumentException(fBundle.getXml2sourceFileErr005(
                        argTelegramStructure.getName(),
                        fieldStructure.getName()
                ));

            }
            String kotlinType = parsePhpTypes(phpType, false, argTelegramStructure);
            fieldStructure.setType(kotlinType);

            /* Generic に対応 */
            String phpGeneric = BlancoXmlBindingUtil.getTextContent(elementList, "fieldGeneric");
            if (BlancoStringUtil.null2Blank(phpGeneric).length() != 0) {
                String kotlinGeneric = parsePhpTypes(phpGeneric, true, argTelegramStructure);
                fieldStructure.setGeneric(kotlinGeneric);
            }

            /* annnotation に対応 */
            String methodAnnotation = BlancoXmlBindingUtil.getTextContent(elementList, "annotation");
            if (BlancoStringUtil.null2Blank(methodAnnotation).length() != 0) {
                fieldStructure.setAnnotationList(createAnnotaionList(methodAnnotation));
            }

            /*
             * Kotlin型の取得．型名は Kotlin 風に定義されている前提。
             */
            String phpTypeKt = BlancoXmlBindingUtil.getTextContent(elementList, "fieldTypeKt");
            String kotlinTypeKt = parsePhpTypes(phpTypeKt, false, argTelegramStructure);
            fieldStructure.setTypeKt(kotlinTypeKt);

            /* Kotlin の Generic に対応 */
            String phpGenericKt = BlancoXmlBindingUtil.getTextContent(elementList, "fieldGenericKt");
            if (BlancoStringUtil.null2Blank(phpGenericKt).length() != 0) {
                String kotlinGenericKt = parsePhpTypes(phpGenericKt, true, argTelegramStructure);
                fieldStructure.setGenericKt(kotlinGenericKt);
            }

            /* Kotlin の annnotation に対応 */
            String methodAnnotationKt = BlancoXmlBindingUtil.getTextContent(elementList, "annotationKt");
            if (BlancoStringUtil.null2Blank(methodAnnotationKt).length() != 0) {
                fieldStructure.setAnnotationListKt(createAnnotaionList(methodAnnotationKt));
            }

            // Nullable に対応
            fieldStructure.setNullable("true".equals(BlancoXmlBindingUtil
                    .getTextContent(elementList, "nullable")));
            // 説明
            fieldStructure.setDescription(BlancoXmlBindingUtil
                    .getTextContent(elementList, "fieldDescription"));
            final String[] lines = BlancoNameUtil.splitString(
                    fieldStructure.getDescription(), '\n');
            for (int indexLine = 0; indexLine < lines.length; indexLine++) {
                if (indexLine == 0) {
                    fieldStructure.setDescription(lines[indexLine]);
                } else {
                    // 複数行の description については、これを分割して格納します。
                    // ２行目からは、適切に文字参照エンコーディングが実施されているものと仮定します。
                    fieldStructure.getDescriptionList().add(
                            lines[indexLine]);
                }
            }

            // デフォルト
            fieldStructure.setDefault(BlancoXmlBindingUtil.getTextContent(
                    elementList, "default"));
            fieldStructure.setDefaultKt(BlancoXmlBindingUtil.getTextContent(
                    elementList, "defaultKt"));
            // 長さ
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

            // 最大最小
            fieldStructure.setMinInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "minInclusive"));
            fieldStructure.setMaxInclusive(BlancoXmlBindingUtil
                    .getTextContent(elementList, "maxInclusive"));
            // 正規表現
            fieldStructure.setPattern(BlancoXmlBindingUtil.getTextContent(
                    elementList, "pattern"));

            /*
             * 電文では全てのフィールドがコンストラクタ引数
             */
            fieldStructure.setConstArg(true);

            /*
             * 要求電文パラメータは変更不可（暫定）
             */
            if ("Input".equalsIgnoreCase(argTelegramStructure.getTelegramType())) {
                fieldStructure.setValue(true);
            }

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
         * header の一覧作成
         * まず、定義書に書かれたものをそのまま出力します。
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
         * 次に、自動生成されたものを出力します。
         * 現在の方式だと、以下の前提が必要。
         *  * 1ファイルに1クラスの定義
         *  * 定義シートでは Java/kotlin 式の package 表記でディレクトリを表現
         * TODO: 定義シート上にファイルの配置ディレクトリを定義できるようにすべし？
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
     * 中間XMLファイル形式のXMLドキュメントをパースして、バリューオブジェクト情報の配列を取得します。
     *
     * @param argElementRoot
     *            中間XMLファイルのXMLドキュメント。
     * @return パースの結果得られたバリューオブジェクト情報の配列。
     */
    public BlancoRestGeneratorKtTelegramProcessStructure[] parseTelegramProcess(
            final BlancoXmlElement argElementRoot,
            final Map <String, BlancoRestGeneratorKtTelegramStructure> argTelegramStructureMap) {

        final List<BlancoRestGeneratorKtTelegramProcessStructure> processStructures = new ArrayList<>();

        // sheet(Excelシート)のリストを取得します。
        final List<BlancoXmlElement> listSheet = BlancoXmlBindingUtil
                .getElementsByTagName(argElementRoot, "sheet");
        final int sizeListSheet = listSheet.size();
        for (int index = 0; index < sizeListSheet; index++) {
            // おのおののシートを処理します。
            final BlancoXmlElement elementSheet = (BlancoXmlElement) listSheet
                    .get(index);

            // シートから詳細な情報を取得します。
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

        // 共通情報を取得します。
        final BlancoXmlElement elementCommon = BlancoXmlBindingUtil
                .getElement(argElementSheet, fBundle
                        .getMeta2xmlProcessCommon());
        if (elementCommon == null) {
            // commonが無い場合には、このシートの処理をスキップします。
            // System.out.println("BlancoRestXmlSourceFile#processTelegramProcess !!! NO COMMON !!!");
            return null;
        }

        final String name = BlancoXmlBindingUtil.getTextContent(
                elementCommon, "name");

        if (BlancoStringUtil.null2Blank(name).trim().length() == 0) {
            // nameが空の場合には処理をスキップします。
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

        // パッケージ上書き系の設定をします。
        processStructure.setPackageSuffix(BlancoRestGeneratorKtUtil.packageSuffix);
        processStructure.setOverridePackage(BlancoRestGeneratorKtUtil.overridePackage);
        processStructure.setOverrideLocation(BlancoRestGeneratorKtUtil.overrideLocation);

        // 電文処理定義・共通
        parseProcessCommon(elementCommon, processStructure);

        // 電文処理定義・継承
        final List<BlancoXmlElement> extendsList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessExtends());
        if (!this.fServerType.equalsIgnoreCase("micronaut") && extendsList != null && extendsList.size() != 0) {
            final BlancoXmlElement elementExtendsRoot = extendsList.get(0);
            parseProcessExtends(elementExtendsRoot, processStructure);
        }

        // 電文処理定義・実装
        final List<BlancoXmlElement> interfaceList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImplements());
        if (interfaceList != null && interfaceList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = interfaceList.get(0);
            parseProcessImplements(elementInterfaceRoot, processStructure);
        }

        // 電文処理定義・インポート
        final List<BlancoXmlElement> importList = BlancoXmlBindingUtil
                .getElementsByTagName(argElementSheet, fBundle.getMeta2xmlProcessImport());
        if (importList != null && importList.size() != 0) {
            final BlancoXmlElement elementInterfaceRoot = importList.get(0);
            parseProcessImport(elementInterfaceRoot, processStructure);
        }

        /*
         * 電文書IDから電文IDを決定し、定義されているもののみをprocessStructureに設定します。
         * 電文IDは以下のルールで決定されます。
         * <電文処理ID> + <Method> + <Request|Response>
         */
        if (!this.linkTelegramToProcess(processStructure.getName(), argTelegramStructureMap, processStructure)) {
            /* 電文が未定義またはInとOutが揃っていない */
            System.out.println("!!! Invalid Telegram !!! for " + processStructure.getName());
            return null;
        }

        return processStructure;
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・共通」を取得します。
     *
     * @param argElementCommon
     * @param argProcessStructure
     */
    private void parseProcessCommon(final BlancoXmlElement argElementCommon, final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure) {


        // 電文処理ID
        argProcessStructure.setName(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "name"));
        // 説明
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

        // WebサービスID
        argProcessStructure.setServiceId(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "webServiceId"));

        // ロケーション
        argProcessStructure.setLocation(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "location"));

        // パッケージ
        argProcessStructure.setPackage(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "package"));

        // 配置ディレクトリ
        argProcessStructure.setBasedir(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "basedir"));

        // 実装ディレクトリ
        argProcessStructure.setImpleDirKt(BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "impledirKt"));

        // アノテーション
        String classAnnotation = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "annotation");
        if (BlancoStringUtil.null2Blank(classAnnotation).length() > 0) {
            argProcessStructure.setAnnotationList(createAnnotaionList(classAnnotation));
        }

        // リクエストヘッダクラス情報
        String requestHeaderClass = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "requestHeaderClass");
        argProcessStructure.setRequestHeaderClass(BlancoStringUtil.null2Blank(requestHeaderClass));
        // レスポンスヘッダクラス情報
        String responseHeaderClass = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "responseHeaderClass");
        argProcessStructure.setResponseHeaderClass(BlancoStringUtil.null2Blank(responseHeaderClass));

        // メタIDリスト
        String metaIds = BlancoXmlBindingUtil.getTextContent(
                argElementCommon, "metaIdList");
        if (BlancoStringUtil.null2Blank(metaIds).length() > 0) {
            String[] metaIdArray = metaIds.split(",");
            List<String> metaIdList = new ArrayList<>(Arrays.asList(metaIdArray));
            for (String metaId : metaIdList) {
                argProcessStructure.getMetaIdList().add(metaId.trim());
            }
        }

        // 認証が不要なAPI
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "noAuthentication")));

        // import 文の自動生成
        argProcessStructure.setCreateImportList("true"
                .equals(BlancoXmlBindingUtil.getTextContent(argElementCommon,
                        "createImportList")));
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・継承」を取得します。
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
                 * このクラスのパッケージ名を探す
                 */
                BlancoValueObjectKtClassStructure voStructure = BlancoRestGeneratorKtUtil.objects.get(className);
                if (voStructure != null) {
                    packageName = voStructure.getPackage();
                }
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
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文定義・実装」を取得します。
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
             * import 情報の作成
             */
            String fqInterface = interfaceName;
            if (argProcessStructure.getCreateImportList()) {
                String packageName = BlancoRestGeneratorKtUtil.getPackageName(interfaceName);
                String className = BlancoRestGeneratorKtUtil.getSimpleClassName(interfaceName);
                if (packageName.length() == 0) {
                    /*
                     * このクラスのパッケージ名を探す
                     */
                    BlancoValueObjectKtClassStructure voStructure = BlancoRestGeneratorKtUtil.objects.get(className);
                    if (voStructure != null) {
                        packageName = voStructure.getPackage();
                        if (packageName != null & packageName.length() > 0) {
                            fqInterface = packageName + "." + className;
                        }
                    }
                }
            }
            argProcessStructure.getImplementsList().add(fqInterface);
        }
    }

    /**
     * 中間XMLファイル形式のXMLドキュメントをパースして、「電文処理定義・インポート」を取得します。
     * インポートクラス名はCanonicalで記述されている前提です。
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
     * 電文書IDから電文IDを決定し、定義されているもののみをprocessStructureに設定します。
     * 電文IDは以下のルールで決定されます。
     * <電文処理ID> + <Method> + <Request|Response>
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
                }
            }

            if (argProcessStructure.getCreateImportList() && this.isCreateServiceMethod()) {
                /*
                 * デフォルト電文クラスのimport情報を生成する
                 */
                // 要求
                String defaultTelegramId = BlancoRestGeneratorKtUtil.getDefaultRequestTelegramId(method);
                String defaultTelegramPackage = null;
                BlancoValueObjectKtClassStructure voStructure = BlancoRestGeneratorKtUtil.objects.get(defaultTelegramId);
                if (voStructure != null) {
                    defaultTelegramPackage = voStructure.getPackage();
                }
                // 応答
                defaultTelegramId = BlancoRestGeneratorKtUtil.getDefaultResponseTelegramId(method);
                defaultTelegramPackage = null;
                voStructure = BlancoRestGeneratorKtUtil.objects.get(defaultTelegramId);
                if (voStructure != null) {
                    defaultTelegramPackage = voStructure.getPackage();
                }
            }

            if (telegrams.size() == 0) {
                continue;
            }
            if (telegrams.size() != 2) {
                /* In と Out が揃っていない */
                return false;
            }
            argProcessStructure.getListTelegrams().put(methodKey, telegrams);
            found = true;
        }

        return found;
    }

    /**
     * Annotationリストを生成します。
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
     * php風の型定義をkotlin風に置換します。
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
            if ("string".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.String";
            } else
            if ("datetime".equalsIgnoreCase(phpType)) {
                kotlinType = "java.util.Date";
            } else
            if ("array".equalsIgnoreCase(phpType)) {
                if (isGeneric) {
                    throw new IllegalArgumentException("Cannot use array for Generics.");
                } else {
                    kotlinType = "kotlin.collections.ArrayList";
                }
            } else
            if ("object".equalsIgnoreCase(phpType)) {
                kotlinType = "kotlin.Any";
            } else {
                /* この名前の package を探す */
                String packageName = BlancoRestGeneratorKtUtil.searchPackageBySimpleName(phpType);
                if (packageName != null) {
                    kotlinType = packageName + "." + phpType;
                    argTelegramStructure.getImportList().add(kotlinType);
                }

                /* その他はそのまま記述する */
                if (this.isVerbose()) {
                    System.out.println("blancoRestGeneratorKt : Unknown php type: " + kotlinType);
                }
            }
        }
        return kotlinType;
    }
}
