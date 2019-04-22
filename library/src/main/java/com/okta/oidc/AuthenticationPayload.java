/*
 * Copyright (c) 2019, Okta, Inc. and/or its affiliates. All rights reserved.
 * The Okta software accompanied by this notice is provided pursuant to the Apache License,
 * Version 2.0 (the "License.")
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and limitations under the
 * License.
 */

package com.okta.oidc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.okta.oidc.net.request.web.AuthorizeRequest.LOGIN_HINT;
import static com.okta.oidc.net.request.web.AuthorizeRequest.STATE;

/**
 * That is payload that is used in order to provide additional parameters
 * for Authorization request or alter default parameters values (like state).
 */
public class AuthenticationPayload {
    private Map<String, String> mAdditionalParameters;

    private AuthenticationPayload(Map<String, String> additionalParameters) {
        this.mAdditionalParameters = additionalParameters;
    }

    /**
     * Creates instances of {@link AuthenticationPayload}.
     */
    public static class Builder {

        private Map<String, String> mAdditionalParameters = new HashMap<>();

        /**
         * Okta requires the OAuth 2.0 state parameter on all requests to the /authorize endpoint
         * in order to prevent cross-site request forgery (CSRF). The OAuth 2.0 specification
         * requires that clients protect their redirect URIs against CSRF by sending a value in
         * the authorize request which binds the request to the user-agent's authenticated state.
         * Using the state parameter is also a countermeasure to several other known attacks
         * as outlined in
         * [RFC6819 Section 5.3.5](https://tools.ietf.org/html/rfc6819#section-5.3.5).
         * <p>
         * If AuthenticationPayload is not used. The state parameter is generated by default.
         *
         * @param state state value
         * @return current Builder
         * @see "The OAuth 2.0 Authorization Framework (RFC 6749), Section 4.1.1
         * <https://tools.ietf.org/html/rfc6749#section-4.1.1>"
         * @see "The OAuth 2.0 Authorization Framework (RFC 6749), Section 5.3.5
         * <https://tools.ietf.org/html/rfc6749#section-5.3.5>"
         */
        public Builder setState(@NonNull String state) {
            mAdditionalParameters.put(STATE, state);
            return this;
        }

        /**
         * A username to prepopulate if prompting for authentication.
         *
         * @param loginHint login hint value
         * @return current Builder
         * @see "OpenID Connect Core 1.0, Section 3.1.2.1
         * <https://openid.net/specs/openid-connect-core-1_0.html#rfc.section.3.1.2.1>"
         */
        public Builder setLoginHint(String loginHint) {
            mAdditionalParameters.put(LOGIN_HINT, loginHint);
            return this;
        }

        /**
         * Specifies additional parameter. Replaces any previously provided parameter and overwrites
         * default parameters. Parameter keys and values cannot be null or empty.
         *
         * @param name  parameter name.
         * @param value parameter value
         * @return current Builder
         * @see "Okta OpenID Connect & OAuth 2.0 for parameter details
         * <https://developer.okta.com/docs/api/resources/oidc/#authorize>"
         */
        public Builder addParameter(@NonNull String name, @NonNull String value) {
            mAdditionalParameters.put(name, value);
            return this;
        }

        /**
         * Constructs a new instance of {@link AuthenticationPayload}.
         *
         * @return constructed authentication payload
         */
        public AuthenticationPayload build() {
            return new AuthenticationPayload(mAdditionalParameters);
        }

    }

    /**
     * State getter.
     *
     * @return current state
     */
    @Nullable
    public String getState() {
        return mAdditionalParameters.get(STATE);
    }

    /**
     * Login hint getter.
     *
     * @return current login hint
     */
    @Nullable
    public String getLoginHint() {
        return mAdditionalParameters.get(LOGIN_HINT);
    }

    /**
     * Additional Parameters getter.
     *
     * @return additional parameter
     */
    public Map<String, String> getAdditionalParameters() {
        return mAdditionalParameters;
    }
}
