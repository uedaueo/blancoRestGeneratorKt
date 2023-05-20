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
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtGetRequestBindStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Generates class source code to process messages from "Message Definition Form" Excel format.
 *
 * This class is responsible for generation of source code from intermediate XML files.
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
     * An access object to the resource bundle for this product.
     */
    private final BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();

    /**
     * Target programming language.
     */
    private int fTargetLang = BlancoCgSupportedLang.TS;

    /**
     * Programming language expected for the input sheet.
     */
    private int fSheetLang = BlancoCgSupportedLang.JAVA;

    public void setSheetLang(final int argSheetLang) {
        fSheetLang = argSheetLang;
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
     * A factory for blancoCg to be used internally.
     */
    private BlancoCgObjectFactory fCgFactory = null;

    /**
     * Source file information for blancoCg to be used internally.
     */
    private BlancoCgSourceFile fCgSourceFile = null;

    /**
     * Class information for blancoCg to be used internally.
     */
    private BlancoCgClass fCgClass = null;

    /**
     * Interface information for blancoCg to be used internally.
     */
    private BlancoCgInterface fCgInterface = null;

    /**
     * Whether to adjust field names and method names.
     *
     */
    private boolean fNameAdjust = true;

    /**
     * Base class for request telegrams.
     */
    private String inputTelegramBase = null;
    /**
     * Base class for response telegrams.
     */
    private String outputTelegramBase = null;

    /**
     * Character encoding of auto-generated source files.
     */
    private String fEncoding = null;

    public void setEncoding(final String argEncoding) {
        fEncoding = argEncoding;
    }

    /**
     * Auto-generates source code from intermediate XML files.
     *
     * @param argMetaXmlSourceFile
     *            An XML file that contains meta-information.
     * @param argDirectoryTarget
     *            Output directory of the generated source code (specify the part excluding /main).
     * @throws IOException
     *             If an I/O exception occurs.
     */
    public void process(final File argMetaXmlSourceFile,
                        final File argDirectoryTarget)
            throws IOException, TransformerException {

//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGeneratorKtXml2SourceFile#process file = " + argMetaXmlSourceFile.getName());
//        }

        fNameAdjust = true; // The BlancoRestGenerator always transforms the field name.

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
            // Generates a util system.
            generateDeserializer(argDirectoryTarget);
        }

        for (int index = 0; index < processStructures.length; index++) {
            BlancoRestGeneratorKtTelegramProcessStructure processStructure = processStructures[index];
            // Generates Kotlin code from the obtained information.
            generate(processStructure, argDirectoryTarget);
        }
    }

    private void generateDeserializer(
            final File argDirectoryTarget
    ) {

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

        fCgFactory = BlancoCgObjectFactory.getInstance();
        final BlancoCgTransformer transformer = BlancoCgTransformerFactory
                .getKotlinSourceTransformer();
        transformer.transform(new  BlancoRestGeneratorKtRequestDeserializerClass(fCgFactory,
                BlancoRestGeneratorKtUtil.runtimePackage,
                this.fEncoding,
                this.fTabs).expand(), fileBlancoMain);
    }

    private void generate(final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure, final File argDirectoryTarget) {

        // The first step is to generate telegrams.
        Set<String> methodKeys = argProcessStructure.getListTelegrams().keySet(); // It should not be null because it is checked at time of parse.
        for (String methodKey : methodKeys) {
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> kindMap =
                    argProcessStructure.getListTelegrams().get(methodKey);
            Set<String> kindKeys = kindMap.keySet(); // It should not be null because it is checked at time of parse.
            for (String kindKey : kindKeys) {
                generateTelegram(kindMap.get(kindKey), argDirectoryTarget);
            }
            /* Generate Error Telegrams if telegramType is plain. */
            List<BlancoRestGeneratorKtTelegramStructure> errorTelegrams = argProcessStructure.getErrorTelegrams().get(methodKey);
            if (errorTelegrams != null && !errorTelegrams.isEmpty()) {
                for (BlancoRestGeneratorKtTelegramStructure errorTelegram : errorTelegrams) {
                    generateTelegram(errorTelegram, argDirectoryTarget);
                }    
            }
        }

        /*
         * Next, it generates the telegram processing.
         * Generates a controller for micronaut for now.
         * In the future, abstract classes for tomcat will also be supported.
         */
        if (BlancoRestGeneratorKtUtil.client != true) {
            /*
             * Server-side telegram processing.
             */
            generateMicronautController(argProcessStructure, argDirectoryTarget);
            /*
             * Then, generates the interface to be implemented by the class to be injected.
             */
            if (this.isCreateServiceMethod()) {
                generateApplicationInterface(argProcessStructure, argDirectoryTarget);
            }

            /*
             * Finally, generates the skeleton of the implementation class.
             * The package name of the skeleton is not overriden.
             */
            if (BlancoRestGeneratorKtUtil.genSkeleton &&
                    BlancoRestGeneratorKtUtil.impleDir != null &&
                    BlancoRestGeneratorKtUtil.impleDir.length() > 0 &&
                    BlancoRestGeneratorKtUtil.skeletonDelegateClass != null &&
                    BlancoRestGeneratorKtUtil.skeletonDelegateClass.length() > 0 &&
                    BlancoRestGeneratorKtUtil.skeletonDelegateInterface != null &&
                    BlancoRestGeneratorKtUtil.skeletonDelegateInterface.length() > 0
            ) {
                generateApplicationSkeleton(argProcessStructure);
            }
        }

        /*
         * Generates client-side telegram processing.
         * Normally, server-side code and client-side code are not generated in the same project, but just in case, we will assume that they are compatible.
         */
        if (BlancoRestGeneratorKtUtil.client){
            generateClientInterface(argProcessStructure, argDirectoryTarget);
        }

    }

    /**
     * Generates the interface to be implemented by the application implementation class.
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateApplicationInterface(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget
    ) {

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
//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess SATRT with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
//        }

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String myPackage = argProcessStructure.getPackage();
        if (argProcessStructure.getPackageSuffix() != null && argProcessStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argProcessStructure.getPackageSuffix();
        } else if (argProcessStructure.getOverridePackage() != null && argProcessStructure.getOverridePackage().length() > 0) {
            myPackage = argProcessStructure.getOverridePackage();
        }

        String interfacePackage = myPackage + ".interfaces";

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(interfacePackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // The import for use with micronaut and jackson is set to unconditional.
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        // Imports kinds of a telegram.
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");

        // Determines RequestHeader and ResponseHeader here.
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // Assumes that it is specified in fullPackage.
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }

        // Creates an interface.
        String applicationInterfaceId = "I" + argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;
        fCgInterface = fCgFactory.createInterface(applicationInterfaceId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getInterfaceList().add(fCgInterface);
        fCgInterface.setAccess("public");

        // Generates a ServiceMethod.
        createServiceMethods(argProcessStructure, "", requestHeaderIdSimple, responseHeaderIdSimple, true, false, false);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Generates a client-side interface.
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateClientInterface(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final File argDirectoryTarget
    ) {

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
//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess SATRT with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
//        }

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String myPackage = argProcessStructure.getPackage();
        if (argProcessStructure.getPackageSuffix() != null && argProcessStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argProcessStructure.getPackageSuffix();
        } else if (argProcessStructure.getOverridePackage() != null && argProcessStructure.getOverridePackage().length() > 0) {
            myPackage = argProcessStructure.getOverridePackage();
        }

        String interfacePackage = myPackage + ".interfaces";

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(interfacePackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // The import for use with micronaut and jackson is set to unconditional.
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Body");
        // Imports kinds of a telegram.
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");

        // Determines RequestHeader and ResponseHeader here.
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // Assumes that it is specified in fullPackage.
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }

        // Creates an interface.
        String applicationInterfaceId = "I" + argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_CLIENT;
        fCgInterface = fCgFactory.createInterface(applicationInterfaceId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getInterfaceList().add(fCgInterface);
        fCgInterface.setAccess("public");

        // Sets the Client annotation.
        for (String annotation : argProcessStructure.getClientAnnotationList()) {
            fCgInterface.getAnnotationList().add(annotation);
        }
        // The Client annotation must be present. If other annotations are used, the package name must be specified.
        fCgSourceFile.getImportList().add("io.micronaut.http.client.annotation.Client");

        // Generates a ServiceMethod.
        createServiceMethods(argProcessStructure, "", requestHeaderIdSimple, responseHeaderIdSimple, true, false, true);

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Generates a telegram processing class.
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    private void generateMicronautController(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
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
//        if (this.isVerbose()) {
//            System.out.println("BlancoRestGenerateTsXml2SourceFile#generateTelegramProcess SATRT with argDirectoryTarget : " + argDirectoryTarget.getAbsolutePath());
//        }

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String myPackage = argProcessStructure.getPackage();
        if (argProcessStructure.getPackageSuffix() != null && argProcessStructure.getPackageSuffix().length() > 0) {
            myPackage = myPackage + "." + argProcessStructure.getPackageSuffix();
        } else if (argProcessStructure.getOverridePackage() != null && argProcessStructure.getOverridePackage().length() > 0) {
            myPackage = argProcessStructure.getOverridePackage();
        }

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(myPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // The import for use with micronaut and jackson is set to unconditional.
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpRequest");
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Controller");
        // Imports kinds of a telegram.
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        String runtimePkg = BlancoRestGeneratorKtUtil.runtimePackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");
        fCgSourceFile.getImportList().add(runtimePkg + ".util.BlancoRestGeneratorKtRequestDeserializer");

        // Creates a class.
        String controllerClassId = argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_CONTROLLER;
        fCgClass = fCgFactory.createClass(controllerClassId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        // The telegram processing class is always public final.
        fCgClass.setAccess("public");
        fCgClass.setFinal(true);
        // Inheritance
        if (BlancoStringUtil.null2Blank(argProcessStructure.getExtends())
                .length() > 0) {
            fCgClass.getExtendClassList().add(
                    fCgFactory.createType(argProcessStructure.getExtends()));
        }
        // Implementation
        for (int index = 0; index < argProcessStructure.getImplementsList()
                .size(); index++) {
            final String impl = (String) argProcessStructure.getImplementsList()
                    .get(index);
            fCgClass.getImplementInterfaceList().add(
                    fCgFactory.createType(impl));
        }
        // Sets the JavaDoc for the class.
        fCgClass.setDescription(argProcessStructure.getDescription());

        /* Sets the annotation for the class. */
        List annotationList = argProcessStructure.getAnnotationList();
        if (annotationList != null && annotationList.size() > 0) {
            fCgClass.getAnnotationList().addAll(argProcessStructure.getAnnotationList());
            /* tueda DEBUG */
            if (this.isVerbose()) {
                System.out.println("generateTelegramProcess : class annotation = " + argProcessStructure.getAnnotationList().get(0));
            }
        }

        /*
         * The Controller annotation is always set.
         */
        String location = BlancoStringUtil.null2Blank(argProcessStructure.getLocation());

        /*
         * If overrideLocation is specified, it takes precedence.
         */
        String overrideLocation = argProcessStructure.getOverrideLocation();
        if (overrideLocation != null && overrideLocation.length() > 0) {
            location = overrideLocation;
        }

        String serviceId = BlancoStringUtil.null2Blank(argProcessStructure.getServiceId());
        String controllerAnn = "Controller";
        if (serviceId.length() > 0) {
            controllerAnn += "(\"" + location + "/" + serviceId + "\")";
        }
        fCgClass.getAnnotationList().add(controllerAnn);

        /*
         * Injects the implementation class for each API.
         */
        String applicationClassId = argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;
        String applicationClassPackage = argProcessStructure.getPackage() + "." + BlancoRestGeneratorKtConstants.MANAGER_PACKAGE;

        String injectedParameterId = BlancoNameAdjuster.toParameterName(applicationClassId);

        BlancoCgField impleClass = fCgFactory.createField(
                injectedParameterId,
                applicationClassPackage + "." + applicationClassId,
                "The implementation class to be called by " + controllerClassId + "."
        );
        impleClass.setNotnull(true);
        /* Always final because Controller is always final class. */
        impleClass.setFinal(true);
        fCgClass.getConstructorArgList().add(impleClass);

        // Generates a service method.
        // Determines RequestHeader and ResponseHeader here.
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // Assumes that it is specified in fullPackage.
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }
        if (this.isCreateServiceMethod()) {
            createServiceMethods(argProcessStructure, injectedParameterId, requestHeaderIdSimple, responseHeaderIdSimple, false, false, false);
        } else {
            if (this.isVerbose()) {
                System.out.println("BlancoRestGeneratorKtXml2SourceFile#generateTelegramProcess: SKIP SERVICE METHOD!");
            }
        }

        /* Sets the import for the class. */
        for (int index = 0; index < argProcessStructure.getImportList()
                .size(); index++) {
            final String imported = (String) argProcessStructure.getImportList()
                    .get(index);
            fCgSourceFile.getImportList().add(imported);
        }

        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Implements the Service method.
     * @param argProcessStructure
     * @param isInterface
     * @param isSkeleton
     * @param isClient
     */
    private void createServiceMethods(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
            final String argInjectedParameterId,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            final Boolean isInterface,
            final Boolean isSkeleton,
            final Boolean isClient) {

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
                /* This method is unsupported. */
//                createExecuteMethodNotImplemented(method);
                /*
                 * In Micronaut support, unsupported methods are not described.
                 */
            } else {
                if (!isInterface) {
                    createExecuteMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getNoAuxiliaryAuthentication(), argProcessStructure.getMetaIdList(), argProcessStructure.getGetRequestBindList());
                } else {
                    BlancoCgMethod cgMethod = isClient ?
                            createClientMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple, argProcessStructure.getLocation() + "/" + argProcessStructure.getServiceId()) :
                            createExecuteMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple, isSkeleton);
                    if (isSkeleton) {
                        fCgClass.getMethodList().add(cgMethod);
                    } else {
                        fCgInterface.getMethodList().add(cgMethod);
                    }
                }
            }
        }
    }

    private void createExecuteMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            final String argInjectedParameterId,
            final Boolean argNoAuthentication,
            final Boolean argNoAuxiliaryAuthentication,
            final List<String> argMetaIdList,
            final List<BlancoRestGeneratorKtGetRequestBindStructure> argGetRequestBindList
    ) {

        boolean isDeleteMethod = false;
        String executeMethodId = "";
        String methodAnn = "";
        String strMethodName = argMethod.split("_")[2];
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.GET_CONTROLLER_METHOD;
            methodAnn = "Get";
            if (isArgGetRequestBind(argMethod, argGetRequestBindList)) {
                boolean isFirstQuery = true;
                methodAnn += "(\"";
                for (BlancoRestGeneratorKtGetRequestBindStructure structure : argGetRequestBindList) {
                    if (Objects.equals(structure.getKind(), "path")) {
                        methodAnn += "/{" + structure.getName() + "}";
                    } else {
                        if (isFirstQuery) {
                            isFirstQuery = false;
                            methodAnn += "{?" + structure.getName();
                        } else {
                            methodAnn += "," + structure.getName();
                        }
                    }
                }
                if (!isFirstQuery) {
                    methodAnn += "}";
                }
                methodAnn += "\")";

                fCgSourceFile.getImportList().add("io.micronaut.http.annotation.RequestBean");
            } else {
                fCgSourceFile.getImportList().add("io.micronaut.http.annotation.QueryValue");
            }
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Get");

        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.POST_CONTROLLER_METHOD;
            methodAnn = "Post";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Post");
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Body");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(argMethod)) {
            executeMethodId = BlancoRestGeneratorKtConstants.PUT_CONTROLLER_METHOD;
            methodAnn = "Put";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Put");
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Body");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.equals(argMethod)) {
            isDeleteMethod = true;
            executeMethodId = BlancoRestGeneratorKtConstants.DELETE_CONTROLLER_METHOD;
            methodAnn = "Delete";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Delete");
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.QueryValue");
        }

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                executeMethodId, fBundle.getXml2sourceFileExecutorDescription());
        fCgClass.getMethodList().add(cgExecutorMethod);
        cgExecutorMethod.setFinal(true);

        /*
         * Sets the annotation.
         */
        cgExecutorMethod.getAnnotationList().add(methodAnn);

        /*
         * First, generates the HttpRequest parameter.
         */
        BlancoCgParameter httpRequest = fCgFactory.createParameter(
                "argHttpRequest",
                "io.micronaut.http.HttpRequest",
                fBundle.getXml2sourceFileExecutorArgLangdoc()
        );
        cgExecutorMethod.getParameterList().add(httpRequest);
        httpRequest.setNotnull(true);

        /*
         * For packages in which kinds of a telegram are defined, it is assumed that they are explicitly imported in the API definition document. (or the same package)
         */
        BlancoRestGeneratorKtTelegramStructure inputTelegram = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        String requestId = inputTelegram.getName();
        if (this.isVerbose()) {
            System.out.println("### requestId = " + requestId);
        }

        String commonRequestId = "CommonRequest";

        /*
         * With the Kotlin transformer, the "<>" of the first layer of generics is automatically added and the package is removed.
         */
        String requestGenerics = "";
        if (argRequestHeaderIdSimple == null || argRequestHeaderIdSimple.length() <= 0) {
            requestGenerics = commonRequestId + "<" + requestId + ">";
        } else {
            requestGenerics = commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + ">";
        }
        String savedRequestGenerics = requestGenerics;
        if (isDeleteMethod) {
            requestGenerics = "*";
        }
        httpRequest.getType().setGenerics(
                requestGenerics
        );

        /* Gets the body or queryString (request). */
        String rawJsonId = "argBody";
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(argMethod) ||
                BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(argMethod)) {
            /*
             * Next, generates a parameter to get the body.
             */
            rawJsonId = "argBody";
            BlancoCgParameter body = fCgFactory.createParameter(
                    rawJsonId,
                    "java.lang.String",
                    "Raw JSON body."
            );
            cgExecutorMethod.getParameterList().add(body);
            body.setNotnull(true);
            body.getAnnotationList().add("Body");
        } else if (isArgGetRequestBind(argMethod, argGetRequestBindList)) {
            /*
             *  generates a parameter to get the bean.
             */
            rawJsonId = "bean";
            BlancoCgParameter parameter = fCgFactory.createParameter(
                    rawJsonId,
                    requestId,
                    "get parameter set as bean."
            );
            cgExecutorMethod.getParameterList().add(parameter);
            parameter.setNotnull(true);
            parameter.getAnnotationList().add("RequestBean");
        } else {
            /*
             * Then, generates a parameter to get the queryString.
             */
            rawJsonId = "request";
            BlancoCgParameter parameter = fCgFactory.createParameter(
                    rawJsonId,
                    "java.lang.String",
                    "Raw JSON set as queryString."
            );
            cgExecutorMethod.getParameterList().add(parameter);
            parameter.setNotnull(true);
            parameter.getAnnotationList().add("QueryValue");
        }

        /*
         * Sets the Return type.
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

        // Implements the method body.
        final List<String> listLine = cgExecutorMethod.getLineList();

        String requestDeserializerId = BlancoRestGeneratorKtUtil.runtimePackage + ".util.BlancoRestGeneratorKtRequestDeserializer";
        fCgSourceFile.getImportList().add(requestDeserializerId);
        String requestDeserializerIdSimple = "BlancoRestGeneratorKtRequestDeserializer";

        /*
         * Implement spoiled option.
         * ApiSpoilException should be implemented manually.
         */
        if (inputTelegram.getImpleSpoiled()) {
            listLine.add("/*");
            listLine.add(" * Test method is spoiled or not.");
            listLine.add(" * ApiSpoilException should be implemented manually.");
            listLine.add(" */");
            listLine.add("if (" + argInjectedParameterId + ".isSpoiled(\"" + strMethodName + "\")) {");
            listLine.add("throw ApiSpoilException()");
            listLine.add(" }");
        }

        /*
         * Generates a deserializer.
         */
        if (!isArgGetRequestBind(argMethod, argGetRequestBindList)) {
            listLine.add("/* Creates a CommonRequest instance from a JSON string. */");
            listLine.add("val deserializer = " + requestDeserializerIdSimple + "<" + argRequestHeaderIdSimple + ", " + requestId + ">(CommonRequest::class.java)");
            listLine.add("deserializer.infoClazz = " + argRequestHeaderIdSimple + "::class.java");
            listLine.add("deserializer.telegramClazz = " + requestId + "::class.java");
            listLine.add("");
        }

        /*
         * Generates a HttpCommonRequest.
         */

        listLine.add("/* Creates HttpCommonRequest with httpRequest as delegator. */");
        listLine.add("/* At this stage, commonRequest is tentative.*/");

        /*
         * Whether the API requires authentication.
         */
        String noAuthentication = argNoAuthentication ? "true" : "false";

        /*
         * If there is, sets MetaIdList.
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

        String deleteRequestCast = "";
        if (isDeleteMethod) {
            listLine.add("@Suppress(\"UNCHECKED_CAST\")");
            deleteRequestCast = " as HttpRequest<" + savedRequestGenerics + ">";
        }
        listLine.add("val httpCommonRequest = HttpCommonRequest<" + commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + ">>(argHttpRequest" + deleteRequestCast + ", " + noAuthentication + ", listOf(" + metaIdList + "), null)");
        /*
         * Whether or not auxiliary authentication is required.
         */
        if (argNoAuxiliaryAuthentication) {
            listLine.add("httpCommonRequest.noAuxiliaryAuthentication = true");
        }
        listLine.add("");

        /*
         * Generates a commonRequest.
         *
         */
        if (isArgGetRequestBind(argMethod, argGetRequestBindList)) {
            listLine.add("val info = " + argRequestHeaderIdSimple + "()");
            listLine.add("val commonRequest: " + commonRequestId + "<" + argRequestHeaderIdSimple + "," + requestId + "> = " + commonRequestId + "(info, bean)");
        } else {
            listLine.add("val commonRequest: " + commonRequestId + "<" + argRequestHeaderIdSimple + ", " + requestId + "> = " + argInjectedParameterId + ".convertJsonToCommonRequest(" + rawJsonId + ", deserializer, httpCommonRequest)");
        }
        listLine.add("");

        listLine.add("/* Stores the commonRequest with its type determined */");
        listLine.add("httpCommonRequest.commonRequest = commonRequest");
        listLine.add("");

        listLine.add("/* Performs preprocessing (validation, etc.) */");
        listLine.add(argInjectedParameterId + ".prepare(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Passes HttpCommonRequest */");
        listLine.add("val httpResponse = " + argInjectedParameterId + "." + executeMethodId + "(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Postprocessing */");
        listLine.add(argInjectedParameterId + ".finish(httpResponse, httpCommonRequest)");
        listLine.add("");
        listLine.add("return httpResponse");
    }

    private BlancoCgMethod createExecuteMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            final Boolean isSkelton) {

        String executeMethodId = this.getExecuteMethodId(argMethod);

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                executeMethodId, fBundle.getXml2sourceFileExecutorDescription());
//        fCgClass.getMethodList().add(cgExecutorMethod);
        if (isSkelton) {
            cgExecutorMethod.setOverride(true);
            cgExecutorMethod.setFinal(true);
        }

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

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String overridePackage = requestStructure.getPackage();
        if (requestStructure.getPackageSuffix() != null && requestStructure.getPackageSuffix().length() > 0) {
            overridePackage = overridePackage + "." + requestStructure.getPackageSuffix();
        } else if (requestStructure.getOverridePackage() != null && requestStructure.getOverridePackage().length() > 0) {
            overridePackage = requestStructure.getOverridePackage();
        }

        String requestIdPackage = overridePackage;
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
        String responseIdPackage = overridePackage;
        String responseId = responseIdPackage + "." + responseIdSimple;
        fCgSourceFile.getImportList().add(responseId);

        String responseGenerics = "";
        if (argResponseHeaderIdSimple == null || argResponseHeaderIdSimple.length() <= 0) {
            responseGenerics = commonResponseId + "<" + responseIdSimple + ">";
        } else {
            responseGenerics = commonResponseId + "<" + argResponseHeaderIdSimple + ", " + responseIdSimple + ">";
        }
        cgReturn.getType().setGenerics(responseGenerics);

        if (isSkelton) {
            // In the case of skeleton, generates the method body.
            List<String> lineList = cgExecutorMethod.getLineList();
            lineList.add("TODO(\"Not yet implemented\")");
        }

        return cgExecutorMethod;
    }

    private BlancoCgMethod createClientMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
            final String argRequestHeaderIdSimple,
            final String argResponseHeaderIdSimple,
            final String argTargetpoint) {

        String executeMethodId = this.getExecuteMethodId(argMethod);

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                executeMethodId, fBundle.getXml2sourceFileClientExecutorDescription());

        /* Annotation */
        String methodAnn = "";
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod)) {
            methodAnn = "Get";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Get");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(argMethod)) {
            methodAnn = "Post";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Post");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(argMethod)) {
            methodAnn = "Put";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Put");
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.equals(argMethod)) {
            methodAnn = "Delete";
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Delete");
        }
        if (BlancoStringUtil.null2Blank(argTargetpoint).length() > 0) {
            methodAnn = methodAnn + "(\"" + argTargetpoint + "\")";
        }
        cgExecutorMethod.getAnnotationList().add(methodAnn);

        /* request */
        String commonRequestId = BlancoRestGeneratorKtUtil.telegramPackage + ".CommonRequest";

        BlancoCgParameter httpRequest = fCgFactory.createParameter(
                "commonRequest",
                commonRequestId,
                fBundle.getXml2sourceFileClientExecutorArgLangdoc()
        );
        cgExecutorMethod.getParameterList().add(httpRequest);
        httpRequest.setNotnull(true);
        httpRequest.getAnnotationList().add("Body");

        BlancoRestGeneratorKtTelegramStructure requestStructure = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        String requestIdSimple = requestStructure.getName();
        if (this.isVerbose()) {
            System.out.println("### client requestId = " + requestIdSimple);
        }

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String overridePackage = requestStructure.getPackage();
        if (requestStructure.getPackageSuffix() != null && requestStructure.getPackageSuffix().length() > 0) {
            overridePackage = overridePackage + "." + requestStructure.getPackageSuffix();
        } else if (requestStructure.getOverridePackage() != null && requestStructure.getOverridePackage().length() > 0) {
            overridePackage = requestStructure.getOverridePackage();
        }

        String requestIdPackage = overridePackage;
        String requestId = requestIdPackage + "." + requestIdSimple;
        fCgSourceFile.getImportList().add(requestId);

        String requestGenerics = "";
        if (argRequestHeaderIdSimple == null || argRequestHeaderIdSimple.length() <= 0) {
            requestGenerics = requestIdSimple;
        } else {
            requestGenerics = argRequestHeaderIdSimple + ", " + requestIdSimple;
        }
        httpRequest.getType().setGenerics(requestGenerics);

        /* response */
        String commonResponseId = "CommonResponse";

        BlancoCgReturn cgReturn = fCgFactory.createReturn(
                "io.micronaut.http.HttpResponse",
                fBundle.getXml2sourceFileClientExecutorReturnLangdoc()
        );
        cgExecutorMethod.setReturn(cgReturn);

        BlancoRestGeneratorKtTelegramStructure responseStructure = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT);
        String responseIdSimple = responseStructure.getName();
        if (this.isVerbose()) {
            System.out.println("### client responseId = " + responseIdSimple);
        }
        String responseIdPackage = overridePackage;
        String responseId = responseIdPackage + "." + responseIdSimple;
        fCgSourceFile.getImportList().add(responseId);

        String responseGenerics = "";
        if (argResponseHeaderIdSimple == null || argResponseHeaderIdSimple.length() <= 0) {
            responseGenerics = commonResponseId + "<" + responseIdSimple + ">";
        } else {
            responseGenerics = commonResponseId + "<" + argResponseHeaderIdSimple + ", " + responseIdSimple + ">";
        }
        cgReturn.getType().setGenerics(responseGenerics);

        return cgExecutorMethod;
    }


    private String getExecuteMethodId(final String argMethod) {
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

    /**
     * Generates a telegram class.
     *
     * @param argTelegramStructure
     * @param argDirectoryTarget
     */
    private void generateTelegram(
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

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(myPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
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
        List annotationList = argTelegramStructure.getAnnotationList();
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

    /**
     * Generates a field in the class.
     * @param argClassStructure
     *            Class information.
     * @param argFieldStructure
     */
    private void buildField(
            final BlancoRestGeneratorKtTelegramStructure argClassStructure,
            final BlancoRestGeneratorKtTelegramFieldStructure argFieldStructure) {

        switch (fSheetLang) {
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
            if (!isConstArg && (defaultRawValue == null || defaultRawValue.length() <= 0)) {
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
        List annotationList = argFieldStructure.getAnnotationListKt();
        if (annotationList == null || annotationList.size() == 0) {
            annotationList = argFieldStructure.getAnnotationList();
        }
        if (annotationList != null && annotationList.size() > 0) {
            field.getAnnotationList().addAll(annotationList);
            System.out.println("/* tueda */ method annotation = " + field.getAnnotationList().get(0));
        }
    }

    private void buildPermissionKindField(String value) {
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
     * Generates the skeleton of the application implementation class.
     * @param argProcessStructure
     */
    private void generateApplicationSkeleton(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure
    ) {
        String applicationPackage = argProcessStructure
                .getPackage() + "." + BlancoRestGeneratorKtConstants.MANAGER_PACKAGE;
        String applicationClassId = argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;

        String applicationFileName = BlancoRestGeneratorKtUtil.impleDir + "/" + applicationPackage.replace(".", "/") + "/" + applicationClassId + ".kt";
        /*
         * If the file already exists, it will not be created.
         */
        File classFile = new File(applicationFileName);
        if (classFile.exists()) {
            System.out.println(applicationClassId + " exists. Skips it.");
            return;
        } else {
            if (this.isVerbose()) {
                System.out.println("Creates " + applicationClassId + ".");
            }
        }

        final File fileBlancoMain = new File(BlancoRestGeneratorKtUtil.impleDir);

        fCgFactory = BlancoCgObjectFactory.getInstance();
        fCgSourceFile = fCgFactory.createSourceFile(applicationPackage, "This source code has been generated by blanco Framework.");
        fCgSourceFile.setEncoding(fEncoding);
        fCgSourceFile.setTabs(this.getTabs());

        // The import for use with micronaut is set to unconditional.
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        // Imports kinds of a telegram.
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonRequest");
        fCgSourceFile.getImportList().add(telegramPkg + ".CommonResponse");

        // Determines RequestHeader and ResponseHeader here.
        String requestHeaderClass = argProcessStructure.getRequestHeaderClass();
        String responseHeaderClass = argProcessStructure.getResponseHeaderClass();
        String requestHeaderIdSimple = null;
        if (requestHeaderClass != null && requestHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(requestHeaderClass); // Assumes that it is specified in fullPackage.
            requestHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(requestHeaderClass);
        }
        String responseHeaderIdSimple = null;
        if (responseHeaderClass != null && responseHeaderClass.length() > 0) {
            fCgSourceFile.getImportList().add(responseHeaderClass);
            responseHeaderIdSimple = BlancoRestGeneratorKtUtil.getSimpleClassName(responseHeaderClass);
        }

        fCgClass = fCgFactory.createClass(applicationClassId,
                BlancoStringUtil.null2Blank(argProcessStructure
                        .getDescription()));
        fCgSourceFile.getClassList().add(fCgClass);
        fCgClass.setAccess("public");
        // It is always @Singleton.
        fCgClass.getAnnotationList().add("Singleton");
        fCgSourceFile.getImportList().add("javax.inject.Singleton");

        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String overridePackage = argProcessStructure.getPackage();
        if (argProcessStructure.getPackageSuffix() != null && argProcessStructure.getPackageSuffix().length() > 0) {
            overridePackage = overridePackage + "." + argProcessStructure.getPackageSuffix();
        } else if (argProcessStructure.getOverridePackage() != null && argProcessStructure.getOverridePackage().length() > 0) {
            overridePackage = argProcessStructure.getOverridePackage();
        }

        // The first step is to implement an interface.
        String interfacePackage = overridePackage + ".interfaces";
        // Creates an interface.
        String applicationInterfaceId = "I" + argProcessStructure.getName() + BlancoRestGeneratorKtConstants.SUFFIX_MANAGER;
        BlancoCgType interfaceType = fCgFactory.createType(
                interfacePackage + "." + applicationInterfaceId
        );
        fCgClass.getImplementInterfaceList().add(
                interfaceType
        );

        // Creates a delegate.
        String delegateInterface = BlancoRestGeneratorKtUtil.skeletonDelegateInterface;
        BlancoCgType cgDeleInterface = fCgFactory.createType(delegateInterface);
        fCgClass.getImplementInterfaceList().add(cgDeleInterface);
        fCgClass.getDelegateMap().put(cgDeleInterface.getName(), "apiBase");

        // Generates the corresponding constructor arguments.
        String delegateClass = BlancoRestGeneratorKtUtil.skeletonDelegateClass;
        BlancoCgType cgDeleClass = fCgFactory.createType(delegateClass);
        BlancoCgField cgField = fCgFactory.createField("apiBase", delegateClass, "This is a delegate class that defines the common processing of the Application class.");
        fCgClass.getConstructorArgList().add(cgField);
        cgField.setNotnull(true);

        // Generates a ServiceMethod.
        createServiceMethods(argProcessStructure, "", requestHeaderIdSimple, responseHeaderIdSimple, true, true, false);

        if (this.isVerbose()) {
            System.out.println("Generates the skeleton.");
            System.out.println("Settled  : " + fileBlancoMain.getAbsolutePath());
            System.out.println("package  : " + fCgSourceFile.getPackage());
            System.out.println("ClassName: " + fCgSourceFile.getClassList().get(0).getName());
        }
        // Auto-generates the actual source code based on the collected information.
        BlancoCgTransformerFactory.getKotlinSourceTransformer().transform(
                fCgSourceFile, fileBlancoMain);
    }

    /**
     * Gets the adjusted field name.
     *
     * @param argFieldStructure
     *            Field information.
     * @return Adjusted field name.
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

    private boolean isArgGetRequestBind(
            String argMethod,
            List<BlancoRestGeneratorKtGetRequestBindStructure> argGetRequestBindList
    ) {
        boolean result = false;
        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(argMethod) && !argGetRequestBindList.isEmpty()) {
            result = true;
        }

        return result;
    }
}
