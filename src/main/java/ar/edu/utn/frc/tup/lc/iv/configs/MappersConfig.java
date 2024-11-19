package ar.edu.utn.frc.tup.lc.iv.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper and ObjectMapper configuration class.
 */
@Configuration
public class MappersConfig {

    /**
     * The ModelMapper bean by default.
     *
     * @return the ModelMapper by default.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Configure specific mapping for AccessEntity to AccessDTO
        PropertyMap<AccessEntity, AccessDTO> accessMapping = new PropertyMap<AccessEntity, AccessDTO>() {
            @Override
            protected void configure() {
                // Explicitly map the authId field
                map().setAuthId(source.getAuth().getAuthId());
                
                // Skip other ambiguous fields if any
                skip().setAuthDocNumber(null);
                skip().setAuthDocType(null);
                
                // Map other fields that need explicit mapping
                map().setVehicleType(source.getVehicleType());
                map().setVehicleReg(source.getVehicleReg());
                map().setVehicleDescription(source.getVehicleDescription());
                map().setComments(source.getComments());
                map().setAction(source.getAction());
                map().setActionDate(source.getActionDate());
                map().setIsLate(source.getIsLate());
            }
        };
        
        modelMapper.addMappings(accessMapping);
        
        return modelMapper;
    }

    /**
     * The ModelMapper bean to merge objects.
     *
     * @return the ModelMapper to use in updates.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    /**
     * The ObjectMapper bean.
     *
     * @return the ObjectMapper with JavaTimeModule included.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}
