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
import blanco.cg.BlancoCgTransformer;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
     * 内部的に利用するblancoCg用インタフェイス情報。
     */
    private BlancoCgInterface fCgInterface = null;

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

//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGeneratorKtXml2SourceFile#process file = " + argMetaXmlSourceFile.getName());
//        }

        fNameAdjust = true; // BlancoRestGenerator では常にフィールド名を変形します。

        BlancoRestGeneratorKtXmlParser parser = new BlancoRestGeneratorKtXmlParser();
        parser.setVerbose(this.isVerbose());
        parser.setCreateServiceMethod(this.isCreateServiceMethod());
        parser.setServerType(this.getServerType());
        parser.setTelegramPackage(this.getTelegramPackage());
        BlancoRestGeneratorKtTelegramProcessStructure[] processStructures =
                parser.parse(argMetaXmlSourceFile);

        if (processStructures == null) {
            System.out.println("!!! SKIP !!!! " + argMetaXmlSourceFile.getName());
            return;
        }

        if (BlancoRestGeneratorKtUtil.genUtils) {
            // util系を生成します
            generateDeserializer(argDirectoryTarget);
        }

        for (int index = 0; index < processStructures.length; index++) {
            BlancoRestGeneratorKtTelegramProcessStructure processStructure = processStructures[index];
            // 得られた情報から kotlin コードを生成します。
            generate(processStructure, argDirectoryTarget);
        }
    }

    private void generateDeserializer(
            final File argDirectoryTarget
    ) {

        /*
         * 出力ディレクトリはant taskのtargetStyel引数で
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

        fCgFactory = BlancoCgObjectFactory.getInstance();
        final BlancoCgTransformer transformer = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        transformer.transform(new  BlancoRestGeneratorKtRequestDeserializerClass(fCgFactory,
                BlancoRestGeneratorKtUtil.runtimePackage,
                this.fEncoding,
                this.fTabs).expand(), fileBlancoMain);
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

        /*
         * 次に電文処理を生成します。
         * 現時点では micronaut 向けの controller を生成します。
         * 将来的には tomcat 向けの abstract クラスにも対応します。
         */
        generateMicronautController(argProcessStructure, argDirectoryTarget);

        /*
         * 次に、インジェクションするクラスが実装するインタフェイスを生成します。
         */
        if (this.isCreateServiceMethod()) {
            generateApplicationInterface(argProcessStructure, argDirectoryTarget);
        }
    }

    /**
     * アプリケーション実装クラスが実装するインタフェイスを生成します。
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateApplicationInterface(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget
    ) {

        /*
         * 出力ディレクトリはant taskのtargetStyel引数で
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
//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess SATRT with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
//        }

        String interfacePackage = argProcessStructure
                .getPackage() + ".interfaces";

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(interfacePackage, "このソースコードは blanco Frameworkによって自動生成されています。");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // micronaut と jackson で使用するための
        // import分は無条件で設定します。
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        // telegram 類を import します。
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");

        // RequestHeader, ResponseHeader はここで確定しておく
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // fullPackageで指定されている前提
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }

        // インタフェイスを作成
        String applicationInterfaceId = "I" + argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;
        fCgInterface = fCgFactory.createInterface(applicationInterfaceId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getInterfaceList().add(fCgInterface);
        fCgInterface.setAccess("public");

        // ServiceMethod を生成します。
        createServiceMethods(argProcessStructure, "", requestHeaderIdSimple, responseHeaderIdSimple, true);

        // 収集された情報を元に実際のソースコードを自動生成。
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * 電文処理クラスを生成します。
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateMicronautController(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget) {


        /*
         * 出力ディレクトリはant taskのtargetStyel引数で
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
//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess SATRT with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
//        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(argProcessStructure
                .getPackage(), "このソースコードは blanco Frameworkによって自動生成されています。");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // micronaut と jackson で使用するための
        // import分は無条件で設定します。
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpRequest");
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Body");
        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Controller");
        // telegram 類を import します。
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        String runtimePkg = BlancoRestGeneratorKtUtil.runtimePackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");
        fCgSourceFile.getImportList().add(runtimePkg + ".util.BlancoRestGeneratorKtRequestDeserializer");

        // クラスを作成
        String controllerClassId = argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_CONTROLLER;
        fCgClass = fCgFactory.createClass(controllerClassId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // 電文処理クラスは常に public final
        fCgClass.setAccess("public");
        fCgClass.setFinal(true);
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

        /*
         * Controller アノテーションは常に設定します。
         */
        String location = BlancoStringUtil.null2Blank(argProcessStructure.getLocation());
        String serviceId = BlancoStringUtil.null2Blank(argProcessStructure.getServiceId());
        String controllerAnn = "Controller";
        if (serviceId.length() > 0) {
            controllerAnn += "(\"" + location + "/" + serviceId + "\")";
        }
        fCgClass.getAnnotationList().add(controllerAnn);

        /*
         * APIごとの実装クラスをインジェクションします。
         */
        String applicationClassId = argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;
        String applicationClassPackage = argProcessStructure.getPackage() + "." + BlancoRestGeneratorKtConstants.MANAGER_PACKAGE;

        String injectedParameterId = BlancoNameAdjuster.toParameterName(applicationClassId);

        BlancoCgField impleClass = fCgFactory.createField(
                injectedParameterId,
                applicationClassPackage + "." + applicationClassId,
                controllerClassId + "が呼び出す実装クラスです。"
        );
        impleClass.setNotnull(true);
        fCgClass.getConstructorArgList().add(impleClass);

        // サービスメソッドを生成します。
        // RequestHeader, ResponseHeader はここで確定しておく
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // fullPackageで指定されている前提
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }
        if (this.isCreateServiceMethod()) {
            createServiceMethods(argProcessStructure, injectedParameterId, requestHeaderIdSimple, responseHeaderIdSimple, false);
        } else {
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegramProcess: SKIP SERVICE METHOD!");
            }
        }

        // 収集された情報を元に実際のソースコードを自動生成。
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Serviceメソッドを実装します。
     *
     * @param argProcessStructure
     * @param isInterface
     */
    private void createServiceMethods(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final String argInjectedParameterId,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            Boolean isInterface
    ) {

        List<String> httpMethods = new ArrayList<>();
        httpMethods.add(BlancoRestGeneratorKtConstants.HTTP_METHOD_GET);
        httpMethods.add(BlancoRestGeneratorKtConstants.HTTP_METHOD_POST);
        httpMethods.add(BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT);
        httpMethods.add(BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE);
        List<String> telegramKind = new ArrayList<>();
        telegramKind.add(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        telegramKind.add(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT);

        for (String method : httpMethods) {
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> telegrams = argProcessStructure.getListTelegrams().get(method);
            if (telegrams == null) {
                /* このメソッドは未対応 */
//                createExecuteMethodNotImplemented(method);
                /*
                 * Micronaut対応では未対応メソッドは記述しない
                 */
            } else {
                if (!isInterface) {
                    createExecuteMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getMetaIdList());
                } else {
                    createInterfaceMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple);
                }
            }
        }
    }

    private void createInterfaceMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple
    ) {

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

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                executeMethodId, fBundle.getXml2sourceFileExecutorDescription());
        fCgInterface.getMethodList().add(cgExecutorMethod);

        /* request */
        String httpCommonRequestId = BlancoRestGeneratorKtUtil.telegramPackage + ".HttpCommonRequest";
        String commonRequestId = "CommonRequest";

        BlancoCgParameter httpRequest = fCgFactory.createParameter(
                "httpRequest",
                httpCommonRequestId,
                fBundle.getXml2sourceFileExecutorArgLangdoc()
        );
        cgExecutorMethod.getParameterList().add(httpRequest);
        httpRequest.setNotnull(true);

        BlancoRestGeneratorKtTelegramStructure requestStructure = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        String requestIdSimple = requestStructure.getName();
        if (this.isVerbose()) {
            System.out.println("### requestId = " + requestIdSimple);
        }
        String requestIdPackage = requestStructure.getPackage();
        String requestId = requestIdPackage + "." + requestIdSimple;
        fCgSourceFile.getImportList().add(requestId);

        String requestGenerics = "";
        if (argRequestHeaderIdSimple == null || argRequestHeaderIdSimple.length() <= 0) {
            requestGenerics = commonRequestId + "<" + requestIdSimple + ">";
        } else {
            requestGenerics = commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestIdSimple + ">";
        }
        httpRequest.getType().setGenerics(requestGenerics);

        /* response */
        String commonResponseId = "CommonResponse";

        BlancoCgReturn cgReturn = fCgFactory.createReturn(
                "io.micronaut.http.HttpResponse",
                fBundle.getXml2sourceFileExecutorReturnLangdoc()
        );
        cgExecutorMethod.setReturn(cgReturn);

        BlancoRestGeneratorKtTelegramStructure responseStructure = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT);
        String responseIdSimple = responseStructure.getName();
        if (this.isVerbose()) {
            System.out.println("### responseId = " + responseIdSimple);
        }
        String responseIdPackage = responseStructure.getPackage();
        String responseId = responseIdPackage + "." + responseIdSimple;
        fCgSourceFile.getImportList().add(responseId);

        String responseGenerics = "";
        if (argResponseHeaderIdSimple == null || argResponseHeaderIdSimple.length() <= 0) {
            responseGenerics = commonResponseId + "<" + responseIdSimple + ">";
        } else {
            responseGenerics = commonResponseId + "<" + argResponseHeaderIdSimple + ", " + responseIdSimple + ">";
        }
        cgReturn.getType().setGenerics(responseGenerics);
    }

    private void createExecuteMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            final String argInjectedParameterId,
            final Boolean argNoAuthentication,
            final List<String> argMetaIdList
    ) {

        String executeMethodId = "";
        String methodAnn = "";
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.GET_CONTROLLER_METHOD;
            methodAnn = "Get";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Get");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.POST_CONTROLLER_METHOD;
            methodAnn = "Post";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Post");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.PUT_CONTROLLER_METHOD;
            methodAnn = "Put";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Put");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.DELETE_CONTROLLER_METHOD;
            methodAnn = "Delete";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Delete");
        }

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                executeMethodId, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setFinal(true);

        /*
         * アノテーションを設定します。
         */
        cgExecutorMethod.getAnnotationList().add(methodAnn);

        /*
         * まず、HttpRequestパラメータを生成します。
         */
        BlancoCgParameter httpRequest = fCgFactory.createParameter(
                "argHttpRequest",
                "io.micronaut.http.HttpRequest",
                fBundle.getXml2sourceFileExecutorArgLangdoc()
        );
        cgExecutorMethod.getParameterList().add(httpRequest);
        httpRequest.setNotnull(true);

        /*
         * telegram 類が定義されているパッケージについては、API定義書で
         * 明示的にimportされている前提とする。（または同一パッケージ）
         */
        String requestId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT).getName();
        if (this.isVerbose()) {
            System.out.println("### requestId = " + requestId);
        }

        String commonRequestId = "CommonRequest";

        /*
         * kotlin transformer では一重目の generics の
         * <> は自動的に追加され、パッケージは削除される。
         */
        String requestGenerics = "";
        if (argRequestHeaderIdSimple == null || argRequestHeaderIdSimple.length() <= 0) {
            requestGenerics = commonRequestId + "<" + requestId + ">";
        } else {
            requestGenerics = commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + ">";
        }
        httpRequest.getType().setGenerics(
                requestGenerics
        );

        /*
         * 次にbodyを取得するパラメータを生成します。
         */
        BlancoCgParameter body = fCgFactory.createParameter(
                "argBody",
                "java.lang.String",
                "生JSONボディです。"
        );
        cgExecutorMethod.getParameterList().add(body);
        body.setNotnull(true);
        body.getAnnotationList().add("Body");

        /*
         * Return 型を設定します。
         */
        String responseId = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT).getName();
        if (this.isVerbose()) {
            System.out.println("### responseId = " + responseId);
        }

        BlancoCgReturn cgReturn = fCgFactory.createReturn("io.micronaut.http.HttpResponse",
                fBundle.getXml2sourceFileExecutorReturnLangdoc());
        cgExecutorMethod.setReturn(cgReturn);

        String commonResponseId = BlancoRestGeneratorKtUtil.telegramPackage + ".CommonResponse";

        String responseGenerics = "";
        if (argResponseHeaderIdSimple == null || argResponseHeaderIdSimple.length() <= 0) {
            responseGenerics = commonResponseId + "<" + responseId + ">";
        } else {
            responseGenerics = commonResponseId +  "<" + argResponseHeaderIdSimple + ", " + responseId + ">";
        }
        cgReturn.getType().setGenerics(
                responseGenerics
        );

        // メソッド本体の実装
        final List<String> listLine = cgExecutorMethod.getLineList();

        String requestDeserializerId = BlancoRestGeneratorKtUtil.runtimePackage + ".util.BlancoRestGeneratorKtRequestDeserializer";
        fCgSourceFile.getImportList().add(requestDeserializerId);
        String requestDeserializerIdSimple = "BlancoRestGeneratorKtRequestDeserializer";

        listLine.add("/* json 文字列から CommonRequest インスタンスを生成する */");
        listLine.add("val deserializer = " + requestDeserializerIdSimple + "<" + argRequestHeaderIdSimple + ", " + requestId + ">(argHttpRequest.javaClass)");
        listLine.add("deserializer.infoClazz = " + argRequestHeaderIdSimple + "::class.java");
        listLine.add("deserializer.telegramClazz = " + requestId + "::class.java");
        listLine.add("");

        listLine.add("val commonRequest: " + commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + "> = " + argInjectedParameterId + ".convertJsonToCommonRequest(argBody, deserializer)");
        listLine.add("");

        listLine.add("/* httpRequest を delegator とした HttpCommonRequest を作成し、ここに型を確定させた commonRequest を格納する */");

        /*
         * 認証が必要なAPIかどうか
         */
        String noAuthentication = argNoAuthentication ? "true" : "false";

        /*
         * あれば、MetaIdListを設定します。
         */
        String metaIdList = "";
        if (argMetaIdList != null && argMetaIdList.size() > 0) {
            int count = 0;
            StringBuffer sb = new StringBuffer();
            for (String metaId : argMetaIdList) {
                if (count > 0) {
                    sb.append(",");
                    listLine.add(sb.toString());
                    sb = new StringBuffer();
                }
                sb.append("\"" + metaId + "\"");
                count++;
            }
            metaIdList = sb.toString();
        }

        listLine.add("val httpCommonRequest = HttpCommonRequest<" + commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + ">>(argHttpRequest, " + noAuthentication + ", listOf(" + metaIdList + "), commonRequest)");
        listLine.add("");

        listLine.add("/* 前処理を行う（validation等） */");
        listLine.add(argInjectedParameterId + ".prepare(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* HttpCommonRequest を渡す */");
        listLine.add("val httpResponse = " + argInjectedParameterId + "." + executeMethodId + "(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* 後処理 */");
        listLine.add(argInjectedParameterId + ".finish(httpResponse, httpCommonRequest)");
        listLine.add("");
        listLine.add("return httpResponse");
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
         * 出力ディレクトリはant taskのtargetStyel引数で
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
            System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegram START with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
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
        // 電文クラスは常に public, kotlin では省略可。
        String access = "";
        // data クラスかどうか、かつコンストラクタ引数がひとつでもあるか。
        if (argTelegramStructure.getData() && this.hasConstructorArgs(argTelegramStructure)) {
            access += "data";
        }
        fCgClass.setAccess(access);
        // Finalクラスかどうか。dataクラスは強制的にfinal。
        if (argTelegramStructure.getData() && !argTelegramStructure.getFinal()) {
            fCgClass.setFinal(true);
        } else {
            fCgClass.setFinal(argTelegramStructure.getFinal());
        }
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

        /* クラスのimport を設定します */
        for (int index = 0; index < argTelegramStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argTelegramStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
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

        switch (fSheetLang) {
            case BlancoCgSupportedLang.PHP:
                if (argFieldStructure.getType() == "kotlin.Int") argFieldStructure.setType("kotlin.Long");
                break;
            /* 対応言語を増やす場合はここに case を追記します */
        }

        /* 型を決定します。typeKtが設定されている場合はそちらを優先します */
        boolean isKtPreferred = true;
        String typeRaw = argFieldStructure.getTypeKt();
        if (typeRaw == null || typeRaw.length() == 0) {
            typeRaw = argFieldStructure.getType();
            isKtPreferred = false;
        }

        /*
         * blancoValueObjectではプロパティ名の前にfをつける流儀であるが、
         * kotlinについては暗黙のgetter/setterを使う都合上、つけない。
         */
        final BlancoCgField field = fCgFactory.createField(argFieldStructure.getName(),
                typeRaw, null);

        /*
         * Generic に対応します。blancoCg 側では <> が付いている前提かつ
         * package部をtrimするので、ここで設定しないと正しく設定されません。
         * genericKt が設定されている場合はそちらを優先します。
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
         * 当面の間、blancoValueObjectKt ではprivateやgetter/setter,
         * open には対応しません。
         */
        field.setAccess("public");
        field.setFinal(true);

        // nullable に対応します。
        Boolean isNullable = argFieldStructure.getNullable();
        if (isNullable != null && isNullable) {
            field.setNotnull(false);
        } else {
            field.setNotnull(true);
        }

        // value / variable に対応します。
        Boolean isValue = argFieldStructure.getValue();
        if (isValue != null && isValue) {
            field.setConst(true);
        } else {
            field.setConst(false);
        }

        /*
         * field がconstructotr引数かどうをチェック
         */
        Boolean isConstArg = argFieldStructure.getConstArg();
        if (isConstArg != null && isConstArg) {
            fCgClass.getConstructorArgList().add(field);
        } else {
            fCgClass.getFieldList().add(field);
        }

        // フィールドのJavaDocを設定します。
        field.setDescription(argFieldStructure.getDescription());
        for (String line : argFieldStructure.getDescriptionList()) {
            field.getLangDoc().getDescriptionList().add(line);
        }

        // フィールドのデフォルト値を設定します。
        if (argFieldStructure.getDefault() != null || argFieldStructure.getDefaultKt() != null) {
            final String type = field.getType().getName();

            if (type.equals("java.util.Date")) {
                /*
                 * java.util.Date 型ではデフォルト値を許容しません。
                 */
                throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg06());
            }

            /*
             * kotlinでは プロパティのデフォルト値は原則必須
             * ただし、abstract クラスにおいては、プロパティに abstract 修飾子が
             * ついている場合は省略可。
             * とはいえ、blancoValueObjectKt では当面abstractプロパティはサポートしません。
             */

            /*
             * defaultKt があればそちらを優先します。
             */
            String defaultRawValue = argFieldStructure.getDefaultKt();
            if (!isKtPreferred && (defaultRawValue == null || defaultRawValue.length() == 0)) {
                defaultRawValue = argFieldStructure.getDefault();
            }
            if (!isConstArg && (defaultRawValue == null || defaultRawValue.length() <= 0)) {
                System.err.println("/* tueda */ フィールドにデフォルト値が設定されていません。blancoValueObjectKtは当面の間、abstractフィールドはサポートしませんので、必ずデフォルト値を設定してください。");
                throw new IllegalArgumentException(fBundle
                        .getBlancorestErrorMsg07());
            }

            // フィールドのデフォルト値を設定します。
            field.getLangDoc().getDescriptionList().add(
                    BlancoCgSourceUtil.escapeStringAsLangDoc(BlancoCgSupportedLang.KOTLIN, fBundle.getXml2sourceFileFieldDefault(defaultRawValue)));

            // blancoRestGenerator では、デフォルト値は定義書上の値をそのまま採用。
            field.setDefault(defaultRawValue);
        }

        /* メソッドの annotation を設定します */
        List annotationList = argFieldStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            field.getAnnotationList().addAll(annotationList);
            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }
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

    private boolean hasConstructorArgs(final BlancoRestGeneratorKtTelegramStructure argTelegramStructure) {
        boolean found = false;

        for (BlancoRestGeneratorKtTelegramFieldStructure fieldStructure : argTelegramStructure.getListField()) {
            if (fieldStructure.getConstArg()) {
                found = true;
                break;
            }
        }
        return  found;
    }
}
