package blanco.restgeneratorkt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import blanco.cg.BlancoCgObjectFactory;
import blanco.cg.transformer.BlancoCgTransformerFactory;
import blanco.cg.util.BlancoCgSourceUtil;
import blanco.cg.valueobject.*;
import blanco.commons.util.BlancoNameAdjuster;
import blanco.commons.util.BlancoStringUtil;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramFieldStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;
import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramStructure;

public class BlancoRestGeneratorKtPlainStyleExpander extends BlancoRestGeneratorKtExpander {

    @Override
    public void expand(BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure, File argDirectoryTarget) {

        // The first step is to generate telegrams.
        Set<String> methodKeys = argProcessStructure.getListTelegrams().keySet(); // It should not be null because it is checked at time of parse.
        for (String methodKey : methodKeys) {
            HashMap<String, BlancoRestGeneratorKtTelegramStructure> kindMap =
                    argProcessStructure.getListTelegrams().get(methodKey);
            Set<String> kindKeys = kindMap.keySet(); // It should not be null because it is checked at time of parse.
            for (String kindKey : kindKeys) {
                BlancoRestGeneratorKtTelegramStructure telegramStructure = kindMap.get(kindKey);
                generateTelegram(telegramStructure, argDirectoryTarget);
                 if (BlancoRestGeneratorKtConstants.TELEGRAM_INPUT.equals(kindKey) &&
                 /* telegramStructure.getHasQueryParams() && */
                 (
                     BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(methodKey) ||
                     BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(methodKey)
                 )) {
                     BlancoRestGeneratorKtTelegramStructure partialStructure = telegramStructure.getBodyTelegram();
                     if (partialStructure != null) {
                         /* Generate body telegram. */
                         generateTelegram(partialStructure, argDirectoryTarget);
                     }
                 }
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
     * Generates the skeleton of the application implementation class.
     * @param argProcessStructure
     */
    private void generateApplicationSkeleton(
            final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure
    ) {
        String applicationPackage = argProcessStructure
                .getPackage();
        if (BlancoRestGeneratorKtUtil.isAppendApplicationPackage) {
            applicationPackage += "." + BlancoRestGeneratorKtConstants.MANAGER_PACKAGE;
        }
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
        fCgSourceFile.setEncoding(this.getEncoding());
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

            /*
            * Method specifying first.
            */
            boolean isDeleteMethod = BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.equals(method);
            boolean isGetMethod = BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.equals(method);
            boolean isPostMethod = BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.equals(method);
            boolean isPutMethod = BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.equals(method);

            BlancoCgMethod cgMethod = null;
            if (telegrams == null) {
                /* This method is unsupported. */
//                createExecuteMethodNotImplemented(method);
                /*
                 * In Micronaut support, unsupported methods are not described.
                 */
            } else {
                if (!isInterface) {
                    if (isGetMethod) {
                        cgMethod = createGetDeleteMethod(
                            telegrams, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getNoAuxiliaryAuthentication(), argProcessStructure.getMetaIdList(),
                            BlancoRestGeneratorKtConstants.GET_CONTROLLER_METHOD,
                            "Get",
                            BlancoRestGeneratorKtConstants.HTTP_METHOD_GET.split("_")[2] // Means GET
                        );
                        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Get");
                    } else if (isDeleteMethod) {
                        cgMethod = createGetDeleteMethod(
                            telegrams, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getNoAuxiliaryAuthentication(), argProcessStructure.getMetaIdList(),
                            BlancoRestGeneratorKtConstants.DELETE_CONTROLLER_METHOD,
                            "Delete",
                            BlancoRestGeneratorKtConstants.HTTP_METHOD_DELETE.split("_")[2] // Means DELETE
                        );
                        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Delete");
                    } else if (isPutMethod) {
                        cgMethod = createPostPutMethod(
                            telegrams, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getNoAuxiliaryAuthentication(), argProcessStructure.getMetaIdList(),
                            BlancoRestGeneratorKtConstants.PUT_CONTROLLER_METHOD,
                            "Put",
                            BlancoRestGeneratorKtConstants.HTTP_METHOD_PUT.split("_")[2] // Meas PUT
                        );
                        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Put");
                    } else {
                        /* PostMethod */
                        cgMethod = createPostPutMethod(
                            telegrams, argInjectedParameterId, argProcessStructure.getNoAuthentication(), argProcessStructure.getNoAuxiliaryAuthentication(), argProcessStructure.getMetaIdList(),
                            BlancoRestGeneratorKtConstants.POST_CONTROLLER_METHOD,
                            "Post",
                            BlancoRestGeneratorKtConstants.HTTP_METHOD_POST.split("_")[2] // Meas POST
                        );
                        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Post");
                    }
                } else {
                    cgMethod = isClient ?
                            createClientMethod(telegrams, method, argRequestHeaderIdSimple, argResponseHeaderIdSimple, argProcessStructure.getLocation() + "/" + argProcessStructure.getServiceId()) :
                            createExecuteMethod(telegrams, method, isSkeleton);
                }
                if (isInterface && !isSkeleton) {
                    fCgInterface.getMethodList().add(cgMethod);
                } else {
                    /* Controller or Skelton */
                    fCgClass.getMethodList().add(cgMethod);
                }
            }
        }
    }

    /**
     * Create Post execute methods for Controller
     *
     * @param argTelegrams
     * @param argInjectedParameterId
     * @param argNoAuthentication
     * @param argNoAuxiliaryAuthentication
     * @param argMetaIdList
     */
    private BlancoCgMethod createPostPutMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argInjectedParameterId,
            final Boolean argNoAuthentication,
            final Boolean argNoAuxiliaryAuthentication,
            final List<String> argMetaIdList,
            final String argExecuteMethodId,
            final String argMethodAnn,
            final String argStrMethodName
    ) {
        String methodAnn = argMethodAnn;

        /*
         * For packages in which kinds of a telegram are defined, it is assumed that they are explicitly imported in the API definition document. (or the same package)
         */
        BlancoRestGeneratorKtTelegramStructure inputTelegram = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        String requestId = inputTelegram.getCalculatedPackage() + "." + inputTelegram.getName();

        BlancoRestGeneratorKtTelegramStructure bodyTelegram = inputTelegram.getBodyTelegram();
        Boolean hasBodyTelegram = false;
        String bodyTelegramId = "";
        if (bodyTelegram != null && bodyTelegram.getName().equals(inputTelegram.getName() + "Body")) {
            hasBodyTelegram = true;
            bodyTelegramId = requestId + "Body";
        }

        if (this.isVerbose()) {
            System.out.println("### requestId = " + requestId);
            System.out.println("### bodyTelegramId = " + bodyTelegramId);
        }

        String additionalPath = inputTelegram.getAdditionalPath();
        Boolean isAdditionalPath = false;
        if (BlancoStringUtil.null2Blank(additionalPath).trim().length() > 0) {
            isAdditionalPath = true;
        }

        /*
         * Generate method paramters issue.
         */
        boolean hasPathVariable = false;
        boolean hasPathQueryFormat = false;
        String pathQueryFormats = null;
        boolean hasQueryParams = false;
        boolean isFirstQuery = true;
        boolean isFirstOptional = true;
        boolean isFirstConstArg = true;
        StringBuffer methodAnnUri = new StringBuffer();
        List<BlancoCgParameter> queryParmeters = new ArrayList<>();
        if (isAdditionalPath) {
            methodAnnUri.append(additionalPath);
        }

        if (!BlancoStringUtil.null2Blank(inputTelegram.getPathQueryFormat()).trim().isEmpty()) {
            hasPathQueryFormat = true;
            pathQueryFormats = inputTelegram.getPathQueryFormat();
        }

        List<String> requestBeanField = new ArrayList<>();
        List<String> requestBeanConst = new ArrayList<>();
        requestBeanConst.add("val requestBean = " + requestId + "(");
        for (BlancoRestGeneratorKtTelegramFieldStructure field : inputTelegram.getListField()) {
            String queryKind = BlancoStringUtil.null2Blank(field.getQueryKind());
            String name = field.getName();
            String alias = field.getAlias();
            if (BlancoStringUtil.null2Blank(alias).trim().length() == 0) {
                alias = name;
            }

            /*
            * GET/DELETE: Treat as query on not spcified.
            * POST/PUT: Treat as body json on not specified.
            */
            String paramAnn = "QueryValue";
            boolean isPathVariable = false;
            Boolean isBodyProp = false;
            if (BlancoRestGeneratorKtConstants.TELEGRAM_QUERY_KIND_PATH.equals(queryKind)) {
                hasPathVariable = true;
                isPathVariable = true;
                paramAnn = "PathVariable";
                String expectedPath = "/{" + alias + "}";
                if (hasPathQueryFormat) {
                    if (pathQueryFormats.indexOf(expectedPath) == -1) {
                        throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg10(alias));
                    }
                } else {
                    methodAnnUri.append(expectedPath);
                }
            } else if (BlancoRestGeneratorKtConstants.TELEGRAM_QUERY_KIND_QUERY.equals(queryKind)) {
                hasQueryParams = true;
                if (isFirstQuery) {
                    methodAnnUri.append("{?");
                    isFirstQuery = false;
                } else {
                    methodAnnUri.append(",");
                }
                if (BlancoRestGeneratorKtUtil.isStringArray(field)) {
                    /* Just adapt to StringArray yet now. */
                    if (!inputTelegram.getArrayNoBracket()) {
                        alias += "[]";
                    }
                    methodAnnUri.append(alias + "*");
                } else {
                    methodAnnUri.append(alias);
                }
                /* query parameters are always nullable (Optional) */
                field.setNullable(true);
            } else {
                isBodyProp = true;
            }

            String paramType = field.getType();
            if (BlancoStringUtil.null2Blank(field.getTypeKt()).trim().length() > 0) {
                paramType = field.getTypeKt();
            }
            final String paramName = "arg" + BlancoNameAdjuster.toClassName(name);
            if (!isBodyProp) {
                String paramGeneric = field.getGeneric();
                if (BlancoStringUtil.null2Blank(field.getGenericKt()).trim().length() > 0) {
                    paramGeneric = field.getGenericKt();
                }
                boolean isOptional = (field.getNullable() != null && field.getNullable());
                if (isOptional) {
                    if (isPathVariable) {
                        throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg09(inputTelegram.getName(), field.getName()));
                    }
                    /* Optional parameter should be NotNull */
                    field.setNullable(false);
                    String paramTypeBk = paramType;
                    paramType = "Optional";
                    if (isFirstOptional) {
                        fCgSourceFile.getImportList().add("java.util.Optional");
                        isFirstOptional = false;
                    }
                    if (BlancoStringUtil.null2Blank(paramGeneric).trim().length() > 0) {
                        paramTypeBk = paramTypeBk + "<" + paramGeneric + ">";
                    }
                    paramGeneric = paramTypeBk;
                }
                String paramDescription = field.getDescription();
                BlancoCgParameter queryParam = fCgFactory.createParameter(
                        paramName,
                        paramType,
                        paramDescription
                );
                queryParam.setNotnull(isOptional ? true : !field.getNullable());
                if (BlancoStringUtil.null2Blank(paramGeneric).trim().length() > 0) {
                    queryParam.getType().setGenerics(paramGeneric);
                }
                /* Always specify alias */
                paramAnn = paramAnn + "(\"" + alias + "\")";
                queryParam.getAnnotationList().add(paramAnn);
                queryParmeters.add(queryParam);
            }

            /* Copy value to bean */
            if (field.getConstArg()) {
                if (isFirstConstArg) {
                    isFirstConstArg = false;
                } else {
                    int last = requestBeanConst.size();
                    String lastString = requestBeanConst.get(last - 1);
                    lastString = lastString + ",";
                    requestBeanConst.set(last - 1, lastString);
                }
                String defaultValue = field.getDefault();
                if (BlancoStringUtil.null2Blank(field.getDefaultKt()).trim().length() > 0) {
                    defaultValue = field.getDefaultKt();
                }
                if (isBodyProp) {
                    requestBeanConst.add(name + " = argRequestBean." + name);
                } else {
                    if ("Optional".equals(paramType)) {
                        requestBeanConst.add(name + " = if (" + paramName + ".isPresent == true) " + paramName + ".get() else " + defaultValue);
                    } else {
                        requestBeanConst.add(name + " = " + paramName);
                    }
                }
            } else {
                if (isBodyProp) {
                    requestBeanField.add("requestBean." + name + " = argRequestBean." + name);
                } else {
                    if ("Optional".equals(paramType)) {
                        requestBeanField.add("if (" + paramName + ".isPresent == true) {");
                        requestBeanField.add("requestBean." + name + " = " + paramName + ".get()");
                        requestBeanField.add("}");
                    } else {
                        requestBeanField.add("requestBean." + name + " = " + paramName);
                    }
                }
            }
        }
        requestBeanConst.add(")");
        if (requestBeanField.size() > 0) {
            requestBeanConst.addAll(requestBeanField);
        }
        if (!isFirstQuery) {
            methodAnnUri.append("}");
        }
        if (hasPathVariable) {
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.PathVariable");
        }
        if (hasQueryParams) {
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.QueryValue");
        }

