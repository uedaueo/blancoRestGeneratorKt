package blanco.restgeneratorkt.task;

import java.io.IOException;

import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;

/**
 * Batch process class [BlancoRestGeneratorKtBatchProcess].
 *
 * <P>Example of a batch processing call.</P>
 * <code>
 * java -classpath (classpath) blanco.restgeneratorkt.task.BlancoRestGeneratorKtBatchProcess -help
 * </code>
 */
public class BlancoRestGeneratorKtBatchProcess {
    /**
     * Normal end.
     */
    public static final int END_SUCCESS = 0;

    /**
     * Termination due to abnormal input. In the case that java.lang.IllegalArgumentException is raised internally.
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * Termination due to I/O exception. In the case that java.io.IOException is raised internally.
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * Abnormal end. In the case that batch process fails to start or java.lang.Error or java.lang.RuntimeException is raised internally.
     */
    public static final int END_ERROR = 9;

    /**
     * The entry point when executed from the command line.
     *
     * @param args Agruments inherited from the console.
     */
    public static final void main(final String[] args) {
        final BlancoRestGeneratorKtBatchProcess batchProcess = new BlancoRestGeneratorKtBatchProcess();

        // Arguments for batch process.
        final BlancoRestGeneratorKtProcessInput input = new BlancoRestGeneratorKtProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldMetadirProcessed = false;
        boolean isFieldBasepackageProcessed = false;

        // Parses command line arguments.
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
                isFieldMetadirProcessed = true;
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-tmpdir=")) {
                input.setTmpdir(arg.substring(8));
            } else if (arg.startsWith("-searchTmpdir=")) {
                input.setSearchTmpdir(arg.substring(14));
            } else if (arg.startsWith("-nameAdjust=")) {
                input.setNameAdjust(Boolean.valueOf(arg.substring(12)).booleanValue());
            } else if (arg.startsWith("-encoding=")) {
                input.setEncoding(arg.substring(10));
            } else if (arg.startsWith("-tabs=")) {
                try {
                    input.setTabs(Integer.parseInt(arg.substring(6)));
                } catch (NumberFormatException e) {
                    System.out.println("BlancoRestGeneratorKtBatchProcess: Failed to start the process. Tried to parse the field [tabs] of the input parameter[input] as a number (int), but it failed.: " + e.toString());
                    System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
                }
            } else if (arg.startsWith("-xmlrootelement=")) {
                input.setXmlrootelement(Boolean.valueOf(arg.substring(16)).booleanValue());
            } else if (arg.startsWith("-sheetType=")) {
                input.setSheetType(arg.substring(11));
            } else if (arg.startsWith("-targetStyle=")) {
                input.setTargetStyle(arg.substring(13));
            } else if (arg.startsWith("-client=")) {
                input.setClient(Boolean.valueOf(arg.substring(8)).booleanValue());
            } else if (arg.startsWith("-clientAnnotation=")) {
                input.setClientAnnotation(arg.substring(18));
            } else if (arg.startsWith("-overrideClientAnnotation=")) {
                input.setOverrideClientAnnotation(arg.substring(26));
            } else if (arg.startsWith("-serverType=")) {
                input.setServerType(arg.substring(12));
            } else if (arg.startsWith("-basepackage=")) {
                input.setBasepackage(arg.substring(13));
                isFieldBasepackageProcessed = true;
            } else if (arg.startsWith("-runtimepackage=")) {
                input.setRuntimepackage(arg.substring(16));
            } else if (arg.startsWith("-genUtils=")) {
                input.setGenUtils(Boolean.valueOf(arg.substring(10)).booleanValue());
            } else if (arg.startsWith("-telegrampackage=")) {
                input.setTelegrampackage(arg.substring(17));
            } else if (arg.startsWith("-impledir=")) {
                input.setImpledir(arg.substring(10));
            } else if (arg.startsWith("-genSkeleton=")) {
                input.setGenSkeleton(Boolean.valueOf(arg.substring(13)).booleanValue());
            } else if (arg.startsWith("-skeletonDelegateClass=")) {
                input.setSkeletonDelegateClass(arg.substring(23));
            } else if (arg.startsWith("-skeletonDelegateInterface=")) {
                input.setSkeletonDelegateInterface(arg.substring(27));
            } else if (arg.startsWith("-lineSeparator=")) {
                input.setLineSeparator(arg.substring(15));
            } else if (arg.startsWith("-packageSuffix=")) {
                input.setPackageSuffix(arg.substring(15));
            } else if (arg.startsWith("-overridePackage=")) {
                input.setOverridePackage(arg.substring(17));
            } else if (arg.startsWith("-overrideLocation=")) {
                input.setOverrideLocation(arg.substring(18));
            } else if (arg.startsWith("-voPackageSuffix=")) {
                input.setVoPackageSuffix(arg.substring(17));
            } else if (arg.startsWith("-voOverridePackage=")) {
                input.setVoOverridePackage(arg.substring(19));
            } else if (arg.startsWith("-telegramStyle=")) {
                input.setTelegramStyle(arg.substring(15));
            } else if (arg.startsWith("-appendApplicationPackage=")) {
                input.setAppendApplicationPackage(Boolean.valueOf(arg.substring(26)).booleanValue());
            } else if (arg.startsWith("-serdeable=")) {
                input.setSerdeable(Boolean.valueOf(arg.substring(11)).booleanValue());
            } else if (arg.startsWith("-ignoreUnknown=")) {
                input.setIgnoreUnknown(Boolean.valueOf(arg.substring(15)).booleanValue());
            } else if (arg.startsWith("-nullableAnnotation=")) {
                input.setNullableAnnotation(Boolean.valueOf(arg.substring(20)).booleanValue());
            } else if (arg.startsWith("-isTargetJaraktaEE=")) {
                input.setIsTargetJaraktaEE(Boolean.valueOf(arg.substring(19)).booleanValue());
            } else if (arg.startsWith("-injectInterfaceToController=")) {
                input.setInjectInterfaceToController(Boolean.valueOf(arg.substring(29)).booleanValue());
            } else if (arg.startsWith("-micronautVersion=")) {
                input.setMicronautVersion(arg.substring(18));
            } else if (arg.startsWith("-deserializerRequestHeader=")) {
                input.setDeserializerRequestHeader(arg.substring(27));
            } else if (arg.startsWith("-deserializerCommonRequest=")) {
                input.setDeserializerCommonRequest(arg.substring(27));
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoRestGeneratorKtBatchProcess: The input parameter[" + arg + "] was ignored.");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldBasepackageProcessed == false) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: Failed to start the process. The required field value[basepackage] in the input parameter[input] is not set to a value.");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // Returns the exit code.
        // Note: Please note that calling System.exit().
        System.exit(retCode);
    }

    /**
     * A method to describe the specific batch processing contents.
     *
     * This method is used to describe the actual process.
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public int process(final BlancoRestGeneratorKtProcessInput input) throws IOException, IllegalArgumentException {
        // Checks the input parameters.
        validateInput(input);

        // If you get a compile error at this point, You may be able to solve it by implementing a BlancoRestGeneratorKtProcess interface and creating an BlancoRestGeneratorKtProcessImpl class in package blanco.restgeneratorkt.task.
        final BlancoRestGeneratorKtProcess process = new BlancoRestGeneratorKtProcessImpl();

        // Executes the main body of the process.
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * The entry point for instantiating a class and running a batch.
     *
     * This method provides the following specifications.
     * <ul>
     * <li>Checks the contents of the input parameters of the method.
     * <li>Catches exceptions such as IllegalArgumentException, RuntimeException, Error, etc. and converts them to return values.
     * </ul>
     *
     * @param input Input parameters for batch process.
     * @return The exit code for batch process. Returns one of the values END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public final int execute(final BlancoRestGeneratorKtProcessInput input) throws IllegalArgumentException {
        try {
            // Executes the main body of the batch process.
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: An input exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: An I/O exception has occurred. Abort the batch process.:" + ex.toString());
            // Termination due to abnormal input.
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: A runtime exception has occurred. Abort the batch process.:" + ex.toString());
            ex.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: A runtime exception has occurred. Abort the batch process.:" + er.toString());
            er.printStackTrace();
            // Abnormal end.
            return END_ERROR;
        }
    }

    /**
     * A method to show an explanation of how to use this batch processing class on the stdout.
     */
    public static final void usage() {
        System.out.println("BlancoRestGeneratorKtBatchProcess: Usage:");
        System.out.println("  java blanco.restgeneratorkt.task.BlancoRestGeneratorKtBatchProcess -verbose=value1 -metadir=value2 -targetdir=value3 -tmpdir=value4 -searchTmpdir=value5 -nameAdjust=value6 -encoding=value7 -tabs=value8 -xmlrootelement=value9 -sheetType=value10 -targetStyle=value11 -client=value12 -clientAnnotation=value13 -overrideClientAnnotation=value14 -serverType=value15 -basepackage=value16 -runtimepackage=value17 -genUtils=value18 -telegrampackage=value19 -impledir=value20 -genSkeleton=value21 -skeletonDelegateClass=value22 -skeletonDelegateInterface=value23 -lineSeparator=value24 -packageSuffix=value25 -overridePackage=value26 -overrideLocation=value27 -voPackageSuffix=value28 -voOverridePackage=value29 -telegramStyle=value30 -appendApplicationPackage=value31 -serdeable=value32 -ignoreUnknown=value33 -nullableAnnotation=value34 -isTargetJaraktaEE=value35 -injectInterfaceToController=value36 -micronautVersion=value37 -deserializerRequestHeader=value38 -deserializerCommonRequest=value39");
        System.out.println("    -verbose");
        System.out.println("      explanation[Whether to run in verbose mode.]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -metadir");
        System.out.println("      explanation[メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -targetdir");
        System.out.println("      explanation[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -tmpdir");
        System.out.println("      explanation[テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]");
        System.out.println("      type[string]");
        System.out.println("      default value[tmp]");
        System.out.println("    -searchTmpdir");
        System.out.println("      explanation[import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]");
        System.out.println("      type[string]");
        System.out.println("    -nameAdjust");
        System.out.println("      explanation[フィールド名やメソッド名を名前変形を実施するかどうか。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[true]");
        System.out.println("    -encoding");
        System.out.println("      explanation[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -tabs");
        System.out.println("      explanation[タブをwhite spaceいくつで置き換えるか、という値です。]");
        System.out.println("      type[number(int)]");
        System.out.println("      default value[4]");
        System.out.println("    -xmlrootelement");
        System.out.println("      explanation[XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -sheetType");
        System.out.println("      explanation[meta定義書が期待しているプログラミング言語を指定します]");
        System.out.println("      type[string]");
        System.out.println("      default value[java]");
        System.out.println("    -targetStyle");
        System.out.println("      explanation[出力先フォルダの書式を指定します。<br>\nblanco: [targetdir]/main<br>\nmaven: [targetdir]/main/java<br>\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -client");
        System.out.println("      explanation[trueの場合はサーバ用のメソッドを生成しません。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -clientAnnotation");
        System.out.println("      explanation[Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。]");
        System.out.println("      type[string]");
        System.out.println("    -overrideClientAnnotation");
        System.out.println("      explanation[Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。]");
        System.out.println("      type[string]");
        System.out.println("    -serverType");
        System.out.println("      explanation[Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。]");
        System.out.println("      type[string]");
        System.out.println("      default value[micronaut]");
        System.out.println("    -basepackage");
        System.out.println("      explanation[blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。]");
        System.out.println("      type[string]");
        System.out.println("      a required parameter");
        System.out.println("    -runtimepackage");
        System.out.println("      explanation[ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。]");
        System.out.println("      type[string]");
        System.out.println("    -genUtils");
        System.out.println("      explanation[ユーティリティ類の生成を省略する場合はfalseを指定します。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[true]");
        System.out.println("    -telegrampackage");
        System.out.println("      explanation[電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。]");
        System.out.println("      type[string]");
        System.out.println("    -impledir");
        System.out.println("      explanation[実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。]");
        System.out.println("      type[string]");
        System.out.println("    -genSkeleton");
        System.out.println("      explanation[controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -skeletonDelegateClass");
        System.out.println("      explanation[実装クラスが処理を委譲するクラスのCanonical名です。]");
        System.out.println("      type[string]");
        System.out.println("    -skeletonDelegateInterface");
        System.out.println("      explanation[実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。]");
        System.out.println("      type[string]");
        System.out.println("    -lineSeparator");
        System.out.println("      explanation[行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]");
        System.out.println("      type[string]");
        System.out.println("      default value[LF]");
        System.out.println("    -packageSuffix");
        System.out.println("      explanation[定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -overridePackage");
        System.out.println("      explanation[定義書で指定されたパッケージ名を上書きします。]");
        System.out.println("      type[string]");
        System.out.println("    -overrideLocation");
        System.out.println("      explanation[定義書で指定されたロケーション名を上書きします。]");
        System.out.println("      type[string]");
        System.out.println("    -voPackageSuffix");
        System.out.println("      explanation[packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -voOverridePackage");
        System.out.println("      explanation[packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。]");
        System.out.println("      type[string]");
        System.out.println("    -telegramStyle");
        System.out.println("      explanation[電文の形式を指定します。\nblanco: 電文をCommonRequest/CommonResponseでくるみます。\nplain: 電文を直接 payload に乗せます。GET は第一階層がクエリ文字列として定義されます。]");
        System.out.println("      type[string]");
        System.out.println("      default value[blanco]");
        System.out.println("    -appendApplicationPackage");
        System.out.println("      explanation[Management クラスの package として、Controller クラスの package に application を追加したものを想定し、importします。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[true]");
        System.out.println("    -serdeable");
        System.out.println("      explanation[電文クラスに@Serdeableアノテーションを付与します。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -ignoreUnknown");
        System.out.println("      explanation[電文クラスに@JsonIgnoreProperties(ignoreunknow = true)アノテーションを付与します。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -nullableAnnotation");
        System.out.println("      explanation[「必須」が指定されていないパラメータに@Nullableアノテーションを強制します]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -isTargetJaraktaEE");
        System.out.println("      explanation[target が JVM17 以降の開発環境で利用する場合、J2EE の代わりに JakartaEE を使用する。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -injectInterfaceToController");
        System.out.println("      explanation[Micronaut の Controller にManagement クラスの代わりに Interface を依存注入する。true にすると genSkelton と appendApplicationPackage は false を強制されます。]");
        System.out.println("      type[boolean]");
        System.out.println("      default value[false]");
        System.out.println("    -micronautVersion");
        System.out.println("      explanation[想定する Micronaut の最低バージョンを指定します。バージョンによって若干の動作差異や不具合があるため、それに対応する目的です。]");
        System.out.println("      type[string]");
        System.out.println("      default value[3.0]");
        System.out.println("    -deserializerRequestHeader");
        System.out.println("      explanation[自動生成されるdeserializerに設定するRequestHeaderクラスを指定します]");
        System.out.println("      type[string]");
        System.out.println("    -deserializerCommonRequest");
        System.out.println("      explanation[自動生成されるdeserializerに設定するCommonRequestクラスを指定します]");
        System.out.println("      type[string]");
        System.out.println("    -? , -help");
        System.out.println("      explanation[show the usage.]");
    }

    /**
     * A method to check the validity of input parameters for this batch processing class.
     *
     * @param input Input parameters for batch process.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    public void validateInput(final BlancoRestGeneratorKtProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: Failed to start the process. The input parameter[input] was given as null.");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoRestGeneratorKtBatchProcess: Failed to start the process. The required field value[metadir] in the input parameter[input] is not set to a value.");
        }
        if (input.getBasepackage() == null) {
            throw new IllegalArgumentException("BlancoRestGeneratorKtBatchProcess: Failed to start the process. The required field value[basepackage] in the input parameter[input] is not set to a value.");
        }
    }
}
