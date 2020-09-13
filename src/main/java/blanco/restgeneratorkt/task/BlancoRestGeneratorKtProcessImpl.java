/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.restgeneratorkt.task;

import blanco.cg.BlancoCgSupportedLang;
import blanco.restgeneratorkt.BlancoRestGeneratorKtConstants;
import blanco.restgeneratorkt.BlancoRestGeneratorKtMeta2Xml;
import blanco.restgeneratorkt.BlancoRestGeneratorKtUtil;
import blanco.restgeneratorkt.BlancoRestGeneratorKtXml2SourceFile;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class BlancoRestGeneratorKtProcessImpl implements
        BlancoRestGeneratorKtProcess {
    /**
     * このプロダクトのリソースバンドルへのアクセスオブジェクト。
     */
    private final BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();

    /**
     * {@inheritDoc}
     */
    public int execute(final BlancoRestGeneratorKtProcessInput input) {
        System.out.println("- " + BlancoRestGeneratorKtConstants.PRODUCT_NAME
                + " (" + BlancoRestGeneratorKtConstants.VERSION + ")" + " for " + input.getSheetType());

        try {
            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fBundle
                        .getAnttaskErr001(input.getMetadir()));
            }

            /*
             * targetdir, targetStyleの処理。
             * 生成されたコードの保管場所を設定する。
             * targetstyle = blanco:
             *  targetdirの下に main ディレクトリを作成
             * targetstyle = maven:
             *  targetdirの下に main/java ディレクトリを作成
             * targetstyle = free:
             *  targetdirをそのまま使用してディレクトリを作成。
             *  ただしtargetdirがからの場合はデフォルト文字列(blanco)使用する。
             * by tueda, 2019/08/30
             */
            String strTarget = input.getTargetdir();
            String style = input.getTargetStyle();
            // ここを通ったら常にtrue
            boolean isTargetStyleAdvanced = true;

            if (style != null && BlancoRestGeneratorKtConstants.TARGET_STYLE_MAVEN.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorKtConstants.TARGET_DIR_SUFFIX_MAVEN;
            } else if (style == null ||
                    !BlancoRestGeneratorKtConstants.TARGET_STYLE_FREE.equals(style)) {
                strTarget = strTarget + "/" + BlancoRestGeneratorKtConstants.TARGET_DIR_SUFFIX_BLANCO;
            }
            /* style が free だったらtargetdirをそのまま使う */
//            if (input.getVerbose()) {
//                System.out.println("BlancoRestGeneratorKtProcessImpl#process TARGETDIR = " + strTarget);
//            }

            BlancoRestGeneratorKtUtil.encoding = input.getEncoding();
            BlancoRestGeneratorKtUtil.isVerbose = input.getVerbose();
            BlancoRestGeneratorKtUtil.basePackage = input.getBasepackage();
            if (BlancoRestGeneratorKtUtil.basePackage == null || BlancoRestGeneratorKtUtil.basePackage.length() == 0) {
                throw new IllegalArgumentException(fBundle.getAnttaskErr002());
            }
            if (input.getRuntimepackage() != null && input.getRuntimepackage().length() > 0) {
                BlancoRestGeneratorKtUtil.runtimePackage = input.getRuntimepackage();
            } else {
                BlancoRestGeneratorKtUtil.runtimePackage = BlancoRestGeneratorKtUtil.basePackage;
            }
            BlancoRestGeneratorKtUtil.genUtils = input.getGenUtils();

            /*
             * validator を作る時に使うために，
             * ValueObject で既に定義されている（はずの）オブジェクトを取得しておく
             */
            BlancoRestGeneratorKtUtil.processValueObjects(input);

            // テンポラリディレクトリを作成。
            new File(input.getTmpdir()
                    + BlancoRestGeneratorKtConstants.TARGET_SUBDIRECTORY)
                    .mkdirs();

            // 指定されたメタディレクトリを処理します。
            new BlancoRestGeneratorKtMeta2Xml()
                    .processDirectory(fileMetadir, input.getTmpdir()
                            + BlancoRestGeneratorKtConstants.TARGET_SUBDIRECTORY);

            // XML化された中間ファイルからソースコードを生成
            final File[] fileMeta2 = new File(input.getTmpdir()
                    + BlancoRestGeneratorKtConstants.TARGET_SUBDIRECTORY)
                    .listFiles();
            for (int index = 0; index < fileMeta2.length; index++) {
                if (fileMeta2[index].getName().endsWith(".xml") == false) {
                    continue;
                }

                final BlancoRestGeneratorKtXml2SourceFile xml2source = new BlancoRestGeneratorKtXml2SourceFile();
                xml2source.setEncoding(input.getEncoding());
                xml2source.setSheetLang(new BlancoCgSupportedLang().convertToInt(input.getSheetType()));
                xml2source.setTargetStyleAdvanced(isTargetStyleAdvanced);
                xml2source.setVerbose(input.getVerbose());
                xml2source.setCreateServiceMethod(!input.getClient());
                xml2source.setTabs(input.getTabs());
                xml2source.setServerType(input.getServerType());
                xml2source.process(fileMeta2[index], new File(strTarget));
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.toString());
        } catch (TransformerException ex) {
            throw new IllegalArgumentException(ex.toString());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
