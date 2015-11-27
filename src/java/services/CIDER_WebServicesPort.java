/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author admCIDER
 */
@Path("cider_webservicesport")
public class CIDER_WebServicesPort {

    private services_client.CIDERWebServices port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CIDER_WebServicesPort
     */
    public CIDER_WebServicesPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method getEntidadesLabels
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getentidadeslabels/")
    public String getEntidadesLabels() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getEntidadesLabels();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getVariableLabels
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getvariablelabels/")
    public String getVariableLabels() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getVariableLabels();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getVariableOptions
     * @param variableName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getvariableoptions/")
    public String getVariableOptions(@QueryParam("variableName") String variableName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getVariableOptions(variableName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method ArrayListToJSONArray
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<services_client.ArrayListToJSONArrayResponse>
     */
    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("arraylisttojsonarray/")
    public JAXBElement<services_client.ArrayListToJSONArrayResponse> postArrayListToJSONArray(JAXBElement<List<Object>> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<java.lang.Object> result = port.arrayListToJSONArray(arg0.getValue());

                class ArrayListToJSONArrayResponse_1 extends services_client.ArrayListToJSONArrayResponse {

                    ArrayListToJSONArrayResponse_1(java.util.List<java.lang.Object> _return) {
                        this._return = _return;
                    }
                }
                services_client.ArrayListToJSONArrayResponse response = new ArrayListToJSONArrayResponse_1(result);
                return new services_client.ObjectFactory().createArrayListToJSONArrayResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method numCentros
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("numcentros/")
    public String getNumCentros() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.numCentros();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method numEntidades
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("numentidades/")
    public String getNumEntidades() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.numEntidades();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getCentro
     * @param centroName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getcentro/")
    public String getCentro(@QueryParam("centroName") String centroName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getCentro(centroName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getEntidad
     * @param entidadName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getentidad/")
    public String getEntidad(@QueryParam("entidadName") String entidadName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getEntidad(entidadName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getCentroLabels
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getcentrolabels/")
    public String getCentroLabels() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getCentroLabels();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getVariable
     * @param variableName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getvariable/")
    public String getVariable(@QueryParam("variableName") String variableName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getVariable(variableName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method parseJSONArray
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<services_client.ParseJSONArrayResponse>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("parsejsonarray/")
    public JAXBElement<services_client.ParseJSONArrayResponse> getParseJSONArray(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<java.lang.String> result = port.parseJSONArray(arg0);

                class ParseJSONArrayResponse_1 extends services_client.ParseJSONArrayResponse {

                    ParseJSONArrayResponse_1(java.util.List<java.lang.String> _return) {
                        this._return = _return;
                    }
                }
                services_client.ParseJSONArrayResponse response = new ParseJSONArrayResponse_1(result);
                return new services_client.ObjectFactory().createParseJSONArrayResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getConnectionBetweenEntities
     * @param entity1Name resource URI parameter
     * @param entity2Name resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getconnectionbetweenentities/")
    public String getConnectionBetweenEntities(@QueryParam("entity1Name") String entity1Name, @QueryParam("entity2Name") String entity2Name) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getConnectionBetweenEntities(entity1Name, entity2Name);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private services_client.CIDERWebServices getPort() {
        try {
            // Call Web Service Operation
            services_client.CIDERWebServices_Service service = new services_client.CIDERWebServices_Service();
            services_client.CIDERWebServices p = service.getCIDERWebServicesPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
