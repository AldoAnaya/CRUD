package com.ProyectoCiclo3.CRUD.Service;

import com.ProyectoCiclo3.CRUD.Entities.Enterprise;
import com.ProyectoCiclo3.CRUD.Entities.Profile;
import com.ProyectoCiclo3.CRUD.Repository.EnterpriseRepository;
import com.ProyectoCiclo3.CRUD.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    EmployeeService employeeService;

    public void crearProfile(Profile profile){
        profileRepository.save(profile);
    }
    public List<Profile> verProfile(){
        List<Profile> listProfile = profileRepository.findAll();
        return listProfile;
    }
    public Optional<Profile> verProfileId(Long id){
        Optional<Profile> profileId = profileRepository.findById(id);
        return profileId;
    }
    public void eliminarProfile(Long id){
        profileRepository.deleteById(id);
    }
    public void modificarProfile(Long id, Profile modificadoProfile){
        Optional<Profile> profileInDB = profileRepository.findById(id);
        if(profileInDB.isEmpty()){
            Profile profileInSave = modificadoProfile;
            profileInSave.setId(profileInDB.get().getId());
            profileRepository.save(profileInSave);
        }
    }


}
