package com.dunice.projectNews.repository;

import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.mapper.UserMapper;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.models.tables.records.UsersRecord;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dunice.projectNews.models.Tables.USERS;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext dsl;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public void saveUser(User user) {
        dsl.insertInto(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, passwordEncoder.encode(user.getPassword()))
                .set(USERS.AVATAR, user.getAvatar())
                .set(USERS.NAME, user.getName())
                .set(USERS.ROLE, user.getRole())
                .set(USERS.USERS_ID, UUID.randomUUID())
                .execute();
    }

    public User getUserByEmail(String email) {
       return dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
               .fetchOptionalInto(User.class)
                .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
    }

    public Integer checkEmail(String email) {
        return dsl.selectCount()
                .from(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne(0, Integer.class);
    }

    public List<User> getAllUsers() {

        List<UsersRecord> userRecords = dsl.selectFrom(USERS)
                .fetch();

        return userRecords.stream()
                .map(userMapper::userRecordToUser)
                .collect(Collectors.toList());
    }

    public void deleteByEmail(String email) {
        dsl.deleteFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .execute();
    }

    public User getUserById(UUID id) {
        return dsl.selectFrom(USERS)
                .where(USERS.USERS_ID.eq(id))
                .fetchOptionalInto(User.class)
                .orElseThrow(() -> new CustomException(ServerErrorCodes.USER_NAME_HAS_TO_BE_PRESENT));
    }
}
