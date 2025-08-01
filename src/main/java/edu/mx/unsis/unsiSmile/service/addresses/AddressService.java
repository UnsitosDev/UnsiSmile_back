package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.repository.addresses.IHousingRepository;
import edu.mx.unsis.unsiSmile.repository.addresses.IStreetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.model.addresses.HousingModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IAddressRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final IAddressRepository addressRepository;
    private final IHousingRepository housingRepository;
    private final IStreetRepository streetRepository;
    private final AddressMapper addressMapper;
    private final HousingService housingService;
    private final StreetService streetService;

    @Transactional
    public AddressResponse createAddress(@NonNull AddressRequest addressRequest) {
        try {
            Assert.notNull(addressRequest, "AddressRequest cannot be null");

            AddressModel savedAddress = this.findOrCreateAddress(addressRequest);

            return addressMapper.toDto(savedAddress);
        } catch (AppException e) {
            throw e;
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
    public List<AddressResponse> getAddressesByHousing(@NonNull String housingId) {
        try {
            HousingModel housing = housingRepository.findById(housingId)
                    .orElseThrow(() -> new AppException("Housing not found with ID: " + housingId, HttpStatus.NOT_FOUND));
            List<AddressModel> addressModels = addressRepository.findByHousing(housing);
            return addressModels.stream()
                    .map(addressMapper::toDto)
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch addresses by housing", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByStreet(@NonNull Long streetId) {
        try {
            StreetModel street = streetRepository.findById(streetId)
                    .orElseThrow(() -> new AppException("Street not found with ID: " + streetId, HttpStatus.NOT_FOUND));

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

    @Transactional
    public AddressModel findOrCreateAddress(@NonNull AddressRequest addressRequest) {
        try {
            Assert.notNull(addressRequest, ResponseMessages.ADDRESS_REQUEST_NULL);

            String streetNumber = addressRequest.getStreetNumber();
            String interiorNumber = addressRequest.getInteriorNumber();
            String housingId = addressRequest.getHousing().getIdHousing();
            Long streetId = addressRequest.getStreet().getIdStreet();

            AddressModel existingAddress = addressRepository.findByStreetDataRequest(
                    streetNumber, interiorNumber, housingId, streetId
            ).orElse(null);

            if (existingAddress != null) {
                return existingAddress;
            }

            HousingModel housing = housingService.findHousingById(addressRequest.getHousing().getIdHousing());
            StreetModel street = streetService.findOrCreateStreet(addressRequest.getStreet());

            AddressModel addressModel = addressMapper.toModel(addressRequest);
            addressModel.setHousing(housing);
            addressModel.setStreet(street);

            return addressRepository.save(addressModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ADDRESS_CREATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
