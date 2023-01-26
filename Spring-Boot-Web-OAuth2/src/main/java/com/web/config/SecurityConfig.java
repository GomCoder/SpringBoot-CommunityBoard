package com.web.config;

import com.sun.istack.NotNull;
import com.web.oauth2.CustomOAuth2Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.web.domain.enums.SocialType.*;

@Configuration
@EnableWebSecurity //웹에서 시큐리티 기능 사용 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter { //최적화 설정을 위해 WebSecurityConfigurerAdapter을 상속

    @Override
    protected void configure(HttpSecurity http) throws Exception { //원하는 형식의 시큐리티 설정
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
                .authorizeRequests() //인증 메커니즘을 위한 HttpServletRequest 기반으로 설정
                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**")// 요청 패턴을 리스트 형식으로 반환
                .permitAll()// 설정한 Request 패턴을 누구나 접근할 수 있도록 허용
                .antMatchers("/facebook").hasAnyAuthority(FACEBOOK.getRoleType()) //antMatchers() 각각의 소셜 미디어용 경로 지정, hasAnyAuthority() 원하는 권한을 전달하여 해당 권한을 지닌 사용자로만 경로를 사용할 수 있도록 통제
                .antMatchers("/google").hasAnyAuthority(GOOGLE.getRoleType())
                .antMatchers("/kakao").hasAnyAuthority(KAKAO.getRoleType())
                .anyRequest()// 설정한 요청 이외의 Request 요청을 표현
                .authenticated()//해당 요청은 인즌된 사용자만 할 수 있음
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .and()
                .headers()// 응답에 해당하는 header를 설정, 설정하지 않을시 default 값으로 설정됨
                .frameOptions().disable()//XFrameOptionalWriter의 최적화 설정을 허용하지 않음
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))//인증의 진입 지점, 인증되지 않은 사용자가 허용하지 않은 경로로 request를 요청할 경우 '/login'으로 이동
                .and()
                .formLogin().successForwardUrl("/board/list")// 로그인에 성공하면 설정된 경로로 포워딩됨
                .and()
                .logout()//로그 아웃에 대한 설정
                .logoutUrl("/logout")//로그 아웃이 수행될 URL
                .logoutSuccessUrl("/")//로그아웃 성공시 포워딩될 URL
                .deleteCookies("JSESSIONID")//로그아웃을 성공했을 때 삭제될 쿠키값
                .invalidateHttpSession(true)//설정할 세션의 무효화
                .and()
                .addFilterBefore(filter, CsrfFilter.class)//문자 인코딩 필터보다 CsrfFilter를 먼저 실행하도록 설정
                .csrf().disable();
    }

    
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream().map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") // 필요없는 값이지만 null이면 실행이 안되므로 임시 값을 넣음
                .jwkSetUri("test") // 필요없는 값이지만 null이면 실행이 안되므로 임시 값을 넣음
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        if("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");

            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email", "profile")
                    .build();
        }
        return null;
    }
}
