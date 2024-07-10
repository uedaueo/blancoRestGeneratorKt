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

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.BlancoCgSupportedLang;
import blanco.cg.BlancoCgTransformer;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;

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
     * Whether to adjust field names and method names.
     *
     */
    private boolean fNameAdjust = true;

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

        BlancoRestGeneratorKtExpander expander = new BlancoRestGeneratorKtBlancoStyleExpander();
        if (BlancoRestGeneratorKtUtil.isTelegramStylePlain) {
            expander = new BlancoRestGeneratorKtPlainStyleExpander();
        }

        expander.setEncoding(this.fEncoding);
        expander.setSheetLang(this.fSheetLang);
        expander.setTargetStyleAdvanced(this.fTargetStyleAdvanced);
        expander.setVerbose(this.fVerbose);
        expander.setCreateServiceMethod(this.fCreateServiceMethod);
        expander.setTabs(this.getTabs());
        expander.setServerType(this.getServerType());
        expander.setTelegramPackage(this.fTelegramPackage);
        expander.setNameAdjust(this.fNameAdjust);

        for (int index = 0; index < processStructures.length; index++) {
            BlancoRestGeneratorKtTelegramProcessStructure processStructure = processStructures[index];
            // Generates Kotlin code from the obtained information.
            if (BlancoRestGeneratorKtUtil.isVerbose) {
                System.out.println("BlancoRestGeneratorKt: style = " + BlancoRestGeneratorKtUtil.telegramStyle + ", processing " + processStructure.getName());
            }
            expander.expand(processStructure, argDirectoryTarget);
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
}
