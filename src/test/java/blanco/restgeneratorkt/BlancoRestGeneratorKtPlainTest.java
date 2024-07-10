/*
 * blanco Framework
 * Copyright (C) 2004-2020 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorkt;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import blanco.restgeneratorkt.task.BlancoRestGeneratorKtProcessImpl;
import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;
import blanco.valueobjectkt.task.BlancoValueObjectKtProcessImpl;
import blanco.valueobjectkt.task.valueobject.BlancoValueObjectKtProcessInput;

/**
 * Java言語用の生成試験。
 *
 * @author IGA Tosiki
 * @author tueda
 */
public class BlancoRestGeneratorKtPlainTest {

    @Test
    public void testBlancoRestGeneratorPlain() {
        /*
         * まず ValueObject を生成します。
         */
        this.testGenerateValueObjects();

        /*
         * その後、電文と電文処理を生成します。
         */
        this.testGeneratorProcessesPlain();
    }

    private void testGenerateValueObjects() {
        System.out.println("!!! Plain Test Starts !!!");
        /*
         * まず ValueObject を生成します。
         */
        BlancoValueObjectKtProcessInput inputValueObject = new BlancoValueObjectKtProcessInput();
        inputValueObject.setMetadir("meta/telegrams");
        inputValueObject.setEncoding("UTF-8");
        inputValueObject.setSheetType("php");
        inputValueObject.setTmpdir("tmpTest");
        inputValueObject.setTargetdir("sample/blanco");
        inputValueObject.setTargetStyle("maven");
        inputValueObject.setVerbose(true);

        BlancoValueObjectKtProcessImpl impleValueObject = new BlancoValueObjectKtProcessImpl();
        try {
            impleValueObject.execute(inputValueObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testGeneratorProcessesPlain() {
        /*
         * その後、電文と電文処理を生成します。
         */
        BlancoRestGeneratorKtProcessInput inputRestGenerator = new BlancoRestGeneratorKtProcessInput();
        inputRestGenerator.setMetadir("meta/plainApi");
        inputRestGenerator.setEncoding("UTF-8");
        inputRestGenerator.setSheetType("php");
        inputRestGenerator.setTmpdir("tmpTest");
        inputRestGenerator.setTargetdir("sample/blanco");
        inputRestGenerator.setTargetStyle("maven");
        inputRestGenerator.setNameAdjust(true);
        inputRestGenerator.setClient(false);
        inputRestGenerator.setVerbose(true);
        inputRestGenerator.setLineSeparator("LF");
        inputRestGenerator.setServerType("micronaut");
        inputRestGenerator.setBasepackage("blanco.restgenerator");
        inputRestGenerator.setTelegrampackage("blanco.restgenerator.valueobject");
        inputRestGenerator.setGenSkeleton(true);
        inputRestGenerator.setImpledir("sample/blanco/main/kotlin");
        inputRestGenerator.setSkeletonDelegateClass("blanco.restgenerator.application.ApiBase");
        inputRestGenerator.setSkeletonDelegateInterface("blanco.restgenerator.application.IApiBase");
//        inputRestGenerator.setPackageSuffix("blanco");
        inputRestGenerator.setOverrideLocation("/api/sample/blanco");
        inputRestGenerator.setTelegramStyle("plain");
        inputRestGenerator.setAppendApplicationPackage(false);
        inputRestGenerator.setSerdeable(true);
        inputRestGenerator.setIgnoreUnknown(false);
        inputRestGenerator.setNullableAnnotation(true);
        inputRestGenerator.setIsTargetJaraktaEE(true);

        BlancoRestGeneratorKtProcessImpl imple = new BlancoRestGeneratorKtProcessImpl();
        imple.execute(inputRestGenerator);
    }
}
