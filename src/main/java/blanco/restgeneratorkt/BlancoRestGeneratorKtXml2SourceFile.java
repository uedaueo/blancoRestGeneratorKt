/*
 * blanco Framework
 * Copyright (C) 2004-2006 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorkt;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgLineUtil;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 「メッセージ定義書」Excel様式からメッセージを処理するクラス・ソースコードを生成。
 *
 * このクラスは、中間XMLファイルからソースコードを自動生成する機能を担います。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoRestGeneratorKtXml2SourceFile {

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

    /**
     * このプロダクトのリソースバンドルへのアクセスオブジェクト。
     */
    private final BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();

    /**
     * 出力対象となるプログラミング言語。
     */
    private int fTargetLang = BlancoCgSupportedLang.TS;

    /**
     * 入力シートに期待するプログラミング言語
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
    }

    /**
     * ソースコード生成先ディレクトリのスタイル
     */
    private boolean fTargetStyleAdvanced = false;
    public void setTargetStyleAdvanced(boolean argTargetStyleAdvanced) {
        this.fTargetStyleAdvanced = argTargetStyleAdvanced;
    }
    public boolean isTargetStyleAdvanced() {
        return this.fTargetStyleAdvanced;
    }

    /**
     * 内部的に利用するblancoCg用ファクトリ。
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * 内部的に利用するblancoCg用ソースファイル情報。
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * 内部的に利用するblancoCg用クラス情報。
     */
    private BlancoCgClass fCgClass = null;

    /**
     * フィールド名やメソッド名の名前変形を行うかどうか。
     *
     */
    private boolean fNameAdjust = true;

    /**
     * 要求電文のベースクラス
     */
    private String inputTelegramBase = null;
    /**
     * 応答電文のベースクラス
     */
    private String outputTelegramBase = null;

    /**
     * 自動生成するソースファイルの文字エンコーディング。
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * 中間XMLファイルからソースコードを自動生成します。
     *
     * @param argMetaXmlSourceFile
     *            メタ情報が含まれているXMLファイル。
     * @param argDirectoryTarget
     *            ソースコード生成先ディレクトリ (/mainを除く部分を指定します)。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void process(final File argMetaXmlSourceFile,
                        final File argDirectoryTarget)
            throws IOException, TransformerException {

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXml2SourceFile#process file = " + argMetaXmlSourceFile.getName());
        }

        fNameAdjust = true; // BlancoRestGenerator では常にフィールド名を変形します。

        BlancoRestGeneratorKtXmlParser parser = new BlancoRestGeneratorKtXmlParser();
        parser.setVerbose(this.isVerbose());
        parser.setCreateServiceMethod(this.isCreateServiceMethod());
        BlancoRestGeneratorKtTelegramProcessStructure[] processStructures =
                parser.parse(argMetaXmlSourceFile);

        if (processStructures == null) {
            System.out.println("!!! SKIP !!!! " + argMetaXmlSourceFile.getName());
            return;
        }

        for (int index = 0; index < processStructures.length; index++) {
            BlancoRestGeneratorKtTelegramProcessStructure processStructure = processStructures[index];
            // 得られた情報から TypeScript コードを生成します。
            generate(processStructure, argDirectoryTarget);
        }
    }

    private void generate(final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure, final File argDirectoryTarget) {

        // まず電文を生成します。
        Set<String> methodKeys = argProcessStructure.getListTelegrams().keySet(); // parse 時点で check しているので null はないはず
        for (String methodKey : methodKeys) {
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> kindMap =
                    argProcessStructure.getListTelegrams().get(methodKey);
            Set<String> kindKeys = kindMap.keySet(); // parse 時点で check しているので null はないはず
            for (String kindKey : kindKeys) {
                generateTelegram(kindMap.get(kindKey), argDirectoryTarget);
            }
        }

        // 次に電文処理を生成します。
        generateTelegramProcess(argProcessStructure, argDirectoryTarget);
    }

    /**
     * 電文処理クラスを生成します。
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateTelegramProcess(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget) {


        /*
         * 出力ディレクトリはant taskのtargetStyle引数で
         * 指定された書式で出力されます。
         * 従来と互換性を保つために、指定がない場合は blanco/main
         * となります。
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
            System.out.println("BlancoRestGenerateKtXml2SourceFile#generateTelegramProcess START with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argProcessStructure
                .getPackage(), "このソースコードは blanco Frameworkによって自動生成されています。");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // クラスを作成
        fCgClass = fCgFactory.createClass(BlancoRestGeneratorKtConstants.PREFIX_ABSTRACT + argProcessStructure.getName(),
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // 電文処理クラスは常に public abstract
        fCgClass.setAccess("public");
        fCgClass.setAbstract(true);
        // 継承
        if (BlancoStringUtil.null2Blank(argProcessStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argProcessStructure.getExtends()));
        }
        // 実装
        for (int index = 0; index < argProcessStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argProcessStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }
        // クラスのJavaDocを設定します。
        fCgClass.setDescription(argProcessStructure.getDescription());

        /* クラスのannotation を設定します */
        List annotationList = argProcessStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argProcessStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("generateTelegramProcess : class annotation = " + argProcessStructure.getAnnotationList().get(0));
            }
        }

        /* TypeScript では import の代わりに header を設定します */
        for (int index = 0; index < argProcessStructure.getHeaderList()
                .size(); index++) {
            final String header = (String) argProcessStructure.getHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        // サービスメソッドを生成します。
        if (this.isCreateServiceMethod()) {
            createServiceMethods(argProcessStructure);
        } else {
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegramProcess: SKIP SERVICE METHOD!");
            }
        }

        // isAuthenticationRequired メソッドの上書き
        overrideAuthenticationRequired(argProcessStructure);

        // ロケーションとWEBサービスIDからURLを生成します。
        createLocationUrl(argProcessStructure);

        // 収集された情報を元に実際のソースコードを自動生成。
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Serviceメソッドを実装します。
     *
     * @param argProcessStructure
     */
    private void createServiceMethods(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure
    ) {

        Map<String, String> httpMethods = new HashMap<>();
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_GET, "Get");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_POST, "Post");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT, "Put");
        httpMethods.put(BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE, "Delete");
        Map<String, String> telegramKind = new HashMap<>();
        telegramKind.put(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT, "Request");
        telegramKind.put(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT, "Response");

        for (Map.Entry<String, String> method : httpMethods.entrySet()) {
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> telegrams = argProcessStructure.getListTelegrams().get(method.getKey());
            if (telegrams == null) {
                /* このメソッドは未対応 */
                createExecuteMethodNotImplemented(method.getKey());
                createTelegramGenerateMethodNotImplemented(method, telegramKind);
            } else {
                createAbstractProcessMethod(telegrams);
                createExecuteMethod(telegrams, method);
                createTelegramGenerateMethod(telegrams, method, telegramKind);
            }
        }

        // 電文インスタンスのファクトリメソッドを生成
        createTelegramGeneratorFactory(httpMethods, telegramKind);
    }

    private void createAbstractProcessMethod(HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams) {

        // Processor の定義
        final BlancoCgMethod cgProcessorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorKtConstants.API_PROCESS_METHOD, fBundle.getXml2sourceFileProcessorDescription());
        fCgClass.getMethodList().add(cgProcessorMethod);
        cgProcessorMethod.setAccess("protected");
        cgProcessorMethod.setAbstract(true);

        String requestId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT).getName();
        String responseId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT).getName();

        cgProcessorMethod.getParameterList().add(
                fCgFactory.createParameter("arg" + requestId, requestId,
                        fBundle.getXml2sourceFileProsessorArgLangdoc()));

        cgProcessorMethod.setReturn(fCgFactory.createReturn(responseId,
                fBundle.getXml2sourceFileProsessorReturnLangdoc()));

    }

    private void createExecuteMethod(
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            Map.Entry<String, String> method) {

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorKtConstants.BASE_EXECUTOR_METHOD, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setAccess("protected");

        /*
         * 型チェックを通す為にデフォルトの電文親クラスを使います
         */
        String requestSuperId = BlancoRestGeneratorKtUtil.getDefaultRequestTelegramId(method.getKey());
        String responseSuperId = BlancoRestGeneratorKtUtil.getDefaultResponseTelegramId(method.getKey());
        /*
         * 本来の電文クラス
         */
        String requestId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT).getName();
        String responseId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT).getName();

        //        System.out.println("### requestId = " + requestId);
        cgExecutorMethod.getParameterList().add(
                fCgFactory.createParameter("arg" + requestSuperId, requestSuperId,
                        fBundle
                                .getXml2sourceFileExecutorArgLangdoc()));

        cgExecutorMethod.setReturn(fCgFactory.createReturn(responseSuperId,
                fBundle.getXml2sourceFileExecutorReturnLangdoc()));

        // メソッドの実装
        final List<String> listLine = cgExecutorMethod.getLineList();

        listLine.add(
                responseSuperId + " " +
                BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "ret" + responseId + " = "
                        + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "this." + BlancoRestGeneratorKtConstants.API_PROCESS_METHOD
                        + "( "  +  "("+ requestId + ")" + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "arg" + requestSuperId + " )"
                        + BlancoCgLineUtil.getTerminator(fTargetLang));

        listLine.add("\n");
        listLine.add("return "
                + BlancoCgLineUtil.getVariablePrefix(fTargetLang) + "ret" + responseId
                + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    /**
     * 非対応なメソッドが呼ばれた場合用のexecuteメソッドです。
     *
     * @param method
     */
    private void createExecuteMethodNotImplemented(String method) {
        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                BlancoRestGeneratorKtConstants.BASE_EXECUTOR_METHOD, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setAccess("protected");
        final List<String> ListLine = cgExecutorMethod.getLineList();

        // 電文定義がないので，デフォルトの電文名を指定しておく
        String requestId = BlancoRestGeneratorKtUtil.getDefaultRequestTelegramId(method);
        String responseId = BlancoRestGeneratorKtUtil.getDefaultResponseTelegramId(method);

        /* Excel sheet に電文定義がない場合にここにくるので、その場合は電文処理シートの定義を使う */
        cgExecutorMethod.getParameterList().add(
                fCgFactory.createParameter("argRequest", requestId,
                        fBundle
                                .getXml2sourceFileExecutorArgLangdoc()));
        cgExecutorMethod.setReturn(fCgFactory.createReturn(responseId,
                fBundle.getXml2sourceFileExecutorReturnLangdoc()));

        /*
         * TypeScript では例外は Error を継承する必要がある。
         * ここではそのまま Error を投げる。
         */
        //throw new BlancoRestException("GetMethod is not implemented in this api");
        // メソッドの実装
        ListLine.add(
                "throw new " + BlancoRestGeneratorKtConstants.DEFAULT_EXCEPTION + "( " + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) +
                        fBundle.getBlancorestErrorMsg05(method) + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) + ")" + BlancoCgLineUtil.getTerminator(fTargetLang));

    }


    /**
     * TelegramGenerateメソッドです。
     *
     * @param method
     */
    private void createTelegramGenerateMethod(
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            Map.Entry<String, String> method,
            Map<String, String> telegramKind) {
//        List<String> telegramNames = new ArrayList<>();
//        String requestId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT).getName();
//        String responseId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT).getName();
//
//        telegramNames.add(requestId);
//        telegramNames.add(responseId);

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final String name = argTelegrams.get(kind.getKey()).getName();
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("getApi%s%s", method.getValue(), kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);

            cgTelegramGenerateMethod.setAccess("protected");
            cgTelegramGenerateMethod.setReturn(fCgFactory.createReturn(
                    name,
                    fBundle.getXml2sourceFileTelegramGeneratorReturnDescription()));
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // メソッドの実装
            listLine.add("return new " + name + "()" + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }


    /**
     * 非対応なメソッドが呼ばれた場合用のTelegramGenerateメソッドです。
     *
     * @param method 対象のメソッド
     */
    private void createTelegramGenerateMethodNotImplemented(Map.Entry<String, String> method, Map<String, String> telegramKind) {

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("getApi%s%s", method.getValue(), kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);

            cgTelegramGenerateMethod.setAccess("protected");
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // メソッドの実装
            listLine.add(
                    "throw new " + BlancoRestGeneratorKtConstants.DEFAULT_EXCEPTION + "( " + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) +
                            fBundle.getBlancorestErrorMsg05(method.getKey()) + BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang) + ")" + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }


    /**
     * 電文インスタンスのファクトリメソッドです
     */
    private void createTelegramGeneratorFactory(Map<String, String> httpMethods, Map<String, String> telegramKind) {
        final String lineTerminal =  BlancoCgLineUtil.getTerminator(fTargetLang);
        final String literalEnclosure = BlancoCgLineUtil.getStringLiteralEnclosure(fTargetLang);

        for (Map.Entry<String, String> kind : telegramKind.entrySet()) {
            final BlancoCgMethod cgTelegramGenerateMethod = fCgFactory.createMethod(
                    String.format("get%sTelegram", kind.getValue()), fBundle.getXml2sourceFileTelegramGeneratorFactoryDescription());
            fCgClass.getMethodList().add(cgTelegramGenerateMethod);
            cgTelegramGenerateMethod.getParameterList().add(
                    fCgFactory.createParameter("method", "String",
                            fBundle.getXml2sourceFileTelegramGeneratorFactoryArgMethodDescription()));
            cgTelegramGenerateMethod.setAccess("protected");
            cgTelegramGenerateMethod.setReturn(fCgFactory.createReturn(
                    BlancoRestGeneratorKtConstants.API_TELEGRAM_BASE,
                    fBundle.getXml2sourceFileTelegramGeneratorFactoryReturnDescription()));
            final List<String> listLine = cgTelegramGenerateMethod.getLineList();

            // メソッドの実装
            listLine.add("let req: ApiTelegram" + lineTerminal);
            listLine.add("switch(method) {");
            for (Map.Entry<String, String> method : httpMethods.entrySet()) {
                listLine.add(String.format("case %s%s%s:", literalEnclosure, method.getValue().toUpperCase(), literalEnclosure));
                listLine.add(String.format("req = this.getApi%s%s()",
                        method.getValue().substring(0, 1).toUpperCase() + method.getValue().substring(1).toLowerCase(),
                        kind.getValue().substring(0, 1).toUpperCase() + kind.getValue().substring(1).toLowerCase()) + lineTerminal);
                listLine.add("break" + lineTerminal);
            }
            listLine.add("default:");
            listLine.add("throw new " + BlancoRestGeneratorKtConstants.DEFAULT_EXCEPTION + "(`" +
                    fBundle.getBlancorestErrorMsg06("${method}") + "`)" + lineTerminal);
            listLine.add("}");
            listLine.add("return req" + lineTerminal);
        }
    }


    private void overrideAuthenticationRequired(BlancoRestGeneratorKtTelegramProcessStructure argStructure) {
        String methodName = BlancoRestGeneratorKtConstants.API_AUTHENTICATION_REQUIRED;

        final BlancoCgMethod cgAuthenticationRequiredMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileAuthflagDescription());
        fCgClass.getMethodList().add(cgAuthenticationRequiredMethod);
        cgAuthenticationRequiredMethod.setAccess("public");

        cgAuthenticationRequiredMethod.setReturn(fCgFactory.createReturn("java.lang.boolean",
                fBundle.getXml2sourceFileAuthflagReturnLangdoc()));

        // メソッドの実装
        final List<String> listLine = cgAuthenticationRequiredMethod.getLineList();

        String retval = "true";
        if (argStructure.getNoAuthentication()) {
            retval = "false";
        }

        listLine.add("return " + retval
                + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    private void createLocationUrl(BlancoRestGeneratorKtTelegramProcessStructure argStructure) {
        // ロケーションURLを作成
        String locationUrl = argStructure.getLocation() + "/" + argStructure.getServiceId();

        final String fieldName = "_locationURL";
        final BlancoCgField field = fCgFactory.createField(fieldName,
                "string", null);

        fCgClass.getFieldList().add(field);
        field.setAccess("private");
        field.setNotnull(true);
        field.getLangDoc().getDescriptionList().add(
                BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.TS, fBundle.getXml2sourceFileFieldDefault(
                        locationUrl)));
        field.setDefault("\"" + locationUrl +"\"");

        // セッターメソッドを生成
        final BlancoCgMethod setterMethod = fCgFactory.createMethod(BlancoNameAdjuster.toParameterName(fieldName),
                fBundle.getXml2sourceFileSetLangdoc01(fieldName));
        fCgClass.getMethodList().add(setterMethod);

        setterMethod.setAccess("set");

        // セッターメソッドのJavaDoc設定
        setterMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileSetLangdoc02("string"));

        // セッターメソッドのparam設定
        BlancoCgParameter param = fCgFactory.createParameter("arg"
                        + BlancoNameAdjuster.toClassName(fieldName),
                "java.lang.string",
                fBundle.getXml2sourceFileSetArgLangdoc(fieldName));
        param.setNotnull(true);

        setterMethod.getParameterList().add(param);

        // セッターメソッドの実装
        setterMethod.getLineList().add("this." + fieldName + " = "
                + "arg" + BlancoNameAdjuster.toClassName(fieldName) + ";");

        // ゲッターメソッドを生成
        final BlancoCgMethod getterMethod = fCgFactory.createMethod(BlancoNameAdjuster.toParameterName(fieldName),
                fBundle.getXml2sourceFileGetLangdoc01(fieldName));
        fCgClass.getMethodList().add(getterMethod);

        getterMethod.setAccess("get");

        // ゲッターメソッドのJavaDoc設定。
        getterMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileGetLangdoc02("string"));

        // ゲッターメソッドの返り値設定
        getterMethod.setNotnull(true);
        getterMethod.setReturn(fCgFactory.createReturn("java.lang.string",
                fBundle.getXml2sourceFileGetReturnLangdoc(fieldName)));

        // ゲッターメソッドの実装
        getterMethod.getLineList().add("return this." + fieldName + ";");
    }

    private void createRequestIdMethod(String methodName, String requestIdName, String packageName) {
        final BlancoCgMethod cgRequestIdMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileRequestidDesctiption());
        fCgClass.getMethodList().add(cgRequestIdMethod);

        cgRequestIdMethod.setAccess("protected");

        List<String> annotators = new ArrayList<>();
        annotators.add("Override");
        cgRequestIdMethod.setAnnotationList(annotators);

        cgRequestIdMethod.setReturn(fCgFactory.createReturn("java.lang.string",
                fBundle.getXml2sourceFileRequestidReturnLangdoc()));

        /*
         * メソッドの実装
         * 定義されていないときはnullを返す
         */
        final List<String> listLine = cgRequestIdMethod.getLineList();
        if(requestIdName == null) {
            listLine.add("return null" + BlancoCgLineUtil.getTerminator(fTargetLang));
        } else {
            listLine.add("return " + "\"" + packageName + "." + requestIdName + "\""
                    + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }

    private void createResponseIdMethod(String methodName, String responseIdName, String packageName) {

        final BlancoCgMethod cgResponseIdMethod = fCgFactory.createMethod(
                methodName, fBundle.getXml2sourceFileRequestidDesctiption());
        fCgClass.getMethodList().add(cgResponseIdMethod);
        cgResponseIdMethod.setAccess("protected");

        List<String> annotators = new ArrayList<>();
        annotators.add("Override");
        cgResponseIdMethod.setAnnotationList(annotators);

        cgResponseIdMethod.setReturn(fCgFactory.createReturn("java.lang.String",
                fBundle.getXml2sourceFileRequestidReturnLangdoc()));

        /*
         * メソッドの実装
         * 定義されていないときはnullを返す
         */
        final List<String> listLine = cgResponseIdMethod.getLineList();
        if(responseIdName == null) {
            listLine.add("return null" + BlancoCgLineUtil.getTerminator(fTargetLang));
        } else {
            listLine.add("return " + "\"" + packageName + "." + responseIdName + "\""
                    + BlancoCgLineUtil.getTerminator(fTargetLang));
        }
    }

    /**
     * 電文クラスを生成します。
     *
     * @param argTelegramStructure
     * @param argDirectoryTarget
     */
    private void generateTelegram(
            final BlancoRestGeneratorKtTelegramStructure argTelegramStructure,
            final File argDirectoryTarget) {

        /*
         * 出力ディレクトリはant taskのtargetStyle引数で
         * 指定された書式で出力されます。
         * 従来と互換性を保つために、指定がない場合は blanco/main
         * となります。
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
            System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegram SATR with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argTelegramStructure
                .getPackage(), "このソースコードは blanco Frameworkによって自動生成されています。");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());
        // クラスを作成
        fCgClass = fCgFactory.createClass(argTelegramStructure.getName(),
                BlancoStringUtil.null2Blank(argTelegramStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // 電文処理クラスは常に public
        fCgClass.setAccess("public");
        // 継承
        if (BlancoStringUtil.null2Blank(argTelegramStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argTelegramStructure.getExtends()));
        }
        // 実装
        for (int index = 0; index < argTelegramStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argTelegramStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }
        // クラスのJavaDocを設定します。
        fCgClass.setDescription(argTelegramStructure.getDescription());

        /* クラスのannotation を設定します */
        List annotationList = argTelegramStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argTelegramStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegram : class annotation = " + argTelegramStructure.getAnnotationList().get(0));
            }
        }

        /* TypeScript では import の代わりに header を設定します */
        for (int index = 0; index < argTelegramStructure.getHeaderList()
                .size(); index++) {
            final String header = (String) argTelegramStructure.getHeaderList()
                    .get(index);
            fCgSourceFile.getHeaderList().add(header);
        }

        if (this.isVerbose()) {
            System.out.println("BlancoRestGeneratorKtXml2SourceFile: Start create properties : " + argTelegramStructure.getName());
        }

        // 電文定義・一覧
        for (int indexField = 0; indexField < argTelegramStructure.getListField()
                .size(); indexField++) {
            // おのおののフィールドを処理します。
            final BlancoRestGeneratorKtTelegramFieldStructure fieldStructure =
                    argTelegramStructure.getListField().get(indexField);

            // 必須項目が未設定の場合には例外処理を実施します。
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

            // フィールドの生成。
            buildField(argTelegramStructure, fieldStructure);

            // セッターメソッドの生成。
            buildMethodSet(fieldStructure);

            // ゲッターメソッドの生成。
            buildMethodGet(fieldStructure);

            // Type文字列を返すメソッドの生成
            buildMethodType(fieldStructure);

            // Generic文字列を返すメソッドの生成
            buildMethodGeneric(fieldStructure);

            // validation は後日実装予定
        }

        // 収集された情報を元に実際のソースコードを自動生成。
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * クラスにフィールドを生成します。
     * @param argClassStructure
     *            クラス情報。
     * @param argFieldStructure
     */
    private void buildField(
            final BlancoRestGeneratorKtTelegramStructure argClassStructure,
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {

        /*
         * フィールド名は常に変形する
         */
        String fieldName = "f" + this.getFieldNameAdjustered(argFieldStructure);
        final BlancoCgField field = fCgFactory.createField(fieldName,
                argFieldStructure.getType(), null);

        fCgClass.getFieldList().add(field);

        /*
         * Generic に対応します。blancoCg 側では <> が付いている前提かつ
         * package部をtrimするので、ここで設定しないと正しく設定されません。
         */
        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            field.getType().setGenerics(generic);
        }

        if (this.isVerbose()) {
            System.out.println("!!! type = " + argFieldStructure.getType());
            System.out.println("!!! generic = " + field.getType().getGenerics());
        }

        field.setAccess("private");
        /*
         * 当面、final には対応しません。
         */
        field.setFinal(false);

        // nullable に対応します。
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // フィールドのJavaDocを設定します。
        field.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            field.getLangDoc().getDescriptionList().add(line);
        }

        /*
         * TypeScript ではプロパティのデフォルト値は原則必須
         */
        String defaultRawValue = argFieldStructure.getDefault();
        if ((defaultRawValue == null ||defaultRawValue.length() <= 0)) {
            if (!isNullable) {
                throw new IllegalArgumentException(fBundle
                        .getXml2sourceFileErr009(argClassStructure.getName(), argFieldStructure.getName()));
            }
        } else {
            // フィールドのデフォルト値を設定します。
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.TS, fBundle.getXml2sourceFileFieldDefault(argFieldStructure
                            .getDefault())));
            // 当面の間、デフォルト値の変形は常にoff。定義書上の値をそのまま採用。
            field.setDefault(argFieldStructure.getDefault());

            /* メソッドの annotation を設定します */
            List annotationList = argFieldStructure.getAnnotationList();
            if (annotationList != null && annotationList.size() > 0) {
                field.getAnnotationList().addAll(annotationList);
                if (this.isVerbose()) {
                    System.out.println("method annotation = " + field.getAnnotationList().get(0));
                }
            }
        }
    }

    /**
     * setメソッドを生成します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     */
    private void buildMethodSet(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        // おのおののフィールドに対するセッターメソッドを生成します。
        final BlancoCgMethod method = fCgFactory.createMethod(argFieldStructure.getName(),
                fBundle.getXml2sourceFileSetLangdoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        method.setAccess("set");

        // メソッドの JavaDoc設定。
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2sourceFileSetLangdoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }

        BlancoCgParameter param = fCgFactory.createParameter("arg"
                        + getFieldNameAdjustered(argFieldStructure),
                argFieldStructure.getType(),
                fBundle.getXml2sourceFileSetArgLangdoc(argFieldStructure
                        .getName()));
        if (!argFieldStructure.getNullable()) {
            param.setNotnull(true);
        }

        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            param.getType().setGenerics(generic);
        }
        method.getParameterList().add(param);

        // メソッドの実装
        method.getLineList().add(
                "this.f"
                        + getFieldNameAdjustered(argFieldStructure)
                        + " = "
                        + "arg"
                        + getFieldNameAdjustered(argFieldStructure) + ";");
    }

    /**
     * getメソッドを生成します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     */
    private void buildMethodGet(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        // おのおののフィールドに対するゲッターメソッドを生成します。
        final BlancoCgMethod method = fCgFactory.createMethod(argFieldStructure.getName(),
                fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                        .getName()));
        fCgClass.getMethodList().add(method);

        if (!argFieldStructure.getNullable()) {
            method.setNotnull(true);
        }

        method.setAccess("get");

        // メソッドの JavaDoc設定。
        if (argFieldStructure.getDescription() != null) {
            method.getLangDoc().getDescriptionList().add(
                    fBundle.getXml2sourceFileGetLangdoc02(argFieldStructure
                            .getDescription()));
        }
        for (String line : argFieldStructure.getDescriptionList()) {
            method.getLangDoc().getDescriptionList().add(line);
        }
        if (argFieldStructure.getDefault() != null) {
            method.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.JAVA, fBundle.getXml2sourceFileGetArgLangdoc(argFieldStructure
                            .getDefault())));
        }

        method.setReturn(fCgFactory.createReturn(argFieldStructure.getType(),
                fBundle.getXml2sourceFileGetReturnLangdoc(argFieldStructure.getName())));
        String generic = argFieldStructure.getGeneric();
        if (generic != null && generic.length() > 0) {
            method.getReturn().getType().setGenerics(generic);
        }

        // メソッドの実装
        method.getLineList().add(
                "return this.f"
                        + this.getFieldNameAdjustered(argFieldStructure) + ";");
    }

    /**
     * typeメソッドを展開します
     *
     * @param argFieldStructure
     */
    private void buildMethodType(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        // 常にフィールド名を調整
        String fieldName = getFieldNameAdjustered(argFieldStructure);

        final BlancoCgMethod cgMethod = fCgFactory.createMethod("type"
                + fieldName, fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                .getName()));
        fCgClass.getMethodList().add(cgMethod);
        cgMethod.setAccess("public");
        cgMethod.setStatic(true);

        cgMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileTypeLangdoc02(argFieldStructure.getType()));

        cgMethod.setReturn(fCgFactory.createReturn("string", fBundle
                .getXml2sourceFileTypeReturnLangdoc(argFieldStructure.getName())));

        if (BlancoStringUtil.null2Blank(argFieldStructure.getDescription()).length() > 0) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    argFieldStructure.getDescription());
        }

        // メソッドの実装
        final List<String> listLine = cgMethod.getLineList();

        /*
         * TypeScript では、package が付与されている場合は外す
         */
        String methodType = BlancoRestGeneratorKtUtil
                .getSimpleClassName(argFieldStructure.getType());

        listLine
                .add("return "
                        + "\"" + methodType + "\""
                        + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    /**
     * Genericメソッドを展開します。
     * @param argFieldStructure
     */
    private void buildMethodGeneric(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        String fieldName = getFieldNameAdjustered(argFieldStructure);
        String fieldGeneric = argFieldStructure.getGeneric();

        if (BlancoStringUtil.null2Blank(fieldGeneric).length() == 0) {
            return;
        }

        final BlancoCgMethod cgMethod = fCgFactory.createMethod("generic"
                + fieldName, fBundle.getXml2sourceFileGetLangdoc01(argFieldStructure
                .getName()));
        fCgClass.getMethodList().add(cgMethod);
        cgMethod.setAccess("public");
        cgMethod.setStatic(true);

        cgMethod.getLangDoc().getDescriptionList().add(
                fBundle.getXml2sourceFileTypeLangdoc02(fieldGeneric));

        cgMethod.setReturn(fCgFactory.createReturn("string", fBundle
                .getXml2sourceFileTypeReturnLangdoc(argFieldStructure.getName())));

        if (BlancoStringUtil.null2Blank(argFieldStructure.getDescription()).length() > 0) {
            cgMethod.getLangDoc().getDescriptionList().add(
                    argFieldStructure.getDescription());
        }

        // メソッドの実装
        final List<String> listLine = cgMethod.getLineList();

        /*
         * TypeScript では、package が付与されている場合は外す
         */
        String genericType = BlancoRestGeneratorKtUtil
                .getSimpleClassName(argFieldStructure.getGeneric());

        listLine
                .add("return "
                        + "\"" + genericType + "\""
                        + BlancoCgLineUtil.getTerminator(fTargetLang));
    }

    /**
     * 調整済みのフィールド名を取得します。
     *
     * @param argFieldStructure
     *            フィールド情報。
     * @return 調整後のフィールド名。
     */
    private String getFieldNameAdjustered(
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {
        return BlancoNameAdjuster.toClassName(argFieldStructure.getName());
    }
}
