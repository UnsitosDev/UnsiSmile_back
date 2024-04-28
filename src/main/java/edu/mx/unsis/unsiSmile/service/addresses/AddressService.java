package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.model.AddressModel;
import edu.mx.unsis.unsiSmile.model.HousingModel;
import edu.mx.unsis.unsiSmile.model.StreetModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IAddressRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final IAddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional(readOnly = true)
    public AddressResponse getAddressById(@NonNull Long idAddress) {
        try {
            AddressModel addressModel = addressRepository.findByIdAddress(idAddress)
                    .orElseThrow(() -> new AppException("Address not found with ID: " + idAddress, HttpStatus.NOT_FOUND));

            return addressMapper.toDto(addressModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch address by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByStreetNumber(@NonNull String streetNumber) {
        try {
            List<AddressModel> addressModels = addressRepository.findByStreetNumber(streetNumber);
            return addressModels.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch addresses by street number", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByInteriorNumber(@NonNull String interiorNumber) {
        try {
            List<AddressModel> addressModels = addressRepository.findByInteriorNumber(interiorNumber);
            return addressModels.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch addresses by interior number", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByHousing(@NonNull HousingModel housing) {
        try {
            List<AddressModel> addressModels = addressRepository.findByHousing(housing);
            return addressModels.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch addresses by housing", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByStreet(@NonNull StreetModel street) {
        try {
            List<AddressModel> addressModels = addressRepository.findByStreet(street);
            return addressModels.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch addresses by street", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAllAddresses() {
        try {
            List<AddressModel> allAddresses = addressRepository.findAll();
            return allAddresses.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all addresses", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
