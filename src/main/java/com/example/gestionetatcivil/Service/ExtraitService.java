package com.example.birthadvance.Service;

import com.example.birthadvance.Dto.AvisDto;
import com.example.birthadvance.Dto.ExtraitDto;
import com.example.birthadvance.Entities.Avis;
import com.example.birthadvance.Entities.ExtraitNaissance;
import com.example.birthadvance.Entities.Paiement;
import com.example.birthadvance.Entities.Account;
import com.example.birthadvance.MapperDto.ExtraitMapperDto;
import com.example.birthadvance.Repositories.AvisRepository;
import com.example.birthadvance.Repositories.ExtraitRepository;
import com.example.birthadvance.Repositories.PaiementRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;



@Service
@Transactional
@Slf4j
public class ExtraitService {


    private final ExtraitRepository birthDocRepository;
    private final PaiementRepository paiementRepository;
    private final AvisRepository avisDocRepository;
    private Optional<ExtraitNaissance> documentLu;
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
    public List<ExtraitDto> liretousdoc(String numeroExtrait) {
        boolean notEmpty = Strings.isNotEmpty(numeroExtrait);

        if(notEmpty){
            return ( Stream.of(this.birthDocRepository.
                    findByNumeroExtrait(numeroExtrait)).map(extraitMapperDto).toList()); }

            return ( this.birthDocRepository.findAll()).stream().map(extraitMapperDto).toList();
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
        //Subscriber sub= (Subscriber) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //BirthDoc bb = (BirthDoc) ( SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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


}



