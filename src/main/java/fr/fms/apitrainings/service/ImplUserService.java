package fr.fms.apitrainings.service;

import fr.fms.apitrainings.dao.UsersRepository;
import fr.fms.apitrainings.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplUserService implements IService<Users> {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<Users> getAll() {
        return null;
    }

    @Override
    public Optional<Users> getOneById(long id) {
        return Optional.empty();
    }

    @Override
    public Users save(Users obj) {
        return usersRepository.save(obj);
    }

    @Override
    public void delete(long id) {

    }

    public Users getUser(String email) {
        return usersRepository.findByEmail(email);
    }
}
