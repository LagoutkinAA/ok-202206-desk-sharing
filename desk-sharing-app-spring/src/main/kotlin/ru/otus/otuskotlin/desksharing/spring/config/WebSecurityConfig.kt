package ru.otus.otuskotlin.desksharing.spring.config

import com.nimbusds.jose.shaded.json.JSONArray
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import java.util.stream.Collectors


@Configuration
@EnableWebSecurity
class WebSecurityConfig(val keycloakLogoutHandler: KeycloakLogoutHandler?) {

    @Bean
    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy? {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.authorizeRequests()
            .antMatchers("/v1/demand/*")
            .hasRole("USER")
            .anyRequest()
            .permitAll()
        http.oauth2Login()
            .and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler)
            .logoutSuccessUrl("/")
        return http.build()
    }

    @Bean
    fun oidcUserService(): OAuth2UserService<OidcUserRequest?, OidcUser>? {
        val delegate = OidcUserService()
        return OAuth2UserService { userRequest: OidcUserRequest? ->
            val oidcUser = delegate.loadUser(userRequest)
            val claims = oidcUser.claims
            val groups: JSONArray? = claims["groups"] as JSONArray?
            val mappedAuthorities: Set<GrantedAuthority> = groups?.stream()
                ?.map { role -> SimpleGrantedAuthority("ROLE_$role") }
                ?.collect(Collectors.toSet()) ?: emptySet()
            DefaultOidcUser(mappedAuthorities, oidcUser.idToken, oidcUser.userInfo)
        }
    }

    companion object {
        const val ID_CLAIM = "sub"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"
    }
}