package com.oriun.oriun.Services;

import com.oriun.oriun.Models.AlqElem;
import com.oriun.oriun.Models.AlquilerModel;
import com.oriun.oriun.Repositories.AlquilerRepository;
import com.oriun.oriun.Repositories.ElementRepository;
import com.oriun.oriun.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AlquilerService {
    @Autowired
    AlquilerRepository alquilerRepository;
    @Autowired
    ElementRepository elementRepository;
    @Autowired
    UserRepository userRepository;

    public List<AlquilerModel> getAlquileres(){
        return alquilerRepository.findAll();
    }
    public ResponseEntity registraralquiler(AlquilerModel am){
        if(userRepository.existsById(am.getUSER_NAME())){
            if(elementRepository.existsById(am.getID_ELEMENT())){
                Date sqlDate = new Date(am.getRENT_DATE().getTime()+(86400000));//60000*60*24
                am.setRENT_DATE(sqlDate);
                /*return new ResponseEntity<>(ram.getUSER_NAME()+" reservo elemento con id: "+Integer.toString(ram.getID_ELEMENT())+" , para el dia: "+ ram.getRENT_DATE().toString(),
                        HttpStatus.OK );*/
                if(alquilerRepository.LAfechaElemento(am.getID_ELEMENT(), am.getRENT_DATE()).size()==0){
                    return new ResponseEntity<>(alquilerRepository.save(am),
                            HttpStatus.OK );
                }
                else{
                    return new ResponseEntity<>("Elemento reservado para esa fecha",
                            HttpStatus.CONFLICT );
                }
            }
            else{
                return new ResponseEntity<>("Id de elemento no valido",
                        HttpStatus.BAD_REQUEST );
            }
        }
        else{
            return new ResponseEntity<>("Usuario no valido",
                    HttpStatus.BAD_REQUEST );
        }
    }
    public boolean deletealquiler(int id_al){
        if(alquilerRepository.existsById(id_al)){
            alquilerRepository.deleteById(id_al);
            return true;
        }
        else return false;
    }
    public List<AlqElem> LAlquilerplus(String nloc){
        return alquilerRepository.LAlquilerplus(nloc);
    }
    public List<AlquilerModel> LAfechaElemento(int ide, Date rdate){
        return alquilerRepository.LAfechaElemento(ide,rdate);
    }
    public List<AlqElem> LAbyUser(String usern){
        return alquilerRepository.LAbyUser(usern);
    }
    public List<AlquilerModel> LAbyDate(Date rdate){
        return alquilerRepository.LAbyDate(rdate);
    }
    public List<AlquilerModel> LAbyElemento(int ide){
        return alquilerRepository.LAbyElemento(ide);
    }
    
    public boolean IsUsed(Date rdate,Time itime, int interv, int id_elem){
        LocalTime localtime = itime.toLocalTime();
        localtime = localtime.plusMinutes(interv);
        Time ftime = Time.valueOf( localtime );
        return !(alquilerRepository.IsRented(rdate, itime, ftime, id_elem).size()==0);
    }
    public AlquilerModel saveAlquiler(AlquilerModel user_alquiler){
        return alquilerRepository.save(user_alquiler);
    }
    
    
}
