package com.franchise.ServiceImpl;

import com.franchise.Entity.User;
import com.franchise.Repository.UserRepo;
import com.franchise.Service.UserService;
import com.franchise.Tenant.TenantContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	    @Autowired
	    private UserRepo userrepo;

	    @Override
		public User saveUser(User user) {
			return userrepo.save(user);
		}
    @Override
    public Optional<User> findByEmail(String email) {
        return userrepo.findByEmail(email);
    }

    @Override
    public boolean isActiveUser(String email) {
        Optional<User> userOpt = userrepo.findByEmail(email);
        return userOpt.isPresent() && userOpt.get().getIsActive();
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userrepo.findByEmail(email);
        return userOpt.isPresent() && 
               userOpt.get().getPassword().equals(password) && 
               userOpt.get().getIsActive();
    }

    @Override
    public List<User> getallusers() {
        return userrepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userrepo.findById(id);
    }

    @Override
    @Transactional
    public Optional<User> updateUser(Long id, User user) {
        return userrepo.findById(id).map(existingUser -> {
            // Update all fields as in your original code
            existingUser.setPrefix(user.getPrefix());
            existingUser.setFirstname(user.getFirstname());
            // ... (all other field updates)
            
            return userrepo.save(existingUser);
        });
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (userrepo.existsById(id)) {
            userrepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User getUserWithRolesAndPermissions(String email) {
        List<User> users = userrepo.findByEmailItsPermissions(email);
        if (users.size() == 1) {
            return users.get(0);
        } else if (users.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Multiple users found with email: " + email);
        }
    }

    @Override
    public Optional<String> getUserName(String email) {
        return userrepo.getUsernameByEmail(email);
    }
}