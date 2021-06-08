package com.example.userservice.service;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import com.example.userservice.feign.OrderServiceFeignClient;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final Environment env;
    private final RestTemplate restTemplate;
    private final OrderServiceFeignClient
            orderServiceFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncPassword(),
                true, true, true, true,
                new ArrayList<>()
        );
    }

    @Override
    public Long createUser(UserDto userDto) {

        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);

        return userEntity.getId();
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException("user id not found");

        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        /**
         * RestTemplate를 통한 MSA간 통신
         */
//        String url = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> response = restTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });

        /**
         * FeignClient를 통한 MSA간 통신
         */
        List<ResponseOrder> orders  = null;

        try{
            //orders = orderServiceFeignClient.getOrders(userId);
            orders = orderServiceFeignClient.getOrdersError(userId);
        } catch (FeignException e) {
            // 로그만을 찍으므로 예외로 인해 서비스가 멈추지 않음
            // orders부분을 null로 하여 users를 조회 가능
            log.error(e.getMessage());
        }

        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByUsername(String username) {
        return new ModelMapper().map(userRepository.findByEmail(username), UserDto.class);
    }
}
