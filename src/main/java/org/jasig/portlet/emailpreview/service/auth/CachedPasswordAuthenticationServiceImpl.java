/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.emailpreview.service.auth;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.mail.Authenticator;
import javax.portlet.PortletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.emailpreview.MailStoreConfiguration;
import org.jasig.portlet.emailpreview.SimplePasswordAuthenticator;
import org.jasig.portlet.emailpreview.service.ConfigurationParameter;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Jen Bourey, jbourey@unicon.net
 * @version $Revision$
 */
@Component("cachedPasswordAuthenticationService")
public class CachedPasswordAuthenticationServiceImpl implements
        IAuthenticationService {
    
    private static final String KEY = "cachedPassword";
    protected static final String USERNAME_SUFFIX_KEY = "usernameSuffix";
    
    private String usernameKey = "user.login.id";
    private String passwordKey = "password";

    /*
     * (non-Javadoc)
     * @see org.jasig.portlet.emailpreview.service.IAuthenticationService#getAuthenticator(javax.portlet.PortletRequest, org.jasig.portlet.emailpreview.MailStoreConfiguration)
     */
    public Authenticator getAuthenticator(PortletRequest request,
            MailStoreConfiguration config) {
        
        @SuppressWarnings("unchecked")
        Map<String, String> userInfo = (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
        String username = userInfo.get(usernameKey);
        String password = userInfo.get(passwordKey);
        
        String usernameSuffix = config.getAdditionalProperties().get(USERNAME_SUFFIX_KEY);
        if (!StringUtils.isBlank(usernameSuffix)) {
            username = username.concat(usernameSuffix);
        }
        
        return new SimplePasswordAuthenticator(username, password);
    }

    /*
     * (non-Javadoc)
     * @see org.jasig.portlet.emailpreview.service.IAuthenticationService#getKey()
     */
    public String getKey() {
        return KEY;
    }

    /*
     * (non-Javadoc)
     * @see org.jasig.portlet.emailpreview.service.auth.IAuthenticationService#getAdminConfigurationParameters()
     */
    public List<ConfigurationParameter> getAdminConfigurationParameters() {
        ConfigurationParameter param = new ConfigurationParameter();
        param.setKey(USERNAME_SUFFIX_KEY);
        param.setLabel("Username Suffix");
        return Collections.<ConfigurationParameter>singletonList(param);
    }

    /*
     * (non-Javadoc)
     * @see org.jasig.portlet.emailpreview.service.auth.IAuthenticationService#getUserConfigurationParameters()
     */
    public List<ConfigurationParameter> getUserConfigurationParameters() {
        return Collections.<ConfigurationParameter>emptyList();
    }

}
