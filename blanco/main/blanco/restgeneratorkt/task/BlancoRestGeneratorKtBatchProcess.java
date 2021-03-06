package blanco.restgeneratorkt.task;

import java.io.IOException;

import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;

/**
 * バッチ処理クラス [BlancoRestGeneratorKtBatchProcess]。
 *
 * <P>バッチ処理の呼び出し例。</P>
 * <code>
 * java -classpath (クラスパス) blanco.restgeneratorkt.task.BlancoRestGeneratorKtBatchProcess -help
 * </code>
 */
public class BlancoRestGeneratorKtBatchProcess {
    /**
     * 正常終了。
     */
    public static final int END_SUCCESS = 0;

    /**
     * 入力異常終了。内部的にjava.lang.IllegalArgumentExceptionが発生した場合。
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * 入出力例外終了。内部的にjava.io.IOExceptionが発生した場合。
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * 異常終了。バッチの処理開始に失敗した場合、および内部的にjava.lang.Errorまたはjava.lang.RuntimeExceptionが発生した場合。
     */
    public static final int END_ERROR = 9;

    /**
     * コマンドラインから実行された際のエントリポイントです。
     *
     * @param args コンソールから引き継がれた引数。
     */
    public static final void main(final String[] args) {
        final BlancoRestGeneratorKtBatchProcess batchProcess = new BlancoRestGeneratorKtBatchProcess();

        // バッチ処理の引数。
        final BlancoRestGeneratorKtProcessInput input = new BlancoRestGeneratorKtProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldMetadirProcessed = false;
        boolean isFieldBasepackageProcessed = false;

        // コマンドライン引数の解析をおこないます。
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
                    System.out.println("BlancoRestGeneratorKtBatchProcess: 処理開始失敗。入力パラメータ[input]のフィールド[tabs]を数値(int)としてパースを試みましたが失敗しました。: " + e.toString());
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
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoRestGeneratorKtBatchProcess: 入力パラメータ[" + arg + "]は無視されました。");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldMetadirProcessed == false) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[metadir]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldBasepackageProcessed == false) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[basepackage]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // 終了コードを戻します。
        // ※注意：System.exit()を呼び出している点に注意してください。
        System.exit(retCode);
    }

