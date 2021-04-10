package com.oriun.oriun.Services;
import java.util.ArrayList;
import java.util.Optional;
import javax.transaction.Transactional;

import com.oriun.oriun.Models.SportModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oriun.oriun.Repositories.SportRepository;

@Service
@Transactional
public class SportService {
    @Autowired
    
    SportRepository sportRepository;

    public ArrayList<SportModel> getSports(){
        return (ArrayList<SportModel>)sportRepository.findAll();
    }
    public SportModel saveSport(SportModel sport){
        return sportRepository.save(sport);
    }
    public SportModel updateSport(String oldsport,String newsport) {
        Optional<SportModel> old = sportRepository.findById(oldsport);
        if(old.isPresent()){
            sportRepository.delete(old.get());
            SportModel newsp= new SportModel(newsport);
            SportModel updatedSport = sportRepository.save(newsp);
            return updatedSport;
        }else{
            return old.get();
        }
    }
    public SportModel deleteSport(SportModel sport){
        sportRepository.delete(sport);
        return sport;
    }
}
