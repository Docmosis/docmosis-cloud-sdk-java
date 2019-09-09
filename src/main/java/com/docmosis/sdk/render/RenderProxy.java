/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.docmosis.sdk.render;
//TODO: Delete
import java.io.Serializable;

/**
 * A simple class holding web-proxy information that can be used
 * when reaching the web services.
 * 
 * TODO - make this proxy class part of basic connectivity - not render specific.  Should be part of Envrionment.  
 */
public class RenderProxy implements Serializable {

    private static final long serialVersionUID = 8574977465143475121L;

    private String host;
    private int port;
    private String user;
    private String passwd;

    public RenderProxy() {
        super();
    }
    
    /**
     * Create a render proxy with the given host port and credentials.
     * 
     * @param host the hostname
     * @param port the port
     * @param user the user name for the proxy
     * @param passwd the password for the proxy
     */
    public RenderProxy(String host, int port, String user, String passwd) {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.passwd = passwd;
    }

    public String getHost() {
        return host;
    }

    /**
     * Set the host name
     * 
     * @param host the proxy host name
     */
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    /**
     * Set the proxy port
     * 
     * @param port the proxy port number
     */
    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    /**
     * Set the proxy user
     * 
     * @param user the user name for the proxy account
     */
    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    /**
     * Set the proxy password
     * 
     * @param passwd the password for the proxy account
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "RendererProxy [host=" + host + ", port=" + port + ", user=" + user + ", passwd=" + passwd + "]";
    }
}
