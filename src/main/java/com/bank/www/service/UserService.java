package com.bank.www.service;

import com.bank.www.domain.user.User;
import com.bank.www.domain.user.UserEnum;
import com.bank.www.domain.user.UserRepository;
import com.bank.www.dto.user.UserReqDto;
import com.bank.www.dto.user.UserRespDto;
import com.bank.www.dto.user.UserRespDto.JoinRespDto;
import com.bank.www.handler.ex.CustomApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional // 트랜잭션이 메서드 시작할 때, 시작되고, 종료될 때 함께 종료
    public JoinRespDto 회원가입(UserReqDto.JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다"); // 유저네임 중복되었다는 뜻
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinRespDto(userPS);
    }

//    @Setter
//    @Getter
//    @ToString
//    public static class JoinRespDto {
//        private Long id;
//        private String username;
//        private String fullname;
//
//        public JoinRespDto(User user) {
//            this.id = user.getId();
//            this.username = user.getUsername();
//            this.fullname = user.getFullname();
//        }
//    }

//    @Setter
//    @Getter
//    public static class JoinReqDto {
//        // 유효성 검사
//        private String username;
//        private String password;
//        private String email;
//        private String fullname;
//
//        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
//            return User.builder()
//                       .username(username)
//                       .password(passwordEncoder.encode(password))
//                       .email(email)
//                       .fullname(fullname)
//                       .role(UserEnum.CUSTOMER)
//                       .build();
//        }
//    }
}
