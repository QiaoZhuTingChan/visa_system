package com.jyd.bms.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.BCodec;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.Servlets;

public class DownloadUtil
{
    private static final Logger log;
    public static final MimetypesFileTypeMap MIME_TYPES;
    public static final String IN_LINE = "inline";
    public static final String ATTACHMENT = "attachment";
    
    static {
        log = LoggerFactory.getLogger((Class)DownloadUtil.class);
        MIME_TYPES = new MimetypesFileTypeMap();
    }
    
    public static void fileNotFound(final HttpServletRequest request, final HttpServletResponse response, final String errorMsg) throws IOException, ServletException {
        request.getRequestDispatcher("/noCache.jsp").include((ServletRequest)request, (ServletResponse)response);
        final StringBuffer buffer = new StringBuffer("<html>\n<head>\n<title>ERROR</title></head><body>");
        buffer.append(errorMsg);
        buffer.append("\n</body>\n</html>");
        response.getWriter().println(buffer.toString());
    }
    
    public static void inline(final HttpServletRequest request, final HttpServletResponse response, final String location, final String fileName, final String fileNameEncoding) throws IOException, ServletException {
        openFile(request, response, location, fileName, fileNameEncoding, "inline");
    }
    
    public static void attachment(final HttpServletRequest request, final HttpServletResponse response, final String location, final String fileName, final String fileNameEncoding) throws IOException, ServletException {
        openFile(request, response, location, fileName, fileNameEncoding, "attachment");
    }
    
    private static void openFile(final HttpServletRequest request, final HttpServletResponse response, final String location, final String fileName, final String fileNameEncoding, final String openType) throws IOException, ServletException {
        String errorMsg = null;
        final File file = new File(location);
        if (file.exists()) {
            setDownloadHeader(request, response, fileName, openType, fileNameEncoding, errorMsg);
            if (errorMsg == null) {
                IOUtils.copy((InputStream)new FileInputStream(file), (OutputStream)response.getOutputStream());
                response.getOutputStream().flush();
                return;
            }
        }
        else {
            errorMsg = "The specific file (" + location + ",  not existed!";
            DownloadUtil.log.error(errorMsg);
        }
        fileNotFound(request, response, errorMsg);
    }
    
    public static void inline(final HttpServletRequest request, final HttpServletResponse response, final byte[] bytes, final String fileName, final String fileNameEncoding) throws IOException, ServletException {
        openByteArray(request, response, bytes, fileName, fileNameEncoding, "inline");
    }
    
    public static void attachment(final HttpServletRequest request, final HttpServletResponse response, final byte[] bytes, final String fileName, final String fileNameEncoding) throws IOException, ServletException {
        openByteArray(request, response, bytes, fileName, fileNameEncoding, "attachment");
    }
    
    private static void openByteArray(final HttpServletRequest request, final HttpServletResponse response, final byte[] bytes, final String fileName, final String fileNameEncoding, final String openType) throws IOException, ServletException {
        final String errorMsg = null;
        setDownloadHeader(request, response, fileName, openType, fileNameEncoding, errorMsg);
        if (errorMsg == null) {
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        }
        else {
            fileNotFound(request, response, errorMsg);
        }
    }
    
    public static void attachmentForString(final HttpServletRequest request, final HttpServletResponse response, final String htmlString, final String fileName, final String fileNameEncoding) throws IOException, ServletException {
        openString(request, response, htmlString, fileName, fileNameEncoding, "attachment");
    }
    
    private static void openString(final HttpServletRequest request, final HttpServletResponse response, final String htmlString, final String fileName, final String fileNameEncoding, final String openType) throws IOException, ServletException {
        final String errorMsg = null;
        setDownloadHeader(request, response, fileName, openType, fileNameEncoding, errorMsg);
        if (errorMsg == null) {
            response.setCharacterEncoding(fileNameEncoding);
            response.getWriter().print(htmlString);
            response.getWriter().flush();
        }
        else {
            fileNotFound(request, response, errorMsg);
        }
    }
    
    private static void setDownloadHeader(final HttpServletRequest request, final HttpServletResponse response, final String fileName, final String openType, final String fileNameEncoding, String errorMsg) {
        if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
            response.setHeader("Pragma", "No-cache");
        }
        else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
            response.setHeader("Cache-Control", "No-cache");
        }
        response.setDateHeader("Expires", 0L);
        final String contentType = DownloadUtil.MIME_TYPES.getContentType(fileName);
        if ("application/octet-stream".equals(contentType)) {
            DownloadUtil.log.error("file " + fileName + " mimetype mapping not found. " + "Please add new ContentType record in /META-INF/mime.types");
        }
        response.setContentType(contentType);
        DownloadUtil.log.info("agent ================{}", (Object)Servlets.getBrowser((ServletRequest)request));
        if (Servlets.isBrowser((ServletRequest)request, "ie")) {
            try {
                response.setHeader("Content-Disposition", openType + "; filename=\"" + URLEncoder.encode(fileName, fileNameEncoding) + "\"");
            }
            catch (UnsupportedEncodingException ex) {
                errorMsg = "Encode file name (" + fileName + ") occur errors!";
                DownloadUtil.log.error(errorMsg, (Throwable)ex);
            }
        }
        else {
            try {
                response.setHeader("Content-Disposition", openType + "; filename=\"" + new BCodec().encode(fileName, fileNameEncoding) + "\"");
            }
            catch (EncoderException ex2) {
                errorMsg = "Encode file name (" + fileName + ") occur errors!";
                DownloadUtil.log.error(errorMsg, (Throwable)ex2);
            }
        }
    }
}