        if (hasPathQueryFormat) {
            methodAnn = methodAnn + "(\"" + pathQueryFormats + methodAnnUri.toString() + "\")";
        } else if (methodAnnUri.length() > 0) {
            methodAnn = methodAnn + "(\"" + methodAnnUri.toString() + "\")";
        }

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                argExecuteMethodId, fBundle.getXml2sourceFileExecutorDescription());
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
         * With the Kotlin transformer, the "<>" of the first layer of generics is automatically added and the package is removed.
         */
        String requestGenerics = requestId;
        /*
         * To inject httpRequest object as bean,
         * do not specify Request Telegram type if no body expected.
         */
        if (!hasBodyTelegram) {
            requestGenerics = "*";
        }
        httpRequest.getType().setGenerics(
                requestGenerics
        );

        /*
         * bind query parameters to bean
         */
        cgExecutorMethod.getParameterList().addAll(queryParmeters);

        /*
         * bind body json to bean if exist.
         */
        if (hasBodyTelegram) {
            BlancoCgParameter body = fCgFactory.createParameter(
                    "argRequestBean",
                    bodyTelegramId,
                    "bean that body json is binded to"
            );
            cgExecutorMethod.getParameterList().add(body);
            body.setNotnull(true);
            body.getAnnotationList().add("Body");
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Body");
        }

        /*
         * Sets the Return type.
         */
        BlancoRestGeneratorKtTelegramStructure outputTelegram = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT);
        String responseId = outputTelegram.getCalculatedPackage() + "." + outputTelegram.getName();
        if (this.isVerbose()) {
            System.out.println("### responseId = " + responseId);
        }

        BlancoCgReturn cgReturn = fCgFactory.createReturn("io.micronaut.http.HttpResponse",
                fBundle.getXml2sourceFileExecutorReturnLangdoc());
        cgExecutorMethod.setReturn(cgReturn);

        String responseGenerics = responseId;
        cgReturn.getType().setGenerics(
                responseGenerics
        );

        // Implements the method body.
        final List<String> listLine = cgExecutorMethod.getLineList();

        /*
         * Implement spoiled option.
         * ApiSpoilException should be implemented manually.
         */
        if (inputTelegram.getImpleSpoiled()) {
            listLine.add("/*");
            listLine.add(" * Test method is spoiled or not.");
            listLine.add(" * ApiSpoilException should be implemented manually.");
            listLine.add(" */");
            listLine.add("if (" + argInjectedParameterId + ".isSpoiled(\"" + argStrMethodName + "\")) {");
            listLine.add("throw ApiSpoilException()");
            listLine.add(" }");
        }

        /*
         * Add request bean creation.
         */
        listLine.add("");
        listLine.addAll(requestBeanConst);
        listLine.add("");

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

        /*
         * Merge queryString bean and body json bean
         */

        if (!hasBodyTelegram) {
            /* May exception occurs here if some illegal body is specified. */
            listLine.add("@Suppress(\"UNCHECKED_CAST\")");
            listLine.add("val typedHttpRequest = argHttpRequest as HttpRequest<" + requestId + ">");
            listLine.add("val httpCommonRequest = HttpCommonRequest(typedHttpRequest, " + noAuthentication + ", listOf(" + metaIdList + "), null)");
        } else {
            listLine.add("val httpCommonRequest = HttpCommonRequest<" + requestId + ">(argHttpRequest, " + noAuthentication + ", listOf(" + metaIdList + "), null)");
        }

        /*
         * Whether or not auxiliary authentication is required.
         */
        if (argNoAuxiliaryAuthentication) {
            listLine.add("httpCommonRequest.noAuxiliaryAuthentication = true");
        }
        listLine.add("");

        listLine.add("/* Stores the RequestBean with its type determined */");
        listLine.add("httpCommonRequest.commonRequest = requestBean");
        listLine.add("");

        listLine.add("/* Performs preprocessing (validation, etc.) */");
        listLine.add(argInjectedParameterId + ".prepare(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Passes HttpCommonRequest */");
        listLine.add("val httpResponse = " + argInjectedParameterId + "." + argExecuteMethodId + "(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Postprocessing */");
        listLine.add(argInjectedParameterId + ".finish(httpResponse, httpCommonRequest)");
        listLine.add("");
        listLine.add("return httpResponse");

        return cgExecutorMethod;
    }

    /**
     * Create Get execute methods for Controller
     *
     * @param argTelegrams
     * @param argInjectedParameterId
     * @param argNoAuthentication
     * @param argNoAuxiliaryAuthentication
     * @param argMetaIdList
     */
    private BlancoCgMethod createGetDeleteMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argInjectedParameterId,
            final Boolean argNoAuthentication,
            final Boolean argNoAuxiliaryAuthentication,
            final List<String> argMetaIdList,
            final String argExecuteMethodId,
            final String argMethodAnn,
            final String argStrMethodName
    ) {
        String methodAnn = argMethodAnn;

        /*
         * For packages in which kinds of a telegram are defined, it is assumed that they are explicitly imported in the API definition document. (or the same package)
         */
        BlancoRestGeneratorKtTelegramStructure inputTelegram = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_INPUT);
        String requestId = inputTelegram.getCalculatedPackage() + "." +inputTelegram.getName();
        if (this.isVerbose()) {
            System.out.println("### requestId = " + requestId);
        }

        String additionalPath = inputTelegram.getAdditionalPath();
        Boolean isAdditionalPath = false;
        if (BlancoStringUtil.null2Blank(additionalPath).trim().length() > 0) {
            isAdditionalPath = true;
        }

        /*
         * Generate method paramters issue.
         */
        boolean hasPathVariable = false;
        boolean hasPathQueryFormat = false;
        String pathQueryFormats = null;
        boolean hasQueryParams = false;
        boolean isFirstQuery = true;
        boolean isFirstOptional = true;
        boolean isFirstConstArg = true;
        StringBuffer methodAnnUri = new StringBuffer();
        List<BlancoCgParameter> queryParmeters = new ArrayList<>();
        if (isAdditionalPath) {
            methodAnnUri.append(additionalPath);
        }

        if (!BlancoStringUtil.null2Blank(inputTelegram.getPathQueryFormat()).trim().isEmpty()) {
            hasPathQueryFormat = true;
            pathQueryFormats = inputTelegram.getPathQueryFormat();
        }

        List<String> requestBeanConst = new ArrayList<>();
        List<String> requestBeanField = new ArrayList<>();
        requestBeanConst.add("val requestBean = " + requestId + "(");
        for (BlancoRestGeneratorKtTelegramFieldStructure field : inputTelegram.getListField()) {
            String queryKind = BlancoStringUtil.null2Blank(field.getQueryKind());
            String name = field.getName();
            String alias = field.getAlias();
            if (BlancoStringUtil.null2Blank(alias).trim().length() == 0) {
                alias = name;
            }

            /*
            * GET/DELETE: Treat as query on not spcified.
            * POST/PUT: Treat as body json on not specified.
            */
            String paramAnn = "QueryValue";
            boolean isPathVariable = false;
            if (BlancoRestGeneratorKtConstants.TELEGRAM_QUERY_KIND_PATH.equals(queryKind)) {
                hasPathVariable = true;
                isPathVariable = true;
                paramAnn = "PathVariable";
                String expectedPath = "/{" + alias + "}";
                if (hasPathQueryFormat) {
                    if (pathQueryFormats.indexOf(expectedPath) == -1) {
                        throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg10(alias));
                    }
                } else {
                    methodAnnUri.append(expectedPath);
                }
            } else {
                hasQueryParams = true;
                if (isFirstQuery) {
                    methodAnnUri.append("{?");
                    isFirstQuery = false;
                } else {
                    methodAnnUri.append(",");
                }
                if (BlancoRestGeneratorKtUtil.isStringArray(field)) {
                    /* Just adapt to StringArray yet now. */
                    if (!inputTelegram.getArrayNoBracket()) {
                        alias += "[]";
                    }
                    methodAnnUri.append(alias + "*");
                } else {
                    methodAnnUri.append(alias);
                }
                /* query parameters are always nullable (Optional) */
                field.setNullable(true);
            }

            String paramType = field.getType();
            if (BlancoStringUtil.null2Blank(field.getTypeKt()).trim().length() > 0) {
                paramType = field.getTypeKt();
            }
            String paramGeneric = field.getGeneric();
            if (BlancoStringUtil.null2Blank(field.getGenericKt()).trim().length() > 0) {
                paramGeneric = field.getGenericKt();
            }
            boolean isOptional = (field.getNullable() != null && field.getNullable());
            if (isOptional) {
                if (isPathVariable) {
                    throw new IllegalArgumentException(fBundle.getBlancorestErrorMsg09(inputTelegram.getName(), field.getName()));
                }
                /* Optional parameter should be NotNull */
                field.setNullable(false);
                String paramTypeBk = paramType;
                paramType = "Optional";
                if (isFirstOptional) {
                    fCgSourceFile.getImportList().add("java.util.Optional");
                    isFirstOptional = false;
                }
                if (BlancoStringUtil.null2Blank(paramGeneric).trim().length() > 0) {
                    paramTypeBk = paramTypeBk + "<" + paramGeneric + ">";
                }
                paramGeneric = paramTypeBk;
            }
            String paramDescription = field.getDescription();
            String paramName = "arg" + BlancoNameAdjuster.toClassName(name);
            BlancoCgParameter queryParam = fCgFactory.createParameter(
                paramName,
                paramType,
                paramDescription
            );
            queryParam.setNotnull(isOptional ? true : !field.getNullable());
            if (BlancoStringUtil.null2Blank(paramGeneric).trim().length() > 0) {
                queryParam.getType().setGenerics(paramGeneric);
            }
            /* Always specify alias */
            paramAnn = paramAnn + "(\"" + alias + "\")";
            queryParam.getAnnotationList().add(paramAnn);
            queryParmeters.add(queryParam);

            /* Copy value to bean */
            if (field.getConstArg()) {
                if (isFirstConstArg) {
                    isFirstConstArg = false;
                } else {
                    int last = requestBeanConst.size();
                    String lastString = requestBeanConst.get(last - 1);
                    lastString = lastString + ",";
                    requestBeanConst.set(last - 1, lastString);
                }
                String defaultValue = field.getDefault();
                if (BlancoStringUtil.null2Blank(field.getDefaultKt()).trim().length() > 0) {
                    defaultValue = field.getDefaultKt();
                }
                if ("Optional".equals(paramType)) {
                    requestBeanConst.add(name + " = if (" + paramName + ".isPresent == true) " + paramName + ".get() else " + defaultValue);
                } else {
                    requestBeanConst.add(name + " = " + paramName);
                }
            } else {
                if ("Optional".equals(paramType)) {
                    requestBeanField.add("if (" + paramName + ".isPresent == true) {");
                    requestBeanField.add("requestBean." + name + " = " + paramName + ".get()");
                    requestBeanField.add("}");
                } else {
                    requestBeanField.add("requestBean." + name + " = " + paramName);
                }
            }
        }
        requestBeanConst.add(")");
        if (requestBeanField.size() > 0) {
            requestBeanConst.addAll(requestBeanField);
        }
        if (!isFirstQuery) {
            methodAnnUri.append("}");
        }
        if (hasPathVariable) {
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.PathVariable");
        }
        if (hasQueryParams) {
            fCgSourceFile.getImportList().add("io.micronaut.http.annotation.QueryValue");
        }
        if (hasPathQueryFormat) {
            methodAnn = methodAnn + "(\"" + pathQueryFormats + methodAnnUri.toString() + "\")";
        } else if (methodAnnUri.length() > 0) {
            methodAnn = methodAnn + "(\"" + methodAnnUri.toString() + "\")";
        }

        final BlancoCgMethod cgExecutorMethod = fCgFactory.createMethod(
                argExecuteMethodId, fBundle.getXml2sourceFileExecutorDescription());
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
         * With the Kotlin transformer, the "<>" of the first layer of generics is automatically added and the package is removed.
         */
