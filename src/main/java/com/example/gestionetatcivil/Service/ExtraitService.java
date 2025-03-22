package com.example.gestionetatcivil.Service;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.gestionetatcivil.Dto.AvisDto;
import com.example.gestionetatcivil.Dto.ExtraitDto;
import com.example.gestionetatcivil.Entities.Account;
import com.example.gestionetatcivil.Entities.Avis;
import com.example.gestionetatcivil.Entities.ExtraitNaissance;
import com.example.gestionetatcivil.Entities.Paiement;
import com.example.gestionetatcivil.MapperDto.ExtraitMapperDto;
import com.example.gestionetatcivil.Repositories.AvisRepository;
import com.example.gestionetatcivil.Repositories.ExtraitRepository;
import com.example.gestionetatcivil.Repositories.PaiementRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;



@Service
@Transactional
@Slf4j
public class ExtraitService {


    private final ExtraitRepository birthDocRepository;
    private final PaiementRepository paiementRepository;
    private final AvisRepository avisDocRepository;
    private  Optional<ExtraitNaissance> documentLu;
    private final ExtraitMapperDto extraitMapperDto;


    public ExtraitService(ExtraitMapperDto extraitMapperDto, ExtraitRepository birthDocRepository, PaiementRepository paiementRepository, AvisRepository avisDocRepository) {
        this.birthDocRepository = birthDocRepository;
        this.paiementRepository = paiementRepository;
        this.avisDocRepository = avisDocRepository;
        this.extraitMapperDto = extraitMapperDto;
    }



    //service paiement
    public boolean paiementDoc(Paiement paiement)  {
       /* String username = paiement.getBirth
        String email = paiement.getBirthDocument().getSubscriber().getEmail();
        String phone = paiement.getBirthDocument().getSubscriber().getPhone();
        Long number = (long) paiement.getPaiementAmount();*/
        paiement.setNumeroPaiment(paiement.getBirthDocument().getNumeroExtrait());
        paiement.setActivated(true);
        if (paiement.getActivated()) {
            paiement.setBirthDocument(paiement.getBirthDocument());
            paiement.getBirthDocument().setPactive(true);
            this.paiementRepository.save(paiement);
            this.birthDocRepository.save(paiement.getBirthDocument());
            log.info("PAIEMENT EFFECTUE");
        }
        else { log.info("PAIEMENT AMOUNT NOT AVAILABLE");}

        return paiement.getBirthDocument().getPactive();
    }
/*******************************************************************************************************/
    //lire tous les extraits

    
    public Stream<ExtraitDto> liretousdoc(String numero) {
        boolean notEmpty = Strings.isNotEmpty(numero);
       Optional<ExtraitNaissance> extrait =  birthDocRepository.findByNumeroExtrait(numero);

        if(notEmpty){
           this.documentLu= extrait;
            return  (Stream<ExtraitDto>) extrait.stream().map(extraitMapperDto);
            
        }

            return  (Stream<ExtraitDto>) this.birthDocRepository.findAll().stream().map(extraitMapperDto);
    }

/*********************************************************************************************************/
    // lire un extrait
    public Stream<ExtraitDto> lireundocument(Long id) {
        Stream<ExtraitDto> extraitDtoStream = this.birthDocRepository.findById(id).stream().map(extraitMapperDto);
        this.documentLu= this.birthDocRepository.findById(id);
        return extraitDtoStream;

    }
/************************************************************************************************************/

    //creation d'un document(extrait de naissance)
    public void creationdocument(ExtraitNaissance birth)  {
        Account sub= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        birth.setSubscriber(sub);
        this.birthDocRepository.save(birth);
    }


/************************************************************************************************************/
    // suppression des paiements et extraits de naissance pas valides
   @Scheduled(cron = "@weekly")
    public void suppextrait(){
        Iterable<Paiement> paiements= this.paiementRepository.findAll();
       for(Paiement E: paiements){
           if( !E.getActivated()){
               this.paiementRepository.delete(E);
               if(!E.getActivated()){this.birthDocRepository.delete(E.getBirthDocument());}

            log.info("DELETE PAIEMENT IVALIDE: "+E.getNumeroPaiment());
            log.info("DELETE EXTRAIT NON VALIDE: "+E.getBirthDocument());}

        }

    }


/*****************************************************************************************************************/
    //enregistrement d'un avis pour le document
    public void avisdocument(Avis noted) {
      // Account  sub= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      //  ExtraitNaissance bb = (ExtraitNaissance) ( SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("DOCUMENT LU :");
        noted.setBirthDoc(this.documentLu.get());

        this.avisDocRepository.save(noted);
    }

/**********************************************************************************************************************/

    //lire les avis
    public Stream<AvisDto> readavis(){
        return this.avisDocRepository.findAll().stream().map(avis ->new AvisDto(avis.getId(),
                avis.getNote(), avis.getValid(),avis.getDate()));
    }


/**********************************************************************************************************************/

    //lire un avis
    public Stream<AvisDto> readunavis(Long id)  {
        return this.avisDocRepository.findById(id).stream().map(avis -> (new AvisDto(avis.getId(),
                avis.getNote(), avis.getValid(),avis.getDate())));
    }

    //retrouver un extrait

}




