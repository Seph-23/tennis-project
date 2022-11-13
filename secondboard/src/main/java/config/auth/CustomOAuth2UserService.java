package config.auth;

import config.auth.dto.OAuthAttributes;
import myweb.secondboard.domain.user.User;
import myweb.secondboard.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.servlet.http.HttpSession;
import java.util.Collections;

public class CustomOAuth2UserService {

    public class CustomOAUth2UserService implements CustomOAuth2UserService <OAuth2UserRequest, OAuth2User> {
        private final UserRepository userRepository;
        private final HttpSession httpSession;

        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2UserService<OAuth2UserRequest, OAuth2User>
                    delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.
                                            loadUser(userRequest);

            String registrationId = userRequest.
                getClientRegistration().getRegistrationId();
            String userNameAttibuteName = userRequest.
                    getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().
                                getUserNameAttributeName();

            OAuthAttributes attributes = OAuthAttributes.
                    of(registrationId, userNameAttibuteName,
                            OAuth2User.getAttributes());

            User user = saveOrUpdate(attributes);
            httpSession.setAttribute("user",new SessionUser(user));

            return new DefaultOAuth2User(
                    Collections.singleton(new
                            SimpleGrantedAuthority(user.gotRoleKey())),
                    attributes.getAttributes().
                    attributes.getNameAttributeKey());
            }

            private User saveOrUpdate(OAuthAttributes attributes) {
                User user = userRepository.findByEmail(attributes.getEmail())
                        .map(entity -> entity.update(attributes.
                                getName(), attributes.getPicture()))
                        .orElse(attributes.toEntity());

                return userRepository.save(user);
            }
    }

}