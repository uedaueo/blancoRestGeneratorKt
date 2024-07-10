package blanco.restgeneratorkt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import blanco.cg.BlancoCgSupportedLang;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.resourcebundle.BlancoRestGeneratorKtResourceBundle;
import blanco.restgeneratorkt.task.valueobject.BlancoRestGeneratorKtProcessInput;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;
import blanco.valueobjectkt.BlancoValueObjectKtXmlParser;
import blanco.valueobjectkt.valueobject.BlancoValueObjectKtClassStructure;

/**
 * Gets the list of Object created in BlancoValueObject from XML and stores it.
 *
 * Created by tueda on 15/07/05.
 */
public class BlancoRestGeneratorKtUtil {
    /**
     * An access object to the resource bundle for ValueObject.
     */
    private final static BlancoRestGeneratorKtResourceBundle fBundle = new BlancoRestGeneratorKtResourceBundle();

    public static Map<String, Integer> mapCommons = new HashMap<String, Integer>() {
        {put(fBundle.getMeta2xmlElementCommon(), BlancoCgSupportedLang.JAVA);}
        {put(fBundle.getMeta2xmlElementCommonCs(), BlancoCgSupportedLang.CS);}
        {put(fBundle.getMeta2xmlElementCommonJs(), BlancoCgSupportedLang.JS);}
        {put(fBundle.getMeta2xmlElementCommonVb(), BlancoCgSupportedLang.VB);}
        {put(fBundle.getMeta2xmlElementCommonPhp(), BlancoCgSupportedLang.PHP);}
        {put(fBundle.getMeta2xmlElementCommonRuby(), BlancoCgSupportedLang.RUBY);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.PYTHON);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.KOTLIN);}
        {put(fBundle.getMeta2xmlElementCommonPython(), BlancoCgSupportedLang.TS);}
    };

    /**
     * Whether to adjust name of field and method.
     */
    private boolean fNameAdjust = true;

    /**
     * Character encoding of auto-generated source files.
     */
    public static String encoding = null;
    public static boolean isVerbose = false;

    public static HashMap<String, BlancoValueObjectKtClassStructure> objects = new HashMap<>();

    public static String basePackage = null;
    public static String runtimePackage = null;
    public static String telegramPackage = null;
    public static boolean genUtils = true;
    public static boolean genSkeleton = false;
    public static String impleDir = null;
    public static String skeletonDelegateClass = null;
    public static String skeletonDelegateInterface = null;
    public static String packageSuffix = null;
    public static String overridePackage = null;
    public static String overrideLocation = null;
    public static String voPackageSuffix = null;
    public static String voOverridePackage = null;
    public static boolean client = false;
    public static String serverType = null;
    public static boolean isServerTypeMicronaut = true;
    public static String clientAnnotation = null;
    public static String overrideClientAnnotation = null;
    public static String telegramStyle = BlancoRestGeneratorKtConstants.TELEGRAM_STYLE_BLANCO;
    public static boolean isTelegramStyleBlanco = true;
    public static boolean isTelegramStylePlain = false;
    public static boolean isAppendApplicationPackage = true;
    public static boolean isSerdeable = false;
    public static boolean isIgnoreUnknown = false;
    public static boolean isNullAnnotation = false;
    public static boolean isTargetJakartaEE = false;

    static public void processValueObjects(final BlancoRestGeneratorKtProcessInput input) throws IOException {
        if (isVerbose) {
            System.out.println("BlancoRestGeneratorKtObjectsInfo : processValueObjects start !");
        }

        /* tmpdir is unique. */
        String baseTmpdir = input.getTmpdir();
        /* searchTmpdir is comma separated. */
        String tmpTmpdirs = input.getSearchTmpdir();
        List<String> searchTmpdirList = null;
        if (tmpTmpdirs != null && !tmpTmpdirs.equals(baseTmpdir)) {
            String[] searchTmpdirs = tmpTmpdirs.split(",");
            searchTmpdirList = new ArrayList<>(Arrays.asList(searchTmpdirs));
        }
        if (searchTmpdirList == null) {
            searchTmpdirList = new ArrayList<>();
        }
        searchTmpdirList.add(baseTmpdir);

        for (String tmpdir : searchTmpdirList) {
            searchTmpdir(tmpdir.trim());
        }
    }

    static private void searchTmpdir(String tmpdir) {

        // Reads information from XML-ized intermediate files.
        final File[] fileMeta3 = new File(tmpdir
                + BlancoRestGeneratorKtConstants.OBJECT_SUBDIRECTORY)
                .listFiles();

        if (fileMeta3 == null) {
            System.out.println("!!! NO FILES in " + tmpdir
                    + BlancoRestGeneratorKtConstants.OBJECT_SUBDIRECTORY);
            throw new IllegalArgumentException("!!! NO FILES in " + tmpdir
                    + BlancoRestGeneratorKtConstants.OBJECT_SUBDIRECTORY);
        }

        for (int index = 0; index < fileMeta3.length; index++) {
            if (fileMeta3[index].getName().endsWith(".xml") == false) {
                continue;
            }

            BlancoValueObjectKtXmlParser parser = new BlancoValueObjectKtXmlParser();
//            parser.setVerbose(this.isVerbose());
            /*
             * First, it searches all the sheets and make a list of class and package names.
             * This is because in the PHP-style definitions, the package name is not specified when specifying class.
             *
             */
            final BlancoValueObjectKtClassStructure[] structures = parser.parse(fileMeta3[index]);

            if (structures != null ) {
                for (int index2 = 0; index2 < structures.length; index2++) {
                    BlancoValueObjectKtClassStructure structure = structures[index2];
                    if (structure != null) {
                        if (isVerbose) {
                            System.out.println("processValueObjects: " + structure.getName());
                        }
                        objects.put(structure.getName(), structure);
                    } else {
                        System.out.println("processValueObjects: a structure is NULL!!!");
                    }
                }
            } else {
                System.out.println("processValueObjects: structures are NULL!!!");
            }
        }
    }

    /**
     * Returns the parent class of the telegram for each method.
     * @param method
     * @return
     */
    static public String getDefaultRequestTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_GET_REQUESTID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_POST_REQUESTID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_PUT_REQUESTID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_DELETE_REQUESTID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }

    /**
     * Returns the parent class of the telegram for each method.
     * @param method
     * @return
     */
    static public String getDefaultResponseTelegramId(String method) {
        String telegramId = null;

        if (method == null) {
            throw new IllegalArgumentException("!!! METHOD CANNOT BE NULL !!! " + method);
        }

        if (BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_GET_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_POST_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_PUT_RESPONSEID;
        } else if (BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.endsWith(method.toUpperCase())) {
            telegramId = BlancoRestGeneratorKtConstants.DEFAULT_API_DELETE_RESPONSEID;
        } else {
            throw new IllegalArgumentException("!!! NO SUCH METHOD !!! " + method);
        }

        return telegramId;
    }

    /**
     * Search telegrams startWith specified name.
     * It may very slow if the sheet contains many telegrams, but be patient
     * because it is just on generate sources phase.
     * @param start
     * @param argTelegramStructureMap
     * @return
     */
    static public List<BlancoRestGeneratorKtTelegramStructure> searchTelegramsStartWith(
        final String start,
        final Map<String, BlancoRestGeneratorKtTelegramStructure> argTelegramStructureMap
    ) {
        List<BlancoRestGeneratorKtTelegramStructure> telegrams = new ArrayList<>();

        Set<String> keySet = argTelegramStructureMap.keySet();
        for (String key : keySet) {
            if (key.startsWith(start)) {
                telegrams.add(argTelegramStructureMap.get(key));
            }
        }

        return telegrams;
    }

    /**
     * Make canonical classname into Simple.
     *
     * @param argClassNameCanon
     * @return simpleName
     */
    static public String getSimpleClassName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot == -1) {
            simpleName = argClassNameCanon;
        } else if (findLastDot != argClassNameCanon.length() - 1) {
            simpleName = argClassNameCanon.substring(findLastDot + 1);
        }
        return simpleName;
    }

    /**
     * Make canonical classname into packageName
     *
     * @param argClassNameCanon
     * @return
     */
    static public String getPackageName(final String argClassNameCanon) {
        if (argClassNameCanon == null) {
            return "";
        }

        String simpleName = "";
        final int findLastDot = argClassNameCanon.lastIndexOf('.');
        if (findLastDot > 0) {
            simpleName = argClassNameCanon.substring(0, findLastDot);
        }
        return simpleName;
    }

    static public String searchPackageBySimpleName(String simpleName) {
        // Replaces the package name if the replace package name option is specified.
        // If there is Suffix, that is the priority.
        String packageName = null;
        BlancoValueObjectKtClassStructure voStructure = objects.get(simpleName);
        if (voStructure != null) {
            packageName = voStructure.getPackage();
            if (BlancoRestGeneratorKtUtil.voPackageSuffix != null && BlancoRestGeneratorKtUtil.voPackageSuffix.length() > 0) {
                packageName = packageName + "." + BlancoRestGeneratorKtUtil.voPackageSuffix;
            } else if (BlancoRestGeneratorKtUtil.voOverridePackage != null && BlancoRestGeneratorKtUtil.voOverridePackage.length() > 0) {
                packageName = BlancoRestGeneratorKtUtil.voOverridePackage;
            }
        }
        return packageName;
    }

    static public boolean isStringArray(BlancoRestGeneratorKtTelegramFieldStructure fieldStructure) {
        boolean yes =false;
        String type = fieldStructure.getType();
        if (BlancoStringUtil.null2Blank(fieldStructure.getTypeKt()).trim().length() > 0) {
            type = fieldStructure.getTypeKt();
        }
        String generic = fieldStructure.getGeneric();
        if (BlancoStringUtil.null2Blank(fieldStructure.getGenericKt()).trim().length() > 0) {
            generic = fieldStructure.getGenericKt();
        }
        System.out.println("### isStringArray? " + type + "<" + generic +">");
        if ("kotlin.collections.ArrayList".equalsIgnoreCase(type)
        ) {
            if ("kotlin.String".equalsIgnoreCase(generic)) {
                yes = true;
            }
        }
        return yes;
    }

    /**
     * Add new annotaion. ignore if duplicate.
     * @param argAnnotation
     * @param argFullAnnotation
     * @param argImport
     * @param argAnnotationList
     * @param argImportList
     * @return
     */
    static public boolean addNewAnnotation(
            final String argAnnotation,
            final String argFullAnnotation,
            final String argImport,
            final List<String> argAnnotationList,
            final List<String> argImportList) {
        /* Check already added */
        boolean found = false;
        for (String ann : argAnnotationList) {
            if (ann.contains(argAnnotation)) {
                found = true;
            }
        }
        if (found) {
            System.out.println("@" + argAnnotation + " already exists. SKIP!!");
        } else {
            argAnnotationList.add(argFullAnnotation);
            argImportList.add(argImport);
        }
        return found;
    }
}