//        String requestGenerics = requestId;
        /*
         * Because Get and Put method never has body json,
         * we cannot specify Type to HttpRequest's generics
         * to inject HttpRequest bean.
         */
        String requestGenerics = "*";
        httpRequest.getType().setGenerics(
                requestGenerics
        );

        /*
         * bind query parameters to bean
         */
        cgExecutorMethod.getParameterList().addAll(queryParmeters);

        /*
         * Sets the Return type.
         */
        BlancoRestGeneratorKtTelegramStructure outputTelegram = argTelegrams.get(BlancoRestGeneratorKtConstants.TELEGRAM_OUTPUT);
        String responseId = outputTelegram.getCalculatedPackage() + "." + outputTelegram.getName();
        if (this.isVerbose()) {
            System.out.println("### responseId = " + responseId);
        }

        BlancoCgReturn cgReturn = fCgFactory.createReturn("io.micronaut.http.HttpResponse",
                fBundle.getXml2sourceFileExecutorReturnLangdoc());
        cgExecutorMethod.setReturn(cgReturn);

        String responseGenerics = responseId;
        cgReturn.getType().setGenerics(
                responseGenerics
        );

        // Implements the method body.
        final List<String> listLine = cgExecutorMethod.getLineList();

        /*
         * Implement spoiled option.
         * ApiSpoilException should be implemented manually.
         */
        if (inputTelegram.getImpleSpoiled()) {
            listLine.add("/*");
            listLine.add(" * Test method is spoiled or not.");
            listLine.add(" * ApiSpoilException should be implemented manually.");
            listLine.add(" */");
            listLine.add("if (" + argInjectedParameterId + ".isSpoiled(\"" + argStrMethodName + "\")) {");
            listLine.add("throw ApiSpoilException()");
            listLine.add(" }");
        }

        /*
         * Add request bean creation.
         */
        listLine.add("");
        listLine.addAll(requestBeanConst);
        listLine.add("");

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

        /* May exception occurs here if some illegal body is specified. */
        listLine.add("@Suppress(\"UNCHECKED_CAST\")");
        listLine.add("val typedHttpRequest = argHttpRequest as HttpRequest<" + requestId + ">");
        listLine.add("val httpCommonRequest = HttpCommonRequest(typedHttpRequest, " + noAuthentication + ", listOf(" + metaIdList + "), null)");

        /*
         * Whether or not auxiliary authentication is required.
         */
        if (argNoAuxiliaryAuthentication) {
            listLine.add("httpCommonRequest.noAuxiliaryAuthentication = true");
        }
        listLine.add("");

        listLine.add("/* Stores the RequestBean with its type determined */");
        listLine.add("httpCommonRequest.commonRequest = requestBean");
        listLine.add("");

        listLine.add("/* Performs preprocessing (validation, etc.) */");
        listLine.add(argInjectedParameterId + ".prepare(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Passes HttpCommonRequest */");
        listLine.add("val httpResponse = " + argInjectedParameterId + "." + argExecuteMethodId + "(httpCommonRequest)");
        listLine.add("");
        listLine.add("/* Postprocessing */");
        listLine.add(argInjectedParameterId + ".finish(httpResponse, httpCommonRequest)");
        listLine.add("");
        listLine.add("return httpResponse");

        return cgExecutorMethod;
    }

    /**
     * create execute methods for interface or skelton.
     *
     * @param argTelegrams
     * @param argMethod
     * @param isSkelton
     * @return
     */
    private BlancoCgMethod createExecuteMethod(
            final HashMap<String, BlancoRestGeneratorKtTelegramStructure> argTelegrams,
            final String argMethod,
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

        String requestGenerics = requestIdSimple;
        httpRequest.getType().setGenerics(requestGenerics);

        /* response */
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

        String responseGenerics = responseIdSimple;
        cgReturn.getType().setGenerics(responseGenerics);

        if (isSkelton) {
            // In the case of skeleton, generates the method body.
            List<String> lineList = cgExecutorMethod.getLineList();
            lineList.add("TODO(\"Not yet implemented\")");
        }

        return cgExecutorMethod;
    }

    /**
     * create execute methods for clients.
     *
     * @param argTelegrams
     * @param argMethod
     * @param argRequestHeaderIdSimple
     * @param argResponseHeaderIdSimple
     * @param argTargetpoint
     * @return
     */
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

        BlancoCgParameter httpRequest = fCgFactory.createParameter(
                "argHttpRequest",
                requestIdSimple,
                fBundle.getXml2sourceFileClientExecutorArgLangdoc()
        );
        cgExecutorMethod.getParameterList().add(httpRequest);
        httpRequest.setNotnull(true);
        httpRequest.getAnnotationList().add("Body");

        /* response */
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

        String responseGenerics = responseIdSimple;
        cgReturn.getType().setGenerics(responseGenerics);

        return cgExecutorMethod;
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
        fCgSourceFile.setEncoding(this.getEncoding());
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
        fCgSourceFile.setEncoding(this.getEncoding());
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
        fCgSourceFile.setEncoding(this.getEncoding());
        fCgSourceFile.setTabs(this.getTabs());

        // The import for use with micronaut and jackson is set to unconditional.
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpRequest");
        fCgSourceFile.getImportList().add("io.micronaut.http.HttpResponse");
        fCgSourceFile.getImportList().add("io.micronaut.http.annotation.Controller");
        // Imports kinds of a telegram.
        String telegramPkg = BlancoRestGeneratorKtUtil.telegramPackage;
        String runtimePkg = BlancoRestGeneratorKtUtil.runtimePackage;
        fCgSourceFile.getImportList().add(telegramPkg + ".HttpCommonRequest");
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
        List<String> annotationList = argProcessStructure.getAnnotationList();
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
        String applicationClassPackage = argProcessStructure.getPackage();
        if (BlancoRestGeneratorKtUtil.isAppendApplicationPackage) {
            applicationClassPackage += "." + BlancoRestGeneratorKtConstants.MANAGER_PACKAGE;
        }

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
}