    /**
     * 具体的なバッチ処理内容を記述するためのメソッドです。
     *
     * このメソッドに実際の処理内容を記述します。
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public int process(final BlancoRestGeneratorKtProcessInput input) throws IOException, IllegalArgumentException {
        // 入力パラメータをチェックします。
        validateInput(input);

        // この箇所でコンパイルエラーが発生する場合、BlancoRestGeneratorKtProcessインタフェースを実装して blanco.restgeneratorkt.taskパッケージに BlancoRestGeneratorKtProcessImplクラスを作成することにより解決できる場合があります。
        final BlancoRestGeneratorKtProcess process = new BlancoRestGeneratorKtProcessImpl();

        // 処理の本体を実行します。
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * クラスをインスタンス化してバッチを実行する際のエントリポイントです。
     *
     * このメソッドは下記の仕様を提供します。
     * <ul>
     * <li>メソッドの入力パラメータの内容チェック。
     * <li>IllegalArgumentException, RuntimeException, Errorなどの例外をcatchして戻り値へと変換。
     * </ul>
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public final int execute(final BlancoRestGeneratorKtProcessInput input) throws IllegalArgumentException {
        try {
            // バッチ処理の本体を実行します。
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: 入力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: 入出力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: ランタイム例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            ex.printStackTrace();
            // 異常終了。
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoRestGeneratorKtBatchProcess: ランタイムエラーが発生しました。バッチ処理を中断します。:" + er.toString());
            er.printStackTrace();
            // 異常終了。
            return END_ERROR;
        }
    }

    /**
     * このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。
     */
    public static final void usage() {
        System.out.println("BlancoRestGeneratorKtBatchProcess: Usage:");
        System.out.println("  java blanco.restgeneratorkt.task.BlancoRestGeneratorKtBatchProcess -verbose=値1 -metadir=値2 -targetdir=値3 -tmpdir=値4 -searchTmpdir=値5 -nameAdjust=値6 -encoding=値7 -tabs=値8 -xmlrootelement=値9 -sheetType=値10 -targetStyle=値11 -client=値12 -clientAnnotation=値13 -overrideClientAnnotation=値14 -serverType=値15 -basepackage=値16 -runtimepackage=値17 -genUtils=値18 -telegrampackage=値19 -impledir=値20 -genSkeleton=値21 -skeletonDelegateClass=値22 -skeletonDelegateInterface=値23 -lineSeparator=値24 -packageSuffix=値25 -overridePackage=値26 -overrideLocation=値27 -voPackageSuffix=値28 -voOverridePackage=値29");
        System.out.println("    -verbose");
        System.out.println("      説明[verboseモードで動作させるかどうか。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -metadir");
        System.out.println("      説明[メタディレクトリ。xlsファイルの格納先または xmlファイルの格納先を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -targetdir");
        System.out.println("      説明[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[blanco]");
        System.out.println("    -tmpdir");
        System.out.println("      説明[テンポラリディレクトリを指定します。無指定の場合にはカレント直下のtmpを用います。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[tmp]");
        System.out.println("    -searchTmpdir");
        System.out.println("      説明[import文作成のために検索するtmpディレクトリをカンマ区切りで指定します。指定ディレクトリ直下のvalueobjectディレクトリの下にxmlを探しにいきます。]");
        System.out.println("      型[文字列]");
        System.out.println("    -nameAdjust");
        System.out.println("      説明[フィールド名やメソッド名を名前変形を実施するかどうか。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[true]");
        System.out.println("    -encoding");
        System.out.println("      説明[自動生成するソースファイルの文字エンコーディングを指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -tabs");
        System.out.println("      説明[タブをwhite spaceいくつで置き換えるか、という値です。]");
        System.out.println("      型[数値(int)]");
        System.out.println("      デフォルト値[4]");
        System.out.println("    -xmlrootelement");
        System.out.println("      説明[XML ルート要素のアノテーションを出力するかどうか。JDK 1.6 以降が必要。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -sheetType");
        System.out.println("      説明[meta定義書が期待しているプログラミング言語を指定します]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[java]");
        System.out.println("    -targetStyle");
        System.out.println("      説明[出力先フォルダの書式を指定します。<br>\nblanco: [targetdir]/main<br>\nmaven: [targetdir]/main/java<br>\nfree: [targetdir](targetdirが無指定の場合はblanco/main)]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[blanco]");
        System.out.println("    -client");
        System.out.println("      説明[trueの場合はサーバ用のメソッドを生成しません。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -clientAnnotation");
        System.out.println("      説明[Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書に記載がある場合はそちらを優先します。現在の所、micronautのみが使用可能です。]");
        System.out.println("      型[文字列]");
        System.out.println("    -overrideClientAnnotation");
        System.out.println("      説明[Clientモード時に、接続先サーバ名を指定するためのアノテーションを記述します。定義書の記載よりも優先されます。現在の所、micronautのみが使用可能です。]");
        System.out.println("      型[文字列]");
        System.out.println("    -serverType");
        System.out.println("      説明[Webアプリケーションサーバのタイプを指定します。現在の所、micronautのみが使用可能です。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[micronaut]");
        System.out.println("    -basepackage");
        System.out.println("      説明[blancoRestGeneratorがJavaソースコードを生成する際の基準となるパッケージ名を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -runtimepackage");
        System.out.println("      説明[ランタイムクラスを生成する生成先を指定します。無指定の場合には basepackageを基準に生成されます。]");
        System.out.println("      型[文字列]");
        System.out.println("    -genUtils");
        System.out.println("      説明[ユーティリティ類の生成を省略する場合はfalseを指定します。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[true]");
        System.out.println("    -telegrampackage");
        System.out.println("      説明[電文の基底クラスが配備されているパッケージを指定します。指定がない場合はvalueobjectから探します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -impledir");
        System.out.println("      説明[実装ファイルの配置ディレクトリを指定します。controllerから呼び出されるmanagementクラスのスケルトンはここに生成されます。]");
        System.out.println("      型[文字列]");
        System.out.println("    -genSkeleton");
        System.out.println("      説明[controllerから呼び出されるmanagementクラスのスケルトンを生成します。既にファイルが存在する場合は上書きしません。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -skeletonDelegateClass");
        System.out.println("      説明[実装クラスが処理を委譲するクラスのCanonical名です。]");
        System.out.println("      型[文字列]");
        System.out.println("    -skeletonDelegateInterface");
        System.out.println("      説明[実装クラスが処理を委譲するクラスが実装するIntefaceのCanonical名です。]");
        System.out.println("      型[文字列]");
        System.out.println("    -lineSeparator");
        System.out.println("      説明[行末記号をしていします。LF=0x0a, CR=0x0d, CFLF=0x0d0x0a とします。LFがデフォルトです。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[LF]");
        System.out.println("    -packageSuffix");
        System.out.println("      説明[定義書で指定されたパッケージ名の後ろに追加するパッケージ文字列を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -overridePackage");
        System.out.println("      説明[定義書で指定されたパッケージ名を上書きします。]");
        System.out.println("      型[文字列]");
        System.out.println("    -overrideLocation");
        System.out.println("      説明[定義書で指定されたロケーション名を上書きします。]");
        System.out.println("      型[文字列]");
        System.out.println("    -voPackageSuffix");
        System.out.println("      説明[packageを探しにいくValueObject定義書を処理する際に指定されていたはずの packageSuffix を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -voOverridePackage");
        System.out.println("      説明[packageを探しにいくValueObject定義書を処理する際に指定されていたはずの overridePackage を指定します。]");
        System.out.println("      型[文字列]");
        System.out.println("    -? , -help");
        System.out.println("      説明[使い方を表示します。]");
    }

    /**
     * このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。
     *
     * @param input バッチ処理の入力パラメータ。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public void validateInput(final BlancoRestGeneratorKtProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。");
        }
        if (input.getMetadir() == null) {
            throw new IllegalArgumentException("BlancoRestGeneratorKtBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[metadir]に値が設定されていません。");
        }
        if (input.getBasepackage() == null) {
            throw new IllegalArgumentException("BlancoRestGeneratorKtBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[basepackage]に値が設定されていません。");
        }
    }
}
