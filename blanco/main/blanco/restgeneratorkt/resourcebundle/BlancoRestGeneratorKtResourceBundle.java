package blanco.restgeneratorkt.resourcebundle;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * BlancoRestGeneratorKtが利用するリソースバンドルを蓄えます。
 *
 * リソースバンドル定義: [BlancoRestGeneratorKt]。<BR>
 * このクラスはリソースバンドル定義書から自動生成されたリソースバンドルクラスです。<BR>
 * 既知のロケール<BR>
 * <UL>
 * <LI>ja
 * </UL>
 */
public class BlancoRestGeneratorKtResourceBundle {
    /**
     * リソースバンドルオブジェクト。
     *
     * 内部的に実際に入力を行うリソースバンドルを記憶します。
     */
    private ResourceBundle fResourceBundle;

    /**
     * BlancoRestGeneratorKtResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoRestGeneratorKt]、デフォルトのロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     */
    public BlancoRestGeneratorKtResourceBundle() {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/restgeneratorkt/resourcebundle/BlancoRestGeneratorKt");
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoRestGeneratorKtResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoRestGeneratorKt]、指定されたロケール、呼び出し側のクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     */
    public BlancoRestGeneratorKtResourceBundle(final Locale locale) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/restgeneratorkt/resourcebundle/BlancoRestGeneratorKt", locale);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * BlancoRestGeneratorKtResourceBundleクラスのコンストラクタ。
     *
     * 基底名[BlancoRestGeneratorKt]、指定されたロケール、指定されたクラスローダを使用して、リソースバンドルを取得します。
     *
     * @param locale ロケールの指定
     * @param loader クラスローダの指定
     */
    public BlancoRestGeneratorKtResourceBundle(final Locale locale, final ClassLoader loader) {
        try {
            fResourceBundle = ResourceBundle.getBundle("blanco/restgeneratorkt/resourcebundle/BlancoRestGeneratorKt", locale, loader);
        } catch (MissingResourceException ex) {
        }
    }

