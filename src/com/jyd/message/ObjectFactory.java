
package com.jyd.message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jyd.message package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllClientUser_QNAME = new QName("http://message.jyd.com/", "getAllClientUser");
    private final static QName _SendMessageToAllClientResponse_QNAME = new QName("http://message.jyd.com/", "sendMessageToAllClientResponse");
    private final static QName _SendMessageToClientResponse_QNAME = new QName("http://message.jyd.com/", "sendMessageToClientResponse");
    private final static QName _GetAllClientUserResponse_QNAME = new QName("http://message.jyd.com/", "getAllClientUserResponse");
    private final static QName _SendMessageToClient_QNAME = new QName("http://message.jyd.com/", "sendMessageToClient");
    private final static QName _SendMessageToAllClient_QNAME = new QName("http://message.jyd.com/", "sendMessageToAllClient");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jyd.message
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendMessageToAllClient }
     * 
     */
    public SendMessageToAllClient createSendMessageToAllClient() {
        return new SendMessageToAllClient();
    }

    /**
     * Create an instance of {@link SendMessageToClientResponse }
     * 
     */
    public SendMessageToClientResponse createSendMessageToClientResponse() {
        return new SendMessageToClientResponse();
    }

    /**
     * Create an instance of {@link SendMessageToAllClientResponse }
     * 
     */
    public SendMessageToAllClientResponse createSendMessageToAllClientResponse() {
        return new SendMessageToAllClientResponse();
    }

    /**
     * Create an instance of {@link GetAllClientUser }
     * 
     */
    public GetAllClientUser createGetAllClientUser() {
        return new GetAllClientUser();
    }

    /**
     * Create an instance of {@link SendMessageToClient }
     * 
     */
    public SendMessageToClient createSendMessageToClient() {
        return new SendMessageToClient();
    }

    /**
     * Create an instance of {@link GetAllClientUserResponse }
     * 
     */
    public GetAllClientUserResponse createGetAllClientUserResponse() {
        return new GetAllClientUserResponse();
    }

    /**
     * Create an instance of {@link ServerMessage }
     * 
     */
    public ServerMessage createServerMessage() {
        return new ServerMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClientUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "getAllClientUser")
    public JAXBElement<GetAllClientUser> createGetAllClientUser(GetAllClientUser value) {
        return new JAXBElement<GetAllClientUser>(_GetAllClientUser_QNAME, GetAllClientUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageToAllClientResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "sendMessageToAllClientResponse")
    public JAXBElement<SendMessageToAllClientResponse> createSendMessageToAllClientResponse(SendMessageToAllClientResponse value) {
        return new JAXBElement<SendMessageToAllClientResponse>(_SendMessageToAllClientResponse_QNAME, SendMessageToAllClientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageToClientResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "sendMessageToClientResponse")
    public JAXBElement<SendMessageToClientResponse> createSendMessageToClientResponse(SendMessageToClientResponse value) {
        return new JAXBElement<SendMessageToClientResponse>(_SendMessageToClientResponse_QNAME, SendMessageToClientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClientUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "getAllClientUserResponse")
    public JAXBElement<GetAllClientUserResponse> createGetAllClientUserResponse(GetAllClientUserResponse value) {
        return new JAXBElement<GetAllClientUserResponse>(_GetAllClientUserResponse_QNAME, GetAllClientUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageToClient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "sendMessageToClient")
    public JAXBElement<SendMessageToClient> createSendMessageToClient(SendMessageToClient value) {
        return new JAXBElement<SendMessageToClient>(_SendMessageToClient_QNAME, SendMessageToClient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageToAllClient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.jyd.com/", name = "sendMessageToAllClient")
    public JAXBElement<SendMessageToAllClient> createSendMessageToAllClient(SendMessageToAllClient value) {
        return new JAXBElement<SendMessageToAllClient>(_SendMessageToAllClient_QNAME, SendMessageToAllClient.class, null, value);
    }

}
