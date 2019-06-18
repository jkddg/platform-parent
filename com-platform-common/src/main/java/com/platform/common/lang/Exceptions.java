package com.platform.common.lang;

/**
 * Created by Huangyonghao on 2019/6/17 16:35.
 */


import org.apache.logging.log4j.util.Strings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * 异常辅助类。
 */
public class Exceptions {

    public static FaultException notFoundObject(String objectName, Object objectId) {
        return new NotFoundObjectException(objectName, objectId);
    }

    /**
     * not implemented method exception
     *
     * @param methodName
     * @return
     */
    public static FaultException notImpl(String methodName) {
        return new FaultException("Not implemented " + methodName);
    }

    /**
     * @param methodName
     * @return
     * @deprecated take notImpl instead
     */
    @Deprecated
    public static FaultException newNotImplemented(String methodName) {
        return new FaultException("Not implemented " + methodName);
    }

    public static FaultException undefinedEnum(Class enumClass, Object value) {
        return new FaultException(String.format("Undefined enum %s: %s", enumClass.getSimpleName(), value));
    }

    public static FaultException undefinedEnum(String enumName, Object value) {
        return new FaultException(String.format("Undefined enum %s: %s", enumName, value));
    }

    /**
     * 构造 RuntimeException 实例，内部使用 MessageFormat.format 进行格式化。
     *
     * @param message
     * @param args
     * @return RuntimeException
     */
    public static RuntimeException error(String message, Object... args) {
        return new RuntimeException(MessageFormat.format(message, args));
    }

    /**
     * 将 Exception 包装为 RuntimeException，如果是 FaultException/RuntimeException 则直接返回。
     *
     * @param ex
     * @return
     * @deprecated 不建议直接使用 RuntimeException, 请使用 asUnchecked 方法替代
     */
    @Deprecated
    public static RuntimeException asRuntime(Exception ex) {
        if (ex instanceof FaultException || ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }
        return new RuntimeException(ex);
    }

    /**
     * 将 Exception 包装为 UncheckedException
     *
     * @param ex
     * @return
     */
    public static UncheckedException asUnchecked(Throwable ex) {
        return new UncheckedException(ex);
    }

    /**
     * 返回指定异常的堆栈信息。
     *
     * @param e
     * @return
     */
    public static String getStackTrace(Throwable e) {
        Objects.requireNonNull(e, "arg e");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 返回异常错误信息，如果 getMessage 返回空，则返回异常类型名称。
     *
     * @param e
     * @return
     */
    public static String getMessage(Throwable e) {
        Objects.requireNonNull(e, "arg e");
        String msg = e.getMessage();
        if (Strings.isEmpty(msg)) {
            msg = e.getClass().getName();
        }
        return msg;
    }
}