    /**
     * Gets the resource bundle object held internally.
     *
     * @return The resource bundle object held internally.
     */
    public ResourceBundle getResourceBundle() {
        return fResourceBundle;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[METAFILE_DISPLAYNAME]
     *
     * [バリューオブジェクト定義書(Java)] (ja)<br>
     *
     * @return key[METAFILE_DISPLAYNAME]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMetafileDisplayname() {
        // 初期値として定義書の値を利用します。
        String strFormat = "バリューオブジェクト定義書(Java)";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("METAFILE_DISPLAYNAME");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON]
     *
     * [blancorestgenerator-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommon() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancorestgenerator-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_LIST]
     *
     * [blancorestgenerator-list] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_LIST]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementList() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancorestgenerator-list";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_LIST");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TELEGRAM_COMMON]
     *
     * [blancotelegram-common] (ja)<br>
     *
     * @return key[META2XML.TELEGRAM_COMMON]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTelegramCommon() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TELEGRAM_COMMON");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TELEGRAM_EXTENDS]
     *
     * [blancotelegram-extends] (ja)<br>
     *
     * @return key[META2XML.TELEGRAM_EXTENDS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTelegramExtends() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-extends";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TELEGRAM_EXTENDS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TELEGRAM_IMPLEMENTS]
     *
     * [blancotelegram-implements] (ja)<br>
     *
     * @return key[META2XML.TELEGRAM_IMPLEMENTS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTelegramImplements() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-implements";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TELEGRAM_IMPLEMENTS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TELEGRAM_IMPORT]
     *
     * [blancotelegram-import] (ja)<br>
     *
     * @return key[META2XML.TELEGRAM_IMPORT]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTelegramImport() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-import";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TELEGRAM_IMPORT");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TELEGRAM_HEADER]
     *
     * [blancotelegram-header] (ja)<br>
     *
     * @return key[META2XML.TELEGRAM_HEADER]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTelegramHeader() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-header";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TELEGRAM_HEADER");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.TEREGRAM_LIST]
     *
     * [blancotelegram-list] (ja)<br>
     *
     * @return key[META2XML.TEREGRAM_LIST]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlTeregramList() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegram-list";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.TEREGRAM_LIST");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_COMMON]
     *
     * [blancotelegramprocess-common] (ja)<br>
     *
     * @return key[META2XML.PROCESS_COMMON]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessCommon() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_COMMON");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_EXTENDS]
     *
     * [blancotelegramprocess-extends] (ja)<br>
     *
     * @return key[META2XML.PROCESS_EXTENDS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessExtends() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-extends";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_EXTENDS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_IMPLEMENTS]
     *
     * [blancotelegramprocess-implements] (ja)<br>
     *
     * @return key[META2XML.PROCESS_IMPLEMENTS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessImplements() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-implements";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_IMPLEMENTS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_IMPORT]
     *
     * [blancotelegramprocess-import] (ja)<br>
     *
     * @return key[META2XML.PROCESS_IMPORT]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessImport() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-import";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_IMPORT");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_HEADER]
     *
     * [blancotelegramprocess-header] (ja)<br>
     *
     * @return key[META2XML.PROCESS_HEADER]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessHeader() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-header";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_HEADER");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_GET_REQUEST_BIND]
     *
     * [blancotelegramprocess-get-request-bind] (ja)<br>
     *
     * @return key[META2XML.PROCESS_GET_REQUEST_BIND]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessGetRequestBind() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-get-request-bind";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_GET_REQUEST_BIND");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_LIST]
     *
     * [blancotelegramprocess-list] (ja)<br>
     *
     * @return key[META2XML.PROCESS_LIST]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessList() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-list";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_LIST");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.PROCESS_DESCRIPTION]
     *
     * [blancotelegramprocess-description] (ja)<br>
     *
     * @return key[META2XML.PROCESS_DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlProcessDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancotelegramprocess-description";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.PROCESS_DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_CS]
     *
     * [blancovalueobjectcs-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_CS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonCs() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectcs-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_CS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_JS]
     *
     * [blancovalueobjectjs-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_JS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonJs() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectjs-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_JS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_VB]
     *
     * [blancovalueobjectvb-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_VB]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonVb() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectvb-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_VB");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_PHP]
     *
     * [blancovalueobjectphp-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_PHP]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonPhp() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectphp-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_PHP");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_RUBY]
     *
     * [blancovalueobjectruby-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_RUBY]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonRuby() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectruby-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_RUBY");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_PYTHON]
     *
     * [blancovalueobjectpython-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_PYTHON]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonPython() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectpython-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_PYTHON");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_KT]
     *
     * [blancovalueobjectkt-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_KT]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonKt() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectkt-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_KT");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[META2XML.ELEMENT_COMMON_TS]
     *
     * [blancovalueobjectts-common] (ja)<br>
     *
     * @return key[META2XML.ELEMENT_COMMON_TS]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getMeta2xmlElementCommonTs() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancovalueobjectts-common";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("META2XML.ELEMENT_COMMON_TS");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR001]
     *
     * [クラス名[{0}]のパッケージ名が指定されていません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR001]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr001(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}]のパッケージ名が指定されていません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR001");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR002]
     *
     * [メッセージ定義ID[{0}]において、キー[{1}]が2回以上あらわれました。同じキーは2回以上指定することはできません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR002]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr002(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "メッセージ定義ID[{0}]において、キー[{1}]が2回以上あらわれました。同じキーは2回以上指定することはできません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR002");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR003]
     *
     * [クラス名[{0}]のフィールド[{1}]の型名が指定されていません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR003]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr003(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}]のフィールド[{1}]の型名が指定されていません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR003");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR004]
     *
     * [クラス名[{0}] でフィールド名が指定されていないものがあります。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR004]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr004(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] でフィールド名が指定されていないものがあります。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR004");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR005]
     *
     * [クラス名[{0}] フィールド[{1}]の「型」が指定されていません。「型」を指定してください。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR005]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr005(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] フィールド[{1}]の「型」が指定されていません。「型」を指定してください。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR005");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR006]
     *
     * [クラス名[{0}] フィールド[{1}]の「デフォルト値({2})」がセットされています。しかし「{3}」はデフォルト値をサポートしません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @param arg2 置換文字列{2}を置換する値。java.lang.String型を与えてください。
     * @param arg3 置換文字列{3}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR006]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr006(final String arg0, final String arg1, final String arg2, final String arg3) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] フィールド[{1}]の「デフォルト値({2})」がセットされています。しかし「{3}」はデフォルト値をサポートしません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR006");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1, arg2, arg3}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR007]
     *
     * [クラス名[{0}] フィールド[{1}]の「最大値」が数値ではありません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR007]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr007(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] フィールド[{1}]の「最大値」が数値ではありません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR007");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR008]
     *
     * [クラス名[{0}] フィールド[{1}]の「最小値」が数値ではありません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR008]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr008(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] フィールド[{1}]の「最小値」が数値ではありません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR008");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.ERR009]
     *
     * [クラス名[{0}] フィールド[{1}]の「デフォルト値」がセットされていません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.ERR009]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileErr009(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クラス名[{0}] フィールド[{1}]の「デフォルト値」がセットされていません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.ERR009");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.FIELD.NAME]
     *
     * [フィールド [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.FIELD.NAME]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileFieldName(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.FIELD.NAME");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.FIELD.TYPE]
     *
     * [項目の型 [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.FIELD.TYPE]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileFieldType(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "項目の型 [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.FIELD.TYPE");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.FIELD.DEFAULT]
     *
     * [規定値   [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.FIELD.DEFAULT]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileFieldDefault(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "規定値   [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.FIELD.DEFAULT");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.SET.LANGDOC.01]
     *
     * [フィールド [{0}]のセッターメソッド] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.SET.LANGDOC.01]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileSetLangdoc01(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド [{0}]のセッターメソッド";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.SET.LANGDOC.01");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.SET.LANGDOC.02]
     *
     * [項目の型 [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.SET.LANGDOC.02]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileSetLangdoc02(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "項目の型 [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.SET.LANGDOC.02");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.SET.ARG.LANGDOC]
     *
     * [フィールド[{0}]に格納したい値] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.SET.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileSetArgLangdoc(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド[{0}]に格納したい値";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.SET.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.GET.LANGDOC.01]
     *
     * [フィールド[{0}]のゲッターメソッド] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.GET.LANGDOC.01]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileGetLangdoc01(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド[{0}]のゲッターメソッド";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.GET.LANGDOC.01");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.GET.LANGDOC.02]
     *
     * [項目の型 [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.GET.LANGDOC.02]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileGetLangdoc02(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "項目の型 [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.GET.LANGDOC.02");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.GET.ARG.LANGDOC]
     *
     * [規定値   [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.GET.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileGetArgLangdoc(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "規定値   [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.GET.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.GET.RETURN.LANGDOC]
     *
     * [フィールド[{0}]に格納されている値] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.GET.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileGetReturnLangdoc(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド[{0}]に格納されている値";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.GET.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.TYPE.LANGDOC.01]
     *
     * [フィールド[{0}]のタイプ取得メソッド] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.TYPE.LANGDOC.01]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileTypeLangdoc01(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド[{0}]のタイプ取得メソッド";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.TYPE.LANGDOC.01");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.TYPE.LANGDOC.02]
     *
     * [項目の型 [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.TYPE.LANGDOC.02]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileTypeLangdoc02(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "項目の型 [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.TYPE.LANGDOC.02");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.TYPE.ARG.LANGDOC]
     *
     * [規定値   [{0}]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.TYPE.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileTypeArgLangdoc(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "規定値   [{0}]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.TYPE.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.TYPE.RETURN.LANGDOC]
     *
     * [フィールド[{0}]の型名文字列] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[XML2SOURCE_FILE.TYPE.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileTypeReturnLangdoc(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "フィールド[{0}]の型名文字列";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.TYPE.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[ANTTASK.ERR001]
     *
     * [メタディレクトリ[{0}]が存在しません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[ANTTASK.ERR001]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getAnttaskErr001(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "メタディレクトリ[{0}]が存在しません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("ANTTASK.ERR001");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[ANTTASK.ERR002]
     *
     * [AntTaskパラメータbasepackageは必須項目です。] (ja)<br>
     *
     * @return key[ANTTASK.ERR002]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getAnttaskErr002() {
        // 初期値として定義書の値を利用します。
        String strFormat = "AntTaskパラメータbasepackageは必須項目です。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("ANTTASK.ERR002");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.INITIALIZER.DESCRIPTION]
     *
     * [API実装クラスが実装すべき初期化メソッドです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.INITIALIZER.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileInitializerDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "API実装クラスが実装すべき初期化メソッドです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.INITIALIZER.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.PROCESSOR.DESCRIPTION]
     *
     * [API実装クラスが実装すべき処理実行クラスです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.PROCESSOR.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileProcessorDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "API実装クラスが実装すべき処理実行クラスです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.PROCESSOR.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.FINISH.DESCRIPTION]
     *
     * [API実装クラスが実装すべき後処理メソッドです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.FINISH.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileFinishDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "API実装クラスが実装すべき後処理メソッドです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.FINISH.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.FINISHARRAY.DESCRIPTION]
     *
     * [API実装クラスが実装すべき後処理メソッド（配列対応版）です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.FINISHARRAY.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileFinisharrayDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "API実装クラスが実装すべき後処理メソッド（配列対応版）です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.FINISHARRAY.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.IS.SPOILED.DESCRIPTION]
     *
     * [API実装クラスが実装すべき、APIが呼び出し可能かどうかを返すメソッドです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.IS.SPOILED.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileIsSpoiledDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "API実装クラスが実装すべき、APIが呼び出し可能かどうかを返すメソッドです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.IS.SPOILED.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.EXECUTOR.DESCRIPTION]
     *
     * [APIベースクラスから呼ばれる実行メソッドです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.EXECUTOR.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileExecutorDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "APIベースクラスから呼ばれる実行メソッドです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.EXECUTOR.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.PROSESSOR.ARG.LANGDOC]
     *
     * [validation済みのリクエスト情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.PROSESSOR.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileProsessorArgLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのリクエスト情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.PROSESSOR.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.PROSESSOR.RETURN.LANGDOC]
     *
     * [validation前のレスポンス情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.PROSESSOR.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileProsessorReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation前のレスポンス情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.PROSESSOR.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.EXECUTOR.ARG.LANGDOC]
     *
     * [validation前のリクエスト情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.EXECUTOR.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileExecutorArgLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation前のリクエスト情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.EXECUTOR.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.EXECUTOR.RETURN.LANGDOC]
     *
     * [validation済みのレスポンス情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.EXECUTOR.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileExecutorReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのレスポンス情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.EXECUTOR.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.VIRTUAL.PARAMETER.LANGDOC]
     *
     * [仮想パラメータです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.VIRTUAL.PARAMETER.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileVirtualParameterLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "仮想パラメータです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.VIRTUAL.PARAMETER.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.IS.SPOILED.METHOD.LANGDOC]
     *
     * [実行可能かどうかを確認するHTTPメソッド名です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.IS.SPOILED.METHOD.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileIsSpoiledMethodLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "実行可能かどうかを確認するHTTPメソッド名です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.IS.SPOILED.METHOD.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.IS.SPOILED.METHOD.RETURN.DESCRIPTION]
     *
     * [実行可能でなければTRUEを返します。] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.IS.SPOILED.METHOD.RETURN.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileIsSpoiledMethodReturnDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "実行可能でなければTRUEを返します。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.IS.SPOILED.METHOD.RETURN.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.CLIENT.EXECUTOR.DESCRIPTION]
     *
     * [クライアントとしてAPIよぶためのインタフェイスです] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.CLIENT.EXECUTOR.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileClientExecutorDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "クライアントとしてAPIよぶためのインタフェイスです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.CLIENT.EXECUTOR.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.CLIENT.EXECUTOR.ARG.LANGDOC]
     *
     * [クライアントとして渡すリクエスト情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.CLIENT.EXECUTOR.ARG.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileClientExecutorArgLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "クライアントとして渡すリクエスト情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.CLIENT.EXECUTOR.ARG.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.CLIENT.EXECUTOR.RETURN.LANGDOC]
     *
     * [サーバから戻されるレスポンス情報です] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.CLIENT.EXECUTOR.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileClientExecutorReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "サーバから戻されるレスポンス情報です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.CLIENT.EXECUTOR.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.AUTHFLAG.DESCRIPTION]
     *
     * [APIが認証を必要とするかどうかのフラグです．必要な場合はtrueです．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.AUTHFLAG.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileAuthflagDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "APIが認証を必要とするかどうかのフラグです．必要な場合はtrueです．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.AUTHFLAG.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.AUTHFLAG.RETURN.LANGDOC]
     *
     * [APIが認証を必要とするかどうかのフラグです．必要な場合はtrueです．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.AUTHFLAG.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileAuthflagReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "APIが認証を必要とするかどうかのフラグです．必要な場合はtrueです．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.AUTHFLAG.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.REQUESTID.DESCTIPTION]
     *
     * [validation済みのリクエスト情報のクラス名です．パッケージは含みません．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.REQUESTID.DESCTIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileRequestidDesctiption() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのリクエスト情報のクラス名です．パッケージは含みません．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.REQUESTID.DESCTIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.REQUESTID.RETURN&gt;LANGDOC]
     *
     * [validation済みのリクエスト情報のクラス名です．パッケージは含みません．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.REQUESTID.RETURN&gt;LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileRequestidReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのリクエスト情報のクラス名です．パッケージは含みません．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.REQUESTID.RETURN>LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.RESPONSEID.DESCRIPTION]
     *
     * [validation済みのレスポンス情報のクラス名です．パッケージは含みません．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.RESPONSEID.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileResponseidDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのレスポンス情報のクラス名です．パッケージは含みません．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.RESPONSEID.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.RESPONSEID.RETURN.LANGDOC]
     *
     * [validation済みのレスポンス情報のクラス名です．パッケージは含みません．] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.RESPONSEID.RETURN.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileResponseidReturnLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "validation済みのレスポンス情報のクラス名です．パッケージは含みません．";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.RESPONSEID.RETURN.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.01]
     *
     * [電文 [ {0} ] のリクエストがnullです] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.01]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg01(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] のリクエストがnullです";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.01");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.02]
     *
     * [電文 [ {0} ] で、期待されるリクエストの型[ {1} ]に対して[ {2} ]が渡されました] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @param arg2 置換文字列{2}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.02]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg02(final String arg0, final String arg1, final String arg2) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] で、期待されるリクエストの型[ {1} ]に対して[ {2} ]が渡されました";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.02");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1, arg2}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.03]
     *
     * [電文 [ {0} ] で、レスポンスの内容が空です] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.03]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg03(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] で、レスポンスの内容が空です";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.03");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.04]
     *
     * [APIへの接続でエラーが発生しました．応答コード[ {0} ]] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.04]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg04(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "APIへの接続でエラーが発生しました．応答コード[ {0} ]";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.04");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.05]
     *
     * [このAPIは[ {0} ]メソッドには対応していません.] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.05]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg05(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "このAPIは[ {0} ]メソッドには対応していません.";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.05");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.06]
     *
     * [java.util.Date 型ではデフォルト値を許容しません。（電文 [ {0} ]）] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.06]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg06(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "java.util.Date 型ではデフォルト値を許容しません。（電文 [ {0} ]）";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.06");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.07]
     *
     * [電文 [ {0} ] のフィールド [ {1} ] にデフォルト値が設定されていません。blancoRestGeneratorKtは当面の間、abstractフィールドはサポートしませんので、必ずデフォルト値を設定してください。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.07]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg07(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] のフィールド [ {1} ] にデフォルト値が設定されていません。blancoRestGeneratorKtは当面の間、abstractフィールドはサポートしませんので、必ずデフォルト値を設定してください。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.07");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.08]
     *
     * [電文 [ {0} ] でメソッドタイプが設定されていません。電文はGet/Delete/Post/Putのいずれかでなければなりません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.08]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg08(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] でメソッドタイプが設定されていません。電文はGet/Delete/Post/Putのいずれかでなければなりません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.08");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.09]
     *
     * [クエリ種別Pathは常に必須です。Nullableも許容されません。電文 [{0}] のパラメータ  [{1}] を確認してください。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.09]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg09(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "クエリ種別Pathは常に必須です。Nullableも許容されません。電文 [{0}] のパラメータ  [{1}] を確認してください。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.09");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.10]
     *
     * [Pathクエリ書式に [{0}] が指定されていません。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.10]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg10(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "Pathクエリ書式に [{0}] が指定されていません。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.10");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.ERROR.MSG.11]
     *
     * [電文 [ {0} ] のペイロードにはプリミティブ型 [ {1} ] が期待されていますが電文定義・一覧にクエリ文字列以外のプロパティが存在します。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @param arg1 置換文字列{1}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.ERROR.MSG.11]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestErrorMsg11(final String arg0, final String arg1) {
        // 初期値として定義書の値を利用します。
        String strFormat = "電文 [ {0} ] のペイロードにはプリミティブ型 [ {1} ] が期待されていますが電文定義・一覧にクエリ文字列以外のプロパティが存在します。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.ERROR.MSG.11");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0, arg1}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.DEFAULT.EXCEPTION.TYPE.LANGDOC]
     *
     * [blancoRestGeneratorTsのデフォルト例外の定義です.] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.DEFAULT.EXCEPTION.TYPE.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileDefaultExceptionTypeLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancoRestGeneratorTsのデフォルト例外の定義です.";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.DEFAULT.EXCEPTION.TYPE.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[XML2SOURCE_FILE.DEFAULT.EXCEPTION.LANGDOC]
     *
     * [blancoRestGeneratorTsのデフォルト例外です.] (ja)<br>
     *
     * @return key[XML2SOURCE_FILE.DEFAULT.EXCEPTION.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getXml2sourceFileDefaultExceptionLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "blancoRestGeneratorTsのデフォルト例外です.";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("XML2SOURCE_FILE.DEFAULT.EXCEPTION.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.PERMISSION_FIELD.DESCRIPTION]
     *
     * [APIの権限を表す文字列を保持します。ApiTelegramクラスに基底フィールドが定義されていることが前提です。] (ja)<br>
     *
     * @return key[BLANCOREST.PERMISSION_FIELD.DESCRIPTION]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestPermissionFieldDescription() {
        // 初期値として定義書の値を利用します。
        String strFormat = "APIの権限を表す文字列を保持します。ApiTelegramクラスに基底フィールドが定義されていることが前提です。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.PERMISSION_FIELD.DESCRIPTION");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.SERVER_TYPE.ERROR]
     *
     * [BlancoRestGeneratorKt では serverType は今の所 micronaut のみをサポートしています。] (ja)<br>
     *
     * @return key[BLANCOREST.SERVER_TYPE.ERROR]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestServerTypeError() {
        // 初期値として定義書の値を利用します。
        String strFormat = "BlancoRestGeneratorKt では serverType は今の所 micronaut のみをサポートしています。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.SERVER_TYPE.ERROR");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.TELEGRAM_STYLE.ERROR]
     *
     * [BlancoRestGeneratorKt では telegramStyle は今の所 blanco または plain のみをサポートしています。] (ja)<br>
     *
     * @return key[BLANCOREST.TELEGRAM_STYLE.ERROR]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestTelegramStyleError() {
        // 初期値として定義書の値を利用します。
        String strFormat = "BlancoRestGeneratorKt では telegramStyle は今の所 blanco または plain のみをサポートしています。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.TELEGRAM_STYLE.ERROR");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_RESERVED]
     *
     * [エラー電文 [ {0} ] では statusCode プロパティは予約されています。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_RESERVED]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestTelegramStylePlainStatusCodeReserved(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "エラー電文 [ {0} ] では statusCode プロパティは予約されています。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_RESERVED");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_REQUIRED]
     *
     * [エラー電文 [ {0} ] では statusCode は必須です。] (ja)<br>
     *
     * @param arg0 置換文字列{0}を置換する値。java.lang.String型を与えてください。
     * @return key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_REQUIRED]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestTelegramStylePlainStatusCodeRequired(final String arg0) {
        // 初期値として定義書の値を利用します。
        String strFormat = "エラー電文 [ {0} ] では statusCode は必須です。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE_REQUIRED");
            }
        } catch (MissingResourceException ex) {
        }
        final MessageFormat messageFormat = new MessageFormat(strFormat);
        final StringBuffer strbuf = new StringBuffer();
        // 与えられた引数を元に置換文字列を置き換えます。
        messageFormat.format(new Object[] {arg0}, strbuf, null);
        return strbuf.toString();
    }

    /**
     * bundle[BlancoRestGeneratorKt], key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE.LANGDOC]
     *
     * [エラー電文を返す際のHttpStatus Codeです。] (ja)<br>
     *
     * @return key[BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE.LANGDOC]に対応する値。外部から読み込みができない場合には、定義書の値を戻します。必ずnull以外の値が戻ります。
     */
    public String getBlancorestTelegramStylePlainStatusCodeLangdoc() {
        // 初期値として定義書の値を利用します。
        String strFormat = "エラー電文を返す際のHttpStatus Codeです。";
        try {
            if (fResourceBundle != null) {
                strFormat = fResourceBundle.getString("BLANCOREST.TELEGRAM_STYLE_PLAIN.STATUS_CODE.LANGDOC");
            }
        } catch (MissingResourceException ex) {
        }
        // 置換文字列はひとつもありません。
        return strFormat;
    }
}
