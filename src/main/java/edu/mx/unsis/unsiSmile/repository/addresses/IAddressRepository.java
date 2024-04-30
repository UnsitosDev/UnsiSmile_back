package edu.mx.unsis.unsiSmile.repository.addresses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.HousingModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;

@Repository
public interface IAddressRepository extends JpaRepository<AddressModel, Long> {

    Optional<AddressModel> findByIdAddress(Long idAddress);

    List<AddressModel> findByStreetNumber(String streetNumber);

    List<AddressModel> findByInteriorNumber(String interiorNumber);

    List<AddressModel> findByHousing(HousingModel housing);

    List<AddressModel> findByStreet(StreetModel street);

}