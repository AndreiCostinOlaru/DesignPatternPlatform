package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.EmailAdapter;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.User;
import com.patterns.designpatternplatform.entities.UserGroup;
import com.patterns.designpatternplatform.interfaces.Notification;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;


    @Inject
    PasswordBean passwordBean;

    public List<UserDto> copyusersToDto(List<User> users){//converts a list of users into a list of userDtos
        List<UserDto> userDtos=new ArrayList<>();
        for(User user:users){
            UserDto userDto=new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getPassword());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public List<UserDto> findAllUsers(){//returns a list of UserDtos converted from a list of all the users from the database
        try {
            LOG.info("findAllusers");
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT c FROM User c", User.class);
            List<User> users = typedQuery.getResultList();
            return copyusersToDto(users);
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }
    public void createUser(String username, String email, String password, Collection<String> groups) {//adds a user to the database based on input
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        Notification emailNotification = new EmailAdapter();
        emailNotification.sendNotification(email, "Design Pattern Platform: Created Account Succesfully!", "Thank you for creating an account!");
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {//assigns userGroups(permissions) to a user
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }
}
