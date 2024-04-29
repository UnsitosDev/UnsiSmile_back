package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
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

    @Transactional
    public AddressResponse createAddress(@NonNull AddressRequest addressRequest) {
        try {
            Assert.notNull(addressRequest, "AddressRequest cannot be null");

            // Map the DTO request to the entity
            AddressModel addressModel = addressMapper.toEntity(addressRequest);

            // Save the entity to the database
            AddressModel savedAddress = addressRepository.save(addressModel);

            // Map the saved entity back to a response DTO
            return addressMapper.toDto(savedAddress);
        } catch (Exception ex) {
            throw new AppException("Failed to create address", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

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

    @Transactional
    public AddressResponse updateAddress(@NonNull Long idAddress, @NonNull AddressRequest updatedAddressRequest) {
        try {
            Assert.notNull(updatedAddressRequest, "Updated AddressRequest cannot be null");

            // Find the address in the database
            AddressModel addressModel = addressRepository.findByIdAddress(idAddress)
                    .orElseThrow(() -> new AppException("Address not found with ID: " + idAddress, HttpStatus.NOT_FOUND));

            // Update the address entity with the new data
            addressMapper.updateEntity(updatedAddressRequest, addressModel);

            // Save the updated entity
            AddressModel updatedAddress = addressRepository.save(addressModel);

            // Map the updated entity back to a response DTO
            return addressMapper.toDto(updatedAddress);
        } catch (Exception ex) {
            throw new AppException("Failed to update address", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteAddressById(@NonNull Long idAddress) {
        try {
            // Check if the address exists
            if (!addressRepository.existsById(idAddress)) {
                throw new AppException("Address not found with ID: " + idAddress, HttpStatus.NOT_FOUND);
            }
            addressRepository.deleteById(idAddress);
        } catch (Exception ex) {
            throw new AppException("Failed to delete address", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
