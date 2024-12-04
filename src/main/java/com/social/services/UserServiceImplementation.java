package com.social.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;


@Service
// Using @Service allows you to inject the service into other components (like controllers) using @Autowired 
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {


        User newUser = new User();
        // create object of User class. 

        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        
        // newUser m hmne ab sari properties set krdi h user object se nikal ke hai.

        User savedUser = userRepository.save(newUser);
        // save krdia naya user ko ab database mai.

        return savedUser;
        // return krdia savedUser ko.
    }

    @Override
    public User findUserById(Integer userId) throws Exception {

        Optional<User> user = userRepository.findById(userId);
        // optional means user may be present or might not.
        // isse pata chal jaega user present h ki nhi database mai.

        if (user.isPresent()) {
            return user.get();
            // agar user object m data hoga to isPresent() method true hoga toh user.get() se data nikal kr return krdenge user data ko.
        }
        throw new Exception("User not exists with given id");
        // wrna nhi toh phir exception throw krdenge ki user nhi mila iss id se.
    }

    @Override
    public User findUserByEmail(String email) {

        User user=userRepository.findByEmail(email);
        // userRepository m hmne ek findByEMail() krke bnaya tha method wo call krdenge user nikalne k liye email se.

        return user;
        // user return krdenge
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws Exception {
       
        User reqUser=findUserById(reqUserId);
        // phle jo logged in user wo nikal lenge reqUserId use krke.
        User user2=findUserById(userId2);
        // isse dusra user jisko follow krna chahta h banda wo nikal lenge

        user2.getFollowers().add(reqUser.getId());
        // user2 ke andar followers krke list h uske setter method se use krke add method use krlenge aur reqUser ki id lga denge

        reqUser.getFollowings().add(user2.getId());
        // ese hi dekho reqUSer ke andar follwoing list m add krdenge dusre user ki id

        userRepository.save(reqUser);
        userRepository.save(user2);
        // dono m dekho followers and follwoing list update kri h hmne to wo save krdenge userRepository ka save method use krke.

        return reqUser;
        // ab return krdo logged in user ko hi.
    }

    @Override
    public User updateUser(User user,Integer id) throws Exception {

        Optional<User> up = userRepository.findById(id);
        // phle toh yr ye dekhnge ki user jisko update krna h wo present h bh ya nhi database mai .

        if (up.isEmpty()) {
            throw new Exception("user does not exist with given id");
        }

        User oldUser = up.get();
        // ab ham purane user ko nikal lenge

        if (user.getFirstName() != null) {
            oldUser.setFirstName(user.getFirstName());
        }
        // user ne agar FirstName field m kch data bheja hua h to mtlb wo update krna chahra wo field toh phir oldUser m wo value nikal kr denge
        if (user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            oldUser.setGender(user.getGender());
        }

        User updatedUser = userRepository.save(oldUser);
        // update krlenge userRepository.save() method use krke

        return updatedUser;
        // ab return krdenge updatedUser
    }

    @Override
    public List<User> searchUser(String query) {
      
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        
        String email=JwtProvider.getEmailFromJwtToken(jwt);
        // email nikal lenge dekho phle token se hmne phle hi JwtProvider m getEmailFromJwtToken() bnaya tha method wha se.

        User user=userRepository.findByEmail(email);
        // phir findByEmail use krke jo hmne custom method bnaya h userRepository m wo use krke user nikal lenge database se.

        return user;
        // ab user return krdenge
    }
    
}